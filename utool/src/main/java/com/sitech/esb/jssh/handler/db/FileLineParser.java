package com.sitech.esb.jssh.handler.db;

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
     * @param lineNum 该行的行号
     * @return 解析后的对象。如果解析错误，可能返回空对象。 因为它被用于批处理中，考虑到效率因素，故解析失败不建议抛出异常，而是返回null
     */
    Map<String,Object> parse(String filePath,String line,long lineNum);

    /**
     * 格式化表名，可能给定的表名格式是带格式化字符串的，比如时间，需要将其进行格式化
     * @param parsedMap 解析后的value
     * @return
     */
    String getTableName(Map<String,Object> parsedMap);

    /**
     * 针对动态表，可以指定创建表的sql，这样就支持动态创建表了
     */
    String createTableSql(String table);
}
