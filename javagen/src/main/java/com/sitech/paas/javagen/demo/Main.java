package com.sitech.paas.javagen.demo;


import com.sitech.paas.javagen.demo.util.CommonUtil;
import com.sitech.paas.javagen.demo.util.ServiceCaller;
import com.alibaba.fastjson.JSONObject;

/**
 * 最基本的流程实现
 * @author liwei_paas
 * @date 2020/3/3
 */
public class Main {

    public static void main(String[] args) throws Exception {
        //第一个流程，调用一个rest服务
        String $1 = ServiceCaller.callRest("http://ip:port/my/rest/uri" , "{\"mypin\":\"something\"}");

        //第二个流程，自定义解析方法
        JSONObject $2 = myParser($1);

        //第三个流程
        int $3 = CommonUtil.sum($2.getString("first"), $2.getString("second"));

        System.out.println($3);
    }

    public static JSONObject myParser(String s){
        int start = s.indexOf(":");
        JSONObject json = CommonUtil.parseJson(s.substring(start+1));
        return  json;
    }
}
