package com.sitech.esb.jsoneye;

import com.alibaba.fastjson.JSONObject;
import com.sitech.esb.jsoneye.hand.MyHandler;
import com.sitech.esb.jsoneye.resv.ExcelResolver;
import com.sitech.esb.jsoneye.resv.JsonResolver;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 规范的报文与需要被比较的报文放在某个excel中。Comparator用于遍历某个excel文件里面所有的记录，分别进行比较，输出结果
 * 规范的报文，可以有两种类型：1、一种是局方提供的类型；2、第二种就是纯json串
 * @author liwei_paas
 * @date 2019/6/11
 */
public class Comparator {

    /**
     * 输入的excel文档
     */
    Workbook workbook;

    /**
     * 比较的方式
     */
    Handler handler;

    public Comparator(Workbook workbook){
        this();
        this.workbook = workbook;
    }
    public Comparator(String excelFile) throws IOException, InvalidFormatException {
        this();
        this.workbook = new XSSFWorkbook(new File(excelFile));
    }
    public Comparator(){
        this.handler = new MyHandler();
    }


    /**
     * 将比较后的结果放在map里面，以sheetName 作为key
     * 注意生成后的result顺序最好跟原来的一直，这里使用的了LinkedHashMap
     * @return
     */
    public Map<String,Result> compare2Map(){
        Map<String,Result> map = new LinkedHashMap<String, Result>();
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i=0;i<numberOfSheets;i++){
            Sheet sheet = workbook.getSheetAt(i);
            Result result = doCompare(sheet);
            String sheetName = sheet.getSheetName();
            map.put(sheetName,result);
        }
        return map;
    }

    /**
     * 具体的比较操作
     * @param sheet
     * @return
     */
    private Result doCompare(Sheet sheet){
        int lastRowNum = sheet.getLastRowNum();
        while (isBlank(sheet.getRow(lastRowNum))){
            lastRowNum--;
        }
        //找出需要被比较的json串
        String comparedJsonStr = sheet.getRow(lastRowNum).getCell(0).toString();
        JSONObject jsonObject = JSONObject.parseObject(comparedJsonStr);
        Resolver toBeComparedResolver = new JsonResolver(jsonObject);
        Resolver benchmarkPinResolver;
        if(lastRowNum<=1){
            Cell cell = sheet.getRow(0).getCell(0);
            JSONObject benchmarkJson = JSONObject.parseObject(cell.toString());
            benchmarkPinResolver = new JsonResolver(benchmarkJson);
        }else{
            benchmarkPinResolver = new ExcelResolver(sheet, --lastRowNum);
        }

        List<String> excelList = benchmarkPinResolver.parse();
        List<String> jsonList = toBeComparedResolver.parse();
        return handler.different(excelList,jsonList);
    }


    /**
     * 判断该excel行是否是空的
     * @param row
     * @return
     */
    private boolean isBlank(Row row){
        if (row==null){
            return true;
        }
        String s = row.getCell(0).toString();
        return s==null||s.trim().length()==0;
    }

    private class ResultVo{
        private String name;
        private Result result;
        public ResultVo(String name,Result result){
            this.name = name;
            this.result = result;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }
    }
}
