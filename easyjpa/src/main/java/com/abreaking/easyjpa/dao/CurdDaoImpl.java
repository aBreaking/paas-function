package com.abreaking.easyjpa.dao;


import com.abreaking.easyjpa.constraint.NoIdOrPkSpecifiedException;
import com.abreaking.easyjpa.exec.Executor;
import com.abreaking.easyjpa.mapping.JpaRowMapper;
import com.abreaking.easyjpa.matrix.Matrix;
import com.abreaking.easyjpa.util.Pagination;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.logging.Logger;

/**
 * 基本的实现
 * @author liwei_paas
 * @date 2019/11/22
 */
public class CurdDaoImpl implements CurdDao{

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CurdDaoImpl.class);

    /**
     * 数据库方言，目前仅支持mysql及oracle两种
     */
    private String dialect;

    /**
     * sql的执行器
     */
    private Executor executor;


    @Override
    public List find(JpaRowMapper condition) {
        StringBuilder builder = new StringBuilder("select * from ");
        builder.append(condition.tableName());
        //將matrix直接
        Matrix matrix = condition.matrix();
        builder.append(conditionSql(matrix));
        String prepareSql = builder.toString();
        if (logger.isDebugEnabled()){
            logger.debug(prepareSql);
        }
        executor.queryForList(prepareSql,matrix.values(),matrix.types(),condition);
        return null;
    }

    @Override
    public List findByPagination(JpaRowMapper condition, Pagination pagination) {
        return null;
    }

    @Override
    public JpaRowMapper get(JpaRowMapper idOrPkCondition) throws NoIdOrPkSpecifiedException {
        return null;
    }

    @Override
    public Object update(JpaRowMapper idOrPkObject) throws NoIdOrPkSpecifiedException {
        return null;
    }

    @Override
    public Object insert(JpaRowMapper mapper) {
        return null;
    }

    @Override
    public Object delete(JpaRowMapper idOrPkCondition) throws NoIdOrPkSpecifiedException {
        return null;
    }
}
