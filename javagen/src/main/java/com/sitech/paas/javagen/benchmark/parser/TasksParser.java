package com.sitech.paas.javagen.benchmark.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.javagen.benchmark.interpret.Express;

import java.util.ArrayList;
import java.util.List;

/**
 * 对tasks的解析
 * @author liwei_paas
 * @date 2020/3/17
 */
public class TasksParser {

    private JSONArray tasks;
    private ImportCollector importCollector;

    public TasksParser(JSONArray tasks, ImportCollector importCollector) {
        this.tasks = tasks;
        this.importCollector = importCollector;
    }

    public TasksParser(JSONArray tasks) {
        this.tasks = tasks;
        importCollector = new ImportCollector();
    }

    public List<Express> parse(){
        List<Express> expressList = new ArrayList<>(tasks.size());
        tasks.forEach(o -> {
            Express express = new Express();
            JSONObject task = (JSONObject) o;
            //如果有包先收集起来
            if (task.containsKey("imports")){
                importCollector.addClass(task.getString("imports"));
            }
            //固定的标准
            express.setVarname(task.getString("alias"));
            express.setTimeout(task.getInteger("timeout"));

            //这里的type应该放到task里面去解析
            String type = task.getString("type");
            Integer expressType  = Express.METHOD_TYPE;
            if (type.equals("1")){
                expressType = Express.CODE_TYPE;
            }else{
                importCollector.addClass(type);
            }
            //表达式基本信息
            express.setType(expressType);
            //表达式的代码块
            TaskParser taskGen = new TaskParser(task,importCollector);
            express.setCode(taskGen.parseMethod());

            expressList.add(express);
        });
        return expressList;
    }

    public String type(String returnType){
        if (returnType.matches("java.lang.\\w+") || importCollector.contains(returnType)){
            return returnType.substring(returnType.lastIndexOf(".") + 1);
        }
        return returnType;
    }

    public ImportCollector getImportCollector() {
        return importCollector;
    }

    public void setImportCollector(ImportCollector importCollector) {
        this.importCollector = importCollector;
    }
}
