package com.sitech.esb.jssh.beat;


import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对filerecord 的内容缓存，需要将文件的读取记录持久化，默认持久化在本地文件
 * @author liwei_paas
 * @date 2021/1/15
 */
public class FileRecordCache implements Externalizable {

    /**
     * 记录文件的读取情况，每一次的heartbeat 都会对指定文件进行一次内容读取；
     *  如果文件没有读取完，文件的读取信息（比如已经读到第几行了）将会存放在该FILE_RECORD_MAP里
     */
    private final Map<String,FileRecord> FILE_RECORD_MAP = new ConcurrentHashMap<>();

    /**
     * 用于存放已经被读取过的文件名，防止被重复读
     * FIXME 它应该有设置最大长度，或过期时间
     */
    private final Set<String> FILE_ABANDONMENT_SET = new HashSet<>();

    public FileRecord getOrStartFileRecord(String filePath){
        if (FILE_RECORD_MAP.containsKey(filePath)){
            return FILE_RECORD_MAP.get(filePath);
        }
        FileRecord fileRecord = new FileRecord();
        fileRecord.setStartReadTimestamp(System.currentTimeMillis());
        fileRecord.setFilePath(filePath);
        FILE_RECORD_MAP.put(filePath,fileRecord);
        return fileRecord;
    }

    /**
     * 文件是否过期了，或者说是否不需要检测该文件
     * @param filePath
     * @return
     */
    public boolean isAbandoned(String filePath){
        return  FILE_ABANDONMENT_SET.contains(filePath);
    }

    private void expire(String filePath,int maxKeepAliveSecond){
        FileRecord fileRecord = FILE_RECORD_MAP.get(filePath);
        Long timeDifference = System.currentTimeMillis()-fileRecord.getStartReadTimestamp();
        if (timeDifference/1000 > maxKeepAliveSecond){
            abandon(filePath);
            System.out.println("abandon filePath");
            return;
        }
    }

    /**
     * 遗弃该文件，以后都不需要再读该文件了
     * @param filePath
     */
    public void abandon(String filePath){
        FILE_RECORD_MAP.remove(filePath);
        FILE_ABANDONMENT_SET.add(filePath);
    }

    /**
     * 将两个缓存里的内容保存到本地，防止工程重启丢失了读取记录
     */
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(FILE_RECORD_MAP);
        out.writeObject(FILE_ABANDONMENT_SET);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        FILE_RECORD_MAP.putAll((Map<String, FileRecord>) in.readObject());
        FILE_ABANDONMENT_SET.addAll((Set<String>) in.readObject());
    }
}
