package com.sitech.paas.javagen.benchmark.interpret;

import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.javagen.benchmark.parser.TaskParser;
import com.sitech.paas.javagen.benchmark.parser.TasksParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author liwei_paas
 * @date 2020/3/24
 */
public class Interpreter {

    private static String DEFAULT_OUTZIP = "C:\\Users\\MI\\Desktop\\服务编排\\gen\\";

    public void gen(String inputJsonStr,List<String> javaTemplates) throws FileNotFoundException {
        JSONObject inputJson = JSONObject.parseObject(inputJsonStr);
        TasksParser tasksGen = new TasksParser(inputJson.getJSONArray("tasks"));

        //返回数据 outputGen
        JSONObject output = inputJson.getJSONObject("output");
        List<String> outList = new ArrayList<>();
        output.forEach((k,v)->outList.add(TaskParser.strPlaceholder((String) v,null)));

        //代码输出
        String outZip = DEFAULT_OUTZIP + inputJson.getString("name")+".zip";
        final Map<String,Object> map = new HashMap();
        map.put("expressList",tasksGen.parse());
        map.put("importSet",tasksGen.getImportCollector());
        map.put("outList",outList);
        TemplateUtil.generatorCode(new FileOutputStream(new File(outZip)),map,javaTemplates);
    }
}
