package com.sitech.esb.mouse.filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * liupq说：只要当前时间端的数据.
 */
public class PqFilter implements Filter {
    public static final String FILTER_NAME="pq";
    /**
     * 检查规则，只要当前时段（小时内的数据）
     * @param statement
     * @return
     */
    @Override
    public boolean passCheck(String statement) {
        Date nowTime = new Date();
        String[] split = statement.split(",");
        String time = split[0];

        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String day = dayFormat.format(nowTime); //那一天
        int nowHours = nowTime.getHours();  //那个小时
        String[] _split = time.split(" ");
        if(!day.equals(_split[0])){
            return false;
        }

        try {
            Date date = dateFormat.parse(time);
            int hours = date.getHours();
            return hours==(nowHours-1);
        } catch (ParseException e) {
            return false;
        }
    }

    public static void main(String args[]){
        String time = "2017-07-01 19:06:01";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(time);
            System.out.println(date);
        } catch (ParseException e) {
        }
    }
}
