package com.sitech.paas.elk.dao.impl;

import com.sitech.paas.elk.domain.BaseEsbSrvLog;
import com.sitech.paas.elk.domain.BaseEsbSrvLogAgg;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EsQueryImpl {

    RestHighLevelClient client;

    String index;

    public EsQueryImpl(RestHighLevelClient client,Date date){
        this.client = client;
        this.index = getIndex(date);
    }

    public Object querySrvRetAgg() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder query = QueryBuilders.boolQuery();
        /*query.must(QueryBuilders.rangeQuery("@timestamp").gte(begin).lt(end));*/
        query.mustNot(QueryBuilders.regexpQuery("endPoint",".*TEMPLATE.*"));
        searchSourceBuilder.query(query);

        //TODO 这里的聚合应该放在一个数组里面去
        TermsAggregationBuilder srvNameAgg = AggregationBuilders.terms("srvName").field("srvName");
        TermsAggregationBuilder retCodeAgg = AggregationBuilders.terms("retCode").field("retCode");
        TermsAggregationBuilder retMsgAgg = AggregationBuilders.terms("retMsg").field("retMsg");
        srvNameAgg.subAggregation(retCodeAgg.subAggregation(retMsgAgg));
        searchSourceBuilder.aggregation(srvNameAgg);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest,RequestOptions.DEFAULT);
        Aggregations aggregations = response.getAggregations();

        List<BaseEsbSrvLogAgg> agg = parseAgg(aggregations);
        return agg;
    }


    public Object querySrvMsg(Map<String,String> map) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder query = QueryBuilders.boolQuery();
        for (String msg : map.keySet()){
            query.should(QueryBuilders.regexpQuery("retMsg",msg));
        }

        searchSourceBuilder.query(query);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        List<BaseEsbSrvLog> list = new ArrayList();
        for (SearchHit hit : hits){
            BaseEsbSrvLog srvLog = new BaseEsbSrvLog();
            Map<String, Object> sourceMap = hit.getSourceAsMap();
            srvLog.setSrvName(sourceMap.get("srvName").toString());
            srvLog.setRetCode(sourceMap.get("retCode").toString());
            String retMsg = matchValue(map,sourceMap.get("retMsg").toString());
            srvLog.setRetMsg(retMsg);
            list.add(srvLog);
        }

        return list;
    }

    public Object queryCountSql() {
        return null;
    }

    private String getIndex(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String prefix = "esb-srvlog-";
        String time = dateFormat.format(date);
        return prefix + time;
    }
    /**
     * TODO 这个日后整成递归把
     * @param aggregations
     */
    private List<BaseEsbSrvLogAgg> parseAgg(Aggregations aggregations){
        List<BaseEsbSrvLogAgg> list = new ArrayList();
        Terms srvNameAggResult = aggregations.get("srvName");
        List<? extends Terms.Bucket> buckets = srvNameAggResult.getBuckets();
        for (Terms.Bucket bucket : buckets){
            String srvNameValue = bucket.getKeyAsString();
            Terms retCodeResult = bucket.getAggregations().get("retCode");
            for (Terms.Bucket retCode : retCodeResult.getBuckets()){
                String retCodeValue = retCode.getKeyAsString();
                Terms retMsgResult = bucket.getAggregations().get("retMsg");
                for (Terms.Bucket retMsg : retMsgResult.getBuckets()){
                    String retMsgValue = retMsg.getKeyAsString();
                    long docCount = retMsg.getDocCount();
                    BaseEsbSrvLogAgg srvLogAgg = new BaseEsbSrvLogAgg();
                    srvLogAgg.setSrvName(srvNameValue);
                    srvLogAgg.setRetCode(retCodeValue);
                    srvLogAgg.setRetMsg(retMsgValue);
                    srvLogAgg.setCount(docCount);
                    list.add(srvLogAgg);
                }
            }
        }
        return list;
    }

    private String matchValue(Map<String,String> map,String key){
        for (String s : map.keySet()){
            if (s.indexOf(key)!=-1){
                return map.get(s);
            }
        }
        return null;
    }
}
