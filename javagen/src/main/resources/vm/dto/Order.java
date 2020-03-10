package com.sitech.paas.javagen.demo.dto;

/**
 * 订单信息表
 * @author liwei_paas
 * @date 2020/3/9
 */
public class Order {
    //订单编号
    private String orderId;
    //订单状态
    private String status;

    //下订单的用户
    private Integer userid ;

    //用户订单中的哪个产品
    private String product;

    public Order() {
    }

    public Order(String orderId, String status, int userid, String product) {
        this.orderId = orderId;
        this.status = status;
        this.userid = userid;
        this.product = product;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
