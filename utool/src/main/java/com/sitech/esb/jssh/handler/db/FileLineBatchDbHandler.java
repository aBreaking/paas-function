package com.sitech.esb.jssh.handler.db;


import com.abreaking.easyjpa.dao.EasyJpa;
import com.abreaking.easyjpa.dao.EasyJpaDao;
import com.abreaking.easyjpa.dao.impl.EasyJpaDaoImpl;
import com.abreaking.easyjpa.util.StringUtils;
import com.sitech.esb.jssh.handler.FileLineBatchHandler;
import com.sitech.esb.jssh.handler.FileLineParser;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * 批量解析文件内容，然后保存到数据库
 * @author liwei_paas
 * @date 2021/1/12
 */
public class FileLineBatchDbHandler extends FileLineBatchHandler {

    // 文件每行的解析工具
    FileLineParser fileLineParser ;

    //jdbc执行工具
    EasyJpaDao easyJpaDao;

    String tableName;

    public FileLineBatchDbHandler(Connection connection) {
        this.easyJpaDao = new EasyJpaDaoImpl(connection);
    }

    /**
     * 处理方式是保存到数据库
     * @param lineList
     */
    @Override
    protected void batchHandle(String filePath,List<String> lineList) {
        for (String line : lineList){
            Map<String, Object> map = fileLineParser.parse(filePath,line);
            save2dbWithAutoTable(tableName,map);
        }
    }

    /**
     * 保存到数据库，并且支持自动创建表
     * @param table
     * @param map
     */
    private void save2dbWithAutoTable(String table,Map<String, Object> map){
        try{
            String placeholderSql = map2InsertPlaceholderSql(table, map);
            easyJpaDao.executePlaceholderSql(EasyJpa.buildPlaceholder(placeholderSql,map));
        }catch (Exception e){
            //判断是否是表名不存在的问题，如果表不存在就自动创建表,然后再执行一遍操作
            String message = e.getMessage();
            if (message.matches(".*Table.*doesn't exist.*") || message.indexOf("ORA-00942")!=-1){
                createTable(table,map);
                String placeholderSql = map2InsertPlaceholderSql(table, map);
                easyJpaDao.executePlaceholderSql(EasyJpa.buildPlaceholder(placeholderSql,map));
            }else{
                throw new RuntimeException(e);
            }
        }

    }

    private void createTable(String table,Map<String,Object> map){

    }

    /**
     * 根据Map组装sql片段
     * @param table
     * @param map
     * @return
     */
    private String map2InsertPlaceholderSql(String table,Map<String, Object> map){
        StringBuilder placeholder = new StringBuilder("INSERT INTO ");
        placeholder.append(table);
        placeholder.append("(");

        for (String key : map.keySet()){
            String column = StringUtils.underscoreName(key);
            placeholder.append(column);
            placeholder.append(",");
        }
        StringUtils.cutAtLastSeparator(placeholder,",");
        placeholder.append(") VALUES(");
        for (String column : map.keySet()){
            placeholder.append(column);
            placeholder.append("#{").append(map.get(column)).append(")");
        }
        StringUtils.cutAtLastSeparator(placeholder,",");
        placeholder.append(")");
        return placeholder.toString();
    }

    public FileLineParser getFileLineParser() {
        return fileLineParser;
    }

    public void setFileLineParser(FileLineParser fileLineParser) {
        this.fileLineParser = fileLineParser;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
