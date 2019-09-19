package com.sitech.paas.elk.dao.impl;

import com.sitech.paas.elk.dao.EsQuery;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author liwei_paas
 * @date 2019/9/17
 */
public class BaseEsQuery implements EsQuery {

    private RestHighLevelClient client;

    private String[] indices;

    protected SearchSourceBuilder searchSourceBuilder ;

    protected transient BoolQueryBuilder boolQuery;

    public BaseEsQuery(RestHighLevelClient client,String...indices){
        this();
        this.client = client;
        this.indices = indices;

    }

    public BaseEsQuery(){
        this.searchSourceBuilder =  new SearchSourceBuilder();
        boolQuery = QueryBuilders.boolQuery();
    }

    @Override
    public SearchHits queryAll() {
        return doQuery().getHits();
    }

    @Override
    public Aggregation queryAggregations( Map<String, String> terms, List<String> aggKeys) {
        //TODO
        return null;
    }

    @Override
    public SearchHits queryTerms(Map<String, String> terms) {
        for (String key : terms.keySet()){
            boolQuery.must(QueryBuilders.termQuery(key,terms.get(key)));
        }
        SearchResponse response = doQuery();
        return response.getHits();
    }

    protected SearchResponse doQuery(){
        try {
            searchSourceBuilder.query(boolQuery);
            SearchRequest searchRequest = indices.length==0?new SearchRequest():new SearchRequest(indices);
            searchRequest.source(searchSourceBuilder);
            System.out.println(Arrays.toString(indices));
            System.out.println(searchSourceBuilder.toString());
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(response.getTook());
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public BaseEsQuery addEsbHosts(String[] hosts) {
        boolQuery.must(QueryBuilders.termsQuery("fields._clientip",hosts));
        return this;
    }

    public BaseEsQuery addRangeTime(Date begin,Date end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_DATE_FORMAT);
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("@timestamp");
        rangeQuery.gte(dateFormat.format(begin));
        rangeQuery.lte(dateFormat.format(end));
        boolQuery.must(rangeQuery);
        return this;
    }

    public BaseEsQuery addRangeTime(Date begin) {
        addRangeTime(begin,new Date());
        return this;
    }

    public BaseEsQuery addFromAndSize(int from,int size){
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        return this;
    }

    public BaseEsQuery addFromAndSize(int size){
        return addFromAndSize(0,size);
    }

    public RestHighLevelClient getClient() {
        return client;
    }

    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }

    public String[] getIndices() {
        return indices;
    }

    public void setIndices(String[] indices) {
        this.indices = indices;
    }
}
