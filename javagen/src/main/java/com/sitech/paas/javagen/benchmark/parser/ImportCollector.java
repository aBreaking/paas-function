package com.sitech.paas.javagen.benchmark.parser;


import java.util.TreeSet;

/**
 * 负责收集 有哪些 import包的
 * @author liwei_paas
 * @date 2020/3/17
 */
public class ImportCollector extends TreeSet {

    public void addClass(String imports) {
        String[] split = imports.split(";");
        for (String s : split) {
            //先简单的认为类名就是带. 的
            if (s.indexOf(".")!=-1){
                add(s);
            }
        }
    }
}
