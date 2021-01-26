package com.sitech.esb.jbeat.runner;


import com.sitech.esb.jbeat.beat.*;
import com.sitech.esb.jbeat.shell.RemoteShellExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;


/**
 * 线程运行的入口，开始filebeat
 * @author liwei_paas
 * @date 2021/1/15
 */
public class JBeatRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(JBeatRunnable.class);

    /**
     * 用一个key来标识一个线程运行时所需的上下文信息，比如数据源信息、主机连接信息等
     */
    private RunnerKey runnerKey;

    public JBeatRunnable(RunnerKey runnerKey) {
        this.runnerKey = runnerKey;
    }

    /**
     *
     * 通过线程池启动，key能保证不一样，所以这里不需要考虑线程安全
     */
    @Override
    public void run() {
        boolean context = JBeatLocalContext.start(runnerKey);
        if (!context){
            logger.warn(runnerKey+"->上一个beat线程还未结束，请等待或稍后重试，本次操作取消");
            return;
        }
        logger.info("{}任务开始",runnerKey);
        try{
            Map<String,?> parserMap = JBeatConfiguration.getConfigUnderJBeat(runnerKey.toHostName());
            for (String parser : parserMap.keySet()){
                if (parser.equals("ssh"))continue;
                logger.info("准备使用{}解析器来解析{}下文件",parser,runnerKey.toString());
                startHeartBeat(parser);
            }
            logger.info("{}任务结束",runnerKey);
        }catch (Throwable e){
            logger.error("filbeat失败,runner key->"+runnerKey,e);
        }finally {
            JBeatLocalContext.completed(runnerKey);
        }
    }

    public void startHeartBeat(String parser){
        SshFileLineBeat fileBeat = (SshFileLineBeat) JBeatLocalContext.getLocalFileLineBeat(parser);
        FileLineHandler handler = JBeatLocalContext.getFileLineHandler(parser);
        Map<String,String> parserMap = JBeatConfiguration.getConfigUnderJBeat(runnerKey.qualifiedKeyAfterHostName(parser));
        String file = JBeatLocalContext.assertGet(parserMap, "filePath");
        RemoteShellExecutor localShellExecutor = JBeatLocalContext.getLocalShellExecutor();
        //先找beatName下面所有的file,因为file可能是单个带通配符的路径名
        List<String> filePathList = null;
        try {
            filePathList = localShellExecutor.execute("ls " + file);
        } catch (IOException e) {
            logger.warn("查询filePath所有的文件路径失败，将直接使用该路径作为文件",e);
            filePathList = Collections.singletonList(file);
        }
        if (filePathList.isEmpty()){
            logger.warn("{}主机下没有可用文件，可能配置文件信息有误,filePath配置信息为:{}",runnerKey.getHostIp(),file);
            return;
        }
        logger.info("{}主机下的文件列表:{}",runnerKey.getHostIp(),filePathList);

        FileRecordCache fileRecordCache = FileRecordCache.getFileRecordCache();

        for (String filePath : filePathList){
            String fileKey = runnerKey.toString()+"_"+filePath;
            if (fileRecordCache.isAbandoned(fileKey)){
                logger.info("{}文件已经无效，可能已经读取完毕或过期无效了",fileKey);
                // 防止重复读取无效文件
                continue;
            }
            FileRecord fileRecord = fileRecordCache.getFileRecord(fileKey);
            fileRecord.setFilePath(filePath);
            fileRecord.setStartReadTimestamp(System.currentTimeMillis());
            fileBeat.setFileRecord(fileRecord);
            int status = FileLineBeat.STATUS_NORMAL;
            for (;status == FileLineBeat.STATUS_NORMAL;){
                // 保存记录
                status = fileBeat.heartbeat(filePath,handler);
                if (status >= FileLineBeat.STATUS_ABANDON_BY_EXPIRE){
                    fileRecordCache.abandon(fileKey);
                    logger.info("{}文件已过期，将抛弃，后续不再读取",fileKey);
                }
                logger.info("{}单次读取完毕，读取记录将缓存",fileKey);
                fileRecordCache.save();
            }
        }
    }
}
