package com.sitech.paas.elk.api;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;

/**
 *
 * @author liwei_paas
 * @date 2019/9/2
 */
public class EsClient {

    static RestClient restClient;

    final static EsClient esClient = new EsClient();

    public static RestClient getClient(){

        return getClient("localhost:9200");
    }

    public static RestClient getClient(String url){
        if (restClient==null){
            EsConfig esConfig = new EsConfig();
            esConfig.setEsUrl(url);
            esConfig.setEsUser("user");
            esConfig.setEsbPwd("pwd");
            restClient = esClient.buildClient(esConfig);
        }
        return restClient;
    }

    public static void close() throws IOException {
        restClient.close();
    }

    private RestClient buildClient(EsConfig esConfig){
        String esUrl = esConfig.getEsUrl();
        String[] eu = esUrl.split(":");
        RestClientBuilder builder = RestClient.builder(new HttpHost(eu[0],Integer.parseInt(eu[1])));
        byte[] tokenByte = Base64.encodeBase64((esConfig.getEsUser()+":"+esConfig.getEsbPwd()).getBytes());
        //将加密的信息转换为string
        String tokenStr = bytes2String(tokenByte);
        String token = "Basic "+tokenStr;
        Header[] headers = new Header[]{
                new BasicHeader("Content-type","application/json;charset=utf-8"),
                new BasicHeader("Authorization",token)
        };
        builder.setDefaultHeaders(headers);
        return builder.build();
    }

    private static String bytes2String(byte b[]){
        return new String(b);
    }

    public static class EsConfig{
        private Integer id;
        private String esUrl;
        private String esUser;
        private String esbPwd;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getEsUrl() {
            return esUrl;
        }

        public void setEsUrl(String esUrl) {
            this.esUrl = esUrl;
        }

        public String getEsUser() {
            return esUser;
        }

        public void setEsUser(String esUser) {
            this.esUser = esUser;
        }

        public String getEsbPwd() {
            return esbPwd;
        }

        public void setEsbPwd(String esbPwd) {
            this.esbPwd = esbPwd;
        }
    }
}
