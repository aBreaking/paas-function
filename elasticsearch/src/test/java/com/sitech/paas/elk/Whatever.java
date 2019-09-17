package com.sitech.paas.elk;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.sitech.paas.elk.api.high.EsHighClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @{USER}
 * @{DATE}
 */
public class Whatever {

    @Test
    public void test04() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Date begin = dateFormat.parse("2019-08-30");
        final Date end = null;

        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        beginCalendar.setTime(begin);
        endCalendar.setTime(end);

        System.out.println(beginCalendar.before(endCalendar));

        List<String> list = new ArrayList<>();

        while(beginCalendar.before(endCalendar)){
            list.add(dateFormat.format(beginCalendar.getTime()));
            beginCalendar.add(Calendar.DAY_OF_YEAR,1);
        }
        //最后一天也得加上
        list.add(dateFormat.format(end));
        System.out.println(list);
        System.out.println(begin);
        System.out.println(end);

    }


    @Test
    public void test03(){
        String json = "{\"name\":\"zhansan\",\"age\":14}";
        JSONObject jsonObject = (JSONObject) JSONObject.parse(json, Feature.OrderedField);
        System.out.println(JSONObject.toJSONString(jsonObject,true));
    }


    @Test
    public void test02() throws IOException {
        EsHighClient esHighClient = new EsHighClient();
        RestHighLevelClient client = esHighClient.getRestHighLevelClient();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String index = "esb-srvlog-2019-09-01";
        SearchRequest searchRequest = new SearchRequest(index);

        //过滤
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //指定池的数据
        boolQuery.must(QueryBuilders.termQuery("poolId","31"));
        boolQuery.must(QueryBuilders.termQuery("srvName","sUserOrdQry"));
        //错误的retmsg根据 retCode ~ 0+来匹配
        boolQuery.mustNot(QueryBuilders.regexpQuery("retCode","0+"));
        searchSourceBuilder.query(boolQuery);


        //聚合
        TermsAggregationBuilder retMsgAgg = AggregationBuilders.terms("retMsgAgg").field("retMsg");//TODO 这里的retMsgAgg也应该返回
        searchSourceBuilder.aggregation(retMsgAgg);
        System.out.println(searchSourceBuilder.toString());

        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = response.getAggregations();
        Terms terms = aggregations.get("retMsgAgg");
        System.out.println(terms);
    }

    @Test
    public void test01() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateStr = "2019-10-21T17:22:49.473Z";
        Date parse = simpleDateFormat.parse(dateStr);
        System.out.println(parse);
    }
}
