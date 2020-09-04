package com.abreaking.easyjpa.exec;

import com.abreaking.easyjpa.mapper.RowMapper;

import java.sql.SQLException;
import java.util.List;

/**
 * 执行器，执行sql语句
 * @author liwei_paas 
 */
public interface Executor {

    <T> List<T> queryForList(String preparedSql, Object[] values, int[] types,RowMapper rowMapper) throws SQLException;

    void update(String preparedSql, String[] args);
}
