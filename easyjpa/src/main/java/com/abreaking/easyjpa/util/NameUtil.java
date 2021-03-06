package com.abreaking.easyjpa.util;


import org.apache.commons.lang.StringUtils;

/**
 * 一些名字的转换工具
 * @author liwei_paas
 * @date 2019/11/1
 */
public final class NameUtil {

    /**
     * 将驼峰形式name转为带下划线的形式。name全部小写
     * 比如：XxxYyyZzz -> xxx_yyy_zzz
     * @param name
     * @return
     */
    public static String underscoreName(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        //如果没得大写字母，直接返回了
        if (!name.matches(".*[A-Z].*")){
            return name;
        }
        StringBuilder result = new StringBuilder();
        result.append(name.substring(0, 1).toLowerCase());
        for (int i = 1; i < name.length(); i++) {
            String s = name.substring(i, i + 1);
            String slc = s.toLowerCase();
            if (!s.equals(slc)) {
                result.append("_").append(slc);
            }
            else {
                result.append(s);
            }
        }
        return result.toString();
    }

    /**
     * underscoreName方法的反操作，即将xxx_yyy_zzz -> XxxYyyZzz
     * @author liwei_paas
     * @date 2020/7/13
     */
    public static String deunderscoreName(String name){
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        //如果没得下划线，直接返回了
        if (!name.matches(".*\\_.*")){
            return name;
        }
        StringBuilder result = new StringBuilder();

        char[] chars = name.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Character c = chars[i];
            Character r  = c;
            if (c == '_'){
                Character next = chars[++i]; //把下一个字母大写
                r = Character.toUpperCase(next);
            }
            result.append(r);
        }
        return result.toString();
    }
}
