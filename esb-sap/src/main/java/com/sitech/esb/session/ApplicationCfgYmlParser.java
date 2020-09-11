package com.sitech.esb.session;

import com.sitech.esb.util.StringUtil;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * yml 解析工具
 * @author liwei_paas
 * @date 2020/9/11
 */
public class ApplicationCfgYmlParser{

    private static final String DEFAULT_YML_NAME = "application.yml";

    private static LinkedHashMap<?, ?> YML_CONFIG_MAP = new LinkedHashMap<>();

    private void loadYaml(){
        InputStream in = ApplicationCfgYmlParser.class.getClassLoader().getResourceAsStream(DEFAULT_YML_NAME);
        YML_CONFIG_MAP = new Yaml().load(in);
    }

    public Object getConfig(String key){
        if (YML_CONFIG_MAP.isEmpty()){
            synchronized (YML_CONFIG_MAP){
                if (YML_CONFIG_MAP.isEmpty()){
                    loadYaml();
                }
            }
        }
        return getProperty(YML_CONFIG_MAP,key);
    }

    public Map<String,Map<String,String>> getDataSourceConfig(){
        return (Map<String, Map<String, String>>) getConfig("datasource");
    }

    private static Object getProperty(Map<?, ?> map, Object qualifiedKey)
    {
        if (map != null && !map.isEmpty() && qualifiedKey != null){
            String input = String.valueOf(qualifiedKey);
            if (StringUtil.isNotBlank(input)){
                if (input.indexOf(".")!=-1){
                    int index = input.indexOf(".");
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1, input.length());
                    return getProperty((Map<?, ?>) map.get(left), right);
                }else if (map.containsKey(input)){
                    return map.get(input);
                }
            }
        }
        return null;
    }
}