package com.abreaking.easyjpa.common;

import com.abreaking.easyjpa.constraint.Id;
import com.abreaking.easyjpa.constraint.Table;
import com.abreaking.easyjpa.mapper.impl.DefaultJpaRowMapper;

import java.util.Date;

/**
 * 测试实体，万能的user对象
 * @author liwei_paas
 * @date 2020/7/1
 */
@Table("user")
public class TestUser {
    @Id
    private Integer userId;
    private String userName;
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


    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
