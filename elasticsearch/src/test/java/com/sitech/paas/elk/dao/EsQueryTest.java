package com.sitech.paas.elk.dao;


import com.sitech.paas.elk.dao.impl.BaseEsQuery;
import com.sitech.paas.elk.dao.impl.EsbwsEsQuery;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EsQueryTest {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date begin = dateFormat.parse("2019-09-01 00:00:47");
    Date end = dateFormat.parse("2019-09-01 00:30:47");
    String index = EsQueryFactory.INDEX_ESB_SRVLOG;
    EsQueryFactory esQueryFactory = new EsQueryFactory();


    public EsQueryTest() throws ParseException {
    }

    @Test
    public void test02(){
        EsbwsEsQuery esbwsEsQuery = esQueryFactory.buildEsbwsQuery(11, begin, end);
        ArrayList<String> list = new ArrayList<>();
        list.add("sPubUserWordBrandInfo");
        list.add("040107");

        SearchHits hits = esbwsEsQuery.queryInfoByRegexp(list);
        System.out.println(hits.getTotalHits());
    }

    @Test
    public void test01() throws ParseException {
        BaseEsQuery query = esQueryFactory.build(11, index,begin,end);
        Map<String, String> map = new HashMap<>();
        map.put("srvName", "sPFeeQuery");
        map.put("dbId","A2");
        SearchHits searchHits = query.queryTerms(map);
        System.out.println(searchHits.getTotalHits());
    }
}
