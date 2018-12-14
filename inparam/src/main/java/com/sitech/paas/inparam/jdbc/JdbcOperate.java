package com.sitech.paas.inparam.jdbc;

import com.sitech.paas.inparam.db.ServiceParameters;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class JdbcOperate{

    private volatile static  String DIALECT = "oracle";
    private Connection connection ;
    String url ;
    String username;
    String password;
    private String tableName;

    public JdbcOperate(){
    }
    public JdbcOperate(String tableName){
        this.tableName = tableName;
    }

    public void openConnection(){
        openConnection(url,username,password);
    }

    public void openConnection(String url,String username,String password){
        try {
            if(connection==null||connection.isClosed()) {
                JdbcHelper jdbcHelper = new JdbcHelper();
                connection=jdbcHelper.getConnection(url,username,password);
                DIALECT = jdbcHelper.getDialect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isTableExist(){
        String sql = "select * from "+tableName+ " where 1=2";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(sql);
            System.out.println(tableName+"表存在");
            return true;
        } catch (SQLException e) {
            System.out.println(tableName+"表不存在");
            return  false;
        }finally {
            closeStatement(statement);
        }

    }

    public void saveAllColValue(List<Map<String,String>> list){
        Statement statement = null;
        try{
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            for (Map<String,String> colValue:list){
                String sql = parseSql(colValue);
                statement.addBatch(sql);
            }
            statement.executeBatch();
            connection.commit();
        }catch (Exception e){
            System.err.println("保存失败");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            closeStatement(statement);
        }
    }

    public void saveAll(List<ServiceParameters> list){
        Statement statement = null;
        try{
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            for (ServiceParameters serviceParameters:list){
                String sql = parseSql(serviceParameters);
                statement.addBatch(sql);
            }
            statement.executeBatch();
            connection.commit();
        }catch (Exception e){
            System.err.println("保存失败");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            closeStatement(statement);
        }

    }

    public void dropTable() {

        String dropMysql = "DROP TABLE IF EXISTS "+tableName+";";
        String dropOracle = "DROP TABLE "+tableName;
        String dropSql = DIALECT.equals(JdbcHelper.DIALECT_MYSQL)?dropMysql:dropOracle;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(dropSql);
            System.out.println("删除了表"+tableName+".............");
        }catch (Exception e){
            System.out.println("删除表失败");
            e.printStackTrace();
        }finally {
            closeStatement(statement);
        }
    }

    /**
     * 通过指定了列来创建表
     * @param cols
     */
    public void createTable(Map<String,Integer> cols){
        StringBuilder createTableMysqlBuilder = new StringBuilder("CREATE TABLE `" + tableName + "` (\n");
        createTableMysqlBuilder.append("  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n");

        StringBuilder createTableOracleBuilder = new StringBuilder("CREATE TABLE "+tableName+" (\r\n");

        for(String colName : cols.keySet()){
            Integer size = cols.get(colName)==null?32:cols.get(colName);
            createTableMysqlBuilder.append("`"+colName+"` varchar("+size+") DEFAULT NULL,\n");
            createTableOracleBuilder.append("\""+colName+"\" VARCHAR2("+size+" BYTE),\r\n");
        }
        createTableMysqlBuilder.append("  PRIMARY KEY (`id`)\n");
        createTableMysqlBuilder.append(") ");
        createTableMysqlBuilder.append("ENGINE=InnoDB DEFAULT CHARSET=utf8");
        createTableOracleBuilder.append(")");

        String createTableSql = DIALECT.equals(JdbcHelper.DIALECT_MYSQL)?createTableMysqlBuilder.toString():createTableOracleBuilder.toString();
        Statement statement = null;
        try {
            System.out.println("创建了表"+tableName+"............");

            statement = connection.createStatement();
            statement.execute(createTableSql);
        }catch (Exception e){
            System.out.println("创建表失败");
            e.printStackTrace();
        }finally {
            closeStatement(statement);
        }
    }

    public void createTable() {
        String createTableMysql = "CREATE TABLE `"+tableName+"` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `SRV_NAME` varchar(255) DEFAULT NULL,\n" +
                "  `FUN_NAME` varchar(8) DEFAULT NULL,\n" +
                "  `CALL_BEGINTIME` varchar(32) DEFAULT NULL,\n" +
                "  `RETCODE` varchar(255) DEFAULT NULL,\n" +
                "  `ROUTE_KEY` varchar(16) DEFAULT NULL,\n" +
                "  `CHANNEL_ID` varchar(16) DEFAULT NULL,\n" +
                "  `ROUTE_VALUE` varchar(64) DEFAULT NULL,\n" +
                "  `POOL_ID` varchar(16) DEFAULT NULL,\n" +
                "  `CLIENT_IP` varchar(64) DEFAULT NULL,\n" +
                "  `USERNAME` varchar(64) DEFAULT NULL,\n" +
                "  `PASSWORD` varchar(64) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ";

        String createTableOracle = "CREATE TABLE "+tableName+" (\r\n" +
                "	\"SRV_NAME\" VARCHAR2(255 BYTE),\r\n" +
                "	\"FUN_NAME\" VARCHAR2(8 BYTE),\r\n" +
                "	\"CALL_BEGINTIME\" VARCHAR2(32 BYTE)   ,\r\n" +
                "	\"RETCODE\" VARCHAR2(255 BYTE),\r\n" +
                "	\"ROUTE_KEY\" VARCHAR2(16 BYTE),\r\n" +
                "	\"CHANNEL_ID\" VARCHAR2(16 BYTE),\r\n" +
                "	\"ROUTE_VALUE\"   VARCHAR2(64 BYTE),\r\n" +
                "	\"POOL_ID\" VARCHAR2(16 BYTE)   ,\r\n" +
                "	\"CLIENT_IP\" VARCHAR2(64 BYTE),\r\n" +
                "	\"USERNAME\" VARCHAR2(64 BYTE),\r\n" +
                "	\"PASSWORD\" VARCHAR2(64 BYTE)\r\n" +
                ")";
        String createTableSql = DIALECT.equals(JdbcHelper.DIALECT_MYSQL)?createTableMysql:createTableOracle;
        Statement statement = null;
        try {
            System.out.println("创建了表"+tableName+"............");

            statement = connection.createStatement();
            statement.execute(createTableSql);
        }catch (Exception e){
            System.out.println("创建表失败");
            e.printStackTrace();
        }finally {
            closeStatement(statement);
        }

    }

    public void addColumn(String...keys) {
        Statement statement = null;
        try {
            for(String key:keys) {
                if(key.indexOf("->")>0) {
                    String[] split = key.split("->");
                    key = split[split.length-1];
                }
                String alterTableMysql = "ALTER TABLE "+tableName+" ADD "+key+" VARCHAR(128);";
                String alterTableOracel = "ALTER TABLE "+tableName+" ADD "+key+" VARCHAR2(128 BYTE)";
                String alterTableSql = DIALECT.equals(JdbcHelper.DIALECT_MYSQL)?alterTableMysql:alterTableOracel;

                System.out.println("添加了表字段："+alterTableOracel);
                statement = connection.createStatement();
                statement.execute(alterTableSql);
            }
        }catch (Exception e){
            System.out.println("增加列失败");
            e.printStackTrace();
        }finally {
            closeStatement(statement);
        }
    }


    public void createIndex(String indexName,List<String> cols){
        StringBuilder builder = new StringBuilder("(");
        for (String s : cols){
            builder.append(s+",");
        }
        builder.replace(builder.length()-1,builder.length(),")");
        String oracleSql = "create index "+indexName+" on "+tableName +
                builder.toString() +
                "  tablespace CHNESB_DATA\n" +
                "  pctfree 10\n" +
                "  initrans 2\n" +
                "  maxtrans 255\n" +
                "  storage\n" +
                "  (\n" +
                "    initial 64K\n" +
                "    next 1M\n" +
                "    minextents 1\n" +
                "    maxextents unlimited\n" +
                "  )";
        String MysqlSql = "select * from "+tableName+" where 1=2";
        String sql = DIALECT.equals(JdbcHelper.DIALECT_MYSQL)?MysqlSql:oracleSql;
        Statement statement = null;
        try {
            System.out.println("创建索引"+indexName+"...........");

            statement = connection.createStatement();
            System.out.println("创建索引成功...........");
            statement.execute(sql);
        }catch (Exception e){
            System.out.println("创建索引失败");
            e.printStackTrace();
        }finally {
            closeStatement(statement);
        }

    }


    /**
     *
     * @param colValue 数据表中的字段，以及对应的值
     */
    public String parseSql(Map<String,String> colValue){
        StringBuilder builder = new StringBuilder("INSERT INTO " + tableName + "(");
        StringBuilder insertCols = new StringBuilder();
        StringBuilder insertValues = new StringBuilder();
        for (String col : colValue.keySet()){
            insertCols.append(col+" ,");
            insertValues.append("'"+colValue.get(col)+"',");
        }

        insertCols.replace(insertCols.length()-1,insertCols.length(),")");
        insertValues.replace(insertValues.length()-1,insertValues.length(),")");
        builder.append(insertCols);
        builder.append("VALUES(");
        builder.append(insertValues);
        return builder.toString();
    }


    public String parseSql(ServiceParameters sp){

        Map<String, String> map = sp.getParamMap();
        String sqlPrefix = "INSERT INTO "+tableName+"(" +
                "SRV_NAME      ," +
                "FUN_NAME      ," +
                "CALL_BEGINTIME," +
                "RETCODE       ," +
                "ROUTE_KEY     ," +
                "CHANNEL_ID    ," +
                "ROUTE_VALUE   ," +
                "POOL_ID       ," +
                "CLIENT_IP     ," +
                "USERNAME      ," +
                "PASSWORD      " ;
        String sqlSuffix = "("
                +"'"  +sp.getServiceName()+"'"+","
                +"'"  +sp.getFunName()+"'"+","
                +"'"  +sp.getCallBeginTime()+"'"+","
                +"'"  +sp.getRetCode()+"'"+","
                +"'"  +sp.getRouteKey()+"'"+","
                +"'"  +sp.getChannelId()+"'"+","
                +"'"  +sp.getRouteValue()+"'"+","
                +"'"  +sp.getPoolId()+"'"+","
                +"'"  +sp.getClientIp()+"'"+","
                +"'"  +sp.getUsername()+"'"+","
                +"'"  +sp.getPassword()+"'";

        for(String key:map.keySet()){
            if(key.indexOf("->")>0){
                String[] split = key.split("->");
                sqlPrefix+=","+split[split.length-1]  ;
            }else{
                sqlPrefix+=","+key  ;
            }

            sqlSuffix+=","+"'"  +map.get(key)+"'";
        }

        String sqlPrefixEnd = ") VALUES";
        String sqlSuffixEnd = ")";

        String sql = sqlPrefix+sqlPrefixEnd+sqlSuffix+sqlSuffixEnd;
        return sql;
    }

    public void releaseConnection(){
        if(connection!=null){
            try {
                connection.close();
                System.out.println("数据库连接关闭");
            } catch (SQLException e) {
                System.out.println("数据库连接关闭失败");
                e.printStackTrace();
            }
        }
    }

    public void executeSql(String sql) throws SQLException {

    }

    public void closeStatement(Statement statement){
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
