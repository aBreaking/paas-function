package com.sitech.paas.elk.api.low;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author liwei_paas
 * @date 2019/9/5
 */
public class EsLowClient {

    public RestClient getSimpleClient(){
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http"));
        return restClientBuilder.build();
    }

    public RestClient getClientWithAuthentication(){
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
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
        return restClientBuilder.build();
    }

    public void close(RestClient restClient) throws IOException {
        restClient.close();
    }

    public void doQuery() throws Exception {
        //请求报文
        String json = "{\"query\":{\"term\":{ \"srvName\":\"sDynSvc\"}}}";
        RestClient restClient = getClientWithAuthentication();
        //指定请求方式，请求uri：即索引、类型等
        Request request = new Request("post", "/esb-srvlog-2019-07-21/doc/_search");
        request.setEntity(new NStringEntity(json));
        request.setOptions(RequestOptions.DEFAULT);
        Response response = restClient.performRequest(request);
        //返回的数据保存在HttpEntity里面，以流的形式，json的格式
        HttpEntity responseEntity = response.getEntity();
        String s = EntityUtils.toString(responseEntity);
        System.out.println(s);
        restClient.close();
    }



    public static void main(String[] args) throws Exception {
        EsLowClient esLowClient = new EsLowClient();
        esLowClient.doQuery();
    }
}
