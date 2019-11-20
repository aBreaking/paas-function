package com.abreaking.easyjpa.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 一些日期的工具
 * @author liwei
 */
public class DateUtils {
    /**
     * 获取上一个时刻的日期
     * @param date 当前日期
     * @param calendarField 哪个字段，月？天？小时？还是其他
     * @return
     */
    public static Date getLastDate(Date date , int calendarField){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarField,-1);
        return calendar.getTime();
    }
}
