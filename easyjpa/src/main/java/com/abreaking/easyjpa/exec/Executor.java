package com.abreaking.easyjpa.exec;



import java.sql.SQLException;


/**
 * 执行器，执行sql语句
 * @author liwei_paas 
 */
public interface Executor {

    /** old **/
    <T> T queryForObject(String preparedSql,Object[] values,int[] types,Class<T> obj) throws SQLException;

    void update(String preparedSql, String[] args);
}
