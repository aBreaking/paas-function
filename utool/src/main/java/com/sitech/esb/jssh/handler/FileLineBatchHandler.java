package com.sitech.esb.jssh.handler;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件内容的批量处理
 * @author liwei_paas
 * @date 2021/1/12
 */
public abstract class FileLineBatchHandler implements FileLineHandler{

    //批量处理的量
    private static int BATCH_HANDLE_SIZE = 1024;

    private List<String> lineList = new ArrayList<>(BATCH_HANDLE_SIZE);

    @Override
    public boolean handLine(String line, int lineNum, boolean isLastLine) {
        if (line==null){
            return false;
        }
        if (!isLastLine && lineNum%BATCH_HANDLE_SIZE!=0){
            lineList.add(line);
            return false;
        }
        batchHandle(lineList);
        lineList.clear();
        return true;
    }

    /**
     * 对LineList的批量处理
     * @param lineList
     */
    protected abstract void batchHandle(List<String> lineList);
}
