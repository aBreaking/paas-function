package com.sitech.esb.jssh.beat;

import com.sitech.esb.jssh.handler.FileLineHandler;
import com.sitech.esb.jssh.shell.RemoteShellExecutor;
import com.sitech.esb.jssh.shell.ShellExecutorFactory;

import java.io.*;
import java.util.*;

/**
 * 用来探测文件内容是否有更新，可对批量的文件进行探测，类似elk的FileBeat
 * 对文件内容可以使用解析器来解析
 * 本工具对文件的读取记录不会丢失，它会将文件的读取记录通过序列话的方式保存在指定位置
 * @author liwei_paas
 * @date 2021/1/12
 */
public class FileBeat implements Externalizable {

    private static int MAX_KEEP_ALIVE_TIME = 60*60; //文件最大保持检测时间

    private static int DEFAULT_READ_DATA_SIZE = 1*1024*1024; //单次读取内容数据大小，跟网络、内存大小而定

    /**
     * 记录文件的读取情况，每一次的heartbeat 都会对指定文件进行一次内容读取；
     *  如果文件没有读取完，文件的读取信息（比如已经读到第几行了）将会存放在该FILE_RECORD_MAP里
     */
    private final Map<String,FileRecord> FILE_RECORD_MAP = new HashMap<>();

    /**
     * 用于存放已经被读取过的文件名，防止被重复读
     * FIXME 它应该有设置最大长度，或过期时间
     */
    private final Set<String> FILE_ABANDONMENT_SET = new HashSet<>();


    private int rowOffset = 0; //默认的读取行数

    FileLineHandler fileLineHandler; // 处理文件里每行数据的方法

    private volatile static FileBeat fileBeat ;

    private FileBeat() {
    }

    public static FileBeat getInstance(){
        if (fileBeat==null){
            synchronized (fileBeat){
                if (fileBeat==null){
                    fileBeat = new FileBeat();
                }
            }
        }
        return fileBeat;
    }


    public void heartbeat(String filePath) throws IOException {
        // 就算一秒读取一次，都这么久了也没读取到内容，也该不要了吧
        heartbeat(filePath,Integer.MAX_VALUE);
    }

    /**
     * 对文件进行一次心跳检测，读取文件里的内容，并可以通过fileLineHandler对读到的内容进行处理
     * 每个文件的最大心跳保持时间不会超过MAX_KEEP_ALIVE_TIME，已读过的文件将会被放入垃圾箱FILE_COMPLETE_SET
     * @param filePath 文件位置（一般是文件的全路径名）
     * @param maxNullLineConsecutiveTimes 多少次没有读取到数据就被遗弃
     * @throws IOException
     */
    public void heartbeat(String filePath,int maxNullLineConsecutiveTimes) throws IOException {
        if (FILE_ABANDONMENT_SET.contains(filePath)){
            // 防止重复读取
            return;
        }
        FileRecord fileRecord;
        if (FILE_RECORD_MAP.containsKey(filePath)){
            fileRecord = FILE_RECORD_MAP.get(filePath);
            Long timeDifference = System.currentTimeMillis()-fileRecord.getStartReadTimestamp();
            if (timeDifference/1000 > MAX_KEEP_ALIVE_TIME){
                abandon(filePath);
                return;
            }
        }else{
            fileRecord = new FileRecord();
            fileRecord.setStartReadTimestamp(System.currentTimeMillis());
            fileRecord.setOffset(offset(filePath));
            FILE_RECORD_MAP.put(filePath,fileRecord);
        }
        FileReader fileReader = new FileReader();
        fileReader.readAndHandleLine(fileRecord, fileLineHandler);
        fileRecord.setLastReadTime(new Date());
        if (fileRecord.getNullLineConsecutiveTimes()>=maxNullLineConsecutiveTimes){
            //连续了n次都没有读取到内容，也就abandon该文件了
            abandon(filePath);
        }
    }

    private void abandon(String filePath){
        FILE_RECORD_MAP.remove(filePath);
        FILE_ABANDONMENT_SET.add(filePath);
    }

    /**
     * 文件每次应该读取的行数计算。
     * 如果有指定默认值，那么直接使用该值即可
     * 如果没有，那么根据文件大小以及fileRecord算出来,算法为： （指定的单次读取大小 * 当前文件总行数） / 当前文件大小
     * @param filePath
     * @return
     * @throws IOException
     */
    private int offset(String filePath) {
        // 获取文件总行数，大文件可能这个速度优点慢
        int offset = rowOffset;
        if (offset!=0){
            return offset;
        }
        RemoteShellExecutor remoteShellExecutor = ShellExecutorFactory.getLoggedShellExecutor();
        try{
            int fileSize = Integer.parseInt(remoteShellExecutor.executeAndGetSingle("ls -lt " + filePath + " | awk '{print $5}'")); //文件大小
            if (fileSize > 1*1024*1024*1024){ //文件过大读取行数可能会超时，所以就不用换再去读行数了，直接给定一个值把
                return DEFAULT_READ_DATA_SIZE/512;
            }else{
                int totalRow = Integer.parseInt(remoteShellExecutor.executeAndGetSingle("wc -l "+filePath)); // 文件总行数
                return  (DEFAULT_READ_DATA_SIZE*totalRow)/fileSize;
            }
        }catch (IOException e){
            return 1024;
        }
    }

    /**
     * 将两个缓存里的内容保存到本地，防止工程重启丢失了读取记录
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(FILE_RECORD_MAP);
        out.writeObject(FILE_ABANDONMENT_SET);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        FILE_RECORD_MAP.putAll((Map<String, FileRecord>) in.readObject());
        FILE_ABANDONMENT_SET.addAll((Set<String>) in.readObject());
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public void setRowOffset(int rowOffset) {
        this.rowOffset = rowOffset;
    }

    public FileLineHandler getFileLineHandler() {
        return fileLineHandler;
    }

    public void setFileLineHandler(FileLineHandler fileLineHandler) {
        this.fileLineHandler = fileLineHandler;
    }
}
