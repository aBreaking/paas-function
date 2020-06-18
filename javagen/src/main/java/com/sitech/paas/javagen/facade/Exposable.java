package com.sitech.paas.javagen.facade;

/**
 * 实现该接口的类 标明该类是一个可被外部编排使用的类
 * @author liwei_paas
 * @date 2020/3/17
 */
public interface Exposable {

    /**
     * 需要说明该类如何被实例化
     * @return
     */
    String howInstance();

}
