package com.sitech.paas.javagen.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.javagen.generator.Express;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 将json解析后，字符串的方式拼接起来
 * @author liwei_paas
 * @date 2020/3/4
 */
public class MyJsonVisitor implements JsonVisitor{

    List<Express> expressList = new ArrayList<>();

    Set<String> importSet = new HashSet<>();

    JSONObject jsonObject;

    public MyJsonVisitor(JSONObject jsonObject) {
         this.jsonObject = jsonObject;
    }

    @Override
    public void visit(TaskGen task) {
        //task中两个产出 额外的包、代码表达式
    }

    @Override
    public void visit(TasksGen tasksGen) {
        JSONArray tasks = jsonObject.getJSONArray("tasks");

    }

    @Override
    public void visit(ImportsGen imports) {

    }


}
