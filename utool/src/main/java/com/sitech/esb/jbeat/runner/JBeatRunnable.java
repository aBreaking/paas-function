package com.sitech.esb.jbeat.runner;


import com.sitech.esb.jbeat.beat.*;
import com.sitech.esb.jbeat.shell.RemoteShellExecutor;
import com.sitech.esb.jbeat.util.RangeUtil;
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
public class JBeatRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(JBeatRunnable.class);

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
            Map<String,?> local = JBeatConfiguration.getConfigUnderLocalKey("");
            for (String hostName : local.keySet()){
                logger.info("开始检测{}下的主机文件",hostName);
                if (hostName.equals("datasource"))continue;
                Map<String,?> hostMap = (Map) local.get(hostName);
                Map<String,String> ssh = (Map) hostMap.get("ssh");
                String hosts = ssh.get("host");
                List<String> hostList = RangeUtil.parseHostIpRange(hosts);
                for (String parser : hostMap.keySet()){
                    if (parser.equals("ssh"))continue;
                    for (String hostIp : hostList){
                        startHeartBeat(hostName,hostIp,parser);
                    }
                }
            }
            // 保存记录
            JBeatLocalContext.saveLocalCache();
            logger.info("保存fileBeat记录");
        }catch (Throwable e){
            logger.error("filebeat出错",e);
        }finally {
            JBeatLocalContext.localCompleted(key);
        }
    }

    public void startHeartBeat(String hostName,String hostIp,String parser) throws IOException {
        FileLineBeat fileBeat = JBeatLocalContext.getLocalFileLineBeat(hostName, hostIp);
        FileLineHandler handler = JBeatLocalContext.getFileLineHandler(hostName, parser);
        Map<String,String> parserMap = JBeatConfiguration.getConfigUnderLocalKey(hostName + "." + parser );
        String filePath = JBeatLocalContext.assertGet(parserMap, "filePath");
        RemoteShellExecutor localShellExecutor = JBeatLocalContext.getLocalShellExecutor(hostName, hostIp);
        //先找beatName下面所有的file,因为file可能是单个带通配符的路径名
        List<String> filePathList = localShellExecutor.execute("ls " + filePath);
        if (filePathList.isEmpty()){
            logger.warn("{}主机下没有可用文件，可能配置文件信息有误,filePath配置信息为:{}",hostName+"->"+hostIp,filePath);
            return;
        }
        logger.info("{}主机下的，将使用{}解析器来解析，文件列表:{}",hostName+"->"+hostIp,parser,filePathList);
        for (String file : filePathList){
            logger.info("开始检测文件内容，文件->"+file);
            for (;fileBeat.heartbeat(file, handler) == FileLineBeat.STATUS_NORMAL;){
            }
            logger.info("{}内容检测完毕",file);
        }
    }
}
