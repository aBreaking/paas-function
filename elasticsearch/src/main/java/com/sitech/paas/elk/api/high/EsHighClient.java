package com.sitech.paas.elk.api.high;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 *
 * @author liwei_paas
 * @date 2019/9/5
 */
public class EsHighClient {

    public RestHighLevelClient getRestHighLevelClient(){
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost("172.21.11.65", 9200, "http"),
                new HttpHost("localhost", 9201, "http"));
        //elasticsearch验证的用户名密码。
        String username = "admin";
        String password = "123456";
        byte[] tokenByte = Base64.encodeBase64((username+":"+password).getBytes());
        //将加密的信息转换为string
        String tokenStr = new String(tokenByte);
        String token = "Basic "+tokenStr;
        Header[] headers = new Header[]{
                new BasicHeader("Content-type","application/json;charset=utf-8"),
                new BasicHeader("Authorization",token)
        };
        restClientBuilder.setDefaultHeaders(headers);
        return new RestHighLevelClient(restClientBuilder);
    }

    public void close(RestHighLevelClient highLevelClient) throws IOException {
        highLevelClient.close();
    }

    public void doQuery() throws IOException {
        RestHighLevelClient client = getRestHighLevelClient();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String index = "";
        SearchRequest searchRequest = new SearchRequest(index);


        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("srvName","sDynSvc"));
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("@timestamp");
        rangeQuery.gte("2019-07-21T12:00:00.473Z");
        rangeQuery.lt("2019-07-21T13:23:00.473Z");
        boolQuery.mustNot(rangeQuery);
        boolQuery.should(QueryBuilders.regexpQuery("retMsg",".*ok.*"));
        boolQuery.should(QueryBuilders.termQuery("esbRetCode","0"));

        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.from(2);
        searchSourceBuilder.size(5);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.totalHits);
        close(client);
    }
    public void agg(SearchSourceBuilder searchSourceBuilder){
        TermsAggregationBuilder srvNameAgg = AggregationBuilders.terms("componentAgg").field("COMPONENT");
        TermsAggregationBuilder retCodeAgg = AggregationBuilders.terms("subComponentAgg").field("SUB_COMPONENT");
        TermsAggregationBuilder retMsgAgg = AggregationBuilders.terms("indicatorIdAgg").field("indicatorId");
        srvNameAgg.subAggregation(retCodeAgg.subAggregation(retMsgAgg));

    }

    public static void main(String[] args) throws IOException {
        EsHighClient client = new EsHighClient();
        client.doQuery();
    }

}
