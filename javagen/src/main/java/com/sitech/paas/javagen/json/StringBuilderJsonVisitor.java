package com.sitech.paas.javagen.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 将json解析后，字符串的方式拼接起来
 * @author liwei_paas
 * @date 2020/3/4
 */
public class StringBuilderJsonVisitor implements JsonVisitor{

    StringBuilder stringBuilder = new StringBuilder();

    JSONObject jsonObject;

    public StringBuilderJsonVisitor(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public void visit(TaskGen task) {

    }

    @Override
    public void visit(TasksGen tasksGen) {
        JSONArray tasks = jsonObject.getJSONArray("tasks");
    }

    @Override
    public void visit(ImportsGen imports) {

    }


}
