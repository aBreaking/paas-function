package com.abreaking.easyjpa.dao;


import com.abreaking.easyjpa.constraint.NoIdOrPkSpecifiedException;
import com.abreaking.easyjpa.exec.Executor;
import com.abreaking.easyjpa.mapper.ObjectMapper;
import com.abreaking.easyjpa.matrix.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 基本的实现
 * @author liwei_paas
 * @date 2019/11/22
 */
public class CurdDaoImpl<T extends ObjectMapper> implements CurdDao{

    private static final Logger logger = LoggerFactory.getLogger(CurdDaoImpl.class);

    /**
     * 数据库方言，目前仅支持mysql及oracle两种
     */
    private String dialect;

    /**
     * sql的执行器
     */
    private Executor executor;

    @Override
    public List select(ObjectMapper objectMapper) {
        return null;
    }

    @Override
    public Object update(ObjectMapper objectMapper) throws NoIdOrPkSpecifiedException {
        return null;
    }

    @Override
    public Object insert(ObjectMapper objectMapper) {
        return null;
    }

    @Override
    public Object delete(ObjectMapper idOrPkCondition) throws NoIdOrPkSpecifiedException {
        return null;
    }

    private StringBuilder conditionSql(Matrix matrix){
        StringBuilder builder = new StringBuilder(" where 1=1 ");
        for (String colName : matrix.columns()) {
            builder.append(" and ");
            builder.append(colName);
            builder.append("= ? ");
        }
        return builder;
    }
}
