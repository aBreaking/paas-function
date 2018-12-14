package com.sitech.paas.inparam.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JdbcHelper {

    private String dialect = "mysql";
    public final static String DIALECT_ORACLE = "oracle";
    public final static String DIALECT_MYSQL = "mysql";


    public Connection getConnection(String url,String username,String password) throws SQLException {
        String mysqlDriver = "com.mysql.jdbc.Driver";
        String oracleDriver = "oracle.jdbc.driver.OracleDriver";
        dialect = url.indexOf("mysql")==-1?DIALECT_ORACLE:DIALECT_MYSQL;
        String driver = dialect.equals(DIALECT_MYSQL)?mysqlDriver:oracleDriver;
        Connection connection = getConnection(driver,url, username, password);
        return connection;
    }


    public Connection getConnection(String driver,String url,String username,String password) throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("driver有误，请确认，只能是mysql或者oracle数据库");
        }
        return DriverManager.getConnection(url, username, password);
    }

    public void releaseConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("关闭数据库连接失败");
        }
    }

    public String getDialect() {
        return dialect;
    }


}
