package com.sitech.paas.elk.api;


import com.alibaba.fastjson.JSONReader;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;

/**
 * 索引
 * @author liwei_paas 
 * @date 2019/9/2
 */
public class Index {
    public static void main(String args[]) throws IOException {
        try{
            RestClient client = EsClient.getClient();
            /*Map<String, String> map = Collections.singletonMap("pretty", "true");*/
            Response response = client.performRequest("GET", "/demo/_search");

            HttpEntity entity = response.getEntity();
            toString(entity);
        }finally {
            EsClient.close();
        }
    }

    public static void toString(HttpEntity entity) throws IOException {
        String s = EntityUtils.toString(entity);
        System.out.println(s);
    }

    public static void fastjson(InputStream inputStream){
        JSONReader jsonReader = new JSONReader(new InputStreamReader(inputStream));
        Object o = jsonReader.readObject();
        System.out.println(o);
    }
}
