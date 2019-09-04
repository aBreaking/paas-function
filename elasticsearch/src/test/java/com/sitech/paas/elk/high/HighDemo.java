package com.sitech.paas.elk.high;


import com.sitech.paas.elk.api.EsClient;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HighDemo {

    RestHighLevelClient client;

    public HighDemo(){
        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    @Test
    public void test01() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        SearchHit hit = hits.getAt(0);
        Map<String, Object> map = hit.getSourceAsMap();
        System.out.println(map);
    }

    @Test
    public void testAggregation() throws Exception{
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        TermsAggregationBuilder srvNameAgg = AggregationBuilders.terms("srvNameAgg").field("srvName");
        srvNameAgg.subAggregation(AggregationBuilders.terms("retCodeAgg").field("retCode"));
        searchSourceBuilder.aggregation(srvNameAgg);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest,RequestOptions.DEFAULT);
        Aggregations aggregations = response.getAggregations();
        Terms srvNameAggResult = aggregations.get("srvNameAgg");
        List<? extends Terms.Bucket> buckets = srvNameAggResult.getBuckets();
        for (Terms.Bucket bucket : buckets){
            String srvName = bucket.getKeyAsString();
            long docCount = bucket.getDocCount();
            Aggregation retCodeAggResult = bucket.getAggregations().get("retCodeAgg");
        }
    }

    @Test
    public void testBool() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder query = QueryBuilders.boolQuery();
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("@timestamp");
        rangeQuery.gte("2019-07-21T00:23:47.473Z").lt("2019-07-21T24:30:47.473Z");
        query.must(rangeQuery);
        query.mustNot(QueryBuilders.termsQuery("retMsg","OK","ok!"));
        query.mustNot(QueryBuilders.regexpQuery("endPoint",".*TEMPLATE.*"));

        TermsAggregationBuilder srvNameAgg = AggregationBuilders.terms("srvName").field("srvName");
        TermsAggregationBuilder retCodeAgg = AggregationBuilders.terms("retCode").field("retCode");
        TermsAggregationBuilder retMsgAgg = AggregationBuilders.terms("retMsg").field("retMsg");
        srvNameAgg.subAggregation(retCodeAgg.subAggregation(retMsgAgg));
        searchSourceBuilder.aggregation(srvNameAgg);

        searchSourceBuilder.query(query);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest,RequestOptions.DEFAULT);

        Aggregations aggregations = response.getAggregations();
        Terms srvNameAggResult = aggregations.get("srvName");
        List<? extends Terms.Bucket> buckets = srvNameAggResult.getBuckets();
        for (Terms.Bucket bucket : buckets){
            String srvName = bucket.getKeyAsString();
            long docCount = bucket.getDocCount();
            Terms retCodeAggResult = bucket.getAggregations().get("retCode");
            List<? extends Terms.Bucket> list = retCodeAggResult.getBuckets();
        }
    }
}

