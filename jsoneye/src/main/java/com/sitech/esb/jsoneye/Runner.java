package com.sitech.esb.jsoneye;

import com.sitech.esb.jsoneye.result.ResultOutputExcel;
import java.io.File;
import java.util.Map;

/**
 * 主类
 * @author liwei_paas
 * @date 2019/6/11
 */
public class Runner {

    /**
     * 默认的输出文件名
     */
    public static final String DEFAULT_OUTPUT_EXCEL_NAME = "output.xlsx";

    public static void main(String args[]) throws Exception {
        if (args.length==0) {
            System.err.println("请指定输入文件");
            return;
        }
        String inputFile = args[0];
        String outputFile = args.length>1?args[1]:defaultOutputExcelPath(inputFile);
        //第二步，找出该文件的输入json，输出json
        Comparator comparator = new Comparator(inputFile);
        Map<String, Result> map = comparator.compare2Map();
        ResultOutput resultOutput = new ResultOutputExcel(outputFile);
        resultOutput.output(map);
    }


    public static String defaultOutputExcelPath(String inputFile){
        File file = new File(inputFile);
        return file.getParent()+File.separator+DEFAULT_OUTPUT_EXCEL_NAME;
    }
}
