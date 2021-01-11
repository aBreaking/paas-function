package com.sitech.esb.jssh.reader;

import java.util.Date;

/**
 * 文件读取记录
 * @author liwei_paas
 * @date 2021/1/11
 */
public class FileRecord {

    private String filePath;
    private int lastRow = 0;  // 上次读取到的行号
    private int offset; // 每次读多少行
    private Date startReadTime; //文件的起始读取时间
    private Date lastReadTime; //最近一次的读取时间

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Date getStartReadTime() {
        return startReadTime;
    }

    public void setStartReadTime(Date startReadTime) {
        this.startReadTime = startReadTime;
    }

    public Date getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(Date lastReadTime) {
        this.lastReadTime = lastReadTime;
    }
}
