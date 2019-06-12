package com.sitech.esb.mouse.jdbc;

import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

public class JdbcOperator {


    private static final String COL_ID = "ID"; //主键

    /**
     * 每次的批量插入的insert_id
     * INSERT_ID应该与外键关联，从而保证每一次批次插入的数据insert_id是主键递增的
     */
    private static final String COL_INSERT_ID = "INSERT_ID";

    private static final String COL_INSERTTIME = "INSERT_TIME"; //记录插入时间


    public static String DIALECT = "mysql";
    private Connection connection;
    private String tableName;
    private String foreignName = "FOREIGN_ID";//可能会关联的外键，要是没有，这个就是null
    private String foreignValue;

    /**
     * 根据一个数据库的连接，指定表名，列名，进行数据库的操作
     * @param connection
     */
    public JdbcOperator(Connection connection){
        this.connection =connection;
    }

    /**
     * statement
     * @param insertSqlValues 插入语句的values的部分
     */
    public void insertBatch(List<String> insertSqlValues){
        //找出最大值，手动分配主键
        Long maxId = getMaxKey(COL_ID,null);
        Long maxInsertId = getMaxKey(COL_INSERT_ID,foreignValue);
        Long insertId = ++maxInsertId;
        Statement statement = null;
        try{
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            for (String value:insertSqlValues){
                String insertSql = parseInsertSql(value, ++maxId,insertId,foreignValue);
                System.out.println("sql:"+insertSql);
                statement.addBatch(insertSql);
            }
            int[] ints = statement.executeBatch();
            connection.commit();
            System.out.println("插入了"+ints.length+"条记录");
        }catch (SQLException e) {
            try {
                System.out.println("插入数据失败，数据已回滚");
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e);
        }finally{
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private String parseInsertSql(String statement,Long id,Long insertId,String foreignValue){
        StringBuilder builder = new StringBuilder();
        builder.append("insert into "+tableName+" values(");
        builder.append(id);
        builder.append(",");
        builder.append(insertId);
        builder.append(",");

        builder.append(foreignValue);

        builder.append(",");
        builder.append(statement);
        builder.append(",");
        String sysdate = DIALECT.equals("mysql")?"sysdate()":"sysdate";
        builder.append(sysdate);
        builder.append(")");
        return builder.toString();
    }

    //找  出最大主键的值，针对oracle主键不能自增

    /**
     * 找到某个字段的最大值
     * 如果有外键，那么这个最大值应该所关联的外键的  字段最大值。
     * @param key
     * @return
     */
    public Long getMaxKey(String key,String foreignValue)  {
        String sqlMysql = "select max("+key+") from "+tableName;
        String sqlOracle = "select max(\""+key+"\") from "+tableName;
        String sql = DIALECT.equals("mysql")?sqlMysql:sqlOracle;
        String whereSql = StringUtils.isNullOrEmpty(foreignValue) ?"":" where "+foreignName+"="+foreignValue;
        sql +=whereSql;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet.next()?resultSet.getLong(1):0;
        } catch (SQLException e) {
            throw new RuntimeException("获取最大id失败",e);
        }finally {
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isTableExist(){
        String sql = "select * from "+tableName+" where 1=2";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }finally {
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //根据动态的列的创建表
    public void createTable(String[] cols) {

        StringBuilder createTableMysql = new StringBuilder();
        createTableMysql.append("CREATE TABLE `"+tableName.toUpperCase()+"` (\n");
        createTableMysql.append("  `"+COL_ID+"` bigint(20) NOT NULL AUTO_INCREMENT,\n");
        createTableMysql.append("  `"+COL_INSERT_ID+"` bigint(13) NOT NULL ,\n");
        createTableMysql.append("  `"+foreignName.toUpperCase()+"` int DEFAULT NULL,\n");
        for(String col : cols){



            String[] c = col.split(":");
            String key = c[0];
            String type = "varchar(128)";
            if(c.length>1){
                if(c[1].matches("\\d+")){ //如果是数字
                    type = "varchar("+c[1]+")";
                }else{
                    //不然直接就是传入的类型
                    type = c[1];
                }
            }

            createTableMysql.append("  `"+key.toUpperCase()+"` "+type+" DEFAULT NULL,\n");
        }
        createTableMysql.append("  `"+COL_INSERTTIME+"` datetime DEFAULT NULL,\n");
        createTableMysql.append("  PRIMARY KEY (`"+COL_ID+"`)\n");
        createTableMysql.append(") ");
        createTableMysql.append("ENGINE=InnoDB DEFAULT CHARSET=utf8");


        StringBuilder createTableOracle = new StringBuilder();
        createTableOracle.append("CREATE TABLE "+tableName.toUpperCase()+" (\r\n");
        createTableOracle.append("  \""+COL_ID+"\" NUMBER(*,0),\r\n");
        createTableOracle.append("  \""+COL_INSERT_ID+"\" NUMBER(*,0),\r\n");
        createTableOracle.append("	\""+foreignName.toUpperCase()+"\" NUMBER,\r\n");
        for (String col : cols){
            String[] c = col.split(":");
            String key = c[0];
            String type = "VARCHAR2(128 BYTE)";
            if(c.length>1){
                if(c[1].matches("\\d+")){ //如果是数字
                    type = "VARCHAR2("+c[1]+" BYTE)";
                }else{
                    //不然直接就是传入的类型
                    type = c[1];
                }
            }
            createTableOracle.append("	\""+key.toUpperCase()+"\""+type+",\r\n");
        }
        createTableOracle.append("	\""+COL_INSERTTIME+"\" TIMESTAMP (6) \r\n");
        createTableOracle.append(")");

        String createTableSql = DIALECT.equals("mysql")?createTableMysql.toString():createTableOracle.toString();
        exec(createTableSql);

    }

    private void exec(String sql){
        Statement statement = null;
        try{
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("执行sql语句出错："+sql);
            e.printStackTrace();
        }finally {
            if (statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public String getForeignValue() {
        return foreignValue;
    }

    public void setForeignValue(String foreignValue) {
        this.foreignValue = foreignValue;
    }

}
