package com.sitech.paas.javagen.json;

/**
 * 访问者模式，定义json的格式，json的哪些节点需要被使用
 * @author liwei_paas
 * @date 2020/3/4
 */
public interface JsonVisitor {

    void visit(TaskGen task);

    void visit(TasksGen tasksGen);

    void visit(ImportsGen imports);
}
