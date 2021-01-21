package com.sitech.esb.jssh.runner;

import com.sitech.esb.jssh.beat.FileLineBeat;
import com.sitech.esb.jssh.beat.FileRecordCache;
import com.sitech.esb.jssh.beat.SshFileLineBeat;
import com.sitech.esb.jssh.shell.RemoteShellExecutor;

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
public class JsshLocalContext {

    private static Map<String,RemoteShellExecutor> CACHE_SHELL_EXECUTOR = new HashMap<>();

    private static Map<String,FileRecordCache> CACHE_FILERECORD = new HashMap<>();

    private static Map<String,FileLineBeat> CACHE_FILELINEBEAT = new HashMap<>();

    private static Map<String,Connection> CACHE_CONNECTION = new HashMap<>();

    private static ThreadLocal<String> LOCAL_KEY = new ThreadLocal<>();

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

    public static Connection getLocalConnection(){
        return getLocal(CACHE_CONNECTION,key->{
            Map<String,String> map = JsshConfiguration.getConfigUnderJsshKey("datasource");
            String url = map.get("url");
            String driver = map.get("driver");
            String username = map.get("username");
            String password = map.get("password");
            try{
                Class.forName(driver);
                return DriverManager.getConnection(url,username,password);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        });
    }

    public static FileLineBeat getLocalFileLineBeat(String beatName){
        return getLocal(CACHE_FILELINEBEAT,LOCAL_KEY.get()+"."+beatName,beatKey->{
            SshFileLineBeat beat = new SshFileLineBeat();
            Map<String, Object> beatMap = JsshConfiguration.getConfigUnderJsshKey("beat."+beatName);
            if (beatMap.containsKey("keepAliveSecond")){
                beat.setKeepAliveSecond((Integer) beatMap.get("keepAliveSecond"));
            }
            if (beatMap.containsKey("lineOffset")){
                beat.setLineOffset((Integer) beatMap.get("lineOffset"));
            }
            if (beatMap.containsKey("maxNullLineConsecutiveTimes")){
                beat.setMaxNullLineConsecutiveTimes((Integer) beatMap.get("maxNullLineConsecutiveTimes"));
            }
            return beat;
        });

    }

    public static FileRecordCache getLocalFileRecordCache(){
        return getLocal(CACHE_FILERECORD, key -> {
            String cacheFile = JsshConfiguration.getCacheFilePath(key);
            File file = new File(cacheFile);
            ObjectInputStream objectInputStream = null;
            try{
                if(file.exists()){
                    objectInputStream = new ObjectInputStream(new FileInputStream(file));
                    return  (FileRecordCache) objectInputStream.readObject();
                }
            }catch (Exception e){
            }finally {
                if (objectInputStream!=null){
                    try {
                        objectInputStream.close();
                    } catch (IOException e) {
                    }
                }
            }
            return new FileRecordCache();
        });
    }

    public static void saveLocalCache(){
        String key = LOCAL_KEY.get();
        FileRecordCache fileRecordCache = CACHE_FILERECORD.get(key);
        String cacheFile = JsshConfiguration.getCacheFilePath(key);
        ObjectOutputStream objectOutputStream = null;
        try{
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(cacheFile));
            objectOutputStream.writeObject(fileRecordCache);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (objectOutputStream!=null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static RemoteShellExecutor getLocalShellExecutor(){
        return getLocal(CACHE_SHELL_EXECUTOR,key->{
            Map<String,String> o = (Map) JsshConfiguration.get("jssh." + key+".ssh");
            RemoteShellExecutor remoteShellExecutor = new RemoteShellExecutor(o.get("host"), o.get("username"), o.get("password"));
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
        return getLocal(map,LOCAL_KEY.get(),function);
    }
    private static <T> T getLocal(Map<String,T> map,String key,Function<String,T> function){
        if (!map.containsKey(key)){
            T defaultValue = function.apply(key);
            map.put(key,defaultValue);
            return defaultValue;
        }
        return map.get(key);
    }


}
