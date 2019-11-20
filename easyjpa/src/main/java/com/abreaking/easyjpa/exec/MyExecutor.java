package com.abreaking.easyjpa.exec;

import com.abreaking.easyjpa.mapping.JpaRowMapper;
import com.abreaking.easyjpa.matrix.Matrix;
import com.abreaking.easyjpa.util.DateUtils;
import com.abreaking.easyjpa.util.Pagination;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 通用增删改查的处理
 * @author liwei_paas
 * @date 2019/6/27
 */
public class MyExecutor  {

    /**
     * 数据库方言，目前支持mysql 及 oracle
     */
    private String dialect;
    
    private Executor executor;

    
    public Pagination findByPagination(JpaRowMapper condition, Pagination pagination) {
        Matrix matrix = condition.matrix();
        StringBuilder builder = new StringBuilder("select * from ");
        builder.append(condition.tableName());
        builder.append(matrixConditionSql(matrix));
        String sql = builder.toString();
        String pageSql = dialect.equals("mysql")?pagination.decorateMysqlSql(sql):pagination.decorateOracleSql(sql);
        
        List<?> list = executor.queryForList(pageSql,matrix.values(),matrix.types(), condition);

        pagination.setTotal(queryCount(sql,matrix.values(),matrix.types()));
        pagination.setResults(list);
        return pagination;
    }

    public Object findByPk(JpaRowMapper mapper,Matrix primaryKey) {
        /*if (primaryKey.isEmpty()){
            return mapper;
        }*/
        StringBuilder builder = new StringBuilder("select * from ");
        builder.append(mapper.tableName());
        builder.append(matrixConditionSql(primaryKey));
        return executor.queryForObject(builder.toString(),primaryKey.values(),primaryKey.types(),mapper.getClass());
    }

    
    public  List<?> findByCondition(JpaRowMapper mapper,Matrix condition){
        StringBuilder builder = new StringBuilder("select * from ");
        builder.append(mapper.tableName());
        builder.append(matrixConditionSql(condition));
        String papreSql = builder.toString();
        
        return executor.queryForList(papreSql,condition.values(),condition.types(),mapper);
    }

    
    public List<String> findColDistinctList(JpaRowMapper condition,String colName) {
        Matrix matrix = condition.matrix();
        //就算有多个，也只是要第一个
        StringBuilder builder = new StringBuilder("SELECT DISTINCT ");
        builder.append(colName);
        builder.append(" FROM ");
        builder.append(condition.tableName());
        builder.append(matrixConditionSql(matrix));
        String sql = builder.toString();
        
        return executor.queryForList(sql,matrix.values(),matrix.types(),String.class);
    }


