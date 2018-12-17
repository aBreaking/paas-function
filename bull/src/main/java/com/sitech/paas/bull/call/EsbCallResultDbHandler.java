package com.sitech.paas.bull.call;

import com.sitech.paas.inparam.handler.Handler;
import com.sitech.paas.inparam.jdbc.Column;
import com.sitech.paas.inparam.jdbc.JdbcOperate;
import com.sitech.paas.inparam.util.TableUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对esb调用结果保存到数据库中去
 */
public class EsbCallResultDbHandler implements Handler<EsbResult> {


    JdbcOperate jdbcOperate;

    public EsbCallResultDbHandler(JdbcOperate jdbcOperate){
        this.jdbcOperate = jdbcOperate;
    }

    public void preHand() {
        jdbcOperate.openConnection();
        System.out.println("准备将esb的调用结果写入表："+jdbcOperate.getPassword());
        if(jdbcOperate.isTableExist()){
            jdbcOperate.dropTable();
            System.out.println(jdbcOperate.getPassword()+"表存在，被删除了");
        }
        jdbcOperate.createTable(TableUtils.cast(EsbResult.class));
        System.out.println("创建了表："+jdbcOperate.getPassword());
    }

    public void hand(List<EsbResult> t) {
        List<Map<String,String>> colValues = new ArrayList<Map<String, String>>();
        try{
            for (EsbResult esbResult : t){
                colValues.add(toMap(esbResult));
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        //将esb的调用结果保存到库中去
        jdbcOperate.saveAllColValue(colValues);
    }

    public void postHand() {
        jdbcOperate.releaseConnection();
    }


    public static  Map<String, String>  toMap(EsbResult esbResult) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        Class<? extends EsbResult> clazz = esbResult.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields){
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)){
                String fieldName = field.getName();
                String firstWord = fieldName.substring(0, 1);
                String leftWords = fieldName.substring(1,fieldName.length());
                String getMethodName = "get"+firstWord.toUpperCase()+leftWords;
                Method method = clazz.getMethod(getMethodName);
                Column column = field.getAnnotation(Column.class);
                map.put(column.colName(),(String)method.invoke(esbResult));
            }
        }
        return map;
    }

}
