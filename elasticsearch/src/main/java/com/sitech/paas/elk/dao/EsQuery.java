package com.sitech.paas.elk.dao;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author liwei_paas
 * @date 2019/9/4
 */
public interface EsQuery {

    Object querySrvRetAgg() throws IOException;

    Object querySrvMsg(Map<String,String> map) throws IOException;

    Object queryCountSql();
}
