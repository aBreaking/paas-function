package com.sitech.esb.jsoneye.resv;

import com.sitech.esb.jsoneye.Node;
import com.sitech.esb.jsoneye.NodeParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.*;

/**
 * @{USER}
 * @{DATE}
 */
public class MyExcelResolver implements NodeParser {

    Sheet sheet;
    int endIndex;

    public MyExcelResolver(Sheet sheet,int endIndex){
        this.sheet = sheet;
        this.endIndex = endIndex;
    }

    /**
     * 分成两步：
     *  第一步：先把sheet里面的全部读出来，每个参数作为Node，放在list里面
     *  第二部：对list 作自底向上 的归纳，最后归纳成一个Node
     * @return
     */
    public Node parse(){
        //用这个来保存所有的节点
        List<Node> allNodes = new ArrayList<>();
        for (int i=1;i<=endIndex;i++){
            Row row = sheet.getRow(i);
            Cell cell0 = row.getCell(0);
            Cell cell1 = row.getCell(1);
            if (cell0 == null || cell1 == null){
                continue;
            }
            String nodeName = cell0.toString();
            String parentNodeName = cell1.toString();
            if (nodeName==null || nodeName.trim().isEmpty() || parentNodeName==null || parentNodeName.trim().isEmpty()){
                continue;
            }

            String constraintStr = row.getCell(2).toString();
            int constraint = 0;
            if (constraintStr.matches("\\d.*$")){
                constraint = Float.parseFloat(constraintStr)==1.0f?1:0;
            }
            Node node = new Node();
            node.setName(nodeName);
            node.setConstraint(constraint);
            node.setParentName(parentNodeName);
            allNodes.add(node);
        }
        Node compress = compress(allNodes);
        return compress;
    }

    /**
     * 自底向上的 归纳
     *  也就是说，list里面的每个node，它的爹是哪个它必须知道的，从list 的最底层Node开始，逐步往上，找这个Node的爹，
     * 找到了之后就把它加上它爹的childNodes 里面去，那么最上面的一个就是最后的Node
     * @param list
     */
    private Node compress(List<Node> list){
        Node pNode = new Node();
        for (int i=list.size()-1;i>=0;i--){
            Node node = list.get(i);

            String ptName = node.getParentName();
            //找到这个节点的爹
            pNode = findFather(list, ptName, i);
            //将它塞到它爹下面去
            pNode.addChild(node);
        }
        return pNode;
    }

    private Node findFather(List<Node> list,String parentName,int reverseIndex){
        for (int i=reverseIndex;i>=0;i--){
            Node node = list.get(i);
            if (node.getName().equals(parentName)){
                return node;
            }
        }
        //没找到？那就新建一个
        Node node = new Node();
        node.setName(parentName);
        return node;
    }



}
