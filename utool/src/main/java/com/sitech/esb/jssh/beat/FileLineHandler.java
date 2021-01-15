package com.sitech.esb.jssh.beat;

/**
 * 对文件的每行数据的处理，一般是批量进行处理
 * @author liwei_paas
 * @date 2021/1/11
 */
public interface FileLineHandler {

    /**
     * 批量处理
     * @param filePath 文件名
     * @param line 每一行的数据
     * @param lineNum 行号 一般都是从1开始
     * @param isLastLine
     * @param isLastLine 该行是否是最后一行
     * @return  表示该行是否已经处理完毕，
     */
    boolean handLine(String filePath,String line, long lineNum, boolean isLastLine);

}
