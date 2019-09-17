package com.sitech.paas.elk.dao;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 考虑这里实现两个共同的方法，聚合查询与条件查询
 * @author liwei_paas
 * @date 2019/9/4
 */
public interface EsQuery {

    /**
     * es上@timestamp字段时间格式
     */
    String TIMESTAMP_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    Aggregation queryTermWithAggregations(Map<String,String> terms, List<String> aggKeys);

    SearchHits queryTerms(Map<String,String> terms);

    default SearchResponse doQuery(RestHighLevelClient client, SearchRequest request){
        try {
            System.out.println(request.source().toString());
            return client.search(request,RequestOptions.DEFAULT);
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
}