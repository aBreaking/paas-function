package com.sitech.esb.jsoneye;

import java.util.List;

/**
 * 输出的结果
 * @author liwei_paas
 * @date 2019/6/11
 */
public interface Result {

    String LACK = "缺少的";
    String REMAIN = "多余的";

    /**
     * 缺少的值
     * @return
     */
    List<String> lack();

    /**
     * 多余的值
     * @return
     */
    List<String> remain();

}
