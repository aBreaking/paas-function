package com.sitech.esb.jssh;

import com.sitech.esb.jssh.beat.FileLineBeat;
import com.sitech.esb.jssh.runner.BeatRunnable;
import com.sitech.esb.jssh.runner.JsshConfiguration;
import com.sitech.esb.jssh.runner.JsshThreadFactory;
import com.sitech.esb.jssh.runner.SingleFileBeatRunnable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 *
 * @author liwei_paas
 * @date 2021/1/11
 */
public class JsshMain {

    public static void main(String[] args) throws Exception {

        if (args.length==0){
            startup();
        }
        if (args.length == 1){
            handleSingeFile(args[0]);
        }
        if (args.length >= 3){
            handleSingeFile(args[0],args[1],args[2]);
        }
        System.out.println("args in not correct");
    }

    public static void startup() throws Exception{
        JsshConfiguration.initConfiguration();
        Map jssh = (Map) JsshConfiguration.get("jssh");
        Set<String> JsshBeatKeys = jssh.keySet();
        ExecutorService threadPool = Executors.newFixedThreadPool(JsshBeatKeys.size(),new JsshThreadFactory());
        while(true){
            for (String key : JsshBeatKeys) {
                threadPool.submit(new BeatRunnable(key));
            }
            Thread.sleep(1000);
        }
    }

    public static void handleSingeFile(String file) throws ExecutionException, InterruptedException {
        JsshConfiguration.initConfiguration();
        Map<String,Map> jssh = (Map) JsshConfiguration.get("jssh");
        Set<String> keySet = jssh.keySet();
        String key = keySet.iterator().next();
        Map<String,?> beat = (Map) jssh.get(key).get("beat");
        String beatName = beat.keySet().iterator().next();
        doHandleSingeFile(key,beatName,file);
    }

    public static void handleSingeFile(String key,String beatName,String file) throws ExecutionException, InterruptedException {
        JsshConfiguration.initConfiguration();
        doHandleSingeFile(key,beatName,file);
    }

    /**
     * 单个文件解析处理
     * @param file
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void doHandleSingeFile(String key,String beatName,String file) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newSingleThreadExecutor(new JsshThreadFactory());
        int status = 0;
        while (status==FileLineBeat.STATUS_NORMAL){
            Future<Integer> future = threadPool.submit(new SingleFileBeatRunnable(key,beatName,file));
            status = future.get();
        }
        threadPool.shutdown();
        System.out.println(file+" beat has done");
    }
}
