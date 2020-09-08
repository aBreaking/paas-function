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
         StringBuilder builder = new StringBuilder("UPDATE ");
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

     public String simpleInsertSql(String tableName,String columns[]){
         StringBuilder builder = new StringBuilder("INSERT ");
         builder.append(tableName);
         StringBuilder header = new StringBuilder(" (");
         StringBuilder values = new StringBuilder(" VALUES(");
         for (String key : columns){
             header.append(key);
             header.append(",");
             values.append("?,");
         }
         header = header.deleteCharAt(header.length()-1);
         values = values.deleteCharAt(values.length()-1);
         header.append(")");
         values.append(")");
         builder.append(header);
         builder.append(values);
         return builder.toString();
     }

     public String simpleDeleteSql(String tableName,String[] conditions){
         StringBuilder builder = new StringBuilder("DELETE ");
         builder.append(" FROM ").append(tableName);
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
