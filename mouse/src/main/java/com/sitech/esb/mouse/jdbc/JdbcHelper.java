package com.sitech.esb.mouse.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class JdbcHelper {

    public static Connection getConnection(Properties properties)throws Exception{
        String driver =  properties.getProperty("jdbc.driver");
        String username =  properties.getProperty("jdbc.username");
        String password =  properties.getProperty("jdbc.password");
        String url = properties.getProperty("jdbc.url");
        return getConnection(driver,url,username,password);
    }

    public static Connection getConnection(String url,String username,String password) throws SQLException {
        String oracleDriver = "oracle.com.sitech.esb.inparam.jdbc.driver.OracleDriver";
        Connection connection = getConnection(oracleDriver,url, username, password);
        return connection;
    }

    public static Connection getConnection(String driver,String url,String username,String password) throws SQLException {
        //根据driver确定dialect
        JdbcOperator.DIALECT=driver.indexOf("mysql")!=-1?"mysql":"oracle";

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(url, username, password);
    }

    public static void releaseConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("关闭数据库连接失败");
        }
    }



}
