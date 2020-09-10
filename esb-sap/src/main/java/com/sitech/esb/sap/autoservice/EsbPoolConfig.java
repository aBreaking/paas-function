package com.sitech.esb.sap.autoservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * esb池的相关配置
 * 目前暂不用配置文件的方式，这些配置应该都是从服务端传过来的
 * @author liwei_paas
 * @date 2020/4/8
 */
public class EsbPoolConfig {

    protected String poolKey;
    protected String hbXml;
    protected String poolAddress;

    private static final Map<String,EsbPoolConfig> CONFIG_CACHE = new HashMap();

    private final static String ESB_POOL_CONFIG_FILE = "esb_pool_config.properties";

    static {
        //deprecated
        //readConfig();
    }

    public EsbPoolConfig(){}

    public EsbPoolConfig(String poolKey, String hbXml, String poolAddress) {
        this.poolKey = poolKey;
        this.hbXml = hbXml;
        this.poolAddress = poolAddress;
    }

    /**
     * 程序启动后，需要先读取配置文件里面esb池的相关配置，并缓存起来
     */
    @Deprecated
    private static void readConfig(){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(ESB_POOL_CONFIG_FILE));
            Set<Object> set = properties.keySet();
            for (Object key : set){
                Object o = properties.get(key);
                if (null == o){
                    continue;
                }
                String[] split = o.toString().split("->");
                if (split.length<2){
                    continue;
                }
                String poolKey = (String) o;
                EsbPoolConfig config = new EsbPoolConfig(poolKey,split[0],split[1]);
                CONFIG_CACHE.put(poolKey,config);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static EsbPoolConfig getEsbPoolConfig(String poolKey){
        return CONFIG_CACHE.get(poolKey);
    }

    public String getPoolKey() {
        return poolKey;
    }

    public void setPoolKey(String poolKey) {
        this.poolKey = poolKey;
    }

    public String getHbXml() {
        return hbXml;
    }

    public void setHbXml(String hbXml) {
        this.hbXml = hbXml;
    }

    public String getPoolAddress() {
        return poolAddress;
    }

    public void setPoolAddress(String poolAddress) {
        this.poolAddress = poolAddress;
    }

    @Override
    public String toString() {
        return "EsbPoolConfig{" +
                "poolKey='" + poolKey + '\'' +
                ", hbXml='" + hbXml + '\'' +
                ", poolAddress='" + poolAddress + '\'' +
                '}';
    }
}
