package com.sitech.paas.elk.dao.impl;

import org.elasticsearch.search.SearchHit;


/**
 * 怎么处理SearchHits的内容。
 * @author liwei_paas
 * @date 2019/9/24
 */
public interface SearchHitResolver {

    /**
     * 建议：考虑到查询后的数据一般都是大量的，如果查询后的SearchHit每次都将其放在某个集合，这是相当费内存的。
     *      所以，你应该自己指定容器（如List，Map），并对SearchHit再做出二次筛选
     * @param searchHits
     */
    Object resolve(SearchHit searchHits);
}
