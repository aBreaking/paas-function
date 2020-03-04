package com.sitech.paas.javagen.demo;

import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.javagen.generator.GenUtil;
import com.sitech.paas.javagen.json.TasksGen;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 最基本的流程实现
 * @author liwei_paas
 * @date 2020/3/3
 */
public class Main {

    //json文件
    static final String JSON_PATH = "D:\\workspace\\paas-function\\javagen\\src\\main\\resources\\demo.json";

    public static void main(String[] args) throws Exception {
        String outZipDir = "D:\\";
        String file = FileUtils.readFileToString(new File(JSON_PATH), "utf-8");
        JSONObject inputJson = JSONObject.parseObject(file);
        String outZip = outZipDir + inputJson.getString("name")+".zip";
        TasksGen tasksGen = new TasksGen();
        List<String> tasks = tasksGen.gen(inputJson.getJSONArray("tasks"));
        final Map<String,Object> map = new HashMap();
        map.put("codes",tasks);
        GenUtil.generatorCode(new FileOutputStream(new File(outZip)),map);
    }




}
