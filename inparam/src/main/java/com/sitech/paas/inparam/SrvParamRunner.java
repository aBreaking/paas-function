package com.sitech.paas.inparam;


import com.sitech.paas.inparam.handler.Handler;
import com.sitech.paas.inparam.db.ServiceParameterResolver;
import com.sitech.paas.inparam.db.ServiceParametersHandler;
import com.sitech.paas.inparam.io.Resource;
import com.sitech.paas.inparam.io.ResourceLoader;
import com.sitech.paas.inparam.resovler.Resolver;

import java.util.Properties;

public class SrvParamRunner {
    static Properties properties = new Properties();
    private static volatile Thread currentTaskThread;
    private static String TABLE_NAME ;
    public static void main(String args[]) throws Exception{
        String config = "config.properties";
        Resource configResource;
        if(args.length>0){
            //如果指定了路径
            config = args[0];
            configResource = ResourceLoader.getAbsoluteResource(config);
        }else{
            //如果没有，就在该项目的所在目录下
            configResource = ResourceLoader.getResourceOnJarLocaiton(SrvParamRunner.class,config);
        }
        if(args.length>1){
            TABLE_NAME = args[1];
        }
        properties.load(configResource.getInputStream());
         Handler  handler = new ServiceParametersHandler(properties);
         Resolver resolver = new ServiceParameterResolver(properties.getProperty("srvName"),properties.getProperty("args").split(","));

        long beginTime = System.currentTimeMillis();

        Thread taskThread = new Thread(new InparamParseTask(properties.getProperty("location"), handler,resolver,Integer.parseInt(properties.getProperty("count"))),"文件解析线程");
        currentTaskThread = taskThread;
        currentTaskThread.start();
        System.out.println("文件解析线程开启.....");
        try {
            currentTaskThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long parseTime = System.currentTimeMillis();
        String ret = "total spend time : "+(parseTime-beginTime)/1000+" s";
        System.out.println(ret);
    }


}
