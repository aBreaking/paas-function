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
    private static int BATCH_HANDLE_SIZE = 1024;

    private List<String> lineList = new ArrayList<>(BATCH_HANDLE_SIZE);

    @Override
    public boolean handLine(String filePath,String line, long lineNum, boolean isLastLine) {
        if (line==null){
            return false;
        }
        lineList.add(line);
        if (!isLastLine && lineNum%BATCH_HANDLE_SIZE!=0){
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
}
