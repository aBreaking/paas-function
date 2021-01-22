package com.sitech.esb.jssh.runner;


import com.sitech.esb.jssh.beat.*;
import com.sitech.esb.jssh.handler.db.FileLineParser;
import com.sitech.esb.jssh.handler.db.FileLineBatch2DbHandler;
import com.sitech.esb.jssh.shell.RemoteShellExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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
            Map<String,?> map = JsshConfiguration.getConfigUnderJsshKey("beat");
            for (String beatName : map.keySet()){
                logger.info("filebeat开始，key->{},beatName->{}",key,beatName);
                // 初始化handler
                FileLineHandler fileLineHandler = JsshLocalContext.getFileLineHandler(beatName);
                // 初始化FileLineBeat
                FileLineBeat beat = JsshLocalContext.getLocalFileLineBeat(beatName);
                //开始检测文件heartbeat
                startHeartBeat(beat,beatName,fileLineHandler);
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

    public void startHeartBeat(FileLineBeat beat,String beatName,FileLineHandler handler) throws IOException {
        //先找beatName下面所有的file,因为file可能是单个带通配符的路径名
        RemoteShellExecutor localShellExecutor = JsshLocalContext.getLocalShellExecutor();
        Map<String, Object> beatMap = JsshConfiguration.getConfigUnderJsshKey("beat." + beatName);
        String filePath = JsshLocalContext.assertGet(beatMap, "filePath");
        List<String> filePathList = localShellExecutor.execute("ls " + filePath);
        for (String file : filePathList){
            logger.info("开始检测文件内容，文件->"+file);
            beat.heartbeat(file, handler);
        }
    }

}
