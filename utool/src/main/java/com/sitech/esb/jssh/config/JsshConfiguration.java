package com.sitech.esb.jssh.config;


import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 全局配置
 * @author liwei_paas
 * @date 2021/1/11
 */
public class JsshConfiguration {
    private static final String config_file_name = "jssh-esb.yml";

    private static Map YML_CONFIG_MAP = new LinkedHashMap();

    static {
        InputStream in = JsshConfiguration.class.getClassLoader().getResourceAsStream(config_file_name);
        YML_CONFIG_MAP.putAll(new Yaml().load(in));
    }

    public static Object getOrDefault(String qualifiedKey,Object defaultValue){
        Object o = get(qualifiedKey);
        if (o!=null)return o;
        return defaultValue;
    }

    public static Object get(String qualifiedKey){
        return getProperty(YML_CONFIG_MAP,qualifiedKey);
    }

    private static Object getProperty(Map map,String qualifiedKey){
        if (!map.isEmpty() && qualifiedKey != null){
            String input = qualifiedKey;
            if (!input.equals("")){
                if (input.contains(".")){
                    int index = input.indexOf(".");
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1, input.length());
                    return getProperty((Map) map.get(left), right);
                }
                else if (map.containsKey(input)){
                    return map.get(input);
                }
            }
        }
        return null;
    }

}
