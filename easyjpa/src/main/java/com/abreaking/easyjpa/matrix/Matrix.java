package com.abreaking.easyjpa.matrix;

/**
 *  对一个实体对象三维属性的描述。该实体就是需要被持久化到db。它的三维属性：
 *      column ： 列名。 也就是Java对象字段名 -> 表列名
 *      type ： 列类型。
 *      value: 具体的值
 *  某个实体对象里面的属性，映射到对应的数据库表后，使用数组来将该是实体表所有属性给剥离出来，用于sql的条件查询。
 *  该jpa的一个设计思想就是：每个实体对象，它自身不为null的属性都可以用来进行条件查询。
 *      因此，实体类的所有字段的类型都应该是包装类型，不要使用 int,long这8种的基本类型
 *
 *  Matrix针对的是实体映射后的表，它的数据来自该实体对象。column、type、value都得是一一对应。
 *  你也可以认为，一个不为空的实体对象，它就对应一个Matrix。
 *
 * @see ColumnMatrix
 *
 * @author liwei_paas
 * @date 2019/7/16
 */
public interface Matrix {
    /**
     * 列的名字
     * @return 列名数组
     */
    String[] columns();

    /**
     * 列的类型
     * @return 列类型数组
     */
    int[] types();

    /**
     * 列的值
     * @return 值数组
     */
    Object[] values();
}
