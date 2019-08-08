package com.sitech.esb.jsoneye.resv;

import com.sitech.esb.jsoneye.Node;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.*;

/**
 * @{USER}
 * @{DATE}
 */
public class MyExcelResolver {

    Sheet sheet;
    int endIndex;

    public MyExcelResolver(Sheet sheet,int endIndex){
        this.sheet = sheet;
        this.endIndex = endIndex;
    }

    public Node parse(){
        //用这个来保存所有的父节点
        List<Node> allParentNodes = new ArrayList<>();
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

            if (parentNodeName!=null){
                /*if (parentNodeName.equals("ATTR_INFO")){
                    System.out.println();
                }*/
                Node latestParent = getLatest(allParentNodes, parentNodeName);
                node.setParent(latestParent);
                node.setHashCode(nodeName.hashCode());
                latestParent.getChildNodes().add(node);
            }

        }
        compress(allParentNodes);
        return allParentNodes.get(0);

    }

    /**
     * 自底向上的 归纳
     * 也就是说，list里面的每个node，它有那些 子节点，它是知道的，所以，这个方法就是通过子节点最后汇总所有的节点
     * @param list
     */
    private void compress(List<Node> list){
        Iterator<Node> iterator = list.iterator();
        for (;iterator.hasNext();){
            Node node = iterator.next();
            Set<Node> childNodes = node.getChildNodes();
            Iterator<Node> cIterator = childNodes.iterator();
            for ( ;cIterator.hasNext();){
                Node childNode = cIterator.next();
                childNode.setParent(node);
                for (Node n : list){
                    if (childNode.getName().equals(n.getName())){
                        childNode.setChildNodes(n.getChildNodes());
                        break;
                    }
                }
            }
        }
    }

    /**
     * 因为list是可以重复的，来获取最近的一个node
     * //TODO 这里算法有点问题
     * @param list
     * @return
     */
    private Node getLatest(Collection<Node> list,String name){
        Node node = null;
        for (Node n : list){
            if (n.getName().equals(name)){
                node = n;
            }
            /*else{
                //TODO 这里算法有点问题
                Set<Node> childNodes = n.getChildNodes();
                node = getLatest(childNodes,name);
            }*/
        }
        if (node==null){
            node = new Node();
            node.setName(name);
            list.add(node);
        }
        return node;
    }



}
