package com.sitech.paas.elk.input;

/**
 *
 * @author liwei_paas
 * @date 2019/9/4
 */
public interface EsInput {

    /**
     * 查询的报文。必须是json的报文
     * @return
     */
    String pin();

}
