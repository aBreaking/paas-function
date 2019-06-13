package com.sitech.esb.jsoneye.resv;

import com.sitech.esb.jsoneye.Resolver;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  excel的解析器
 * @author liwei_paas 
 * @date 2019/6/11
 */
public class ExcelResolver implements Resolver {
    
    Sheet sheet;
    int endIndex;
    public ExcelResolver(Sheet sheet,int endIndex){
        this.sheet = sheet;
        this.endIndex = endIndex;
    }
    
    public List<String> parse() {

        Map<String,ExcelBody> keyBodyMap = new HashMap<String,ExcelBody>();
        //从第二行开始读,最后一行不需要读
        for (int i=1;i<=endIndex;i++){
            Row row = sheet.getRow(i);
            String node = row.getCell(0).toString();
            if (node==null||node.trim().length()==0){
                continue;
            }
            if (row.getCell(1)==null){
                System.out.println(row.getRowNum());
            }
            String parentNode = row.getCell(1).toString();
            String constraint = row.getCell(2).toString();
            ExcelBody excelBody = new ExcelBody(node);

            String parentKey;
            if (keyBodyMap.containsKey(parentNode)){
                ExcelBody parentJb = keyBodyMap.get(parentNode);
                parentJb.setHasSon(true);
                parentKey = toString(keyBodyMap.get(parentNode));
            }else{
                parentKey = parentNode;
            }
            //json数组的处理
            if (constraint.equals("*")){
                excelBody.setKey(node+separator+"[]");
            }
            excelBody.setParentKey(parentKey);
            keyBodyMap.put(node,excelBody);
        }
        List<String> list = new ArrayList<String>();
        for (String key : keyBodyMap.keySet()){
            ExcelBody excelBody = keyBodyMap.get(key);
            if (excelBody.hasSon()){
                continue;
            }
            list.add(toString(excelBody));
        }
        return list;
    }

    private static String toString(ExcelBody body){
        return body.getParentKey()+separator+body.getKey();
    }

    static class ExcelBody{
        private String key;
        private String parentKey;
        //是否还有儿子，默认false;
        private boolean hasSon = false;

        public ExcelBody(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getParentKey() {
            return parentKey;
        }

        public void setParentKey(String parentKey) {
            this.parentKey = parentKey;
        }

        public boolean hasSon() {
            return hasSon;
        }

        public void setHasSon(boolean son) {
            this.hasSon = son;
        }
    }
}
