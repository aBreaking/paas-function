package com.abreaking.easyjpa;

import com.abreaking.easyjpa.mapper.JpaRowMapper;

import java.util.Date;

/**
 * @{USER}
 * @{DATE}
 */
public class TestMapperUser extends JpaRowMapper {

    private Integer userId;
    private String userName;
    private Integer age;
    private Float height;
    private Long phoneNo;
    private Date birthday;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
