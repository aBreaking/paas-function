package com.sitech.esb.jsoneye;

/**
 *
 * @author liwei_paas
 * @date 2019/8/8
 */
public interface Comparator {

    /**
     * 进行比较
     */
    void compare();

    /**
     * 比较结果
     * @return
     */
    Result getResult();

}
