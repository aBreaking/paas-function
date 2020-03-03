package com.sitech.paas.javagen.demo;

import com.sitech.paas.javagen.demo.util.ServiceCaller;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * @{USER}
 * @{DATE}
 */
public class MyTest {
    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Test
    public void testTasksGen(){

    }

    public static void mainMethod(String[] args) throws ClassNotFoundException {
        String utilClass = "com.sitech.paas.javagen.demo.util.CommonUtil";
        Class<?> commonUtilClass = Class.forName(utilClass);
        Method[] methods = commonUtilClass.getDeclaredMethods();
        for (Method method : methods){
            String methodName = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Class<?> returnType = method.getReturnType();
            System.out.println(methodName);
            System.out.println(Arrays.toString(parameterTypes));
            System.out.println(returnType.getName());
        }
    }

    public static void main01(String[] args) throws InterruptedException, ExecutionException {
        Future<String> future = executorService.submit(() -> ServiceCaller.callRest("",""));
        String s = null;
        try {
            s = future.get(900, TimeUnit.MILLISECONDS);
            System.out.println(s);
        } catch (TimeoutException e) {
            future.cancel(true);
            e.printStackTrace();
        }
    }
}
