package com.abreaking.easyjpa.dao;


import com.abreaking.easyjpa.constraint.NoIdOrPkSpecifiedException;
import com.abreaking.easyjpa.exec.Executor;
import com.abreaking.easyjpa.mapper.JpaRowMapper;
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
public class CurdDaoImpl<T extends JpaRowMapper> implements CurdDao{

    private static final Logger logger = LoggerFactory.getLogger(CurdDaoImpl.class);

    public CurdDaoImpl(Executor executor) {
        this.executor = executor;
    }

    /**
     * sql的执行器
     */
    private Executor executor;

    private PreparedSqlBuilder sqlBuilder = new PreparedSqlBuilder();

    @Override
    public List select(JpaRowMapper jpaRowMapper) throws SQLException {
        Matrix matrix = jpaRowMapper.matrix();
        String sql = sqlBuilder.simpleSelectSql(jpaRowMapper);
        return executor.queryForList(sql,matrix.values(),matrix.types(),jpaRowMapper);
    }

    @Override
    public Object update(JpaRowMapper jpaRowMapper) throws NoIdOrPkSpecifiedException {
        return null;
    }

    @Override
    public Object insert(JpaRowMapper jpaRowMapper) {
        return null;
    }

    @Override
    public Object delete(JpaRowMapper idOrPkCondition) throws NoIdOrPkSpecifiedException {
        return null;
    }

}
