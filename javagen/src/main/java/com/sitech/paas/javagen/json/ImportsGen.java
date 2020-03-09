package com.sitech.paas.javagen.json;

import java.util.Set;
import java.util.TreeSet;

/**
 * 还是应该采用自定向下对json分析，效率更高。 解析到json哪部分就用那个工具去进行解析。考虑这个实现方案。
 * 每个工具应该有个抽象的方法来对各自部分的json 解析，输出字符串
 * 首先需要 一个父类+若干工具类的方式，父类维护一个容器，父类需要有这些工具类的主要抽象方法。
 *  ----这个模式叫做 访问者模式
 *
 * 对输入的字符串中，包括的包进行解析
 * @author liwei_paas
 * @date 2020/3/4
 */
public class ImportsGen implements GenAccepter{

    Set<String> importSet = new TreeSet<>();

    public void add(String imports) {
        String[] split = imports.split(";");
        for (String s : split) importSet.add(s);
    }

    @Override
    public void accept(JsonVisitor visitor) {
        visitor.visit(this);
    }
}
