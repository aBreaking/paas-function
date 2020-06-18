package com.sitech.paas.javagen.benchmark.parser;

import com.alibaba.fastjson.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author liwei_paas
 * @date 2020/3/17
 */
public class TaskParser {

    public static final String REGEX_PLACEHOLDER = "\\$\\{[^}]+\\}";
    public static final Pattern PATTERN = Pattern.compile(REGEX_PLACEHOLDER);

    private JSONObject task;
    private ImportCollector importCollector;

    public TaskParser(JSONObject task, ImportCollector importCollector) {
        this.task = task;
        this.importCollector = importCollector;
    }

    public TaskParser(JSONObject task) {
        this.task = task;
        importCollector = new ImportCollector();
    }

    public String parseMethod()  {
        StringBuilder builder = new StringBuilder();
        String type = task.getString("type");
        //这里获取到是哪个类，需要先将其进行实例化，然后才能调用其方法
        importCollector.addClass(type);


        String method = task.getString("method");
        String simpleClassName = type.substring(type.lastIndexOf(".")+1);
        builder.append(simpleClassName+"."+method);
        builder.append("(");
        JSONObject inputs = task.getJSONObject("inputs");
        JSONObject inputsExtra = task.getJSONObject("inputsExtra");
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

    public static String strPlaceholder(String statement, String inputType){
        if (inputType!=null && inputType.equals("java.lang.String") && !statement.matches(".*"+REGEX_PLACEHOLDER+".*")){
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
}
