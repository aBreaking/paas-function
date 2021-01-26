package com.sitech.esb.jbeat.runner;

import com.sitech.esb.jbeat.beat.FileLineBeat;
import com.sitech.esb.jbeat.beat.FileLineHandler;
import com.sitech.esb.jbeat.beat.SshFileLineBeat;
import com.sitech.esb.jbeat.handler.db.FileLineBatch2DbHandler;
import com.sitech.esb.jbeat.handler.db.FileLineParser;
import com.sitech.esb.jbeat.shell.RemoteShellExecutor;
import com.sitech.esb.jbeat.util.JBeatServiceLoader;
import com.sitech.esb.jbeat.util.RangeUtil;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import java.util.function.Function;

/**
 * 本地线程运行期间的上下文信息
 * @author liwei_paas
 * @date 2021/1/20
 */
public class JBeatLocalContext {

    private static InheritableThreadLocal<String> LOCAL_KEY = new InheritableThreadLocal<>();

    private static Map<String,RemoteShellExecutor> CACHE_SHELL_EXECUTOR = new HashMap<>();

    private static Map<String,FileLineBeat> CACHE_FILELINEBEAT = new HashMap<>();

    private static Map<String,Connection> CACHE_CONNECTION = new HashMap<>();

    protected static Map<String,Map<String,List>> CACHE_HOST_MAP = new HashMap<>();

    private static Set<String> FLAG_OF_LOCAL_COMPLETION = Collections.synchronizedSet(new HashSet<>());

    public static String getLocalKey(){
        return LOCAL_KEY.get();
    }

    protected static boolean startContext(String key){
        if (FLAG_OF_LOCAL_COMPLETION.contains(key)){
            //表示之前有线程还未结束
            return false;
        }
        FLAG_OF_LOCAL_COMPLETION.add(key);
        LOCAL_KEY.set(key);
        return true;
    }

    protected static void localCompleted(String key){
        FLAG_OF_LOCAL_COMPLETION.remove(key);
    }

    public static Map<String,List> getHostMap(String hostName){
        return getLocal(CACHE_HOST_MAP,hostName,key->{
            Map<String,List> ret = new HashMap<>();
            Map<String,String> map = JBeatConfiguration.getConfigUnderLocalKey(hostName+".ssh");
            String host = map.get("host");
            List<String> list = RangeUtil.parseHostIpRange(host);
            ret.put(hostName,list);
            return ret;
        });
    }

    public static FileLineHandler getFileLineHandler(String hostName,String parserName){
        Connection connection = JBeatLocalContext.getLocalConnection();
        FileLineBatch2DbHandler handler = new FileLineBatch2DbHandler(connection);
        Map<String, Object> beatMap = JBeatConfiguration.getConfigUnderLocalKey(hostName+"."+parserName);
        int lineOffset = getOrDefault(beatMap,"lineOffset",1024);
        handler.setBatchHandleSize(lineOffset);
        try{
            FileLineParser lineParser = JBeatServiceLoader.load(FileLineParser.class, parserName);
            handler.setFileLineParser(lineParser);
            return handler;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static Connection getLocalConnection(){
        return getLocal(CACHE_CONNECTION,key->{
            Map<String,String> map = JBeatConfiguration.getConfigUnderLocalKey("datasource");
            String url = assertGet(map,"url");
            String driver = assertGet(map,"driver");
            String username = assertGet(map,"username");
            String password = assertGet(map,"password");
            try{
                Class.forName(driver);
                return DriverManager.getConnection(url,username,password);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        });
    }

    public static FileLineBeat getLocalFileLineBeat(String hostName,String hostIp){
        String beatKey = hostName+"."+hostIp;
        return getLocal(CACHE_FILELINEBEAT,beatKey,key->{
            SshFileLineBeat beat = new SshFileLineBeat();
            RemoteShellExecutor localShellExecutor = getLocalShellExecutor(hostName, hostIp);
            beat.setRemoteShellExecutor(localShellExecutor);
            Map<String, Object> beatMap = JBeatConfiguration.getConfigUnderLocalKey(hostName);
            if (beatMap.containsKey("keepAliveSecond")){
                beat.setKeepAliveSecond((Integer) beatMap.get("keepAliveSecond"));
            }
            if (beatMap.containsKey("maxNullLineConsecutiveTimes")){
                beat.setMaxNullLineConsecutiveTimes((Integer) beatMap.get("maxNullLineConsecutiveTimes"));
            }
            return beat;
        });
    }

    public static RemoteShellExecutor getLocalShellExecutor(String hostName,String hostIp){
        return getLocal(CACHE_SHELL_EXECUTOR,hostIp,key->{
            Map<String,String> map = JBeatConfiguration.getConfigUnderLocalKey(hostName+".ssh");
            String username = assertGet(map,"username");
            if (!map.containsKey("password") && !map.containsKey("pemFile")){
                String keyPrefix = JBeatConfiguration.getConfigNameUnderLocalKey(hostName+".ssh");
                throw new JBeatConfigurationException("必须指定主机登录（密码或id_rsa）的配置:"+keyPrefix+".password或:"+keyPrefix+".pemFile");
            }
            String password = map.get("password");
            String pemFile = map.get("pemFile");

            RemoteShellExecutor remoteShellExecutor = new RemoteShellExecutor(hostIp,username,password,pemFile);
            if (!remoteShellExecutor.hasLogin()){
                try {
                    remoteShellExecutor.login();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return remoteShellExecutor;
        });
    }

    private static <T> T getLocal(Map<String,T> map,Function<String,T> function){
        return getLocal(map,"",function);
    }

    private static <T> T getLocal(Map<String,T> map,String keyOfLocal,Function<String,T> function){
        String key = keyOfLocal==null || keyOfLocal.isEmpty()?getLocalKey():getLocalKey()+"."+keyOfLocal;
        if (!map.containsKey(key)){
            T defaultValue = function.apply(key);
            map.put(key,defaultValue);
            return defaultValue;
        }
        return map.get(key);
    }

    public static  <T> T assertGet(Map<String,T> map,String key){
        if (map.containsKey(key)) {
            return map.get(key);
        }
        throw new RuntimeException("必须指定配置:"+key);
    }

    private static  <T> T getOrDefault(Map<String,Object> map,String key,T defaultValue){
        return map.containsKey(key)? (T) map.get(key) :defaultValue;
    }

}
