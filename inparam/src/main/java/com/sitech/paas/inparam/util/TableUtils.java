package com.sitech.paas.inparam.util;

import com.sitech.paas.inparam.jdbc.Column;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TableUtils {

    /**
     * 将带有Column的注解的字段转成表的字段
     * @param columnClazz
     * @return
     */
    public static Map<String,Integer> cast(Class columnClazz){
        Map<String,Integer> map = new HashMap<String, Integer>();
        Field[] declaredFields = columnClazz.getDeclaredFields();
        for (Field field : declaredFields){
            field.setAccessible(true);
            if(field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                map.put(column.colName(),column.size());
            }
        }
        return map;
    }
}
