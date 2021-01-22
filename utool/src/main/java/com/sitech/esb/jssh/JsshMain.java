package com.sitech.esb.jssh;

import com.sitech.esb.jssh.runner.BeatRunnable;
import com.sitech.esb.jssh.runner.JsshConfiguration;
import com.sitech.esb.jssh.runner.JsshThreadFactory;

import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 *
 * @author liwei_paas
 * @date 2021/1/11
 */
public class JsshMain {

    private static ExecutorService threadPool ;
    private static Set<String> JsshBeatKeys ;

    public static void justBeat() {
        for (String key : JsshBeatKeys) {
            threadPool.submit(new BeatRunnable(key));
        }
    }

    public static void main(String[] args) throws Exception{
        initConfiguration();
        while(true){
            justBeat();
            Thread.sleep(1000);
        }
    }

    private static void initConfiguration() {
        try{
            JsshConfiguration.readYamlAndInitConfigMap(getJarConfigFileInputStream(JsshConfiguration.CONFIG_FILE_NAME_PATH));
        }catch (Exception e){
            JsshConfiguration.readYamlAndInitConfigMap();
        }
        Map jssh = (Map) JsshConfiguration.get("jssh");
        JsshBeatKeys = jssh.keySet();
        if (JsshBeatKeys.contains("common")){
            JsshBeatKeys.remove("common");
        }
        threadPool = Executors.newFixedThreadPool(JsshBeatKeys.size(),new JsshThreadFactory());
    }

    private static InputStream getJarConfigFileInputStream(String file) throws IOException {
        String path = JsshMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(0, path.lastIndexOf("/"));
        String locationPath = path+File.separator+file;
        JsshConfiguration.CONFIG_FILE_NAME_PATH = locationPath;
        return new BufferedInputStream(new FileInputStream(locationPath));
    }
}
