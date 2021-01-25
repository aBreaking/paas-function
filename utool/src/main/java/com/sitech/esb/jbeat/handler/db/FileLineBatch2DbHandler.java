package com.sitech.esb.jbeat.handler.db;


import com.abreaking.easyjpa.dao.EasyJpa;
import com.abreaking.easyjpa.dao.EasyJpaDao;
import com.abreaking.easyjpa.dao.impl.EasyJpaDaoImpl;
import com.abreaking.easyjpa.util.StringUtils;
import com.sitech.esb.jbeat.handler.FileLineBatchHandler;
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

    //数据库链接
    Connection connection;

    public FileLineBatch2DbHandler(Connection connection) {
        this.connection = connection;
        this.easyJpaDao = new EasyJpaDaoImpl(connection);
    }

    public void setFileLineParser(FileLineParser fileLineParser) {
        this.fileLineParser = fileLineParser;
    }

    /**
     * 处理方式是保存到数据库,使用jdbc事务的方式
     * @param lineList
     */
    @Override
    protected void batchHandle(String filePath,List<String> lineList) {
        try{
            connection.setAutoCommit(false);
            for (String line : lineList){
                String[] split = line.split("->");
                Map<String, Object> map = fileLineParser.parse(filePath,split[1],Long.parseLong(split[0]));
                String table = fileLineParser.getTableName(map);
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
        if (insertWithIsNoTable(table,map)){
            synchronized (connection){
                if (insertWithIsNoTable(table,map)){
                    logger.warn("检测到{}表不存在，即将自动创建表{},而后自动再insert",table);
                    try{
                        connection.rollback();
                        autoCreateTable(table);
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                    doInsert(table,map);
                    logger.info("建表完毕，数据已经insert");
                }
            }
        }
    }

    /**
     * 插入数据，并返回是不是表不存在的问题
     * @param table
     * @param map
     * @return
     */
    private boolean insertWithIsNoTable(String table,Map<String, Object> map){
        try {
            doInsert(table,map);
            return false;
        }catch (Exception e){
            if (isTableNotExistException(e)){
                return true;
            }
            throw new RuntimeException(e);
        }
    }

    private void autoCreateTable(String table){
        String tableSql = fileLineParser.createTableSql(table);
        easyJpaDao.executePrepareSql(EasyJpa.buildPrepared(tableSql));
    }

    /**
     * 根据Map组装sql片段
     * @param table
     * @param map
     * @return
     */
    private void doInsert(String table,Map<String, Object> map){
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
        easyJpaDao.executePlaceholderSql(EasyJpa.buildPlaceholder(placeholder.toString(),map));
    }

    /**
     * 判断是不是表不存在的异常，这样可以支持创建表
     * @param e
     * @return
     */
    private boolean isTableNotExistException(Exception e){
        String message = e.getMessage();
        if (message==null){
            return false;
        }
        if (e.getCause()!=null){
            message += e.getCause().getMessage();
        }
        return message.matches(".*Table.*doesn't exist.*") || message.indexOf("ORA-00942")!=-1;
    }

}
