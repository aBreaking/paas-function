package com.sitech.esb.jsoneye.result;

import com.sitech.esb.jsoneye.Result;
import com.sitech.esb.jsoneye.ResultOutput;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 直接将结果打印到控制台上
 * @author liwei_paas
 * @date 2019/6/12
 */
public class ResultOutputConsole implements ResultOutput {

    public void output(Result result) throws IOException {
        System.out.println("缺少的节点：");
        prettyPrint(result.getLackMap(),"缺少");
        System.out.println("多的节点：");
        prettyPrint(result.getRemainMap(),"多余");
    }

    private void prettyPrint(Map<String, List<String>> map,String desc){
        for (String key : map.keySet()){
            System.out.println("\t"+key+"下面，"+desc+"了这些节点："+map.get(key));
        }
    }
}
