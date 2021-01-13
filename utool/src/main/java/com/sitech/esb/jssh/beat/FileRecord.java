package com.sitech.esb.jssh.beat;

import java.io.*;
import java.util.Date;

/**
 * 文件读取记录
 * @author liwei_paas
 * @date 2021/1/11
 */
public class FileRecord implements Serializable {
    private String filePath;
    private int lastLineNum = 0;  // 上次读取到的行号
    private int offset; // 每次读多少行
    private int nullLineConsecutiveTimes = 0; // 连续多少几次没有读取到内容
    private Long startReadTimestamp; //文件的起始读取时间
    private Date lastReadTime; //最近一次的读取时间

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getLastLineNum() {
        return lastLineNum;
    }

    public void setLastLineNum(int lastLineNum) {
        this.lastLineNum = lastLineNum;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Long getStartReadTimestamp() {
        return startReadTimestamp;
    }

    public void setStartReadTimestamp(Long startReadTimestamp) {
        this.startReadTimestamp = startReadTimestamp;
    }

    public Date getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(Date lastReadTime) {
        this.lastReadTime = lastReadTime;
    }

    public int getNullLineConsecutiveTimes() {
        return nullLineConsecutiveTimes;
    }

    public void setNullLineConsecutiveTimes(int nullLineConsecutiveTimes) {
        this.nullLineConsecutiveTimes = nullLineConsecutiveTimes;
    }
}
