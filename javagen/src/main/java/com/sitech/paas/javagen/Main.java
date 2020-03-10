package com.sitech.paas.javagen;

import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.javagen.generator.GenUtil;
import com.sitech.paas.javagen.json.TaskGen;
import com.sitech.paas.javagen.json.TasksGen;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 指定一个json文件，然后可生成java代码，代码自动打包
 * @author liwei_paas
 * @date 2020/3/3
 */
public class Main {

    @Test
    public void testService() throws IOException {
        //生成的代码list
        String JSON_PATH = "D:\\workspace\\paas-function\\javagen\\src\\main\\resources\\other-demo.json";

        //模板
        List<String> templates = new ArrayList<String>();
        templates.add("vm/Main.java.vm");
        templates.add("vm/dto/Order.java");
        templates.add("vm/dto/User.java");
        templates.add("vm/service/OrderService.java");
        templates.add("vm/service/ProductService.java");
        templates.add("vm/service/UserService.java");
        templates.add("vm/service/impl/CommonServiceImpl.java");


        gen(JSON_PATH,templates);
    }

    public static void main(String[] args) throws Exception {
        //生成的代码list
        String JSON_PATH = "D:\\workspace\\paas-function\\javagen\\src\\main\\resources\\demo.json";

        //模板
        List<String> templates = new ArrayList<String>();
        templates.add("vm/Main.java.vm");
        templates.add("vm/util/CommonUtil.java.vm");
        templates.add("vm/util/ServiceCaller.java.vm");

        gen(JSON_PATH,templates);
    }


    public static void gen(String jsonPath,List<String> templates) throws IOException {
        //生成的代码list
        String file = FileUtils.readFileToString(new File(jsonPath), "utf-8");
        JSONObject inputJson = JSONObject.parseObject(file);
        TasksGen tasksGen = new TasksGen(inputJson.getJSONArray("tasks"));
        tasksGen.gen();

        //返回数据 outputGen
        JSONObject output = inputJson.getJSONObject("output");
        List<String> outList = new ArrayList<>();
        output.forEach((k,v)->{
            outList.add(TaskGen.strPlaceholder((String) v,null));
        });

        //代码输出
        String outZipDir = "C:\\Users\\MI\\Desktop\\服务编排\\gen\\";
        String outZip = outZipDir + inputJson.getString("name")+".zip";
        final Map<String,Object> map = new HashMap();
        map.put("expressList",tasksGen.getExpressList());
        map.put("importSet",tasksGen.getImportSet());
        map.put("outList",outList);
        System.out.println(map);
        GenUtil.generatorCode(new FileOutputStream(new File(outZip)),map,templates);
    }

}
