package com.abreaking.easyjpa.exec;


import com.abreaking.easyjpa.mapping.JpaRowMapper;

import java.util.List;


/**
 *
 * @author liwei_paas 
 */
public interface Executor {

    <T> T queryForObject(String preparedSql,Object[] values,int[] types,Class<T> obj);

    Object queryForObject(String preparedSql,Object[] values,int[] types,JpaRowMapper mapper);

    List<?> queryForList(String preparedSql,Object[] values,int[] types,JpaRowMapper mapper);

    <T> List<T> queryForList(String preparedSql,Object[] values,int[] types,Class<T> obj);



    void update(String preparedSql, String[] args);
}
