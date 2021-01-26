package com.sitech.esb.jbeat.runner;


import com.sitech.esb.jbeat.beat.*;
import com.sitech.esb.jbeat.shell.RemoteShellExecutor;
import com.sitech.esb.jbeat.util.RangeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 线程运行的入口，开始filebeat
 * @author liwei_paas
 * @date 2021/1/15
 */
public class JBeatRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(JBeatRunnable.class);

    private static ThreadPoolExecutor runnerPool = newRunnerPool();

    /**
     * 用一个key来标识一个线程运行时所需的上下文信息，比如数据源信息、主机连接信息等
     */
    private String key;

    public JBeatRunnable(String key) {
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
        boolean context = JBeatLocalContext.startContext(key);
        if (!context){
            logger.warn(key+"->上一个beat线程还未结束，请等待或稍后重试");
            return;
        }
        // beat的配置信息处理
        try {
            Map<String,?> local = JBeatConfiguration.getConfigOfLocalkey();
            int countDownSize = 0;
            for (String hostName : local.keySet()){
                if (hostName.equals("datasource"))continue;
                Map<String, List> listMap = JBeatLocalContext.getHostMap(hostName);
                for (String s : listMap.keySet()){
                    countDownSize += listMap.get(s).size();
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(countDownSize);
            for (String hostName : local.keySet()){
                if (hostName.equals("datasource"))continue;
                logger.info("开始检测{}下的主机文件",hostName);
                Map<String, List> hostMap = JBeatLocalContext.getHostMap(hostName);
                List<String> hostList = hostMap.get(hostName);
                for (String hostIp : hostList){
                    runnerPool.submit(()->{
                        JBeatLocalContext.startContext(key);
                        try{
                            Map<String,?> parserMap = (Map) local.get(hostName);
                            for (String parser : parserMap.keySet()){
                                if (parser.equals("ssh"))continue;
                                logger.info("使用{}解析器来解析{}主机下文件",parser,hostIp);
                                startHeartBeat(hostName,hostIp,parser);
                            }
                        }catch (Exception e){
                            logger.error("filbeat失败,key->"+key+",host ip ->"+hostIp,e);
                        }finally {
                            countDownLatch.countDown();
                        }
                    });
                }
            }
            countDownLatch.await();
            logger.info("保存fileBeat记录");
        }catch (Throwable e){
            logger.error("filebeat出错",e);
        }finally {
            JBeatLocalContext.localCompleted(key);
        }
    }

    public void startHeartBeat(String hostName,String hostIp,String parser){
        SshFileLineBeat fileBeat = (SshFileLineBeat) JBeatLocalContext.getLocalFileLineBeat(hostName, hostIp);
        FileLineHandler handler = JBeatLocalContext.getFileLineHandler(hostName, parser);
        Map<String,String> parserMap = JBeatConfiguration.getConfigUnderLocalKey(hostName + "." + parser );
        String file = JBeatLocalContext.assertGet(parserMap, "filePath");
        RemoteShellExecutor localShellExecutor = JBeatLocalContext.getLocalShellExecutor(hostName, hostIp);
        //先找beatName下面所有的file,因为file可能是单个带通配符的路径名
        List<String> filePathList = null;
        try {
            filePathList = localShellExecutor.execute("ls " + file);
        } catch (IOException e) {
            logger.warn("查询filePath所有的文件路径失败，将直接使用该路径作为文件",e);
            filePathList = Collections.singletonList(file);
        }
        if (filePathList.isEmpty()){
            logger.warn("{}主机下没有可用文件，可能配置文件信息有误,filePath配置信息为:{}",hostName+"->"+hostIp,file);
            return;
        }
        logger.info("{}主机下的，将使用{}解析器来解析，文件列表:{}",hostName+"->"+hostIp,parser,filePathList);

        FileRecordCache fileRecordCache = FileRecordCache.getFileRecordCache();

        for (String filePath : filePathList){
            String fileKey = key +"_"+hostIp+"_"+filePath;
            if (fileRecordCache.isAbandoned(fileKey)){
                logger.info(filePath+"已经无效，可能已经读取完毕或过期无效了");
                // 防止重复读取无效文件
                continue;
            }
            FileRecord fileRecord = fileRecordCache.getOrStartFileRecord(fileKey);
            fileRecord.setFilePath(filePath);
            fileRecord.setStartReadTimestamp(System.currentTimeMillis());
            fileBeat.setFileRecord(fileRecord);
            logger.info("开始检测文件内容，文件->"+filePath);
            int status = FileLineBeat.STATUS_NORMAL;
            for (;status == FileLineBeat.STATUS_NORMAL;){
                // 保存记录
                status = fileBeat.heartbeat(filePath,handler);
                if (status >= FileLineBeat.STATUS_ABANDON_BY_EXPIRE){
                    fileRecordCache.abandon(fileKey);
                }
                fileRecordCache.save();
            }
            logger.info("{}内容检测完毕",filePath);
        }
    }

    private static ThreadPoolExecutor newRunnerPool(){
        Map<String,?> j = (Map) JBeatConfiguration.get(JBeatConfiguration.KEY_OF_JBEAT);
        int corePoolSize = 0;
        for (String pk : j.keySet()){
            Map<String,?> p = (Map) j.get(pk);
            for (String hk : p.keySet()){
                if (hk.equals("datasource"))continue;
                Map<String,?> h = (Map) p.get(hk);
                Map ssh = (Map) h.get("ssh");
                String host = (String) ssh.get("host");
                List<String> hostList = RangeUtil.parseHostIpRange(host);
                corePoolSize+=hostList.size();
            }
        }
        int maximumPoolSize = corePoolSize+1;
        return new ThreadPoolExecutor(corePoolSize,maximumPoolSize,0,TimeUnit.SECONDS,new LinkedBlockingQueue<>(),new JBeatThreadFactory("runner"));
    }
}
