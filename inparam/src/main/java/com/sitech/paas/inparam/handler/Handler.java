package com.sitech.paas.inparam.handler;

import java.util.List;

public interface Handler<T> {
    /**
     * 一开始需要哪些准备工作
     */
    void preHand();

    /**
     * 怎么去处理解析完毕后的集合对象
     * @param t
     */
    void hand(List<T> t);

    /**
     * 处理结束该做啥子
     */
    void postHand();
}
