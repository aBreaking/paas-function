package com.abreaking.easyjpa.mapper.impl;

import com.abreaking.easyjpa.constraint.Column;
import com.abreaking.easyjpa.constraint.Id;
import com.abreaking.easyjpa.mapper.ClassMapper;
import com.abreaking.easyjpa.matrix.ColumnMatrix;
import com.abreaking.easyjpa.matrix.Matrix;
import com.abreaking.easyjpa.matrix.impl.AxisColumnMatrix;
import com.abreaking.easyjpa.util.NameUtil;
import com.abreaking.easyjpa.util.ReflectUtil;
import com.abreaking.easyjpa.util.SqlUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Types;
import java.util.Map;

/**
 *
 * @author liwei_paas
 * @date 2020/7/6
 */
public class BaseClassMapper implements ClassMapper {

    Class obj;

    public BaseClassMapper(Class obj) {
        this.obj = obj;
    }

    public BaseClassMapper() {
        obj = this.getClass();
    }

    @Override
    public String tableName() {
        return ReflectUtil.getTableName(obj);
    }

    @Override
    public Matrix columnType() {
        return matrix(obj,null);
    }

    protected Matrix matrix(Class clazz,Object obj){
        Map<String, Method> methodMap = ReflectUtil.poGetterMethodsMap(clazz);
        ColumnMatrix colMatrix = new AxisColumnMatrix(methodMap.size());
        Field[] fields = this.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            String fieldName = field.getName();
            if (!methodMap.containsKey(fieldName)){
                //如果该字段不是 getter setter字段，就不用再继续了
                continue;
            }
            String columnName = null;
            int type = Types.NULL;
            Object value = null;

            //判断是否主键注解
            if (field.isAnnotationPresent(Id.class)) {
                Id id = field.getAnnotation(Id.class);
                columnName = id.value();
                type = id.type();
            }
            if (field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                columnName = column.value();
                type = column.type();
            }
            //计算
            if (columnName == null){
                columnName = NameUtil.underscoreName(fieldName);
            }
            if (type == Types.NULL){
                type = SqlUtil.getSqlType(field.getType());
            }
            if (obj!=null){
                Method getterMethod = methodMap.get(columnName);
                try {
                    value = getterMethod.invoke(obj);
                } catch (Exception e) {
                    value = null;
                }
            }
            colMatrix.put(columnName,type,value);
        }
        return colMatrix;
    }
}
