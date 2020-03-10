package com.sitech.paas.javagen.demo.service;


import com.sitech.paas.javagen.demo.dto.Order;
import com.sitech.paas.javagen.demo.dto.User;
import com.sitech.paas.javagen.demo.service.impl.CommonServiceImpl;

/**
 * 订单管理服务
 *
 * @author liwei_paas
 * @date 2020/3/9
 */
public class OrderService {

    /**
     * 下订单
     * @return
     */
    public static Order createOrder(User user,String productId){
        return new CommonServiceImpl().createOrder(user,productId);
    }



}
