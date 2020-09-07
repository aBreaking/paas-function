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
        String sql = sqlBuilder.simpleSelectSql(matrix.columns(),mapper);
        return executor.queryForList(sql,matrix.values(),matrix.types(),mapper);
    }

    @Override
    public Object update(Object o) throws NoIdOrPkSpecifiedException {
        return null;
    }

    @Override
    public Object insert(Object o) {
        return null;
    }

    @Override
    public Object delete(Object idOrPkCondition) throws NoIdOrPkSpecifiedException {
        return null;
    }
}
