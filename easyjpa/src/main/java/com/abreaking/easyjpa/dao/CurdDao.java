package com.abreaking.easyjpa.dao;

import com.abreaking.easyjpa.constraint.NoIdOrPkSpecifiedException;
import com.abreaking.easyjpa.mapping.JpaRowMapper;
import com.abreaking.easyjpa.matrix.ColumnMatrix;
import com.abreaking.easyjpa.matrix.Matrix;
import com.abreaking.easyjpa.util.Pagination;

import java.util.List;

/**
 * 通用curd的操作。
 * 在执行条件查询时，这些条件都会是请确匹配。如：select * from table where col1=? and col2=?
 * @author liwei_paas
 * @date 2019/11/22
 */
public interface CurdDao<T extends JpaRowMapper> {

    /**
     * 正常的条件查询，将对象本身作为条件
     * @param condition
     * @return
     */
    List<T> find(T condition);

    /**
     * 带分页的条件查询。
     * @param condition
     * @param pagination
     * @return
     */
    List<T> findByPagination(T condition, Pagination pagination);

    /**
     * 根据主键获取实际的对象
     * @param idOrPkCondition 有指定id或者pk的条件
     * @return
     * @throws com.abreaking.easyjpa.constraint.NoIdOrPkSpecifiedException 没有指定主键时
     */
    T get(T idOrPkCondition) throws NoIdOrPkSpecifiedException;

    /**
     * 针对指定的id或者pk,update对应的数据
     * @param idOrPkObject
     * @throws NoIdOrPkSpecifiedException idOrPkObject没有指定id或者pk
     * @return 如果是根据id修改，返回id。否则返回null
     */
    Object update(T idOrPkObject) throws NoIdOrPkSpecifiedException;

    /**
     * 直接insert对象
     * @param t
     * @return 如果有主键id，返回insert成功的id。否则返回null
     */
    Object insert(T t);

    /**
     * 根据给定主键删除对象
     * @param idOrPkCondition
     * @return
     * @throws NoIdOrPkSpecifiedException 没有指定主键
     */
    Object delete(T idOrPkCondition) throws NoIdOrPkSpecifiedException;

    default StringBuilder conditionSql(Matrix matrix){
        StringBuilder builder = new StringBuilder(" where 1=1 ");
        for (String colName : matrix.columns()) {
            builder.append(" and ");
            builder.append(colName);
            builder.append("= ? ");
        }
        return builder;
    }
}
