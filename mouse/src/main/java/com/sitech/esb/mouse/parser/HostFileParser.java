package com.sitech.esb.mouse.parser;

import com.sitech.esb.mouse.filter.DefaultFilter;
import com.sitech.esb.mouse.filter.Filter;
import com.sitech.esb.mouse.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HostFileParser implements Parser<List<String>>{

    Filter filter;
    public HostFileParser(Filter filter){
        this.filter = filter;
    }

    public HostFileParser(){
        this.filter = new DefaultFilter();
    }


    /**将三维的监控指标的文件，解析成sql的insert语句的values部分，如：
     * 三维的监控指标文件格式如下：
            #主机名1#
            监控指标1,监控指标2,监控指标3,.....
            监控指标1,监控指标2,监控指标3,.....
            ....
            #主机名1#
            #主机名2#
            监控指标1,监控指标2,监控指标3,.....
            ...
            #主机名2#
            ...
     *解析成values部分：
        "监控指标1","监控指标2","监控指标3"
     * TODO 还有问题，主键呢？  insertTime倒是好办
     * 默认还会插入主键
     * @param resource
     * @return
     * @throws IOException
     */
    public List<String> parse(Resource resource) throws IOException {
        Map<String, List<String>> listMap = resolve(resource);

        List<String> sqlValues = new ArrayList<>();
        for(String host : listMap.keySet()){
            List<String> statements = listMap.get(host);
            for(String statement : statements){
                //是否需要通过检查呢。可能某些数据并不需要，
                if(filter.passCheck(statement)){

                    StringBuilder builder = new StringBuilder("");

                    builder.append("'"+host+"'"+",");
                    String[] split = statement.split(",");
                    for(String _s : split){
                        builder.append("'"+_s+"'"+",");
                    }
                    builder.replace(builder.length()-1,builder.length()," ");
                    sqlValues.add(builder.toString());
                }
            }
        }
        return sqlValues;
    }


    /**
     * 将文件的解析成map，key就是每台的主机名，List<String>就是该主机对应一系列信息
     * @param resource
     * @return
     * @throws IOException
     */
    public Map<String, List<String>> resolve(Resource resource) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        String s = "";
        Map<String, List<String>> listMap = new HashMap<>();

        List<String> bigList = new ArrayList<>();
        String hostTemp = "";
        while ((s=reader.readLine())!=null){
            if(s.startsWith("#")){
                String host = s.substring(1,s.length()-1);
                if(host.equals(hostTemp)){
                    listMap.put(host,bigList);
                }else{
                    hostTemp = host;
                    bigList = new ArrayList<>();
                }
            }else{
                bigList.add(s);
            }
        }
        return listMap;
    }

    @Deprecated
    public Map<String, List> parse_back(Resource resource) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        String s = "";
        Map<String, List> listMap = new HashMap<>();

        //TODO 将这个xsList直接换成sql语句？values(?,?,?,?)
        List<String> xsList = new ArrayList<>();

        List<Object> bigList = new ArrayList<>();
        String hostTemp = "";
        while ((s=reader.readLine())!=null){
            if(s.startsWith("#")){
                String host = s.substring(1,s.length()-1);
                if(host.equals(hostTemp)){
                    listMap.put(host,bigList);
                }else{
                    hostTemp = host;
                    bigList = new ArrayList<>();
                }
            }else{
                String[] split = s.split(",");
                for (String _s:split){
                    xsList.add(_s);
                }
                bigList.add(xsList);
                xsList = new ArrayList<>();
            }
        }
        return listMap;
    }
}
