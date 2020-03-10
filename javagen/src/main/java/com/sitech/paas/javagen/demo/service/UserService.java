package com.sitech.paas.javagen.demo.service;

import com.sitech.paas.javagen.demo.dto.User;
import com.sitech.paas.javagen.demo.service.impl.CommonServiceImpl;

/**
 * 客户信息服务
 * @author liwei_paas 
 * @date 2020/3/9
 */
public interface UserService {

    public static User getUser(Integer userid){
        return new CommonServiceImpl().getUser(userid);
    }
}
