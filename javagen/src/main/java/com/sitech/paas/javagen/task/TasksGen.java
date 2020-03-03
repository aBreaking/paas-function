package com.sitech.paas.javagen.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * @{USER}
 * @{DATE}
 */
public class TasksGen {

    public String gen(JSONArray tasks){
        StringBuilder builder = new StringBuilder();
        tasks.forEach(o -> {
            JSONObject task = (JSONObject) o;
            TaskGen taskGen = new TaskGen();
            String right = taskGen.gen(task);
            String alias = task.getString("alias");
            String left = "final Object "+alias+" = ";
            String express = left + "" + right;
            builder.append(express);
            builder.append("\n");
        });
        return builder.toString();
    }
}
