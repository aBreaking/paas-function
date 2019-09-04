package com.sitech.paas.elk.input.tmp;

import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.elk.input.EsInput;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author liwei_paas 
 * @date 2019/9/4
 */
public class AggsSrvRetEsInput implements EsInput {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss.SSSZ");

    public String pin() {
        return null;
    }

    public JSONObject build(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("size",0);
        return jsonObject;
    }

    public JSONObject timeRangeCondtion(Date begin, Date end){
        JSONObject jsonObject = new JSONObject();
        JSONObject range = new JSONObject();
        JSONObject timestamp = new JSONObject();
        timestamp.put("gte",DATE_FORMAT.format(begin));
        timestamp.put("lt",DATE_FORMAT.format(end));
        range.put("@timestamp",timestamp);
        jsonObject.put("range",range);
        return jsonObject;
    }
}
