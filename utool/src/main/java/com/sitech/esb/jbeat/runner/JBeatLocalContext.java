package com.sitech.esb.jbeat.runner;

import com.sitech.esb.jbeat.beat.FileLineBeat;
import com.sitech.esb.jbeat.beat.FileLineHandler;
import com.sitech.esb.jbeat.beat.SshFileLineBeat;
import com.sitech.esb.jbeat.handler.db.FileLineBatch2DbHandler;
import com.sitech.esb.jbeat.handler.db.FileLineParser;
import com.sitech.esb.jbeat.shell.RemoteShellExecutor;
import com.sitech.esb.jbeat.util.JBeatServiceLoader;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import java.util.function.Supplier;

/**
 * 本地线程运行期间的上下文信息
 * @author liwei_paas
 * @date 2021/1/20
 */
public class JBeatLocalContext {

    /**
     * 线程运行时保存一个key,从而可用该key获取需要的数据
     */
    private static ThreadLocal<RunnerKey> LOCAL_KEY = new ThreadLocal<>();

    /**
     * 标识该key对应的线程是否运行完毕
     */
    private static Set<RunnerKey> FLAG_OF_LOCAL_COMPLETION = Collections.synchronizedSet(new HashSet<>());


    private static Map<String,RemoteShellExecutor> CACHE_SHELL_EXECUTOR = new HashMap<>();

    private static Map<String,FileLineBeat> CACHE_FILELINEBEAT = new HashMap<>();

    private static Map<String,Connection> CACHE_CONNECTION = new HashMap<>();


    public static RunnerKey getLocalKey(){
        return LOCAL_KEY.get();
    }

    protected static boolean start(RunnerKey key){
        if (FLAG_OF_LOCAL_COMPLETION.contains(key)){
            //表示之前有线程还未结束
            return false;
        }
        FLAG_OF_LOCAL_COMPLETION.add(key);
        LOCAL_KEY.set(key);
        return true;
    }

    protected static void completed(RunnerKey key){
        FLAG_OF_LOCAL_COMPLETION.remove(key);
    }

    public static FileLineHandler getFileLineHandler(String parserName){
        Connection connection = JBeatLocalContext.getLocalConnection();
        FileLineBatch2DbHandler handler = new FileLineBatch2DbHandler(connection);
        Map<String, Object> beatMap = JBeatConfiguration.getConfigUnderJBeat(getLocalKey().qualifiedKeyAfterHostName(parserName));
        int lineOffset = (int) beatMap.getOrDefault("lineOffset",1024);
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
        RunnerKey localKey = getLocalKey();
        String key = localKey.getPool(); //它只需要用pool作为key
        return getOrDefault(CACHE_CONNECTION,key,()->{
            Map<String,String> map = JBeatConfiguration.getConfigUnderJBeat(getLocalKey().getPool()+".datasource");
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

    public static FileLineBeat getLocalFileLineBeat(String parser){
        return getLocal(CACHE_FILELINEBEAT,()->{
            SshFileLineBeat beat = new SshFileLineBeat();
            RemoteShellExecutor localShellExecutor = getLocalShellExecutor();
            beat.setRemoteShellExecutor(localShellExecutor);
            Map<String, Object> beatMap = JBeatConfiguration.getConfigUnderJBeat(getLocalKey().qualifiedKeyAfterHostName(parser));
            if (beatMap.containsKey("keepAliveSecond")){
                beat.setKeepAliveSecond((Integer) beatMap.get("keepAliveSecond"));
            }
            if (beatMap.containsKey("maxNullLineConsecutiveTimes")){
                beat.setMaxNullLineConsecutiveTimes((Integer) beatMap.get("maxNullLineConsecutiveTimes"));
            }
            return beat;
        });
    }

    public static RemoteShellExecutor getLocalShellExecutor(){
        return getLocal(CACHE_SHELL_EXECUTOR,()->{
            String toSshKey = getLocalKey().qualifiedKeyAfterHostName("ssh");
            Map<String,String> map = JBeatConfiguration.getConfigUnderJBeat(toSshKey);
            String username = assertGet(map,"username");
            if (!map.containsKey("password") && !map.containsKey("pemFile")){
                throw new JBeatConfigurationException("必须指定主机登录（密码或id_rsa）的配置:"+toSshKey+".password或:"+toSshKey+".pemFile");
            }
            String password = map.get("password");
            String pemFile = map.get("pemFile");

            RemoteShellExecutor remoteShellExecutor = new RemoteShellExecutor(getLocalKey().hostIp,username,password,pemFile);
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

    private static <T> T getLocal(Map<String,T> map,Supplier<T> supplier){
        String key = getLocalKey().toString(); //默认直接用runner字符串形式作为key
        return getOrDefault(map,key,supplier);
    }

    private static <T> T getOrDefault(Map<String,T> map,String key,Supplier<T> supplier){
        if (!map.containsKey(key)){
            T defaultValue = supplier.get();
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

}
