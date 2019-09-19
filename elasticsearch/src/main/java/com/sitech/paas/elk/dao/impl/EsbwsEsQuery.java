package com.sitech.paas.elk.dao.impl;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import java.util.List;

/**
 *
 * @author liwei_paas
 * @date 2019/9/17
 */
public class EsbwsEsQuery extends BaseEsQuery {

    public EsbwsEsQuery(RestHighLevelClient client, String... indices) {
        super(client, indices);
    }

    /**
     * 正则匹配 info
     * 注意的是：由于info得类型是text，es会将文本进行分词，
     *      所以传入的要匹配的值必须是关键字，也就是说，不要有特殊符号、空格等。  如需要匹配 "-040107",那么只能传入"040107"
     *      字符串需要全是小写，这里已经默认处理了。
     * @param regexpValuesInInfo
     * @return
     */
    public SearchHits queryInfoByRegexp(List<String> regexpValuesInInfo){
        SearchSourceBuilder searchSourceBuilder = searchSourceBuilder();
        BoolQueryBuilder boolQuery = (BoolQueryBuilder) searchSourceBuilder.query();
        for (String key : regexpValuesInInfo){
            //esbws的info算是个bug吧，必须得将字符串全部小写再匹配。
            String regexpKey = ".*"+key.toLowerCase()+".*";
            boolQuery.must(QueryBuilders.regexpQuery("info",regexpKey));
        }
        searchSourceBuilder.query(boolQuery);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = doQuery(client, searchRequest);
        return response.getHits();
    }

    /**
     * 获取info里面的概要信息
     * @return
     */
    public static String parseInfo2Summary(String info,String srvName){
        if (!info.startsWith("ERROR")){
            return info;
        }
        String summary = info.trim();
        int indexOfDm = summary.indexOf("DETAIL_MSG");
        if (indexOfDm==-1){
            //直接取首行作为概要
            summary = summary.substring(0, summary.indexOf("\t"));
            summary = summary.substring(summary.indexOf(srvName));
        }else{
            summary = summary.substring(indexOfDm);
            summary = summary.substring(summary.indexOf(srvName));
            summary = summary.substring(0,summary.indexOf("\"}}"));
        }
        return summary;
    }

}
