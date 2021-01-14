package com.sitech.esb.jssh.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置
 * @author liwei_paas
 * @date 2021/1/11
 */
public class JsshConfig {

    static Map<String,Object> configMap = new HashMap<>();

    public static String get(String configKey){
        return (String) configMap.get(configKey);
    }

    public static Object getOrDefault(String key,Object value){
        return configMap.containsKey(key)?configMap.get(key):value;
    }

    public String getSrvlogFilePath(String esbKey){
        Map<String,String> s = (Map<String, String>) configMap.get(esbKey);
        return s.containsKey("srvlogFilepath")?s.get("srvlogFilepath"):(String) configMap.get("srvlogFilepath");
    }

}
