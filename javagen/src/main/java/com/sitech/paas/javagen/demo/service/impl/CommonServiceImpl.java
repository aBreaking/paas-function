package com.sitech.paas.javagen.demo.service.impl;

import com.sitech.paas.javagen.demo.dto.Order;
import com.sitech.paas.javagen.demo.dto.User;
import java.util.HashMap;
import java.util.Map;

/**
 * 这里不再为每个接口写实现类了，直接一个类
 * @author liwei_paas
 * @date 2020/3/9
 */
public class CommonServiceImpl  {

    /**
     * 几个map，模仿dao层
     */
    final static Map<Integer,User> USER_MAP = new HashMap<>();
    final static Map<String,Integer> PRODUCT_MAP = new HashMap<>();
    final static Map<String,Order> ORDER_MAP = new HashMap<>();

    static {
        USER_MAP.put(101,new User(101,"zhangsan"));
        USER_MAP.put(102,new User(102,"lisi"));
        USER_MAP.put(103,new User(103,"wangwu"));

        PRODUCT_MAP.put("A1",10);
        PRODUCT_MAP.put("B1",0);
        PRODUCT_MAP.put("C1",1);

        ORDER_MAP.put("A1102",new Order("A1102","success",102,"A1"));
    }

    public Order createOrder(User user, String productId) {
        if (user==null){
            System.out.println("没有此用户的信息");
            System.out.println("订单受理失败");
            return null;
        }
        if (productId==null){
            System.out.println("该产品已没货了");
            System.out.println("订单受理失败");
            return null;
        }
        String orderId = productId + user.getUserid();
        if (ORDER_MAP.containsKey(orderId)){
            System.out.println("该用户已经办理过该产品了");
            System.out.println("订单受理失败");
            return null;
        }
        Order order = new Order();
        order.setUserid(user.getUserid());
        order.setProduct(productId);
        order.setStatus("success");
        order.setOrderId(orderId);
        ORDER_MAP.put(orderId,order);
        System.out.println("订单办理成功");
        return order;
    }

    public Boolean hasMore(String productId) {
        if (PRODUCT_MAP.containsKey(productId)){
            if (PRODUCT_MAP.get(productId)!=0){
                return true;
            }
        }
        return false;
    }

    public User getUser(Integer userid) {
        if (USER_MAP.containsKey(userid)){
            return USER_MAP.get(userid);
        }else{
            return null;
        }
    }
}
