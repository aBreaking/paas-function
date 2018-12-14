package com.sitech.paas.bull;

import com.sitech.paas.bull.call.EsbCallResultCsvHandler;
import com.sitech.paas.bull.call.EsbCallResultDbHandler;
import com.sitech.paas.bull.call.EsbCaller;
import com.sitech.paas.bull.inparam.InparamBullResolver;
import com.sitech.paas.bull.inparam.InparamOutBullHandler;
import com.sitech.paas.bull.mq.BullTask;
import com.sitech.paas.inparam.InparamParseTask;
import com.sitech.paas.inparam.handler.Handler;
import com.sitech.paas.inparam.io.Resource;
import com.sitech.paas.inparam.io.ResourceLoader;
import com.sitech.paas.inparam.jdbc.JdbcOperate;
import com.sitech.paas.inparam.resovler.Resolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class BullRunner {

    public static void main(String args[]) throws InterruptedException, IOException {
        String defaultConfigFile = "config.properties";
        String defaultSrvArgsFile = "srv_args.properties";
        Resource configResource = args.length>1?
                ResourceLoader.getAbsoluteResource(args[0]):
                ResourceLoader.getResourceOnJarLocaiton(BullRunner.class,defaultConfigFile);
        Resource srvArgsResource = args.length>2?
                ResourceLoader.getAbsoluteResource(args[1]):
                ResourceLoader.getResourceOnJarLocaiton(BullRunner.class,defaultSrvArgsFile);
        Properties cp = new Properties();
        Properties sap = new Properties();
        //加载bull的配置文件  config.properties
        cp.load(configResource.getInputStream());
        //加载服务=参数的配置文件 srv_args.properties
        sap.load(srvArgsResource.getInputStream());

        //配置文件的信息
        String inParamLocation = cp.getProperty("inparam.location");
        String outFilePath = cp.getProperty("bull.out.inParamfile");
        int count = Integer.parseInt(cp.getProperty("bull.count"));
        String timeRange = cp.getProperty("bull.timeRange");
        String esbIpPort = cp.getProperty("bull.esb.ipPort");
        String csvFile = cp.getProperty("bull.out.csvFile");

        //srv_args配置文件的信息
        HashMap<String, String[]> map = new HashMap<String, String[]>();
        for (Object key : sap.keySet()){
            map.put(key.toString(),sap.get(key).toString().split(","));
        }
        //配置文件信息读取完毕


        Resolver resolver = new InparamBullResolver(map,timeRange);
        Handler handler = new InparamOutBullHandler(outFilePath);

        int parseCount = 1000;
        Thread InparamParseThread = new Thread(new InparamParseTask(inParamLocation,handler, resolver,parseCount),"解析入参文件线程");
        //处理入参文件开始
        InparamParseThread.start();
        System.out.println(InparamParseThread.getName()+"开始");
        InparamParseThread.join();
        System.out.println(InparamParseThread.getName()+"完毕");
        //处理入参文件结束

        //下一个线程开始，将符合条件的inparam再次调用
        EsbCaller esbCaller = new EsbCaller(esbIpPort);
        handler = new EsbCallResultCsvHandler(csvFile);

        Thread mqThread = new Thread(new BullTask(esbCaller,handler,count),"esb调用线程");
        mqThread.start();
        System.out.println(mqThread.getName()+"开始");
        mqThread.join();
        System.out.println(mqThread.getName()+"完毕");

        Thread.sleep(1000);
        System.out.println("程序结束");

    }


}
