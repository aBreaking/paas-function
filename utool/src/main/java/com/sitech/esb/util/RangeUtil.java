package com.sitech.esb.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author liwei_paas 
 * @date 2019/11/15
 */
public class RangeUtil {

    /**
     * 范围的分隔符号
     */
    private static final String RANGE_SEPARATOR = "~";

    /**
     * 将 一些范围的主机格式化成 list
     * 比如：10.113.161.11~13 会被格式化成10.113.161.11, 10.113.161.12, 10.113.161.13
     * 可以有多个主机，以逗号分开
     * @param range
     * @return
     */
    public static List<String> parseRange(String range){

        List<String> ret = new ArrayList();
        /**
         * 如果是逗号分隔的，递归再去判断
         */
        if (range.indexOf(",")!=-1){
            String[] split = range.split(",");
            for (String s : split){
                ret.addAll(parseRange(s));
            }
            return ret;
        }

        /**
         * 如果是带~ 的形式，如：10.113.172.11~18，就表示所有10.113.172.11  到10.113.172.18 的地址
         */
        if (range.indexOf("~")!=-1){
            String[] split = range.split(RANGE_SEPARATOR);
            String beginHost = split[0];
            if (beginHost.indexOf(".")!=-1){
                String hostPrefix = beginHost.substring(0,beginHost.lastIndexOf(".")+1);
                Integer startTag = Integer.parseInt(beginHost.substring(beginHost.lastIndexOf(".")+1));
                Integer endTag = Integer.parseInt(split[1]);
                for (int i= startTag;i<=endTag;i++){
                    ret.add(hostPrefix+i);
                }
                return ret;
            }else{
                //就看最后的数字了
                return parseHostName(range);
            }


        }
        ret.add(range);
        return ret;
    }

    private static List<String> parseHostName(String state) {
        String[] split = state.split(RANGE_SEPARATOR);
        String s = split[0];
        String endTagStr = split[1];
        //-> e3base001 e3base002 e3base003 .... e3base020
        char[] chars = s.toCharArray();
        int index=0;
        for (int i = chars.length-1; i >0;) {
            Character c = chars[i];
            Character l = chars[--i];
            if (c.toString().matches("\\d")&&!l.toString().matches("\\d")){
                index = i+1;
                break;
            }
        }
        String prefix = s.substring(0, index);
        String suffix = s.substring(index);
        int tagSize = suffix.length();
        int startTag = Integer.parseInt(suffix);
        int endTag = Integer.parseInt(endTagStr);
        List<String> list = new ArrayList();
        list.add(s);
        for (int i = startTag+1; i <= endTag; i++) {
            StringBuilder builder = new StringBuilder(prefix);
            String _s = String.valueOf(i);
            if (_s.length()<tagSize){
                int d = tagSize-_s.length();
                for (int j = 0; j < d; j++)builder.append("0");
            }
            builder.append(_s);
            list.add(builder.toString());
        }
        return list;
    }
}