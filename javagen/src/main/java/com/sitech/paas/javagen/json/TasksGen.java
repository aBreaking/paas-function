package com.sitech.paas.javagen.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @{USER}
 * @{DATE}
 */
public class TasksGen implements GenAccepter{

    public List<String> gen(JSONArray tasks){
        List<String> list = new ArrayList<>();
        tasks.forEach(o -> {
            JSONObject task = (JSONObject) o;
            //别名，也作为变量名
            String alias = task.getString("alias");
            String returnType = task.getString("returnType");
            String left = "final "+returnType+" "+alias;

            TaskGen taskGen = new TaskGen();
            String methodOrCode = taskGen.gen(task);
            //对methodOrCode 里面依赖的占位符；目前一个是变量先不考虑重复的情况
            methodOrCode = placeholder(methodOrCode,null);

            if (task.containsKey("timeout")){
                int timeout = task.getInteger("timeout");
                methodOrCode = methodOrCode + ","+timeout;
            }
            String right = "submitTask(() -> "+methodOrCode+");";

            String express = left + "=" + right;
           list.add(express);
        });
        return list;
    }

    /**
     * 占位符的处理，先暂时最简单的考虑，字符串 与 json
     * @param statement
     * @param type 占位符的类型
     * @return
     */
    public String placeholder(String statement, String type){
        //TODO 待会考虑怎么去实现
        return statement;
    }

    @Override
    public void accept(JsonVisitor visitor) {
        visitor.visit(this);
    }
}
