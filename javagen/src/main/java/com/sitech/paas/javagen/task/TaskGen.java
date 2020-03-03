package com.sitech.paas.javagen.task;

import com.alibaba.fastjson.JSONObject;

/**
 * 每个任务
 * @author liwei_paas
 * @date 2020/3/3
 */
public class TaskGen {

    /**
     * 类.方法(arg1,arg2)
     * @param jsonTask
     * @return
     */
    public String gen(JSONObject jsonTask)  {
        StringBuilder builder = new StringBuilder();
        String type = jsonTask.getString("type");
        String method = jsonTask.getString("method");
        String simpleClassName = type.substring(type.lastIndexOf(".")+1);
        builder.append(simpleClassName+"."+method);
        builder.append("(");
        JSONObject inputs = jsonTask.getJSONObject("inputs");
        inputs.forEach((p,v)->{
            builder.append("\""+v+"\"").append(",");
        });
        String express = builder.toString();
        if (express.endsWith(",")){
            express = express.substring(0,express.length()-1);
        }
        return express+")";
    }
}
