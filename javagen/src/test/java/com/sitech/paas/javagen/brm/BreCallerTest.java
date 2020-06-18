package com.sitech.paas.javagen.brm;

import com.sitech.ac.rdc.re.api.common.service.CommonServiceProcessor;
import com.sitech.ac.rdc.re.api.common.service.InitService;
import org.junit.Test;

/**
 * 规则测试调用
 * @author liwei_paas
 * @date 2020/3/16
 */
public class BreCallerTest {

    @Test
    public void testSqlCaller(){
        String param = "{\n" +
                "    \"ROOT\": {\n" +
                "        \"HEADER\": {\n" +
                "            \"DB_ID\": \"B9\",\n" +
                "            \"ROUTING\": {\n" +
                "                \"ROUTE_KEY\": \"11\",\n" +
                "                \"ROUTE_VALUE\": \"21310016226483\"\n" +
                "            },\n" +
                "            \"TRACE_ID\": \"\",\n" +
                "            \"PARENT_CALL_ID\": \"\"\n" +
                "        },\n" +
                "        \"BODY\": {\n" +
                "            \"REQUEST_INFO\": {\n" +
                "                \"PRODINSTID\": \"2000000011203\",\n" +
                "                \"OPR_INFO\": {\n" +
                "                    \"AUTHEN_CODE\": \"4\",\n" +
                "                    \"REGION_ID\": 21,\n" +
                "                    \"GROUP_ID\": \"121000000\",\n" +
                "                    \"CHANNEL_TYPE\": \"431\",\n" +
                "                    \"LOGIN_NO\": \"LZZZZZCTC\",\n" +
                "                    \"LOGIN_PWD\": \"fb0bdec823cf4cdb\",\n" +
                "                    \"CONTACT_ID\": \"-1\",\n" +
                "                    \"IP_ADDRESS\": \"10.153.238.203\"\n" +
                "                },\n" +
                "                \"BUSI_INFO_LIST\": {\n" +
                "                    \"BUSI_INFO\": {\n" +
                "                        \"RULE_ID\": 1215753851,\n" +
                "                        \"RULE_ID\": \"LZZZZZCTC\",\n" +
                "                        \"QRY_FLAG\": \"4\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        InitService service = new InitService();
        service.init();
        CommonServiceProcessor commonServiceProcessor = new CommonServiceProcessor();
        Object o = commonServiceProcessor.executeBusGroupScript("srv_liwei",param,null);
        System.out.println(o);
    }
}
