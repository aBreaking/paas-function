package com.sitech.paas.javagen.json;

/**
 * 接收visitor的访问操作
 * @author liwei_paas
 * @date 2020/3/4
 */
public interface GenAccepter {

    void accept(JsonVisitor visitor);
}
