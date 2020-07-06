package com.abreaking.easyjpa.util;

import com.abreaking.easyjpa.constraint.DateTable;
import com.abreaking.easyjpa.constraint.Table;
import org.apache.commons.lang.StringUtils;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 反射工具
 * @author liwei_paas
 * @date 2019/11/1
 */
public final class ReflectUtil {

    /**
     * 根据类名得到表名
     * @param clazz
     * @return
     */
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

    /**
     * 带有getter及setter的属性的getter方法。class中所有的
     * @param clazz
     * @return
     */
    public static List<Method> poGetterMethods(Class clazz){
        List<Method> list= new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        Set<String> methodNameSet = new HashSet(methods.length);
        for (Method method : methods){
            String methodName = method.getName();
            if (methodName.startsWith("set")){
                String getMethodName = "g"+methodName.substring(1);
                if (methodNameSet.contains(getMethodName)){
                    try{
                        list.add(clazz.getDeclaredMethod(getMethodName));
                        continue;
                    }catch (Exception e){}
                }
            }
            if (methodName.startsWith("get")){
                String setMethodName = "s"+methodName.substring(1);
                if (methodNameSet.contains(setMethodName)){
                    list.add(method);
                }
            }
            methodNameSet.add(methodName);
        }
        return list;
    }

    /**
     * po类所有的带有getter setter字段的getter方法
     * @param clazz
     * @return map: key:字段名，value:getter 方法
     */
    public static Map<String,Method> poGetterMethodsMap(Class clazz){
        Map<String,Method> map = new HashMap<>();
        Method[] methods = clazz.getDeclaredMethods();
        Set<String> methodNameSet = new HashSet(methods.length);
        for (Method method : methods){
            String methodName = method.getName();
            if (methodName.startsWith("set")){
                //get方法名
                String getMethodName = "g"+methodName.substring(1);
                //get方法对应的字段名
                String filedName = filedName(methodName);
                if (methodNameSet.contains(getMethodName)){
                    try{
                        map.put(filedName,clazz.getDeclaredMethod(getMethodName));
                        continue;
                    }catch (Exception e){}
                }
            }
            if (methodName.startsWith("get")){
                String setMethodName = "s"+methodName.substring(1);
                if (methodNameSet.contains(setMethodName)){
                    map.put(filedName(methodName),method);
                }
            }
            methodNameSet.add(methodName);
        }
        return map;
    }

    private static String filedName(String getterOrSetterMethodName){
        String fieldName = getterOrSetterMethodName.substring(3);
        String firstWord = fieldName.substring(0, 1);
        String suffix = fieldName.substring(1);
        return firstWord.toLowerCase()+suffix;
    }

}
