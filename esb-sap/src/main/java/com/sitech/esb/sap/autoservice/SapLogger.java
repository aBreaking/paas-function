package com.sitech.esb.sap.autoservice;

import org.apache.log4j.Logger;

/**
 * 日志的记录器
 * 目的就是用于记录服务同步过程中的日志信息。
 * 也有一个预留功能，将来考虑将该日志信息塞到某个容器里面，客户端可以通过远程异步调用的方式来获取实时的同步日志
 * @author liwei_paas
 * @date 2020/4/8
 */
public class SapLogger {
    //日志信息最终都会记录到该日志文件里面的
    private static final Logger logger = Logger.getLogger("autojob");

    private String prefix;

    private SapLogger(){}

    public static SapLogger getLogger(String prefix){
        SapLogger sapLogger = new SapLogger();
        sapLogger.setPrefix(prefix);
        return sapLogger;
    }
    public static SapLogger getLogger(){
        return new SapLogger();
    }

    public void info(String msg){
        String logMsg = prefix==null?msg:prefix+msg;
        logger.info(logMsg);
    }

    public void error(String msg,Exception e){
        String logMsg = prefix==null?msg:prefix+msg;
        if (e!=null){
            logger.error(logMsg,e);
        }else{
            logger.error(logMsg);
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
