package com.abreaking.easyjpa.util;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.Date;

/**
 * sql工具
 * @author liwei_paas
 * @date 2019/11/22
 */
public class SqlUtil {

    /**
     * 根据javad=的字段类型得到对应sql的字段类型
     * @param fieldType
     * @return
     */
    public static int getSqlType(Class<?> fieldType){
        //字段类型处理，这里处理三种：String,Number,Date
        return String.class.isAssignableFrom(fieldType)?Types.VARCHAR:
                Integer.class.isAssignableFrom(fieldType)?Types.INTEGER:
                Long.class.isAssignableFrom(fieldType)?Types.NUMERIC:
                Float.class.isAssignableFrom(fieldType)?Types.FLOAT:
                Double.class.isAssignableFrom(fieldType)?Types.DOUBLE:
                Date.class.isAssignableFrom(fieldType)?Types.TIMESTAMP:
                Types.VARCHAR;
    }
}
