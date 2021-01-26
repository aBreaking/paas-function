package com.sitech.esb.jbeat.util;


import java.io.*;

/**
 * 自定义的classLoader
 * @author liwei_paas
 * @date 2021/1/22
 */
public class JBeatServiceLoader {

    private static final String PREFIX = "META-INF/jbeat/";

    public static <S> S load(Class<S> interfaceClass,String key){
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(PREFIX + interfaceClass.getName())));
            String s ;
            while ((s=reader.readLine())!=null){
                String[] split = s.split("=");
                if (split[0].equals(key)){
                    Class<?> aClass = Class.forName(split[1]);
                    return (S) aClass.newInstance();
                }
            }
            throw new NoClassDefFoundError("没有找到"+key+"对应的"+interfaceClass.getName()+"接口实现类");
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
