package com.abreaking.easyjpa.sql;

import com.abreaking.easyjpa.mapper.JpaRowMapper;

/**
 *
 * @author liwei_paas
 * @date 2020/9/4
 */
public class PreparedSqlBuilder {

     public String simpleSelectSql(String columns[],JpaRowMapper jpaRowMapper){
         StringBuilder sqlBuilder = new StringBuilder("SELECT ");
         sqlBuilder.append(" * FROM ").append(jpaRowMapper.tableName());
         sqlBuilder.append(" WHERE 1=1 ");
         for (String colName :columns) {
             sqlBuilder.append(" and ");
             sqlBuilder.append(colName);
             sqlBuilder.append("= ? ");
         }
         return sqlBuilder.toString();
     }
}
