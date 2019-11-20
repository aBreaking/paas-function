package com.sitech.paas.elk.dao.impl;

import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author liwei_paas
 * @date 2019/9/17
 */
public class EsbwsEsQuery extends BaseEsQuery {

    private static Logger logger = LoggerFactory.getLogger(BaseEsQuery.class);

    public EsbwsEsQuery(RestHighLevelClient client, String... indices) {
        super(client, indices);
    }

    public EsbwsEsQuery() {
        super();
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
        for (String key : regexpValuesInInfo){
            //esbws的info算是个bug吧，必须得将字符串全部小写再匹配。
            String regexpKey = ".*"+key.toLowerCase()+".*";
            boolQuery.must(QueryBuilders.regexpQuery("info",regexpKey));
        }
        SearchResponse response = doQuery();
        return response.getHits();
    }

    /**
     * scroll查询，默认查询出所有的数据。
     * 这个方法是非常耗时间的，所以，你应该考虑将其异步
     * @param regexpValuesInInfo 匹配info里面的某些内容
     * @param searchHitResolver 如何去解析每个SearchHit
     */
    public List statisInfo(List<String> regexpValuesInInfo,SearchHitResolver searchHitResolver){
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        SearchRequest searchRequest = getIndices().length==0?new SearchRequest():new SearchRequest(getIndices());

        for (String key : regexpValuesInInfo){
            //esbws的info有进行分词，必须得将字符串全部小写再匹配。
            String regexpKey = ".*"+key.toLowerCase()+".*";
            boolQuery.must(QueryBuilders.regexpQuery("info",regexpKey));
        }
        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.size(100);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(scroll);

        RestHighLevelClient client = getClient();
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("esb ws scroll查询错误",e);
            closeClient();
            return Collections.emptyList();
        }
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        List hitsList = new ArrayList();
        int i=0;
        while (searchHits != null && searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);

            scrollRequest.scroll(scroll);
            try{
                searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            }catch (IOException e){
                logger.warn("scroll查询出现{}异常，解析到此为止！",e.getMessage());
                break;
            }
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();
            for (SearchHit searchHit : searchHits){
                searchHitResolver.resolve(searchHit);
                i++;
            }
        }

        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = null;
        try {
            clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.warn("clear scroll查询异常",e);
        }
        if (!clearScrollResponse.isSucceeded()){
            logger.warn("Clear the scroll context once the scroll is NOT completed");
        }
        closeClient();
        return hitsList;
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
