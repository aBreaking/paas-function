package com.sitech.esb.jbeat.beat;

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

    /**
     * 单次读取内容数据行数，注意不是大小。
     *  需要跟你实际的网络情况、每行数据大小来设置一个最优值，它（ineOffset）等于 网络每秒传送量/单行数据大小
     *  比如你网络一般，一秒钟传送1M的数据；你的文件中每行的数据有1024字节大小，那么，该值建议就是：1M/1024字节 = 1024
     *
     */
    int lineOffset();

}
