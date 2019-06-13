package com.sitech.paas.bull.inparam;

public class Condition {
    private String srvName;
    private Param[] params;

    @Override
    public int hashCode() {
        int hashCode = 18;
        hashCode = hashCode*31 + srvName.hashCode();
        if(params != null){
            for(Param p : params){
                hashCode = hashCode*31 + p.hashCode();
            }
        }

        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Condition){
            return this.hashCode() == obj.hashCode();
        }
        return false;
    }

    public String getSrvName() {
        return srvName;
    }

    public void setSrvName(String srvName) {
        this.srvName = srvName;
    }

    public Param[] getParams() {
        return params;
    }

    public void setParams(Param[] params) {
        this.params = params;
    }
}
class Param{
    private String name;
    private String value;

    public Param(){}
    public Param(String name,String value){
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Param){
            return this.hashCode()==obj.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 18;
        hashCode = hashCode*31 + name.hashCode();
        hashCode = hashCode*31 + value.hashCode();
        return hashCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}



