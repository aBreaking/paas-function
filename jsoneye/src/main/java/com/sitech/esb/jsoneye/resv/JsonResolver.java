package com.sitech.esb.jsoneye.resv;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sitech.esb.jsoneye.Resolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @{USER}
 * @{DATE}
 */
public class JsonResolver implements Resolver {
    
    JSONObject jsonObject;
    public JsonResolver(JSONObject object){
        this.jsonObject = object;
    }
    
    public List<String> parse() {
        Set<String> set = new HashSet<String>();
        allKeyToSet(new JsonBody(jsonObject),set);
        return new ArrayList<String>(set);
    }

    private void allKeyToSet(JsonBody jsonBody, Set<String> set){
        Object value = jsonBody.getValue();
        if (value instanceof JSONObject){
            JSONObject jsonObject = (JSONObject) value;
            for (String s : jsonObject.keySet()){
                Object o = jsonObject.get(s);
                JsonBody _jb = new JsonBody(o);
                _jb.setKey(s);
                _jb.setParentKey(toString(jsonBody));
                allKeyToSet(_jb,set);
            }
        }else if (value instanceof JSONArray){
            JSONArray jsonArray = (JSONArray) value;
            for (Object o : jsonArray){
                JsonBody _jb = new JsonBody(o);
                //父json数组就跟一个[]吧
                _jb.setKey("[]");
                _jb.setParentKey(toString(jsonBody));
                allKeyToSet(_jb,set);
            }
        }else{
            set.add(toString(jsonBody));
        }
    }

    private String toString(JsonBody jsonBody){
        return jsonBody.getParentKey()==null?jsonBody.getKey():jsonBody.getParentKey()+separator+jsonBody.getKey();
    }

    public static class JsonBody{
        //该json的爹
        String parentKey;
        //他自己的key
        String key;
        //他本身的值
        Object value;

        public JsonBody(Object value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getParentKey() {
            return parentKey;
        }

        public void setParentKey(String parentKey) {
            this.parentKey = parentKey;
        }
    }
}
