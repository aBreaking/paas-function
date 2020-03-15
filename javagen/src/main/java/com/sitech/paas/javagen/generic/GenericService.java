package com.sitech.paas.javagen.generic;


import java.lang.reflect.Method;

/**
 * 接口泛化处理
 * @author liwei_paas
 * @date 2020/3/15
 */
public class GenericService  {
    /**
     * 要泛化的类/接口
     */
    Object obj;

    /**
     * 方法的泛化调用
     * @param methodName 方法名
     * @param types 参数类型
     * @param values 入参
     * @return
     */
    public String invoke(String methodName,String[] types,String[] values){
        Class[] ct = new Class[types.length];
        try{
            for (int i = 0; i < types.length; i++) {
                ct[i] = Class.forName(types[i]);
            }
            Method method = obj.getClass().getMethod(methodName, ct);

            //values 应该有类型转换，根据types
            //

            return (String) method.invoke(values);
        }catch (Exception e){
            return "{\"error\":"+e.getMessage()+"}";
        }
    }
}
