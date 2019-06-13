package com.sitech.paas.inparam.util;

public class FilterUtil {

    /**
     * 如果指定了serviceName，那么直接就保存指定服务名的哪些入参
     * 如果没有指定，即serviceName为空，那么就保存所有的。
     * @param inparam
     * @param srvName
     * @return
     */
    public static boolean filter(String inparam,String srvName){

        if(StringUtils.isBlank(srvName)){
            return true;
        }
        if(inparam.startsWith(srvName)){
            return true;
        }
        return false;
    }
}
