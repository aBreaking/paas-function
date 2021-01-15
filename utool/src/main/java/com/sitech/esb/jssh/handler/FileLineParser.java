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
     * @return 解析后的对象。如果解析错误，可能返回空对象。 因为它被用于批处理中，考虑到效率因素，故解析失败不建议抛出异常，而是返回null
     */
    Map<String,Object> parse(String filePath,String line);

}
