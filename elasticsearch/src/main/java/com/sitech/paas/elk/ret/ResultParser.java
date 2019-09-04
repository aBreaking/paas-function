package com.sitech.paas.elk.ret;

import com.alibaba.fastjson.JSONObject;

/**
 * 解析es的结果
 * @author liwei_paas
 * @date 2019/9/3
 */
public interface ResultParser {
    Results parse(JSONObject result);
}
