package com.sitech.esb.jsoneye;

import java.util.List;

/**
 * 解析某个对象 成我们指定的字符串类型
 * @author liwei_paas
 * @date 2019/6/11
 */
public interface Resolver {

    String separator = ".";

    /**
     * 解析的具体操作
     * @return
     */
    List<String> parse();

}
