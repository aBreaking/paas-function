package com.sitech.paas.elk.ret;

import com.alibaba.fastjson.JSONObject;

/**
 * 通用的模板方法
 * @author liwei_paas
 * @date 2019/9/3
 */
public abstract class AbstractResultParser implements ResultParser{

    protected String[] title;

    public AbstractResultParser(String[] title) {
        this.title = title;
    }

    /**
     * 根据index,获取实际上有用的json里面对象
     * @param jsonObject
     * @param index
     * @return
     */
    protected Object real(JSONObject jsonObject,String index){
        if (index.indexOf(".")==-1){
            return jsonObject.get(index);
        }
        String[] split = index.split(".");
        int size = split.length;
        for (int i = 0; i < size - 1; i++) {
            jsonObject = jsonObject.getJSONObject(split[i]);
        }
        return jsonObject.get(split[size-1]);
    }


    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }
}
