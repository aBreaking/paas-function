package com.sitech.esb.jsoneye.result;

import com.sitech.esb.jsoneye.Result;
import com.sitech.esb.jsoneye.ResultOutput;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * 结果输出到某个文件里面去
 * @author liwei_paas
 * @date 2019/6/11
 */
public class ResultOutputFile {
    String outputFile;

    public ResultOutputFile(String outputFile){
        this.outputFile = outputFile;
    }

    public void output(Map<String,Result> map) throws FileNotFoundException {
        OutputStream outputStream = new FileOutputStream(outputFile);
        PrintWriter writer = new PrintWriter(outputStream);

        for (String key : map.keySet()){
            writer.println(key+"的比较差异如下：");
            Result result = map.get(key);
            List<String> lack = null;
            List<String> remain = null;
            boolean hasContent = false;
            if (!lack.isEmpty()){
                hasContent = true;
                writer.println(Result.LACK+"...");
                for (String s : lack){
                    writer.println(s);
                }
            }
            if (!remain.isEmpty()){
                hasContent = true;
                writer.println(Result.REMAIN+"...");
                for (String s : remain){
                    writer.println(s);
                }
            }
            if (!hasContent){
                writer.println("无");
            }
        }
        writer.close();
    }
}
