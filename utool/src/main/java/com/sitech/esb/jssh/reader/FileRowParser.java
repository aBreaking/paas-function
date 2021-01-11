package com.sitech.esb.jssh.reader;

/**
 * 对文件的每行数据的解析器
 * @author liwei_paas
 * @date 2021/1/11
 */
public interface FileRowParser {

    void parse(String row);
}
