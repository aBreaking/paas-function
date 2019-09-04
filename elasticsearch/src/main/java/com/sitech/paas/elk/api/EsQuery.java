package com.sitech.paas.elk.api;

import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.elk.util.FastJsonUtil;
import org.apache.http.HttpEntity;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.Collections;

/**
 *
 * @author liwei_paas
 * @date 2019/9/3
 */
public class EsQuery {

    public JSONObject query(JSONObject json) throws IOException {
        return query(json.toJSONString());
    }

    public JSONObject query(String json) throws IOException {
        RestClient client = EsClient.getClient();
        try{
            HttpEntity entity = new NStringEntity(json);
            Response response = client.performRequest("GET",
                    "/esb-srvlog-2019-07-21/doc/_search",Collections.EMPTY_MAP,entity);
            int statusCode = response.getStatusLine().getStatusCode();
            JSONObject jsonObject = FastJsonUtil.toFastjson(response.getEntity().getContent());

            if (statusCode==200){
                return jsonObject;
            }else{
                throw new RuntimeException(jsonObject.toJSONString());
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            client.close();
        }

    }



}
