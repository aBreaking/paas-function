package com.sitech.esb.jbeat.beat;


import com.sitech.esb.jbeat.runner.JBeatConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对filerecord 的内容缓存，需要将文件的读取记录持久化，默认持久化在本地文件
 * @author liwei_paas
 * @date 2021/1/15
 */
public class FileRecordCache implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(FileRecordCache.class);

    private static String CACHE_FILE = JBeatConfiguration.PRODUCT_PATH + "jbeat-file-record.cache";

    /**
     * 记录文件的读取情况，每一次的heartbeat 都会对指定文件进行一次内容读取；
     *  如果文件没有读取完，文件的读取信息（比如已经读到第几行了）将会存放在该FILE_RECORD_MAP里
     */
    private final Map<String,FileRecord> FILE_RECORD_MAP = new ConcurrentHashMap<>();

    /**
     * 用于存放已经被读取过的文件名，防止被重复读
     */
    private final Set<String> FILE_ABANDONMENT_SET = Collections.synchronizedSet(new HashSet<>());

    private static volatile FileRecordCache fileRecordCache;

    private FileRecordCache() {
    }

    public static FileRecordCache getFileRecordCache(){
        if (fileRecordCache == null){
            synchronized (FileRecordCache.class){
                if (fileRecordCache == null){
                    try{
                        fileRecordCache = read();
                    }catch (Exception e){
                        logger.warn("从序列化文件初始FileRecordCache失败，原因："+e.getMessage()+",将创建新对象");
                        fileRecordCache = new FileRecordCache();
                    }
                }
            }
        }
        return fileRecordCache;
    }

    public void save(){
        try{
            write(this);
        } catch (Exception e) {
            logger.error("写入缓存文件失败",e);
        }
    }

    public FileRecord getOrStartFileRecord(String key){
        if (FILE_RECORD_MAP.containsKey(key)){
            return FILE_RECORD_MAP.get(key);
        }
        FileRecord fileRecord = new FileRecord();
        FILE_RECORD_MAP.put(key,fileRecord);
        return fileRecord;
    }

    /**
     * 文件是否过期了，或者说是否不需要检测该文件
     * @param key
     * @return
     */
    public boolean isAbandoned(String key){
        return  FILE_ABANDONMENT_SET.contains(key);
    }

    /**
     * 遗弃该文件，以后都不需要再读该文件了
     * @param key
     */
    public void abandon(String key){
        FILE_RECORD_MAP.remove(key);
        FILE_ABANDONMENT_SET.add(key);
    }

    private static void write(FileRecordCache recordCache) throws Exception{
        ObjectOutputStream objectOutputStream = null;
        try{
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(CACHE_FILE));
            objectOutputStream.writeObject(recordCache);
        }catch (FileNotFoundException fe) { //文件不存在，那么就重新创建新对象
            if (objectOutputStream!=null){
                objectOutputStream.close();
            }
            File file = new File(CACHE_FILE);
            synchronized (CACHE_FILE){ // 加锁，防止多线程重复创建文件
                if (!file.exists()){
                    logger.warn(CACHE_FILE+"缓存文件不存在，将创建新文件");
                    file.createNewFile();
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(CACHE_FILE));
                    objectOutputStream.writeObject(recordCache);
                }
            }
        }finally {
            if (objectOutputStream!=null){
                objectOutputStream.close();
            }
        }
    }

    private static FileRecordCache read() throws IOException,ClassNotFoundException{
        ObjectInputStream objectInputStream = null;
        try{
            objectInputStream = new ObjectInputStream(new FileInputStream(CACHE_FILE));
            return  (FileRecordCache) objectInputStream.readObject();
        }finally {
            if (objectInputStream!=null){
                objectInputStream.close();
            }
        }
    }


    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        FILE_RECORD_MAP.putAll((Map<String, FileRecord>) in.readObject());
        FILE_ABANDONMENT_SET.addAll((Set<String>) in.readObject());
    }
}
