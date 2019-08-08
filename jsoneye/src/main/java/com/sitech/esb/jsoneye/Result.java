package com.sitech.esb.jsoneye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 输出的结果
 * @author liwei_paas
 * @date 2019/6/11
 */
public class Result {

    public static final String LACK = "缺少";
    public static final String REMAIN = "多余";

    private Map<String,List<String>> lackMap = new HashMap<>();

    private Map<String,List<String>> remainMap = new HashMap<>();

    public void addLack(String parent,String node){
        add(lackMap,parent,node);
    }
    public void addRemain(String parent,String node){
        add(remainMap,parent,node);
    }

    private void add(Map<String,List<String>> map,String parent,String node){
        if (map.containsKey(parent)){
            map.get(parent).add(node);
        }else{
            ArrayList<String> list = new ArrayList<>();
            list.add(node);
            map.put(parent,list);
        }
    }

    public Map<String, List<String>> getLackMap() {
        return lackMap;
    }


    public Map<String, List<String>> getRemainMap() {
        return remainMap;
    }

}
