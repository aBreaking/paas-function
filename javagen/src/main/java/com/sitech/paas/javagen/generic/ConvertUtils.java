package com.sitech.paas.javagen.generic;

/**
 * 将json字符串 进行类型转换的类
 * 扩展自 com.alibaba.fastjson.util.TypeUtils
 * 与其不同的是，这里只是考虑字符串的类型转换形式
 * 
 * 基本类型的转换：直接通过fastjson的TypeUtils 的cast方法直接进行转换，
 * pojo类型的转换：字符串先尝试转为fastjson对象，然后再通过fastjson的JSONObject.parseToJava方法进行转换
  * 前台默认的参数类型
 * @author liwei_paas
 * @date 2020/3/15
 */
public class ConvertUtils {
  
  /*
  * 将字符串转成基本类型
  *
  */
  public <T> T castType(String input,Class<T> type){
  
  }
}
