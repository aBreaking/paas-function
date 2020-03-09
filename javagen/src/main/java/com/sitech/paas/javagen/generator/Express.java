package com.sitech.paas.javagen.generator;

/**
 * 抽象的表达式
 * @author liwei_paas
 * @date 2020/3/9
 */
public class Express {

    public static final Integer CODE_TYPE = 1;
    public static final Integer METHOD_TYPE = 0;

    /**
     * 表达式类型
     */
    private Integer type;

    /**
     * 变量类型
     */
    private String vartype;
    /**
     * 变量名
     */
    private String varname;
    /**
     * 代码块
     */
    private String code; //代码块

    private Integer timeout; //超时 时间

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getVartype() {
        return vartype;
    }

    public void setVartype(String vartype) {
        this.vartype = vartype;
    }

    public String getVarname() {
        return varname;
    }

    public void setVarname(String varname) {
        this.varname = varname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "Express{" +
                "type='" + type + '\'' +
                ", varname='" + varname + '\'' +
                ", code='" + code + '\'' +
                ", timeout=" + timeout +
                '}';
    }
}
