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
        int interval = 5000;
        if (args.length!=0){
            interval = Integer.parseInt(args[0]) * 1000;
        }
        startup(interval);
    }

    public static void startup(int interval) throws Exception{
        JBeatConfiguration.initConfiguration();
        Map jbeat = (Map) JBeatConfiguration.get(JBeatConfiguration.KEY_OF_JBEAT);
        Set<String> keys = jbeat.keySet();
        ExecutorService threadPool = Executors.newFixedThreadPool(keys.size(),new JBeatThreadFactory("main"));
        while(true){
            for (String key : keys) {
                threadPool.submit(new JBeatRunnable(key));
            }
            Thread.sleep(interval);
        }
    }
}
