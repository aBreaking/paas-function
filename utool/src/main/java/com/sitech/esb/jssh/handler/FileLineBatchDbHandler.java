package com.sitech.esb.jssh.handler;


import com.abreaking.easyjpa.dao.EasyJpa;
import com.abreaking.easyjpa.dao.EasyJpaDao;
import com.abreaking.easyjpa.util.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 批量保存到db
 * @author liwei_paas
 * @date 2021/1/12
 */
public class FileLineBatchDbHandler extends FileLineBatchHandler{

    FileLineParser fileLineParser ;

    Connection connection;

    EasyJpaDao easyJpaDao;

    String tableName;

    /**
     * 处理方式是保存到数据库
     * @param lineList
     */
    @Override
    protected void batchHandle(List<String> lineList) {
        try{
            connection.setAutoCommit(false);
            for (String line : lineList){
                Map<String, Object> map = fileLineParser.parse(line);
                save2dbWithAutoTable(tableName,map);
            }
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e = e1;
            }
            throw new RuntimeException(e);
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

}
