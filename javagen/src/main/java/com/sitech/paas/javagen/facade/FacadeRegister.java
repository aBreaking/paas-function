package com.sitech.paas.javagen.facade;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 暴露接口的中心，存储着所有能被编排的类及方法
 * @author liwei_paas
 * @date 2020/3/17
 */
public class FacadeRegister {

    static final Map<String,String> REGISTER = new HashMap<>();

    /**
     * 默认扫描出所有能被编排的类，
     * 这里用到了Java的spi
     */
    public static void scan(){
        ServiceLoader<Exposable> loader = ServiceLoader.load(Exposable.class);
        for (Exposable e : loader){
            Class<? extends Exposable> eClass = e.getClass();
            String name = eClass.getName(); //将name作为key

            Method[] declaredMethods = eClass.getDeclaredMethods();
            for (Method method : declaredMethods){
                if (method.getModifiers() == Method.DECLARED && !method.isAnnotationPresent(Except.class)){
                    /**
                     * 类、方法的泛化处理：1、统一指定的类实例化+方法调用方式；2、返回类型统一成字符串 3、方法需全部静态化
                     */
                }
            }
            //这里应该有个方法的泛化处理

        }
    }
}
