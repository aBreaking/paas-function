package com.sitech.paas.javagen.json;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * TaskGen test
 * @author liwei_paas
 * @date 2020/3/12
 */
public class TaskGenTest {

    String json1 = "{\n" +
            "      \"type\":\"com.sitech.paas.javagen.demo.service.OrderService\",\n" +
            "      \"method\":\"createOrder\",\n" +
            "      \"alias\":\"s4\",\n" +
            "      \"inputs\":{\n" +
            "        \"a\":\"${s1.userid}\",\n" +
            "        \"b\":\"${s3}\"\n" +
            "      },\n" +
            "      \"inputsExtra\":{\n" +
            "        \"a\":\"java.lang.Integer\",\n" +
            "        \"b\":\"java.lang.String\"\n" +
            "      },\n" +
            "      \"returnType\":\"com.sitech.paas.javagen.demo.dto.Order\"\n" +
            "    }";

    @Test
    public void test01(){

    }
}
