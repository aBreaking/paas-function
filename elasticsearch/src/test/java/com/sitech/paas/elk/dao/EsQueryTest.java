package com.sitech.paas.elk.dao;


import com.sitech.paas.elk.dao.impl.BaseEsQuery;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EsQueryTest {

    @Test
    public void test01() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin = dateFormat.parse("2019-09-01 00:00:47");
        Date end = dateFormat.parse("2019-09-01 00:09:47");
        String index = EsQueryFactory.INDEX_ESB_SRVLOG;

        EsQueryFactory esQueryFactory = new EsQueryFactory();
        BaseEsQuery query = esQueryFactory.build(11, index,begin,end);
        Map<String, String> map = new HashMap<>();
        map.put("srvName", "sPFeeQuery");
        map.put("dbId","A2");
        SearchHits searchHits = query.queryTerms(map);
        System.out.println(searchHits.getTotalHits());
    }
}
