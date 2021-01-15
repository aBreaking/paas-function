package com.sitech.esb.jssh.executor;


import com.sitech.esb.jssh.beat.*;
import com.sitech.esb.jssh.config.JsshConfiguration;
import com.sitech.esb.jssh.handler.FileLineParser;
import com.sitech.esb.jssh.handler.db.FileLineBatch2DbHandler;
import com.sitech.esb.jssh.shell.RemoteShellExecutor;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author liwei_paas
 * @date 2021/1/15
 */
public class BeatRunnable implements Runnable {

    private String key;

    private static Map<String,RemoteShellExecutor> CACHE_SHELL_EXECUTOR = new HashMap<>();

    private static Map<String,FileRecordCache> CACHE_FILE_RECORD = new HashMap<>();


    private static ThreadLocal<String> LOCAL_KEY = new ThreadLocal<>();
    /**
     *
     * 通过线程池启动，key能保证不一样，所以这里不需要考虑线程安全
     */

    @Override
    public void run() {
        LOCAL_KEY.set(key);
        //获取所有的esb文件
        List<FileLineBeat> fileLineBeatList = getFileLineBeatList();
        for (FileLineBeat fileLineBeat : fileLineBeatList){ //这里是否有必要也用线程池呢？
            try {
                fileLineBeat.heartbeat();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileRecordCache fileRecordCache = CACHE_FILE_RECORD.get(key);
        String cacheFile = cacheFile(key);
        ObjectOutputStream objectOutputStream = null;
        try{
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(cacheFile));
            objectOutputStream.writeObject(fileRecordCache);
        }catch (Exception e){

        }finally {
            if (objectOutputStream!=null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                }
            }
        }

    }

    public static List<FileLineBeat> getFileLineBeatList(){
        String configKey = "jssh." + LOCAL_KEY.get() + ".beat";
        Map<String,Map> map = (Map) JsshConfiguration.get(configKey);
        List<FileLineBeat> list = new ArrayList<>();
        try{
            RemoteShellExecutor shellExecutor = getShellExecutor();
            for (String tkey : map.keySet()){
                Map<String,String> beatMap = map.get(tkey);
                String parser = beatMap.get("parser");
                Class parserClass = Class.forName(parser);
                Connection connection = null;  //TODO 数据源怎么管理？放在spring中吧
                FileLineBatch2DbHandler handler = new FileLineBatch2DbHandler(connection);
                handler.setFileLineParser((FileLineParser) parserClass.newInstance());
                String tableName = beatMap.containsKey("table")? beatMap.get("table") :tkey;
                handler.setTableName(formatTableName(tableName));
                String keepAliveSecond = beatMap.get("keepAliveSecond");
                String lineOffset = beatMap.get("lineOffset");
                String filePath = beatMap.get("filePath");
                List<String> filePathList = shellExecutor.execute("ls " + filePath);
                for (String f : filePathList){
                    SshFileLineBeat fileLineBeat = new SshFileLineBeat();
                    fileLineBeat.setFilePath(f);
                    fileLineBeat.setFileLineHandler(handler);
                    if (keepAliveSecond!=null)fileLineBeat.setKeepAliveSecond(Integer.parseInt(keepAliveSecond));
                    if (lineOffset!=null)fileLineBeat.setLineOffset(Integer.parseInt(lineOffset));
                    list.add(fileLineBeat);
                }

            }
        }catch (Exception e){
            throw new RuntimeException(configKey+"配置有误",e);
        }
        return list;
    }

    public static FileRecordCache getFileRecordCache(){
        return get(CACHE_FILE_RECORD,key -> {
            String cacheFile = cacheFile(key);
            File file = new File(cacheFile);
            ObjectInputStream objectInputStream = null;
            try{
                if(file.exists()){
                    objectInputStream = new ObjectInputStream(new FileInputStream(file));
                    return  (FileRecordCache) objectInputStream.readObject();
                }
            }catch (Exception e){
            }
            finally {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                }
            }
            return new FileRecordCache();
        });
    }

    public static RemoteShellExecutor getShellExecutor(){
        return get(CACHE_SHELL_EXECUTOR,key->{
            Map<String,String> o = (Map) JsshConfiguration.get("jssh." + key+".ssh");
            return new RemoteShellExecutor(o.get("host"),o.get("username"),o.get("paasword"));
        });
    }

    private static <T> T get(Map<String,T> map,Function<String,T> function){
        String key = LOCAL_KEY.get();
        if (map.containsKey(key)){
            return map.get(key);
        }
        T defaultValue = function.apply(key);
        map.put(key,defaultValue);
        return defaultValue;
    }

    private static String formatTableName(String name){
        return name;
    }

    private static String cacheFile(String key){
        return "beat-cache-file."+key+".cache";
    }

}
