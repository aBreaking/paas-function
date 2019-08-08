package com.sitech.esb.jsoneye;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sitech.esb.jsoneye.resv.MyExcelResolver;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @{USER}
 * @{DATE}
 */
public class MyComparator implements Comparator{

    /**
     * 输入的excel文档
     */
    Workbook workbook;

    /**
     * 输出
     */
    Result result = new Result();

    public static String FIRST_JSON_NODE_NAME = "UNI_BSS_BODY";

    public MyComparator(Workbook workbook) {
        this.workbook = workbook;
    }

    public MyComparator(String excelFile) throws IOException, InvalidFormatException {
        this.workbook = new XSSFWorkbook(new File(excelFile));
    }

    /**
     * 具体的比较操作
     * @return
     */
    public void compare(){
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        while (isBlank(sheet.getRow(lastRowNum))){
            lastRowNum--;
        }
        //找出需要被比较的json串，只要FIRST_JSON_NODE_NAME的数据
        String comparedJsonStr = sheet.getRow(lastRowNum).getCell(0).toString();
        JSONObject jsonObject = JSONObject.parseObject(comparedJsonStr);
        JSONObject compareJson = new JSONObject();
        compareJson.put(FIRST_JSON_NODE_NAME,jsonObject.getJSONObject(FIRST_JSON_NODE_NAME));
        //规范参数
        NodeParser parser = new MyExcelResolver(sheet,--lastRowNum);
        Node node = parser.parse();
        Set<Node> set = new HashSet<>();
        set.add(node);
        compare(set,compareJson,FIRST_JSON_NODE_NAME);
    }

    public Result getResult(){
        return result;
    }

    /**
     *
     * @param benchNode excel表格里解析后的节点对象
     * @param compareJson 要比较的json对象
     * @parem parentNodeNama 父节点名，比较过程中发现有缺少/多余的节点，能够通过parentNodeName定位到是哪个节点
     */
    private void compare(Set<Node> benchNode,Object compareJson,String parentNodeName){
        if (benchNode.isEmpty()){
            return;
        }
        if (compareJson instanceof JSONObject){
            doCompareJsonObject(benchNode, (JSONObject) compareJson,parentNodeName);
        }
        if (compareJson instanceof JSONArray){
            doCompareJsonArray(benchNode, (JSONArray) compareJson,parentNodeName);
        }
    }

    private void doCompareJsonObject(Set<Node> benchNode,JSONObject compareJson,String parentNodeName){

        Iterator<Node> iterator = benchNode.iterator();
        while (iterator.hasNext()){
            Node next = iterator.next();

            iterator.remove();

            if (!compareJson.containsKey(next.getName()) && next.getConstraint()!=0){
                //说明这个节点没得，就不用再继续比较它的子节点了
                result.addLack(parentNodeName,next.getName());
                continue;
            }

            //继续比较它的节点
            Set<Node> childNodes = next.getChildNodes();
            Object o = compareJson.get(next.getName());
            compare(childNodes,o,next.toString());
            compareJson.remove(next.getName());
        }
        //继续看compareJson是不是还有多的
        if (compareJson.keySet().isEmpty()){
            return;
        }
        for (String key : compareJson.keySet()){
            result.addRemain(parentNodeName,key);
        }
    }

    /**
     * json数组的比较
     * @param benchNode
     * @param compareArray
     */
    private void doCompareJsonArray(Set<Node> benchNode,JSONArray compareArray,String parentNodeName){

        for (int i = 0; i < compareArray.size(); i++) {
            Object o = compareArray.get(i);
            String pNodeName = parentNodeName+"数组下面第"+(i+1)+"个";
            Set<Node> _bNode = new HashSet<>();
            _bNode.addAll(benchNode);
            compare(_bNode,o,pNodeName);
        }
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


}
