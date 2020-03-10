package com.sitech.paas.javagen.demo.service;


import com.sitech.paas.javagen.demo.service.impl.CommonServiceImpl;

/**
 * 产品类别服务
 * @author liwei_paas 
 * @date 2020/3/9
 */
public class ProductService {

    /**
     * 产品库存
     * @param productId
     * @return
     */
    public static Boolean hasMore(String productId){
        return new CommonServiceImpl().hasMore(productId);
    }
}
