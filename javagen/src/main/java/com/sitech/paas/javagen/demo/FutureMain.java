package com.sitech.paas.javagen.demo;

import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.javagen.brm.BreCaller;
import com.sitech.paas.javagen.demo.util.CommonUtil;
import com.sitech.paas.javagen.demo.util.ServiceCaller;
import java.util.concurrent.*;


/**
 * 使用future来提交任务，设置任务超时时间
 * @author liwei_paas
 * @date 2020/3/3
 */
public class FutureMain {

    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws TimeoutException {

        String s0 = submitTask(()->{
            BreCaller caller = BreCaller.getInstance();
            return caller.call("param1","pin");
        });


        final String $s1 = submitTask(() -> ServiceCaller.callRest("http://ip:port/my/rest/uri" , "{\"mypin\":\"something\"}"),1000);
        final JSONObject $s2 = submitTask(() -> {
            int start = $s1.indexOf(":");
            JSONObject json = CommonUtil.parseJson($s1.substring(start+1));
            return  json;
        },1234);
        final int $s3 = submitTask(()->CommonUtil.sum($s2.getInteger("first"), $s2.getInteger("second")));
        System.out.println($s3);
    }

    public static <T> T submitTask(Callable<T> task) throws TimeoutException{
        return submitTask(task,Integer.MAX_VALUE);
    }

    public static <T> T submitTask(Callable<T> task,int timeout) throws TimeoutException{
        Future<T> future = executorService.submit(task);
        try {
            return future.get(timeout,TimeUnit.MICROSECONDS);
        } catch (InterruptedException |ExecutionException  e) {
            throw new RuntimeException(e);
        }
    }
}
