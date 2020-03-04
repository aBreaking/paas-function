package com.sitech.paas.javagen.json;

import com.alibaba.fastjson.JSONObject;

/**
 * 每个任务
 * @author liwei_paas
 * @date 2020/3/3
 */
public class TaskGen implements GenAccepter{

    public String gen(JSONObject jsonTask){
        String type = jsonTask.getString("type");
        return type.equals("1")?genCode(jsonTask):genMethod(jsonTask);
    }

    /**
     * 类.方法(arg1,arg2)
     * @param jsonTask
     * @return
     */
    public String genMethod(JSONObject jsonTask)  {
        StringBuilder builder = new StringBuilder();
        String type = jsonTask.getString("type");
        String method = jsonTask.getString("method");
        String simpleClassName = type.substring(type.lastIndexOf(".")+1);
        builder.append(simpleClassName+"."+method);
        builder.append("(");
        JSONObject inputs = jsonTask.getJSONObject("inputs");
        JSONObject inputsExtra = jsonTask.getJSONObject("inputsExtra");
        inputs.forEach((p,v)->{
            Object value = v;
            if (inputsExtra.getString(p).equals("java.lang.String")){
                //对字符串中可能 有的 “ 符号进行转义
                String sv = ((String) v);
                value = sv.replaceAll("\"","\\\\\"");
                //如果输入是表达式
                if (sv.indexOf("&")!=-1 && sv.indexOf(".")!=-1){
                    value = sv.replaceAll("\\$","");
                }else{
                    value = "\""+value+"\"";
                }
            }
            builder.append(value).append(",");
        });
        String express = builder.toString();
        if (express.endsWith(",")){
            express = express.substring(0,express.length()-1);
        }
        return express+")";
    }

    public String genCode(JSONObject jsonTask){
        String body = jsonTask.getJSONObject("body").getString("java");
        return "{\n"+body.replaceAll(";" , ";\\\n")+"}";
    }

    @Override
    public void accept(JsonVisitor visitor) {
        visitor.visit(this);
    }
}
