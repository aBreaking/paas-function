package com.sitech.esb.jssh;

import com.sitech.esb.jssh.beat.FileRecordCache;
import com.sitech.esb.jssh.runner.BeatRunnable;
import com.sitech.esb.jssh.runner.JsshConfiguration;
import com.sitech.esb.jssh.runner.JsshThreadFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 *
 * @author liwei_paas
 * @date 2021/1/11
 */
public class JsshMain {

    private static final ExecutorService threadPool ;
    private static Set<String> JsshBeatKeys ;
    static {
        Map jssh = (Map) JsshConfiguration.get("jssh");
        JsshBeatKeys = jssh.keySet();
        if (JsshBeatKeys.contains("common")){
            JsshBeatKeys.remove("common");
        }
        threadPool = Executors.newFixedThreadPool(JsshBeatKeys.size(),new JsshThreadFactory());
    }

    public static void justBeat() {
        for (String key : JsshBeatKeys) {
            threadPool.submit(new BeatRunnable(key));
        }
    }

    public static void main(String[] args) throws Exception{
        while(true){
            justBeat();
            Thread.sleep(1000);
        }

    }


}
