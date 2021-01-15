package com.sitech.esb.jssh.executor;


import com.sitech.esb.jssh.beat.FileLineBeat;
import com.sitech.esb.jssh.beat.FileRecordCache;
import com.sitech.esb.jssh.config.JsshConfiguration;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 使用线程池开始任务
 * @author liwei_paas
 * @date 2021/1/15
 */
public class JsshExecutor {
    static Map<String,?> CONFIG_MAP = new LinkedHashMap<>();
    static ExecutorService threadPool = Executors.newFixedThreadPool(CONFIG_MAP.size());

    private static void initThreadPool(){
        Map<String,?> map = (Map) JsshConfiguration.get("jssh");
        new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread("abc");
            }
        };

        for (String key : map.keySet()){
            if (key.equals("common")){
                continue;
            }

        }

    }


    public void startup(){
        Map<String,?> map = (Map) JsshConfiguration.get("jssh");
        for (String key : map.keySet()){
            threadPool.submit(()->{

            });

        }
    }

}
