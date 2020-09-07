package com.abreaking.easyjpa.dao;


import com.abreaking.easyjpa.constraint.NoIdOrPkSpecifiedException;
import com.abreaking.easyjpa.exec.Executor;
import com.abreaking.easyjpa.mapper.JpaRowMapper;
import com.abreaking.easyjpa.mapper.impl.DefaultJpaRowMapper;
import com.abreaking.easyjpa.matrix.Matrix;
import com.abreaking.easyjpa.sql.PreparedSqlBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * 基本的实现
 * @author liwei_paas
 * @date 2019/11/22
 */
public class CurdDaoImpl implements CurdDao{

    private static final Logger logger = LoggerFactory.getLogger(CurdDaoImpl.class);

    /**
     * sql的执行器
     */
    private Executor executor;

    /**
     * Prepared sql语句的构造器
     */
    private PreparedSqlBuilder sqlBuilder ;


    public CurdDaoImpl(Executor executor) {
        this.executor = executor;
        this.sqlBuilder = new PreparedSqlBuilder();
    }

    public CurdDaoImpl(Executor executor, PreparedSqlBuilder sqlBuilder) {
        this.executor = executor;
        this.sqlBuilder = sqlBuilder;
    }

    @Override
    public List select(Object o) throws SQLException {
        JpaRowMapper mapper = new DefaultJpaRowMapper(o);
        Matrix matrix = mapper.matrix();
        String sql = sqlBuilder.simpleSelectSql(mapper.tableName(),matrix.columns());
        return executor.queryForList(sql,matrix.values(),matrix.types(),mapper);
    }

    @Override
    public Object update(Object o) throws NoIdOrPkSpecifiedException, SQLException {
        JpaRowMapper mapper = new DefaultJpaRowMapper(o);
        Matrix matrix = mapper.matrix();
        Matrix _ipm = mapper.id();
        if (_ipm == null){
            _ipm = mapper.pk();
            if (_ipm == null){
                throw new NoIdOrPkSpecifiedException("no id or pk specified!you should use @Id oannotation upon your id field , or use @Pk annotation upon your business primary key");
            }
        }
        String sql = sqlBuilder.simpleUpdateSql(mapper.tableName(),matrix.columns(),_ipm.columns());
        Object[] values = merge(matrix.values(), _ipm.values());
        int[] types = merge(matrix.types(), _ipm.types());
        return executor.update(sql,values,types);
    }

    @Override
    public Object insert(Object o) {
        return null;
    }

    @Override
    public Object delete(Object idOrPkCondition) throws NoIdOrPkSpecifiedException {
        return null;
    }

    private Object[] merge(Object[] t1,Object[] t2){
        Object[] ret = new Object[t1.length+t2.length];
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
}
