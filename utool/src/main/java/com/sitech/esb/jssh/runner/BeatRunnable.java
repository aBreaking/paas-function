package com.sitech.esb.jssh.runner;


import com.sitech.esb.jssh.beat.*;
import com.sitech.esb.jssh.handler.db.FileLineParser;
import com.sitech.esb.jssh.handler.db.FileLineBatch2DbHandler;
import com.sitech.esb.jssh.shell.RemoteShellExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 线程运行的入口，开始filebeat
 * @author liwei_paas
 * @date 2021/1/15
 */
public class BeatRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(BeatRunnable.class);

    private String key;

    public BeatRunnable(String key) {
        this.key = key;
    }

    /**
     *
     * 通过线程池启动，key能保证不一样，所以这里不需要考虑线程安全
     */

    @Override
    public void run() {
        logger.info("任务开始，当前任务key为{}",key);
        // 线程开始，设置localkey
        boolean context = JsshLocalContext.startContext(key);
        if (!context){
            logger.warn(key+"->上一个beat线程还未结束，请等待或稍后重试");
            return;
        }
        // beat的配置信息处理
        try {
            Map<String, Map> beatsMap = JsshConfiguration.getConfigUnderJsshKey("beat");
            for (String beatName : beatsMap.keySet()){
                Map<String,Object> beatConfig = beatsMap.get(beatName);
                // 初始化handler
                FileLineHandler fileLineHandler = initHandler(beatConfig);
                logger.info("初始化{}的handler完毕",beatName);
                // 初始化FileLineBeat
                FileLineBeat beat = JsshLocalContext.getLocalFileLineBeat(beatName);
                logger.info("初始化beat完毕->"+beatName);
                //开始检测文件heartbeat
                String filePath = assertGet(beatConfig,"filePath");
                logger.info("开始检测文件内容，文件->"+filePath);
                startHeartBeat(beat,filePath,fileLineHandler);
                logger.info("文件检测完毕");
            }
            // 保存记录
            JsshLocalContext.saveLocalCache();
            logger.info("保存fileBeat记录");
        }catch (Throwable e){
            logger.error("filebeat出错",e);
        }finally {
            JsshLocalContext.localCompleted(key);
        }

    }


    public void startHeartBeat(FileLineBeat beat,String filePath,FileLineHandler handler) throws IOException {
        RemoteShellExecutor localShellExecutor = JsshLocalContext.getLocalShellExecutor();
        List<String> filePathList = localShellExecutor.execute("ls " + filePath);
        for (String file : filePathList){
            beat.heartbeat(file, handler);
        }
    }

    private FileLineHandler initHandler(Map<String,Object> beatMap) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection connection = JsshLocalContext.getLocalConnection();
        String parserClass = assertGet(beatMap,"parser");
        int lineOffset = getOrDefault(beatMap,"lineOffset",1024);
        FileLineBatch2DbHandler handler = new FileLineBatch2DbHandler(connection);
        Class<?> parserClazz = Class.forName(parserClass);
        FileLineParser parser = (FileLineParser) parserClazz.newInstance();
        handler.setFileLineParser(parser);
        handler.setBatchHandleSize(lineOffset);
        return handler;
    }

    private <T> T assertGet(Map<String,Object> map,String key){
        if (map.containsKey(key)) {
            return (T) map.get(key);
        }
        throw new RuntimeException("必须指定配置:"+key);
    }

    private <T> T getOrDefault(Map<String,Object> map,String key,T defaultValue){
        return map.containsKey(key)? (T) map.get(key) :defaultValue;
    }

}
