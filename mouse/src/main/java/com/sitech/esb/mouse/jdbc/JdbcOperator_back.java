package com.sitech.esb.mouse.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcOperator_back {


    private static final String COL_ID = "id"; //主键
    private static final String COL_INSERTTIME = "insert_time"; //记录插入时间

    public static String DIALECT = "mysql";
    private Connection connection;
    private String tableName ;
    private String[] cols;

    /**
     * 根据一个数据库的连接，指定表名，列名，进行数据库的操作
     * @param connection
     * @param tableName
     * @param cols
     */
    public JdbcOperator_back(Connection connection, String tableName, String[] cols){
        this.connection =connection;
        this.tableName = tableName;
        this.cols = cols;
    }

    /**
     * statement
     * @param values
     */
    public void insertBatch(List<String> values,int hadoopId){
        //找出最大值，手动分配主键
        Long maxId = getMaxId();
        Statement statement = null;
        try{
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            for (String value:values){
                String insertSql = parseInsertSql(value, ++maxId,hadoopId);
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

    private String parseInsertSql(String statement,Long id,int clusterId){
        StringBuilder builder = new StringBuilder();
        builder.append("insert into "+tableName+" values(");
        builder.append(id);
        builder.append(",");
        builder.append(clusterId);
        builder.append(",");
        builder.append(statement);
        builder.append(",");
        String sysdate = DIALECT.equals("mysql")?"sysdate()":"sysdate";
        builder.append(sysdate);
        builder.append(")");
        return builder.toString();
    }

    //找出最大主键的值，针对oracle主键不能自增
    public Long getMaxId()  {
        String sqlMysql = "select max("+COL_ID+") from "+tableName;
        String sqlOracle = "select max(\""+COL_ID+"\") from "+tableName;
        String sql = DIALECT.equals("mysql")?sqlMysql:sqlOracle;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            return resultSet.next()?resultSet.getLong(1):0;
        } catch (SQLException e) {
            throw new RuntimeException("获取最大id失败",e);
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
    public void createTable() {
        StringBuilder createTableMysql = new StringBuilder();
        createTableMysql.append("CREATE TABLE `"+tableName+"` (\n");
        createTableMysql.append("  `"+COL_ID+"` bigint(20) NOT NULL AUTO_INCREMENT,\n");
        createTableMysql.append("  `hadoop_id` int DEFAULT NULL,\n");
        for(String col : cols){
            createTableMysql.append("  `"+col+"` varchar(255) DEFAULT NULL,\n");
        }
        createTableMysql.append("  `"+COL_INSERTTIME+"` datetime DEFAULT NULL,\n");
        createTableMysql.append("  PRIMARY KEY (`"+COL_ID+"`)\n");
        createTableMysql.append(") ");

        StringBuilder createTableOracle = new StringBuilder();
        createTableOracle.append("CREATE TABLE "+tableName+" (\r\n");
        createTableOracle.append("  \""+COL_ID+"\" NUMBER(*,0),\r\n");
        createTableOracle.append("	\"hadoop_id\" NUMBER,\r\n");
        for (String col : cols){
            createTableOracle.append("	\""+col+"\" VARCHAR2(255 BYTE),\r\n");
        }
        createTableOracle.append("	\""+COL_INSERTTIME+"\" TIMESTAMP (6) \r\n");
        createTableOracle.append(")");
        String createTableSql = DIALECT.equals("mysql")?createTableMysql.toString():createTableOracle.toString();
        Statement statement = null;
        try {
            System.out.println("创建了表"+tableName+"............");
            statement = connection.createStatement();
            statement.execute(createTableSql);
        }catch (Exception e){
            throw new RuntimeException("创建表失败",e);
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


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String[] getCols() {
        return cols;
    }

    public void setCols(String[] cols) {
        this.cols = cols;
    }
}
