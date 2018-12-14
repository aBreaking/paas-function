package com.sitech.paas.inparam.resovler;

public interface Resolver<T> {
    /**
     * 怎么去解析inparam文件中每一条语句
     * @param statement  该条语句
     * @return
     */
    T resolve(String statement);
}
