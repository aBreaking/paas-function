package com.sitech.esb.jssh.handler;

import com.sitech.esb.jssh.beat.FileLineHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件内容的批量处理
 * @author liwei_paas
 * @date 2021/1/12
 */
public abstract class FileLineBatchHandler implements FileLineHandler {

    //批量处理的量
    private int batchHandleSize ;

    private List<String> lineList ;

    public FileLineBatchHandler() {
        this(1024);
    }

    public FileLineBatchHandler(int batchHandleSize) {
        this.batchHandleSize = batchHandleSize;
        this.lineList = new ArrayList<>(batchHandleSize);
    }

    @Override
    public boolean handLine(String filePath,String line, long lineNum, boolean isLastLine) {
        if (line==null || line.trim().isEmpty()){
            return false;
        }
        lineList.add(lineNum+"->"+line); //记录行号及行内容
        if (!isLastLine && lineNum%batchHandleSize!=0){
            return false;
        }
        batchHandle(filePath,lineList);
        lineList.clear();
        return true;
    }

    /**
     * 文件读取到的内容批量处理
     * @param filePath
     * @param lineList
     */
    protected abstract void batchHandle(String filePath,List<String> lineList);

    public int getBatchHandleSize() {
        return batchHandleSize;
    }

    public void setBatchHandleSize(int batchHandleSize) {
        this.batchHandleSize = batchHandleSize;
    }
}
