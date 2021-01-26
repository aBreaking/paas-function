package com.sitech.esb.jbeat;

import com.sitech.esb.jbeat.runner.JBeatRunnable;
import com.sitech.esb.jbeat.runner.JBeatConfiguration;
import com.sitech.esb.jbeat.runner.JBeatThreadFactory;
import com.sitech.esb.jbeat.runner.RunnerKey;
import com.sitech.esb.jbeat.util.RangeUtil;

import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author liwei_paas
 * @date 2021/1/11
 */
public class JBeatMain {

    static ExecutorService threadPool;

    static Map<String,Map<String,List>> hostMap ;

    static int DEFAULT_INTERVAL = 30*1000;

    public static void main(String[] args) throws Exception {
        int interval = args.length!=0?Integer.parseInt(args[0]) * 1000 : DEFAULT_INTERVAL;
        startup(interval);
    }

    public static void startup(int interval) throws Exception{
        JBeatConfiguration.initConfiguration();
        calculateHostMapAndInitThreadPool();
        while (true){
            for (String pool : hostMap.keySet()){
                Map<String, List> hostNameMap = hostMap.get(pool);
                for (String hostName : hostNameMap.keySet()){
                    List<String> list = hostNameMap.get(hostName);
                    for (String hostIp : list){
                        RunnerKey runnerKey = new RunnerKey();
                        runnerKey.setPool(pool);
                        runnerKey.setHostIp(hostIp);
                        runnerKey.setHostName(hostName);
                        threadPool.submit(new JBeatRunnable(runnerKey));
                    }
                }
            }
            Thread.sleep(interval);
        }

    }

    public static void calculateHostMapAndInitThreadPool(){
        hostMap = new LinkedHashMap<>();
        int corePoolSize = 0;
        Map<String,?> j = (Map) JBeatConfiguration.get(JBeatConfiguration.KEY_OF_JBEAT);
        for (String pk : j.keySet()){
            Map<String,List> map = new HashMap<>();
            hostMap.put(pk,map);
            Map<String,?> p = (Map) j.get(pk);
            for (String hk : p.keySet()){
                if (hk.equals("datasource"))continue;
                Map<String,?> h = (Map) p.get(hk);
                Map ssh = (Map) h.get("ssh");
                String host = (String) ssh.get("host");
                List<String> hostList = RangeUtil.parseHostIpRange(host);
                map.put(hk,hostList);
                corePoolSize += hostList.size();
            }
        }
        threadPool = Executors.newFixedThreadPool(corePoolSize,new JBeatThreadFactory("main"));
        System.out.println("线程池初始化完毕，核心线程数量为"+corePoolSize);
    }
}
