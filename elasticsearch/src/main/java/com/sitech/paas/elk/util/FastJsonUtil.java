package com.sitech.paas.elk.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author liwei_paas 
 * @date 2019/9/3
 */
public class FastJsonUtil {

    public static JSONObject toFastjson(InputStream inputStream){
        JSONReader jsonReader = new JSONReader(new InputStreamReader(inputStream));
        return (JSONObject) jsonReader.readObject();
    }
}
