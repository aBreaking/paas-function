package com.sitech.esb.jssh.esblog;

import com.sitech.esb.jssh.handler.FileLineParser;

import java.util.HashMap;
import java.util.Map;

/**
 * esb日志文件的数据解析器
 * 结合配置将文件的每行数据解析成map对象
 * @author liwei_paas
 * @date 2021/1/12
 */
public class EsbSrvlogFileLineParser implements FileLineParser {

    private static final int MAX_VALUE_LENGTH = 255;

    @Override
    public Map<String, Object> parse(String filePath,String line) {
        String[] split = line.split("，");//esb的日志文件分割符是中文逗号！
        Map<String,Object> map = new HashMap<>(split.length);
        SrvLogEnum[] enums = SrvLogEnum.values();
        for (SrvLogEnum logEnum : enums){
            int index = logEnum.index;
            String name = logEnum.name();
            String value = null;
            if (index>=0 && index<split.length){
                value = split[index];
                //对value进行一层处理，防止超过设定的最大长度
                if (value.length()>MAX_VALUE_LENGTH){
                    value = value.substring(0,MAX_VALUE_LENGTH);
                }
            }

            map.put(name.toLowerCase(),value);
        }
        /*String logFileName = null; //记录日志文件
        if (filePath!=null && filePath.indexOf(File.separator)!=-1){
            logFileName = filePath.substring(filePath.lastIndexOf(File.separator)+1);
        }
        map.put("logfile_name",logFileName);*/
        return map;
    }

}
