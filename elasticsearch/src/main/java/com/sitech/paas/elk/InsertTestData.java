package com.sitech.paas.elk;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.sitech.paas.elk.api.EsClient;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.RestClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;

/**
 *
 * @author liwei_paas
 * @date 2019/9/2
 */
public class InsertTestData {


    private static final String ES_URL = "172.21.11.65:9200";


    /**
     * 插入测试数据
     * @param args
     * @throws Exception
     */
    public  static void main(String args[]) throws Exception {

        /*final String esbwsIndex = "esb-esbws-2019-09-01";
        final String esbwsFile = "C:\\Users\\MI\\Desktop\\es接口\\esb-esbws.txt";*/
        String index = "esb-inparam-2019-09-01";
        String file = "C:\\Users\\MI\\Desktop\\es接口\\esb-inparam.txt";
        new Thread(()->{
           doInsert(index,file);
        }).start();

    }

    private static void doInsert(String index,String file) {
        RestClient client = EsClient.getClient(ES_URL);
        try{
            Map<String, String> map = Collections.<String, String>emptyMap();
            FileInputStream inputStream = new FileInputStream(file);
            JSONReader jsonReader = new JSONReader(new InputStreamReader(inputStream));
            jsonReader.startArray();
            while (jsonReader.hasNext()){
                JSONObject json = (JSONObject) jsonReader.readObject();
                JSONObject source = json.getJSONObject("_source");
                HttpEntity entity = new NStringEntity(source.toJSONString(), ContentType.APPLICATION_JSON);
                client.performRequest("POST", "/"+index+"/doc",map, entity);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
