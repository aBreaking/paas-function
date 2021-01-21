package com.sitech.esb.jssh.beat;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件读取记录
 * @author liwei_paas
 * @date 2021/1/11
 */
public class FileRecord implements Serializable {
    private String filePath; //文件路径
    private long fileSize; //文件大小，用于区分文件是否有被更新过
    private long lineNum = 0;  // 读取到的行号
    private long lineTotal; // 文件总行数
    private int nullLineConsecutiveTimes = 0; // 连续多少几次没有读取到内容
    private Long startReadTimestamp; //文件的起始读取时间
    private Date lastReadTime; //上次读取的时间
    private List<String> errorLog = new ArrayList<>(); //错误日志

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getLineNum() {
        return lineNum;
    }

    public void setLineNum(long lineNum) {
        this.lineNum = lineNum;
    }

    public int getNullLineConsecutiveTimes() {
        return nullLineConsecutiveTimes;
    }

    public void setNullLineConsecutiveTimes(int nullLineConsecutiveTimes) {
        this.nullLineConsecutiveTimes = nullLineConsecutiveTimes;
    }

    public Long getStartReadTimestamp() {
        return startReadTimestamp;
    }

    public void setStartReadTimestamp(Long startReadTimestamp) {
        this.startReadTimestamp = startReadTimestamp;
    }

    public void addErrorLog(String msg){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());
        errorLog.add(currentTime+"->"+msg);
    }
    public void addErrorLog(Exception e){
        String msg = e.getMessage();
        if (e.getCause()!=null) msg+=","+e.getCause().getMessage();
        addErrorLog(msg);
    }

    public long getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(long lineTotal) {
        this.lineTotal = lineTotal;
    }

    public Date getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(Date lastReadTime) {
        this.lastReadTime = lastReadTime;
    }

    @Override
    public String toString() {
        return "FileRecord{" +
                "filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", lineNum=" + lineNum +
                ", lineTotal=" + lineTotal +
                ", nullLineConsecutiveTimes=" + nullLineConsecutiveTimes +
                ", startReadTimestamp=" + startReadTimestamp +
                ", lastReadTime=" + lastReadTime +
                ", errorLog=" + errorLog +
                '}';
    }
}
