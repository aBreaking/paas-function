package com.sitech.paas.inparam.resovler;

/**
 * 对象解析器。同于将某条语句解析成指定的对象
 * @param <T>
 */
public interface Resolver<T> {
    /**
     * 怎么去解析文件中每一条语句记录
     * @param statement  该条语句
     * @return
     */
    T resolve(String statement);
}
