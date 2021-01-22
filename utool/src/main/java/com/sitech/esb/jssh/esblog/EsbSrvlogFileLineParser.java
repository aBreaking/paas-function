package com.sitech.esb.jssh.esblog;

import com.sitech.esb.jssh.handler.db.FileLineParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * esb日志文件的数据解析器
 * 结合配置将文件的每行数据解析成map对象
 * @author liwei_paas
 * @date 2021/1/12
 */
public class EsbSrvlogFileLineParser implements FileLineParser {

    private static final String separator = "，";

    private static final String tablePrefix = "srv_log_";
    private static final String tableFormat = "yyyyMMddHH";


    @Override
    public Map<String, Object> parse(String filePath,String line,long lineNum) {
        String[] split = line.split(separator);//esb的日志文件分割符是中文逗号！
        if(split.length<=1){
            throw new RuntimeException(filePath+"文件中esb的日志不是以中文逗号（，）为分割符的，可能是文件编码编码有误，请确认文件是否是utf-8编码格式");
        }
        Map<String,Object> map = new LinkedHashMap<>(split.length);
        SrvLogEnum[] enums = SrvLogEnum.values();
        if (split.length<enums.length-1){
            //如果需要解析的值 长度没有超过指定长度(即可能esb srvlog 日志不全，那么也就不考虑)
            return null;
        }
        for (SrvLogEnum logEnum : enums){
            int index = logEnum.index;
            String name = logEnum.name();
            if (index>=0 && index<split.length){
                //对类型进行判断,并设置value
                Object value = getValue(split[index],logEnum);
                map.put(name,value);
            }
        }
        map.put("LOGFILE_NAME",filePath);
        map.put("LINE_NUM",lineNum);
        return map;
    }

    @Override
    public String getTableName(Map<String, Object> parsedMap) {
        //表名使用默认的
        Date callTime = (Date) parsedMap.get(SrvLogEnum.CALL_BEGINTIME.name());
        Date date = callTime==null?new Date():callTime;
        String suffix = new SimpleDateFormat(tableFormat).format(date);
        return tablePrefix+suffix;
    }

    @Override
    public String createTableSql(String table) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE ").append(table).append("(");
        SrvLogEnum[] enums = SrvLogEnum.values();
        sqlBuilder.append("ASSESS_ID INT NOT NULL AUTO_INCREMENT");
        for (SrvLogEnum logEnum : enums){
            sqlBuilder.append(",").append(logEnum.name()).append(" ");
            if (logEnum.type.equals("varchar")){
                sqlBuilder.append(logEnum.type).append("(").append(logEnum.varcharLength).append(")");
            }else{
                sqlBuilder.append(logEnum.type);
            }
        }
        sqlBuilder.append(",LOGFILE_NAME varchar(255)");
        sqlBuilder.append(",LINE_NUM bigint ");
        sqlBuilder.append(",PRIMARY KEY (ASSESS_ID)");
        sqlBuilder.append(") DEFAULT CHARSET=utf8");
        return sqlBuilder.toString();
    }

    private Object getValue(String valueStr,SrvLogEnum logEnum)  {
        if (valueStr==null || valueStr.trim().length()==0){
            return null;
        }
        switch (logEnum.type){
            case "varchar":
                return logEnum.varcharLength!=0 && valueStr.length()>logEnum.varcharLength?
                        valueStr.substring(0,logEnum.varcharLength):valueStr;
            case "int": return Integer.parseInt(valueStr);
            case "timestamp":
                try {
                    return valueStr.matches("\\d+")?new Date(Long.parseLong(valueStr)):
                            new SimpleDateFormat(logEnum.format).parse(valueStr);
                } catch (ParseException e) {
                }
            default: return valueStr.length()>255?valueStr.substring(0,255):valueStr;
        }
    }

}
