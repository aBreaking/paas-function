package com.sitech.paas.javagen.demo;

import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.javagen.demo.util.ServiceCaller;
import com.sitech.paas.javagen.json.TasksGen;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @{USER}
 * @{DATE}
 */
public class MyTest {
    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    static final String JSON_PATH = "D:\\workspace\\paas-function\\javagen\\src\\main\\resources\\demo.json";

    @Test
    public void testTasksGen() throws IOException {
        String file = FileUtils.readFileToString(new File(JSON_PATH), "utf-8");
        JSONObject jsonObject = JSONObject.parseObject(file);
        String o = jsonObject.getJSONArray("tasks").getJSONObject(0).getJSONObject("inputs").getString("pin");

        //After
        //TasksGen tasksGen = new TasksGen();
    }


    public static void mainMethod(String[] args) throws ClassNotFoundException {
        String utilClass = "com.sitech.paas.javagen.demo.util.CommonUtil";
        Class<?> commonUtilClass = Class.forName(utilClass);
        Method[] methods = commonUtilClass.getDeclaredMethods();
        for (Method method : methods){
            String methodName = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Class<?> returnType = method.getReturnType();
            System.out.println(methodName);
            System.out.println(Arrays.toString(parameterTypes));
            System.out.println(returnType.getName());
        }
    }

    @Test
    public void testReplace(){
        String s = "a;b;c";
        String s1 = s.replaceAll(";" , "\\\n");
        System.out.println(s1);
    }

    public static void main01(String[] args) throws InterruptedException, ExecutionException {
        Future<String> future = executorService.submit(() -> ServiceCaller.callRest("",""));
        String s = null;
        try {
            s = future.get(900, TimeUnit.MILLISECONDS);
            System.out.println(s);
        } catch (TimeoutException e) {
            future.cancel(true);
            e.printStackTrace();
        }
    }

    @Test
    public void zipTest() throws Exception {
        List<String> list = Collections.singletonList("D:\\pd.sql");
        String target = "D:\\workspace\\my.txt";
        reZipCsvFiles(target,list);
    }


    private void reZipCsvFiles(String targetZipRealPath, List<String> targetFilePathList) throws Exception {
        File targetZipFile = new File(targetZipRealPath);
        InputStream in = null;
        FileOutputStream fos = null;
        ZipOutputStream zipOutputStream = null;
        try {
            fos = new FileOutputStream(targetZipFile);
            zipOutputStream = new ZipOutputStream(fos);
            for (String csvFilePath: targetFilePathList) {
                in = new FileInputStream(csvFilePath);
                String csvFileName = csvFilePath.substring(csvFilePath.lastIndexOf(File.separator) + 1);
                zipOutputStream.putNextEntry(new ZipEntry(csvFileName));
                IOUtils.copy(in, zipOutputStream);
                zipOutputStream.closeEntry();
                in.close();
            }
        } finally {
            if (zipOutputStream != null) {
                zipOutputStream.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }
}
