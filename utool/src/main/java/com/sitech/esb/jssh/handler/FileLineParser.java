package com.sitech.esb.jssh.handler;

import java.util.Map;

/**
 * 每行数据的解析器
 * @author liwei_paas
 * @date 2021/1/12
 */
public interface FileLineParser {

    /**
     * 默认解析文件每行的内容成map对象
     * @param filePath 文件
     * @param line 文件中每行的内容
     * @return
     */
    Map<String,Object> parse(String filePath,String line);

}
