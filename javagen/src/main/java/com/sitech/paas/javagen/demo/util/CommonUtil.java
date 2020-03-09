package com.sitech.paas.javagen.demo.util;

import com.alibaba.fastjson.JSONObject;


/**
 * 通用工具，页面上可选择的
 * @author liwei_paas
 * @date 2020/3/3
 */
public class CommonUtil {

    public static JSONObject parseJson(String pin){
        return JSONObject.parseObject(pin);
    }

    public static int sum(Integer a,Integer b){
        return a+b;
    }


}
