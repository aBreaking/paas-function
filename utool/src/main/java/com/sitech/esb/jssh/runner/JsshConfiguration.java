package com.sitech.esb.jssh.runner;


import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 全局配置
 * @author liwei_paas
 * @date 2021/1/11
 */
public class JsshConfiguration {

    public static String CONFIG_FILE_NAME_PATH = "jssh-esb.yml";

    private static final String KEY_OF_JSSH = "jssh";

    private static Map YML_CONFIG_MAP = new LinkedHashMap();

    public static void readYamlAndInitConfigMap(){
        InputStream in = JsshConfiguration.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME_PATH);
        readYamlAndInitConfigMap(in);
    }

    public static void readYamlAndInitConfigMap(InputStream in){
        Map<String,?> yamlMap = new Yaml().load(in);
        Map<String,?> jsshMap = (Map) yamlMap.get(KEY_OF_JSSH);

        // 判断yaml 中是否有common这个配置
        String keyOfCommon = "common";
        if (!jsshMap.containsKey(keyOfCommon)){
            YML_CONFIG_MAP.putAll(yamlMap);
            return;
        }

        Map<String,Object> common = (Map<String, Object>) jsshMap.get(keyOfCommon);
        for (String key : jsshMap.keySet()){
            if (key.equals(keyOfCommon)){
                continue;
            }
            // FIXME 这里应该需要深度遍历map
            Map<String,Object> map = (Map) jsshMap.get(key);
            for (String commonKey : common.keySet()){
                if (!map.containsKey(commonKey)){
                    map.put(commonKey,common.get(key));
                }
            }
            YML_CONFIG_MAP.put(key,map);
        }
    }

    public static String getCacheFilePath(String localKey){
        String file;
        if (CONFIG_FILE_NAME_PATH.indexOf(File.separator)!=-1){
            file = CONFIG_FILE_NAME_PATH;
        }else{
            URL url = JsshConfiguration.class.getClassLoader().getResource(CONFIG_FILE_NAME_PATH);
            file = url.getFile();
        }
        String prefix = file.substring(0, file.lastIndexOf("jssh-esb.yml"));
        return prefix+"beat-cache-file."+localKey+".cache";
    }

    public static Map getConfigUnderJsshKey(String configKey){
        String key = "jssh." + JsshLocalContext.getLocalKey() + "."+configKey;
        return (Map) JsshConfiguration.get(key);
    }

    public static Object get(String qualifiedKey){
        Object property = getProperty(YML_CONFIG_MAP, qualifiedKey);
        if (property == null){
            throw new RuntimeException(" no config of "+qualifiedKey);
        }
        return property;
    }

    private static Object getProperty(Map map,String qualifiedKey){
        if(map==null){
            return null;
        }
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
