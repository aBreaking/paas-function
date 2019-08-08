package com.sitech.esb.jsoneye.result;

import com.sitech.esb.jsoneye.Result;
import com.sitech.esb.jsoneye.ResultOutput;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * result输出到excel中去
 * @author liwei_paas
 * @date 2019/6/12
 */
public class ResultOutputExcel{

    /**
     * 指定输出的文件
     */
    File file;


    public ResultOutputExcel(File outExcel){
        this.file = outExcel;
    }
    public ResultOutputExcel(String outExcelName){
        this.file = new File(outExcelName);
    }

    public void output(Map<String, Result> map) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        for (String key : map.keySet()){
            Result result = map.get(key);
            //创建sheet使用原名称
            Sheet sheet = workbook.createSheet(key + "比较结果");
            // result的数据写道该sheet里面去
            write(sheet,result);
        }
        if (file.exists()){
            file.delete();
        }
        file.createNewFile();
        workbook.write(new FileOutputStream(file));
        workbook.close();
        System.out.println("比较完成，输出文件："+file.getPath());
    }
    private void write(Sheet sheet,Result result){
        //先整个标题
        Row titleRow = sheet.createRow(0);
        say(titleRow,0,Result.LACK);
        say(titleRow,1,Result.REMAIN);

        List<String> lack = null;
        List<String> remain = null;

        //找出两个最大的部分

        //lack部分插入
        if (lack.isEmpty()){
            say(sheet.createRow(1),0,"无");
        }else{
            for (int i=0;i<lack.size();i++){
                say(sheet.createRow(i+1),0,lack.get(i));
            }
        }

        //remain部分插入
        if (remain.isEmpty()){
            Row row = sheet.getLastRowNum()>0?sheet.getRow(1):sheet.createRow(1);
            say(row,1,"无");
        }else{
            //首先需要判断创建到了那个位置的row
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum>remain.size()){
                for (int i=0;i<remain.size();i++){
                    say(sheet.getRow(i+1),1,remain.get(i));
                }
            }else{
                for (int i=0;i<lastRowNum;i++){
                    say(sheet.getRow(i+1),1,remain.get(i));
                }
                for (int i=lastRowNum;i<remain.size();i++){
                    say(sheet.createRow(i+1),1,remain.get(i));
                }

            }
        }

    }

    private void say(Row row,int index,String inLack){
        Cell ct1 = row.createCell(index);
        ct1.setCellValue(inLack);
    }

}
