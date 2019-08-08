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
    Node parent; //父节点
    Set<Node> childNodes = new HashSet<>(); //子节点，子节点会有多个
    int constraint; //约束，是否可为空  0表示可空

    private transient int hashCode = 0;
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Node node = (Node) object;
        return Objects.equals(name, node.name) && hashCode()==node.hashCode();
    }

    @Override
    public int hashCode() {
        return hashCode==0?Objects.hash(name,parent):hashCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
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

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    @Override
    public String toString() {
        return parent==null?name:parent.getName()+"."+name;
    }
}
