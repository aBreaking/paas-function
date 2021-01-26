package com.sitech.esb.jbeat.runner;

import com.sitech.esb.jbeat.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 全局配置
 * @author liwei_paas
 * @date 2021/1/11
 */
public class JBeatConfiguration {

    private static Logger logger = LoggerFactory.getLogger(JBeatConfiguration.class);

    public static final String CONFIG_FILE_NAME_PATH = "jbeat-esb.yml";

    public static final String KEY_OF_JBEAT = "jbeat";

    private static Map YML_CONFIG_MAP = new LinkedHashMap();

    public static String PRODUCT_PATH = ""; //项目路径

    public static void initConfiguration() {
        logger.info("=======初始化全局配置=======");
        URL resource = JBeatConfiguration.class.getResource("");

        String protocol = resource.getProtocol();
        if (protocol.equals("jar")){
            try {
                logger.info("检测到是jar包启动该工程");
                String filePath = ResourceUtil.getJarConfigFilePath(CONFIG_FILE_NAME_PATH);
                initConfigMapAndRefreshConfigPath(filePath);
                return;
            } catch (FileNotFoundException e) {
                logger.warn("jar包同目录下不存在该配置文件，将使用jar包类的配置文件："+CONFIG_FILE_NAME_PATH);
            }
        }
        logger.info("加载classpath下的配置文件->"+CONFIG_FILE_NAME_PATH);
        String filePath = ResourceUtil.getClasspathFilePath(CONFIG_FILE_NAME_PATH);
        try {
            logger.info("将使用classpath路径下的配置文件");
            initConfigMapAndRefreshConfigPath(filePath);
        } catch (FileNotFoundException e) {
            logger.error("classpath下没有"+CONFIG_FILE_NAME_PATH+"配置文件",e);
            throw new RuntimeException(e);
        }
    }

    private static void initConfigMapAndRefreshConfigPath(String filePath) throws FileNotFoundException {
        InputStream in = null ;
        try {
            in = new FileInputStream(filePath);
            logger.info("开始加载配置文件内容，文件路径:{}",filePath);
            Map<String,?> yamlMap = new Yaml().load(in);
            YML_CONFIG_MAP.putAll(yamlMap);
            PRODUCT_PATH = filePath.substring(0,filePath.lastIndexOf("/")+1);
            logger.info("配置加载完毕");
        }finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("配置文件关闭失败",e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static Map getConfigUnderJBeat(String qualifiedKeyUnderJbeat){
        String key = KEY_OF_JBEAT+"."+qualifiedKeyUnderJbeat;
        return (Map) get(key);
    }

    public static Object get(String qualifiedKey){
        return getProperty(YML_CONFIG_MAP, qualifiedKey);
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
