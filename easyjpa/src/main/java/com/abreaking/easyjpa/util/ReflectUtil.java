package com.abreaking.easyjpa.util;

import com.abreaking.easyjpa.constraint.DateTable;
import com.abreaking.easyjpa.constraint.Table;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 反射工具
 * @author liwei_paas
 * @date 2019/11/1
 */
public final class ReflectUtil {

    public static String getTableName(Class clazz){
        if (clazz.isAnnotationPresent(Table.class)){
            Table table = (Table) clazz.getAnnotation(Table.class);
            String tableName = table.value();
            if (StringUtils.isNotEmpty(tableName)){
                return tableName;
            }
        }
        if(clazz.isAnnotationPresent(DateTable.class)){
            DateTable dateTable = (DateTable) clazz.getAnnotation(DateTable.class);
            String prefix = dateTable.prefix();
            String format = dateTable.format();

            //表前缀，没有指定的话默认类名转换
            String tablePrefix = StringUtils.isEmpty(prefix)?
                    NameUtil.underscoreName(clazz.getSimpleName()):prefix;
            if (StringUtils.isNotEmpty(format)){
                try{
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                    String nowDate = simpleDateFormat.format(new Date());
                    if (!tablePrefix.endsWith("_")){
                        tablePrefix += "_";
                    }
                    return tablePrefix+nowDate;
                }catch (Exception e){
                    //如果这里指定的format格式有误，还是返回tablePrefix
                }
            }
            return tablePrefix;
        }
        return NameUtil.underscoreName(clazz.getSimpleName());

    }


}
