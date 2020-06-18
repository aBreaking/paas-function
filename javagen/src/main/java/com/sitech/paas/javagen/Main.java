package com.sitech.paas.javagen;

import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.javagen.benchmark.interpret.Interpreter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 指定一个json文件，然后可生成java代码，代码自动打包
 * @author liwei_paas
 * @date 2020/3/3
 */
public class Main {

    /**
     * 订单管理服务编排
     * @author liwei_paas
     * @date 2020/3/10
     */
    public static void main(String args[]) throws IOException {
        //生成的代码list
        String jsonPath = "D:\\workspace\\paas-function\\javagen\\src\\main\\resources\\demo-brm.json";
        String inputJson = FileUtils.readFileToString(new File(jsonPath), "utf-8");

        //模板
        List<String> templates = new ArrayList<String>();
        templates.add("vm/Main.java.vm");
        templates.add("com/sitech/paas/javagen/brm/BreCaller.class");

        Interpreter interpreter = new Interpreter();
        interpreter.gen(inputJson,templates);
    }
}
