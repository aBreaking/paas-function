package com.sitech.esb.mouse.parser;

import com.mysql.jdbc.StringUtils;
import com.sitech.esb.mouse.filter.DefaultFilter;
import com.sitech.esb.mouse.filter.Filter;
import com.sitech.esb.mouse.resource.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class HostFileParser implements Parser<List<String>>{

    Filter filter;
    int colSize;
    public HostFileParser(Filter filter,int colSize){
        this.colSize = colSize;
        this.filter = filter;
    }

    public HostFileParser(int colSize){
        this.colSize = colSize;
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
                    int length = split.length;
                    for(int i=0;i<colSize;i++){
                        //可能数据文件中的某些字段并没有值
                        if(i<length){
                            builder.append("'"+split[i].trim()+"'"+",");
                        }else{
                            builder.append("'',");
                        }
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
        Map<String, List<String>> listMap = new LinkedHashMap<>();

        List<String> bigList = new ArrayList<>();
        String hostTemp = "";
        while ((s=reader.readLine())!=null){
            if(s.startsWith("#")){
                String host = s.substring(1,s.length()-1);
                if(host.equals(hostTemp)){
                    listMap.put(host, bigList);
                }else{
                    hostTemp = host;
                }
                bigList = new ArrayList<>();
            }else{
                if(isVaild(s)){
                    bigList.add(s);
                }
            }
        }
        return listMap;
    }

    private boolean isVaild(String statement){
        if(StringUtils.isEmptyOrWhitespaceOnly(statement)){
            return false;
        }
        //默认使用*作为注释
        if(statement.startsWith("*")){
            return false;
        }
        return true;
    }

    /**
     * 考虑下这种的情况
     * Mouse
     * String name;
     *     List<String> statements = new ArrayList<>();
     *     List<Mouse> sonMouseList = new ArrayList<>();
     */
    /*public Mouse resolve(Resource resource,int tmp) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        Mouse mouseTemp = new Mouse();
        Mouse sonMouseTemp = new Mouse();
        Mouse bigMouse = new Mouse();
        String s = "";
        while ((s=reader.readLine())!=null){
            //目前只考虑子模块
            if(s.startsWith("##")){
                //子mouse
                String sonMouseName = s.substring(2, s.length() - 2);
                if(sonMouseName.equals(mouseTemp.getName())){
                    mouseTemp.getSonMouseList().add(sonMouseTemp);
                    sonMouseTemp = new Mouse();
                    continue;
                }else{
                    //TODO这里有问题
                    mouseTemp.getSonMouseList().add(sonMouseTemp);
                }
                sonMouseTemp.setName(sonMouseName);
                continue;
            }
            if(s.startsWith("#")){
                String name = s.substring(1, s.length() - 1);
                if(name.equals(mouseTemp.getName())){
                    bigMouse.getSonMouseList().add(mouseTemp);
                    mouseTemp = new Mouse();
                    continue;
                }else{
                    mouseTemp.setName(name);
                }
                continue;
            }
            sonMouseTemp.getStatements().add(s);
        }
        return bigMouse;
    }*/


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

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }
}
