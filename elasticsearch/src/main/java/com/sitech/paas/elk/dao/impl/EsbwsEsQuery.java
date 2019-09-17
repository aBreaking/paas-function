package com.sitech.paas.elk.dao.impl;

import org.elasticsearch.client.RestHighLevelClient;
/**
 *
 * @author liwei_paas
 * @date 2019/9/17
 */
public class EsbwsEsQuery extends BaseEsQuery {
    public EsbwsEsQuery(RestHighLevelClient client) {
        super(client);
    }

    public EsbwsEsQuery(RestHighLevelClient client, String... indices) {
        super(client, indices);
    }






}
