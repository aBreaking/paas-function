package com.sitech.esb.jbeat;

import com.sitech.esb.jbeat.runner.JBeatRunnable;
import com.sitech.esb.jbeat.runner.JBeatConfiguration;
import com.sitech.esb.jbeat.runner.JBeatThreadFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 *
 * @author liwei_paas
 * @date 2021/1/11
 */
public class JBeatMain {

    public static void main(String[] args) throws Exception {
        int interval = 3000;
        if (args.length!=0){
            interval = Integer.parseInt(args[0]) * 1000;
        }
        startup(interval);
    }

    public static void startup(int interval) throws Exception{
        JBeatConfiguration.initConfiguration();
        Map jssh = (Map) JBeatConfiguration.get("jbeat");
        Set<String> JsshBeatKeys = jssh.keySet();
        ExecutorService threadPool = Executors.newFixedThreadPool(JsshBeatKeys.size(),new JBeatThreadFactory());
        while(true){
            for (String key : JsshBeatKeys) {
                threadPool.submit(new JBeatRunnable(key));
            }
            Thread.sleep(interval);
        }
    }
}
