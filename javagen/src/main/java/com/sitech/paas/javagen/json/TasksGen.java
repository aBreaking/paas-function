package com.sitech.paas.javagen.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.javagen.generator.Express;

import java.util.*;

/**
 * @{USER}
 * @{DATE}
 */
public class TasksGen implements GenAccepter{

    private List<Express> expressList = new ArrayList<>();
    private Set<String> importSet = new TreeSet<>();

    private JSONArray tasks;

    /**
     * 存放json里面的tasks解析后的内容
     * key : alias , values: express
     */
    protected final Map<String,String> aliasVarnameMap = new HashMap<>();

    public TasksGen(JSONArray tasks) {
        this.tasks = tasks;
    }

    public void gen(){
        tasks.forEach(o -> {
            Express express = new Express();
            JSONObject task = (JSONObject) o;
            //需要的包
            if (task.containsKey("imports"))
                addImport(task.getString("imports"));
            Integer expressType  = Express.METHOD_TYPE;
            String type = task.getString("type");
            if (type.equals("1")){
                expressType = Express.CODE_TYPE;
            }else{
                addImport(type);
            }
            //表达式基本信息
            express.setType(expressType);
            express.setVarname(task.getString("alias"));
            express.setVartype(type(task.getString("returnType")));
            express.setTimeout(task.getInteger("timeout"));

            //表达式的代码块
            TaskGen taskGen = new TaskGen(task);
            express.setCode(taskGen.gen());
            expressList.add(express);
        });
    }

    public void addImport(String imports) {
        String[] split = imports.split(";");
        for (String s : split) importSet.add(s);
    }

    public String type(String returnType){
        if (returnType.matches("java.lang.\\w+") || importSet.contains(returnType)){
            return returnType.substring(returnType.lastIndexOf(".") + 1);
        }
        return returnType;
    }

    @Override
    public void accept(JsonVisitor visitor) {
        visitor.visit(this);
    }

    public List<Express> getExpressList() {
        return expressList;
    }

    public Set<String> getImportSet() {
        return importSet;
    }
}
