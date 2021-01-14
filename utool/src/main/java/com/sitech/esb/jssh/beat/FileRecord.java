package com.sitech.esb.jssh.beat;

import java.io.*;

/**
 * 文件读取记录
 * @author liwei_paas
 * @date 2021/1/11
 */
public class FileRecord implements Serializable {
    private String filePath; //文件路径
    private long lineNum = 0;  // 读取到的行号
    private int nullLineConsecutiveTimes = 0; // 连续多少几次没有读取到内容
    private Long startReadTimestamp; //文件的起始读取时间

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

}
