package com.sitech.esb.jssh.handler.db;


import com.abreaking.easyjpa.dao.EasyJpa;
import com.abreaking.easyjpa.dao.EasyJpaDao;
import com.abreaking.easyjpa.dao.impl.EasyJpaDaoImpl;
import com.abreaking.easyjpa.util.StringUtils;
import com.sitech.esb.jssh.handler.FileLineBatchHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 批量解析文件内容，然后保存到数据库
 * @author liwei_paas
 * @date 2021/1/12
 */
public class FileLineBatch2DbHandler extends FileLineBatchHandler {

    private static Logger logger = LoggerFactory.getLogger(FileLineBatch2DbHandler.class);

    // 文件每行的解析工具
    FileLineParser fileLineParser ;

    //jdbc执行工具
    EasyJpaDao easyJpaDao;

    String tableName;

    Connection connection;

    public FileLineBatch2DbHandler(Connection connection) {
        this.connection = connection;
        this.easyJpaDao = new EasyJpaDaoImpl(connection);
    }

    /**
     * 处理方式是保存到数据库,使用jdbc事务的方式
     * @param lineList
     */
    @Override
    protected void batchHandle(String filePath,List<String> lineList) {
        try{
            logger.info("准备保存到数据库表中，表格式->"+tableName);
            connection.setAutoCommit(false);
            for (String line : lineList){
                String[] split = line.split("->");
                Map<String, Object> map = fileLineParser.parse(filePath,split[1],Long.parseLong(split[0]));
                String table = fileLineParser.formatTableName(tableName,map);
                if (map == null || map.isEmpty()){
                    continue;
                }
                save2dbWithAutoTable(table,map);
            }
            connection.commit();
            logger.info("数据库保存成功");
        }catch (Exception e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
            }
            logger.error("数据库存储数据失败",e);
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
            easyJpaDao.executePlaceholderSql(EasyJpa.buildPlaceholder(map2InsertPlaceholderSql(table, map),map));
        }catch (Exception e){
            //判断是否是表名不存在的问题，如果表不存在就自动创建表,然后再执行一遍操作
            String message = e.getMessage();
            if (message!=null){
                if (e.getCause()!=null){
                    message += e.getCause().getMessage();
                }
                if (message.matches(".*Table.*doesn't exist.*") || message.indexOf("ORA-00942")!=-1){
                    synchronized (EasyJpaDao.class){
                        logger.warn("检测到{}表不存在，即将自动创建表{},而后自动再insert",table);
                        String tableSql = fileLineParser.createTableSql(table);
                        easyJpaDao.executePrepareSql(EasyJpa.buildPrepared(tableSql));
                        easyJpaDao.executePlaceholderSql(EasyJpa.buildPlaceholder(map2InsertPlaceholderSql(table, map),map));
                        logger.info("建表完毕，数据已经insert");
                        return;
                    }

                }
            }
            logger.error("插入数据到表"+table+"失败",e);
            throw new RuntimeException(e);
        }
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

        for (String column : map.keySet()){
            placeholder.append(column).append(",");
        }
        StringUtils.cutAtLastSeparator(placeholder,",");
        placeholder.append(") VALUES(");
        for (String column : map.keySet()){
            placeholder.append("#{").append(column).append("},");
        }
        StringUtils.cutAtLastSeparator(placeholder,",");
        placeholder.append(")");
        return placeholder.toString();
    }

    public void setFileLineParser(FileLineParser fileLineParser) {
        this.fileLineParser = fileLineParser;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
