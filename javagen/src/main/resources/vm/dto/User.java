package com.sitech.paas.javagen.demo.dto;

/**
 * 实体对象的处理：
 *  我会将其转为json对象
 * @author liwei_paas 
 * @date 2020/3/9
 */
public class User {
    Integer userid;
    String name;

    public User() {
    }

    public User(Integer userid, String name) {
        this.userid = userid;
        this.name = name;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
