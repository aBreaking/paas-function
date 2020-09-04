package com.abreaking.easyjpa.sql;

import com.abreaking.easyjpa.mapper.JpaRowMapper;
import com.abreaking.easyjpa.matrix.Matrix;

/**
 *
 * @author liwei_paas
 * @date 2020/9/4
 */
public class PreparedSqlBuilder {

     public String simpleSelectSql(JpaRowMapper jpaRowMapper){
         StringBuilder sqlBuilder = new StringBuilder("SELECT ");
         sqlBuilder.append(" * FROM ").append(jpaRowMapper.tableName());
         Matrix matrix = jpaRowMapper.matrix();
         sqlBuilder.append(" WHERE 1=1 ");
         for (String colName : matrix.columns()) {
             sqlBuilder.append(" and ");
             sqlBuilder.append(colName);
             sqlBuilder.append("= ? ");
         }
         return sqlBuilder.toString();
     }
}
