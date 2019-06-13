package com.sitech.paas.inparam;


import com.sitech.paas.inparam.handler.Handler;
import com.sitech.paas.inparam.inparam.InparamHandler;
import com.sitech.paas.inparam.inparam.InparamResolver;
import com.sitech.paas.inparam.resource.Resource;
import com.sitech.paas.inparam.resource.ResourceLoader;
import com.sitech.paas.inparam.resovler.Resolver;

import java.util.Properties;

public class InParamRunner {
    static Properties properties = new Properties();
    private static volatile Thread currentTaskThread;
    private static String TABLE_NAME ;
    public static void main(String args[]) throws Exception{
        String config = "config.properties";
        Resource configResource = args.length>0?ResourceLoader.getAbsoluteResource(args[0]):ResourceLoader.getResourceOnJarLocation(InParamRunner.class,config);

        if(args.length>1){
            TABLE_NAME = args[1];
        }
        properties.load(configResource.getInputStream());
         Handler  handler = new InparamHandler(properties);
         Resolver resolver = new InparamResolver(properties.getProperty("srvName"),properties.getProperty("args").split(","));

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
