package com.sitech.esb.jsoneye;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 给定excel 输入/输出参数，每个参数将其视为Node
 * @author liwei_paas
 * @date 2019/8/7
 */
public class Node {
    String name;
    //它爹的名字，考虑是否有必要还要parent Node
    String parentName;
    Set<Node> childNodes = new HashSet<>(); //子节点，子节点会有多个
    int constraint; //约束，是否可为空  0表示可空

    public void addChild(Node node){
        childNodes.add(node);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Node node = (Node) object;
        return Objects.equals(name, node.name) &&
                Objects.equals(parentName, node.parentName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, parentName);
    }

    @Override
    public String toString() {
        return parentName+"."+name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Set<Node> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(Set<Node> childNodes) {
        this.childNodes = childNodes;
    }

    public int getConstraint() {
        return constraint;
    }

    public void setConstraint(int constraint) {
        this.constraint = constraint;
    }
}
