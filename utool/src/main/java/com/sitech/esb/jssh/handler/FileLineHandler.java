package com.sitech.esb.jssh.handler;

/**
 * 对文件的每行数据的处理，一般是批量进行处理
 * @author liwei_paas
 * @date 2021/1/11
 */
public interface FileLineHandler {

    /**
     * 批量处理
     * @param line 每一行的数据
     * @param lineNum 行号 一般都是从1开始
     * @param isLastLine
     * @param isLastLine 该行是否是最后一行
     * @return  是否处理完毕，
     */
    boolean handLine(String line, int lineNum, boolean isLastLine);

}
