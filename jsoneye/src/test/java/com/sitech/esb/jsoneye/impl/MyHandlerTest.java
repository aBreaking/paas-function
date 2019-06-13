/*
package com.sitech.esb.jsoneye.impl;

import com.alibaba.fastjson.JSONObject;
import com.sitech.esb.jsoneye.Handler;
import com.sitech.esb.jsoneye.Result;

*/
/**
 * @{USER}
 * @{DATE}
 *//*

public class MyHandlerTest {

    public static void main(String args[]){
        //excel
        String json = "{\n" +
                "    \"UNI_BSS_ATTACHED\": {\n" +
                "        \"MEDIA_INFO\": \"\"\n" +
                "    },\n" +
                "    \"UNI_BSS_BODY\": {\n" +
                "        \"MICROSERVICE_QRYSER_SIMPSNRESQRY_RSP\": {\n" +
                "            \"MSG\": \"服务调用成功！\",\n" +
                "            \"RSP\": {\n" +
                "                \"DATA\": [\n" +
                "                    {\n" +
                "                        \"NUM_INFO\": [\n" +
                "                            {\n" +
                "                                \"ADVANCE_PAY\": \"0\",\n" +
                "                                \"CLASS_ID\": \"9\",\n" +
                "                                \"LOW_COST_PRO\": \"0\",\n" +
                "                                \"NUM_ID\": \"14539996167\",\n" +
                "                                \"TIME_DUR_PRO\": \"0\"\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"ADVANCE_PAY\": \"0\",\n" +
                "                                \"CLASS_ID\": \"9\",\n" +
                "                                \"LOW_COST_PRO\": \"0\",\n" +
                "                                \"NUM_ID\": \"14529133359\",\n" +
                "                                \"TIME_DUR_PRO\": \"0\"\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"RSP_CODE\": \"0000\",\n" +
                "                \"RSP_DESC\": \"success\",\n" +
                "                \"SUB_CODE\": \"0000\",\n" +
                "                \"SUB_DESC\": \"success\"\n" +
                "            },\n" +
                "            \"STATUS\": \"0000\",\n" +
                "            \"TXID\": \"055c15624352^1555471687507^373337\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"UNI_BSS_HEAD\": {\n" +
                "        \"APP_ID\": \"51-COMP\",\n" +
                "        \"RESP_CODE\": \"00000\",\n" +
                "        \"RESP_DESC\": \"Success\",\n" +
                "        \"TIMESTAMP\": \"2019-05-08 16:35:52 917\",\n" +
                "        \"TRANS_ID\": \"20190508163552917177197\"\n" +
                "    }\n" +
                "}";

        String json2 = "{\n" +
                "    \"UNI_BSS_ATTACHED\": {\n" +
                "        \"MEDIA_INFO\": \"\"\n" +
                "    },\n" +
                "    \"UNI_BSS_BODY\": {\n" +
                "        \"MICROSERVICE_QRYSER_SIMPSNRESQRY_RSP\": {\n" +
                "            \"MSG\": \"服务调用成功！\",\n" +
                "            \"RSP\": {\n" +
                "                \"DATA\": [\n" +
                "                    {\n" +
                "                        \"NUM_INFO\": [\n" +
                "                            {\n" +
                "                                \"TEST_ABC\": \"0\",\n" +
                "                                \"CLASS_ID\": \"9\",\n" +
                "                                \"LOW_COST_PRO\": \"0\",\n" +
                "                                \"NUM_ID\": \"14539996167\",\n" +
                "                                \"TIME_DUR_PRO\": \"0\"\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"RSP_CODE\": \"0000\",\n" +
                "                \"RSP_DESC\": \"success\",\n" +
                "                \"SUB_CODE\": \"0000\",\n" +
                "                \"SUB_DESC\": \"success\"\n" +
                "            },\n" +
                "            \"STATUS\": \"0000\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"UNI_BSS_HEAD\": {\n" +
                "        \"APP_ID\": \"51-COMP\",\n" +
                "        \"RESP_CODE\": \"00000\",\n" +
                "        \"RESP_DESC\": \"Success\",\n" +
                "        \"TIMESTAMP\": \"2019-05-08 16:35:52 917\",\n" +
                "        \"TRANS_ID\": \"20190508163552917177197\"\n" +
                "    }\n" +
                "}";

        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject jsonObject2 = JSONObject.parseObject(json2);

        Handler handler = new MyHandler();
        Result different = handler.different(jsonObject, jsonObject2);
        System.out.println(different.lack());
        System.out.println(different.remain());

    }

}
*/