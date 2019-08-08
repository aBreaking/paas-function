package com.sitech.esb.jsoneye;

import com.sitech.esb.jsoneye.result.ResultOutputConsole;
import com.sitech.esb.jsoneye.result.ResultOutputExcel;
import java.io.File;
import java.util.Map;

/**
 * 主类
 * @author liwei_paas
 * @date 2019/6/11
 */
public class Runner {

    static Comparator comparator;

    static ResultOutput resultOutput;

    public static void main(String args[]) throws Exception {
        if (args.length==0) {
            System.err.println("请指定输入文件");
            return;
        }
        String inputFile = args[0];
        if (args.length>1){
            ResultOutputFatory.get(args[1]);
        }else{
            resultOutput = new ResultOutputConsole();
        }

        comparator = new MyComparator(inputFile);
        comparator.compare();
        Result result = comparator.getResult();
        resultOutput.output(result);
    }

}
