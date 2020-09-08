package com.abreaking.easyjpa.dao;

import com.abreaking.easyjpa.constraint.NoIdOrPkSpecifiedException;

import java.sql.SQLException;
import java.util.List;

/**
 * 通用curd的操作。
 * 在执行条件查询时，这些条件都会是请确匹配。如：select * from table where col1=? and col2=?
 * @author liwei_paas
 * @date 2019/11/22
 */
public interface CurdDao<T> {

    /**
     * 正常的条件查询，将对象本身作为条件
     * @param t
     * @return
     */
    List<T> select(T t) throws SQLException;

    /**
     * 针对指定的id或者pk,update对应的数据
     * @param t
     * @throws NoIdOrPkSpecifiedException idOrPkObject没有指定id或者pk
     * @return 如果是根据id修改，返回id。否则返回null
     */
    int update(T t) throws NoIdOrPkSpecifiedException, SQLException;

    /**
     * 直接insert对象
     * @param t
     * @return 如果有主键id，返回insert成功的id。否则返回null
     */
    int insert(T t) throws SQLException;

    /**
     * 根据给定主键删除对象
     * @param t
     * @return
     * @throws NoIdOrPkSpecifiedException 没有指定主键
     */
    int delete(T t) throws NoIdOrPkSpecifiedException,SQLException;

}
