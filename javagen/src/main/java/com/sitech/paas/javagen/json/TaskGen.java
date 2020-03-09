package com.sitech.paas.javagen.json;

import com.alibaba.fastjson.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 每个任务
 * @author liwei_paas
 * @date 2020/3/3
 */
public class TaskGen implements GenAccepter{

    public static final String REGEX_PLACEHOLDER = "\\$\\{[a-zA-Z0-9]+\\.?[{[a-zA-Z0-9]]*\\}";
    public static final Pattern PATTERN = Pattern.compile(REGEX_PLACEHOLDER);

    JSONObject jsonTask;

    public TaskGen(JSONObject jsonTask) {
        this.jsonTask = jsonTask;
    }

    public String gen(){
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
            String statement = ((String) v);
            String inputType = inputsExtra.getString(p);
            statement = strPlaceholder(statement,inputType);
            builder.append(statement).append(",");
        });
        String express = builder.toString();
        if (express.endsWith(",")){
            express = express.substring(0,express.length()-1);
        }
        return express+")";
    }

    public String genCode(JSONObject jsonTask){
        String statement = jsonTask.getJSONObject("body").getString("java");
        statement = placeholder(statement, null);
        return "{\n"+statement.replaceAll(";" , ";\\\n")+"}";
    }

    public static String strPlaceholder(String statement, String inputType){
        if (inputType!=null && inputType.equals("java.lang.String")){
            //对字符串中可能 有的 “ 符号进行转义
            statement = statement.replaceAll("\"","\\\\\"");
            //然后字符串需要加上 “
            statement = "\""+statement+"\"";
        }
        return placeholder(statement,inputType);
    }

    /**
     * 占位符的处理，先暂时最简单的考虑，字符串 与 json
     * ${name} -> name
     * ${user.name} -> user.getString("name");
     * @param statement
     * @param type 占位符的类型
     * @return
     */
    public static String placeholder(String statement, String type){
        Matcher matcher = PATTERN.matcher(statement);
        StringBuilder sb = new StringBuilder();
        while(matcher.find()){

            String g = matcher.group();
            //占位符表达式的描述处理
            String e = g.substring(2, g.length() - 1);
            if (e.indexOf(".")!=-1){
                //json字符串的处理： a.name -> a.getString("name");
                String[] split = e.split("\\.");
                String k = split[0];
                String v = split[1];
                if(type!=null){
                    //转换为fastJson的get方法表述
                    if (type.matches("java.lang.\\w+")){
                        String t = type.substring(type.lastIndexOf(".") + 1);
                        v = "get"+t+"(\""+v+"\")";
                    }else{
                        v = "("+type+")get(\""+v+"\")";
                    }
                    e = k+"."+v;
                }
            }
            //对statement重新拼接
            int start = matcher.start();
            int end = matcher.end();

            sb.append(statement.substring(0,start));
            sb.append(e);
            statement = statement.substring(end);
            matcher = PATTERN.matcher(statement);
        }
        sb.append(statement);
        return sb.toString();
    }

    @Override
    public void accept(JsonVisitor visitor) {
        visitor.visit(this);
    }
}