package com.abreaking.easyjpa.sql;

import com.abreaking.easyjpa.mapper.JpaRowMapper;

/**
 *
 * @author liwei_paas
 * @date 2020/9/4
 */
public class PreparedSqlBuilder {

     public String simpleSelectSql(String tableName,String conditions[]){
         StringBuilder sqlBuilder = new StringBuilder("SELECT ");
         sqlBuilder.append(" * FROM ").append(tableName);
         addWhereCondition(sqlBuilder,conditions);

         return sqlBuilder.toString();
     }

     public String simpleUpdateSql(String tableName,String columns[],String conditions[]){
         StringBuilder builder = new StringBuilder("Update ");
         builder.append(tableName);
         builder.append(" SET ");
         for (String col : columns){
             builder.append(col);
             builder.append("=?,");
         }
         if (builder.toString().endsWith(",")){
             builder = builder.deleteCharAt(builder.length()-1);
         }
         addWhereCondition(builder,conditions);
         return builder.toString();
     }

     private void addWhereCondition(StringBuilder sqlBuilder,String conditions[]){

         sqlBuilder.append(" WHERE 1=1 ");
         for (String c :conditions) {
             sqlBuilder.append(" and ");
             sqlBuilder.append(c);
             sqlBuilder.append("= ? ");
         }
     }
}
