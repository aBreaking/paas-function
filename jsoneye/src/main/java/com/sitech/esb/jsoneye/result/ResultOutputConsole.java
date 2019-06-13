package com.sitech.esb.jsoneye.result;

import com.sitech.esb.jsoneye.Result;
import com.sitech.esb.jsoneye.ResultOutput;

import java.io.IOException;
import java.util.Map;

/**
 * 直接将结果打印到控制台上
 * @author liwei_paas
 * @date 2019/6/12
 */
public class ResultOutputConsole implements ResultOutput {

    public void output(Map<String, Result> map) throws IOException {
        for (String key : map.keySet()){
            System.out.println(key+"的比较结果");
            prettyPrint(map.get(key));
            System.out.println("-----------------\n");
        }
        System.out.println("比较完成");
    }

    private void prettyPrint(Result result){
        StringBuilder builder = new StringBuilder();
        builder.append(Result.LACK+"节点：\n");
        for (String s : result.lack()){
            builder.append(s);
            builder.append("\n");
        }
        builder.append(Result.REMAIN+"节点：\n");
        for (String s : result.remain()){
            builder.append(s);
        }
        System.out.println(builder.toString());
    }
}
