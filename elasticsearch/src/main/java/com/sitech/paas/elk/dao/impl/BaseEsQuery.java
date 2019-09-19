package com.sitech.paas.elk.dao.impl;

import com.sitech.paas.elk.dao.EsQuery;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author liwei_paas
 * @date 2019/9/17
 */
public class BaseEsQuery implements EsQuery {
    protected RestHighLevelClient client;

    protected SearchRequest searchRequest;

    protected NecessaryQueryCondition necessaryQueryCondition ;


    public BaseEsQuery(RestHighLevelClient client,String...indices){
        this.client = client;
        this.searchRequest = indices.length==0?new SearchRequest():new SearchRequest(indices);
        this.necessaryQueryCondition = new NecessaryQueryCondition();
    }

    @Override
    public Aggregation queryTermWithAggregations( Map<String, String> terms, List<String> aggKeys) {
        //TODO
        return null;
    }

    @Override
    public SearchHits queryTerms(Map<String, String> terms) {
        SearchSourceBuilder searchSourceBuilder = searchSourceBuilder();

        BoolQueryBuilder boolQuery = (BoolQueryBuilder) searchSourceBuilder.query();
        for (String key : terms.keySet()){
            boolQuery.must(QueryBuilders.termQuery(key,terms.get(key)));
        }
        searchSourceBuilder.query(boolQuery);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = doQuery(client, searchRequest);
        return response.getHits();
    }

    protected SearchSourceBuilder searchSourceBuilder(){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        String[] esbHostIp = necessaryQueryCondition.getEsbHostIp();
        //指定查询esb池
        if (esbHostIp!=null&&esbHostIp.length>0){
            boolQuery.must(QueryBuilders.termsQuery("fields._clientip",esbHostIp));
        }
        //时间范围
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_DATE_FORMAT);
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("@timestamp");
        Date beginTime = necessaryQueryCondition.getBeginTime();
        Date endTime = necessaryQueryCondition.getEndTime();
        if (beginTime!=null){
            rangeQuery.gte(dateFormat.format(beginTime));
        }
        if (endTime!=null){
            rangeQuery.lt(dateFormat.format(endTime));
        }
        if (rangeQuery.includeLower() || rangeQuery.includeUpper()){
            boolQuery.must(rangeQuery);
        }
        searchSourceBuilder.query(boolQuery);

        return searchSourceBuilder;
    }

    public BaseEsQuery addNecessaryEsbHosts(String[] hosts) {
        necessaryQueryCondition.setEsbHostIp(hosts);
        return this;
    }

    public BaseEsQuery addNecessaryBeginTime(Date begin) {
        necessaryQueryCondition.setBeginTime(begin);
        return this;
    }
    public BaseEsQuery addNecessaryEndTime(Date end) {
        necessaryQueryCondition.setEndTime(end);
        return this;
    }



    /**
     * 几个查询的必要条件
     * esb的查询里面，一般来说，都得需要指定查询的esb的那个池、那个时间段
     *
     */
    static class  NecessaryQueryCondition{
        Date beginTime;
        Date endTime;
        String[] esbHostIp;

        public Date getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Date beginTime) {
            this.beginTime = beginTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public String[] getEsbHostIp() {
            return esbHostIp;
        }

        public void setEsbHostIp(String[] esbHostIp) {
            this.esbHostIp = esbHostIp;
        }
    }
}
