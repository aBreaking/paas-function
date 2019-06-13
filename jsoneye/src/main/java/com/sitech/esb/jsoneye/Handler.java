package com.sitech.esb.jsoneye;

import java.util.Collection;

/**
 * 找出两个list的差异
 * @author liwei_paas
 * @date 2019/6/11
 */
public interface Handler {

    /**
     * 两个集合的差异，结果放在result里面去
     * @param list1
     * @param list2
     * @return
     */
    Result different(Collection<String> list1, Collection<String> list2);

}
