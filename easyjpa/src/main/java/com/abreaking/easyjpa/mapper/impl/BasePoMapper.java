package com.abreaking.easyjpa.mapper.impl;

import com.abreaking.easyjpa.mapper.ObjectMapper;
import com.abreaking.easyjpa.matrix.Matrix;

/**
 * 一个Po对象的基本映射关系,包括：
 *  类名 -> 表名的映射
 * @author liwei_paas
 * @date 2020/7/6
 */
public abstract class BasePoMapper extends BaseClassMapper implements ObjectMapper {

    private Object po;

    public BasePoMapper() {
        po = this;
    }

    public BasePoMapper(Object po) {
        this.po = po;
    }

    @Override
     public Matrix matrix(){
         return matrix(po.getClass(),po);
     }
}
