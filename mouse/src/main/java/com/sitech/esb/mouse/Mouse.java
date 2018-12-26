package com.sitech.esb.mouse;

import java.util.ArrayList;
import java.util.List;

public class Mouse {
    String name;
    List<String> statements = new ArrayList<>();
    List<Mouse> sonMouseList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStatements() {
        return statements;
    }

    public void setStatements(List<String> statements) {
        this.statements = statements;
    }

    public List<Mouse> getSonMouseList() {
        return sonMouseList;
    }

    public void setSonMouseList(List<Mouse> sonMouseList) {
        this.sonMouseList = sonMouseList;
    }

    @Override
    public String toString() {
        return "Mouse{" +
                "name='" + name + '\'' +
                ", statements=" + statements +
                ", sonMouseList=" + sonMouseList +
                '}';
    }
}
