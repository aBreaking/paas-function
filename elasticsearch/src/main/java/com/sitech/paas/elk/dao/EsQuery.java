package com.sitech.paas.elk.dao;

import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import java.util.List;
import java.util.Map;

/**
 * 考虑这里实现两个共同的方法，聚合查询与条件查询
 * @author liwei_paas
 * @date 2019/9/4
 */
public interface EsQuery {

    /**
     * es上@timestamp字段时间格式
     */
    String TIMESTAMP_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * 空查询，类似不带条件查询的sql： select * from table
     * @return
     */
    SearchHits queryAll();

    Aggregation queryAggregations(Map<String,String> terms, List<String> aggKeys);

    SearchHits queryTerms(Map<String,String> terms);

}