package com.sitech.paas.javagen.demo;

import java.util.concurrent.*;
import com.sitech.paas.javagen.demo.service.OrderService;
import com.sitech.paas.javagen.demo.service.ProductService;
import com.sitech.paas.javagen.demo.service.UserService;


/**
 * 使用future来提交任务，设置任务超时时间
 * @author liwei_paas
 * @date 2020/3/3
 */
public class Main {

    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args)  {
        try{
            final com.sitech.paas.javagen.demo.dto.User s1 = submitTask(()->UserService.getUser(args[0]));
            final Boolean s2 = submitTask(()->ProductService.hasMore("A1"));
            final String s3 = submitTask(()->{{
                if(s2){return args[1];
                }else{return null;
                }}});
            final com.sitech.paas.javagen.demo.dto.Order s4 = submitTask(()->OrderService.createOrder(s1,s3));
            System.out.println("result is :" + s4.getOrderId());
            System.out.println("result is :" + s1.getUserid());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }

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