    public Integer max(JpaRowMapper mapper,String colName) {
        Matrix matrix = mapper.matrix();
        String sql = "SELECT max(" + colName + ") from " + mapper.tableName();
        sql += matrixConditionSql(matrix);
        
        return executor.queryForObject(sql,matrix.values(),matrix.types(),Integer.class);
    }

    
    /*public void update(JpaRowMapper mapper) {
        Matrix matrix = mapper.matrix();
        Matrix primaryKey = mapper.primaryKey();
        StringBuilder builder = new StringBuilder("update ");
        builder.append(mapper.tableName());
        builder.append(" SET ");
        for (String col : matrix.columns()){
            builder.append(col);
            builder.append("=?,");
        }
        if (builder.toString().endsWith(",")){
            builder = builder.deleteCharAt(builder.length()-1);
        }
        builder.append(matrixConditionSql(primaryKey));
        

        Object[] values = merge(matrix.values(),primaryKey.values());
        int[] types = merge(matrix.types(),primaryKey.types());
        *//*executor.update(builder.toString(),values,types);*//*
    }*/

    
    public void insert(JpaRowMapper mapper) {
        Matrix matrix = mapper.matrix();

        StringBuilder builder = new StringBuilder("insert into ");
        builder.append(mapper.tableName());
        StringBuilder header = new StringBuilder(" (");
        StringBuilder values = new StringBuilder(" VALUES(");

        for (String key : matrix.columns()){
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
        
        /*executor.update(builder.toString(),matrix.values(),matrix.types());*/
    }

    
    public void insertBatch(List<? extends JpaRowMapper> list) {
        if (list.isEmpty()){
            return;
        }
        JpaRowMapper mapper = list.get(0);
        String tableName = mapper.tableName();
        Matrix commonMatrix = mapper.matrix();

        StringBuilder builder = new StringBuilder("insert into ");
        builder.append(tableName);
        StringBuilder header = new StringBuilder(" (");
        StringBuilder values = new StringBuilder(" VALUES(");

        for (String key : commonMatrix.columns()){
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
        

        for (JpaRowMapper m : list){
            Matrix matrix = m.matrix();
            /*executor.update(builder.toString(),matrix.values(),matrix.types());*/
        }
    }


    
    /*public void delete(JpaRowMapper mapper) {
        Matrix matrix = mapper.primaryKey();
        if (matrix.isEmpty()){
            return;
        }
        StringBuilder builder = new StringBuilder("delete from ");
        builder.append(mapper.tableName());
        builder.append(matrixConditionSql(matrix));
        
        executor.update(builder.toString(),matrix.values(),matrix.types());
    }*/

    /**
     * 自动创建表
     * 这里的创建表分为两种情况：
     *  1、一种是针对日期表，也就是说没得这个月的表但是有上个日期的表，那么直接根据上个月的表来创建当前的日期表
     *  2、第二种直接就是新表。那么就根据mapper来手动自动创建表
     */
    
    public void createTable(JpaRowMapper mapper){
        String tableName = mapper.tableName();
        
        //判断该表是不是日期表
        if (tableName.indexOf("_")!=-1){
            int i = tableName.lastIndexOf("_");
            String tablePrefix = tableName.substring(0,i);
            String dateStr = tableName.substring(i + 1);
            //日期表那么肯定都是数字
            if (dateStr.matches("\\d+")){
                int length = dateStr.length();
                //这里只考虑月表、天表两种情况吧
                int calendarField = 0;
                String formatStr = null;
                if (length==6){
                    calendarField = Calendar.MONTH;
                    formatStr = "yyyyMM";
                }else if (length==8){
                    calendarField = Calendar.DATE;
                    formatStr = "yyyyMMdd";
                }
                if (null!=formatStr){
                    SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
                    try {
                        Date date = dateFormat.parse(dateStr);
                        //获取上一时刻的表名
                        Date lastDate = DateUtils.getLastDate(date, calendarField);
                        String lastTableName = tablePrefix+"_"+dateFormat.format(lastDate);

                        //先判断有没有上一个日期表
                        if (containsTable(lastTableName)){
                            //根据上一个的日期表来创建当前的表
                            
                            createTableAsLast(tableName,lastTableName);
                            
                            return;
                        }

                    } catch (ParseException e) {
                        //说明不是日期表
                    }
                }
            }
        }
        
        //直接创建新表
        createNewTable(tableName,mapper.emptyMatrix());

    }

    /**
     * 执行一条测试sql来判断判断是否存在该表
     * @param tableName
     * @return
     */
    public boolean containsTable(String tableName) {
        try {
            exec("select * from " + tableName + " where 1=2");
            return true;
        } catch (Exception e) {
            return !isTableNotExist(e);
        }
    }

    /**
     * 根据上个时间的表来创建当前的表。
     * @param newTableName 要创建的表名
     * @param lastTableName 上一个时刻的表名
     */
    public void createTableAsLast(String newTableName,String lastTableName){
        StringBuilder builder = new StringBuilder();
        builder.append("create table ");
        builder.append(newTableName);
        builder.append(" as select * from ");
        builder.append(lastTableName);
        builder.append(" where 1=2");
        exec(builder.toString());
    }

    public void createNewTable(String tableName,Matrix emptyMatrix){
        final int[] types = emptyMatrix.types();
        final String[] columns = emptyMatrix.columns();
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        //oracel的表名及字段名都的加上 ""
        builder.append(tableName);
        builder.append(" ( ");
        for (int i = 0; i < columns.length; i++) {
            builder.append(columns[i]);
            builder.append(" ");
            builder.append(sqlType(types[i],dialect));
            builder.append(",");
        }
        String createSql = builder.toString();
        //去掉最后一个逗号
        if (createSql.endsWith(",")){
            createSql = createSql.substring(0,createSql.length()-1);
        }
        String execSql = isMysql()?createSql+")ENGINE=INNODB DEFAULT CHARSET=utf8":createSql+")";
        exec(execSql);
    }


    
    public void exec(String preparedSql,String...args) {
        
        executor.update(preparedSql,args);
    }

    
    public <T extends JpaRowMapper> List<?> query(String sql, Matrix matrix, T t) {
        return  executor.queryForList(sql, matrix.values(), matrix.types(), t);
    }

    private <T> T[] merge(T[] t1,T[] t2){
        T[] ret = (T[]) new Object[t1.length+t2.length];
        for (int i = 0; i < t1.length; i++) {
            ret[i] = t1[i];
        }
        for (int i = 0; i < t2.length; i++) {
            ret[t1.length+i] = t2[i];
        }
        return ret;
    }

    private int[] merge(int[] t1,int[] t2){
        int[] ret = new int[t1.length+t2.length];
        for (int i = 0; i < t1.length; i++) {
            ret[i] = t1[i];
        }
        for (int i = 0; i < t2.length; i++) {
            ret[t1.length+i] = t2[i];
        }
        return ret;
    }

    private int queryCount(String prepareSql,Object[] values,int[] types){
        String countSql = "select count(*) from (" + prepareSql + ") t";
        return executor.queryForObject(countSql,values,types,Integer.class);
    }

    private boolean isMysql(){
        return dialect.equals("mysql");
    }

    private  StringBuilder matrixConditionSql(Matrix colMatrix){
        StringBuilder builder = new StringBuilder(" where 1=1 ");
        for (String colName : colMatrix.columns()) {
            builder.append(" and ");
            builder.append(colName);
            builder.append("= ? ");
        }
        return builder;
    }

    private String sqlType(int type,String dialect){
        boolean isMysql = null!=dialect?dialect.toLowerCase().equals("mysql"):false;
        switch (type){
            case Types.VARCHAR:return isMysql?"VARCHAR(255)":"VARCHAR2(255)";
            case Types.FLOAT:return "FLOAT";
            case Types.BIGINT:return isMysql?"NUMERIC":"NUMBER";
            case Types.INTEGER:return isMysql?"NUMERIC":"NUMBER";
            case Types.TIMESTAMP:return "TIMESTAMP";
            default:return isMysql?"VARCHAR(255)":"VARCHAR2(255)";
        }
    }

    /**
     * 根据异常来判断是不是表不存在
     * @param e
     * @return
     */
    private boolean isTableNotExist(Exception e){
        final String oracelTableNotExistKey = "ORA-00942";
        final String mysqlTableNotExistRegexp = ".*Table.*doesn't exist.*";
        String message = e.getMessage();
        if (message.indexOf(oracelTableNotExistKey)!=-1){
            return true;
        }
        if (message.matches(mysqlTableNotExistRegexp)){
            return true;
        }
        return false;
    }
}
