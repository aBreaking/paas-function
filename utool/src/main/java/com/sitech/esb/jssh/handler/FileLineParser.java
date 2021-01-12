package com.sitech.esb.jssh.handler;

import java.util.Map;

/**
 * 每行数据的解析器
 * @author liwei_paas
 * @date 2021/1/12
 */
public interface FileLineParser {

    /**
     * 默认解析成map对象
     * @param line
     * @return
     */
    Map<String,Object> parse(String line);

}
