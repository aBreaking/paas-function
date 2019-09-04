package com.sitech.paas.elk;

import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.elk.api.EsQuery;
import com.sitech.paas.elk.ret.AggregationResultParser;
import com.sitech.paas.elk.ret.Results;
import com.sitech.paas.elk.util.FastJsonUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author liwei_paas
 * @date 2019/9/2
 */
public class Demo {
    static String file = "D:\\workspace\\paas-function\\elasticsearch\\src\\main\\resources\\aggs_srv_retcode_retmsg.json";

    public static void main(String args[]) throws Exception {
        JSONObject result = new EsQuery().query(inputJson(file));
        String[] aggs = {"srvName","retCode","retMsg"};
        AggregationResultParser resultParser = new AggregationResultParser(aggs);
        Results results = resultParser.parse(result);
        List<String[]> list = results.getResult();
        for (String[] s : list){
            System.out.println(arrayToString(s));
        }
    }

    private static JSONObject inputJson(String file) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        return FastJsonUtil.toFastjson(fileInputStream);
    }


    private static String arrayToString(String[] array){
        StringBuilder builder = new StringBuilder();
        for (String s : array){
            builder.append(s).append(",");
        }
        return builder.substring(0,builder.length()-1);
    }

}
