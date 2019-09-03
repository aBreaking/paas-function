package com.sitech.paas.elk;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.sitech.paas.elk.api.EsClient;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.RestClient;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;

/**
 *
 * @author liwei_paas
 * @date 2019/9/2
 */
public class InsertTestData {

    /**
     * 插入测试数据
     * @param args
     * @throws Exception
     */
    public  static void main(String args[]) throws Exception {
        RestClient client = EsClient.getClient();
        Map<String, String> map = Collections.<String, String>emptyMap();
        String file = "C:\\Users\\MI\\Desktop\\es接口\\esSrvlog.data";
        FileInputStream inputStream = new FileInputStream(file);
        JSONReader jsonReader = new JSONReader(new InputStreamReader(inputStream));
        jsonReader.startArray();
        while (jsonReader.hasNext()){
            JSONObject json = (JSONObject) jsonReader.readObject();
            JSONObject source = json.getJSONObject("_source");
            HttpEntity entity = new NStringEntity(source.toJSONString(), ContentType.APPLICATION_JSON);
            client.performRequest("POST", "/esb-srvlog-2019-07-21/doc",map, entity);
        }
        client.close();
    }
}
