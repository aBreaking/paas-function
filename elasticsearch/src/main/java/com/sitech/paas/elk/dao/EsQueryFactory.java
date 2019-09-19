package com.sitech.paas.elk.dao;

import com.sitech.paas.elk.dao.impl.BaseEsQuery;
import com.sitech.paas.elk.dao.impl.EsbwsEsQuery;
import com.sitech.paas.elk.domain.EsEsbConfig;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * FIXME 有这么几个问题，
 *  EsQuery的生产应该只是根据索引；
 *  通用的查询：esb池、时间范围这个应该怎么处理
 *
 * 目前将上面条件全部作为EsQuery的生产，还是有点问题的
 *
 * @author liwei_paas 
 * @date 2019/9/9
 */
public class EsQueryFactory {

    /**
     * 基础的三个esb索引
     */
    public static final String INDEX_ESB_ESBWS = "esb-esbws";
    public static final String INDEX_ESB_SRVLOG = "esb-srvlog";
    public static final String INDEX_ESB_INPARAM = "esb-inparam";


    /**
     * 这里可能有个问题，时间范围本不应该参与EsQuery创建的
     * @param esbPoolId
     * @param index
     * @param begin
     * @param end
     * @return
     */
    public BaseEsQuery build(Integer esbPoolId,String index,Date begin,Date end){
        return buildQuery(BaseEsQuery.class,esbPoolId,index,begin,end);
    }

    public EsbwsEsQuery buildEsbwsQuery(Integer esbPoolId,Date begin,Date end){
        return buildQuery(EsbwsEsQuery.class,esbPoolId,INDEX_ESB_ESBWS,begin,end);
    }

    private <T> T buildQuery(Class<? extends BaseEsQuery> clazz,Integer esbPoolId,String index,Date begin,Date end){
        EsEsbConfig esEsbConfig = getEsEsbConfig(esbPoolId);
        String[] esbHosts = getEsbHosts(esbPoolId);
        RestHighLevelClient esClient = getEsClient(esEsbConfig);
        String[] indices = indices(index, begin, end);
        BaseEsQuery baseEsQuery;
        try {
            baseEsQuery = clazz.newInstance();
            baseEsQuery.setClient(esClient);
            baseEsQuery.setIndices(indices);
            baseEsQuery.addEsbHosts(esbHosts).addRangeTime(begin,end);
            return (T) baseEsQuery;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private EsEsbConfig getEsEsbConfig(Integer esbPoolId){
        EsEsbConfig config = new EsEsbConfig();
        config.setEsUrl("172.21.11.65:9200");
        return config;
    }

    private String[] getEsbHosts(Integer esbPoolId){
        return new String[]{"10.113.161.51","10.113.161.52"};
    }

    /**
     * 通过EsEsbConfig 来初始化一个es的client,目前暂时没有考虑es集群的情况
     * @param esConfig
     * @return
     */
    private final RestHighLevelClient getEsClient(EsEsbConfig esConfig){
        String esUrl = esConfig.getEsUrl();
        String[] eu = esUrl.split(":");
        RestClientBuilder builder = RestClient.builder(new HttpHost(eu[0],Integer.parseInt(eu[1])));
        byte[] tokenByte = Base64.encodeBase64((esConfig.getEsUser()+":"+esConfig.getEsPwd()).getBytes());
        //将加密的信息转换为string
        String tokenStr = new String(tokenByte);
        String token = "Basic "+tokenStr;
        Header[] headers = new Header[]{
                new BasicHeader("Content-type","application/json;charset=utf-8"),
                new BasicHeader("Authorization",token)
        };
        builder.setDefaultHeaders(headers);
        return new RestHighLevelClient(builder);
    }


    /**
     * 从begin-end ，这几天的索引都可能用于查询
     * @param begin
     * @param end
     * @return
     */
    private String[] indices(String index,Date begin,Date end){
        if (begin==null && end==null){
            return new String[]{getIndex(index)};
        }
        if (begin==null){
            return new String[]{getIndex(index,end)};
        }
        if (end==null){
            return new String[]{getIndex(index,begin)};
        }
        return between(index,begin,end);
    }

    /**
     * 不指定天数，默认今天
     * @param index
     * @return
     */
    private String getIndex(String index){
        return getIndex(index,new Date());
    }

    /**
     * 索引，仅是某一天的索引
     * @param index
     * @param date
     * @return
     */
    private String getIndex(String index,Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format(date);
        return index + "-" + time;
    }

    /**
     * 两个相差的天数
     * @param begin
     * @param end
     * @return
     */
    private String[] between(String index,Date begin,Date end){

        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        beginCalendar.setTime(begin);
        endCalendar.setTime(end);

        if (endCalendar.before(beginCalendar)){
            return new String[]{_index(index,begin)};
        }
        if (endCalendar.get(Calendar.DATE)==beginCalendar.get(Calendar.DATE)){
            return new String[]{_index(index,end)};
        }
        ArrayList<String> list = new ArrayList<>();
        while(beginCalendar.before(endCalendar)){
            list.add(_index(index,beginCalendar.getTime()));
            beginCalendar.add(Calendar.DAY_OF_YEAR,1);
        }
        //最后一天也得加上
        list.add(_index(index,end));
        return list.toArray(new String[list.size()]);
    }

    private String _index(String index,Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return index+"-"+dateFormat.format(date);
    }
}
