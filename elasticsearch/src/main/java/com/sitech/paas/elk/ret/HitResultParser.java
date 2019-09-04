package com.sitech.paas.elk.ret;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通hits下面结果集的实现
 * @author liwei_paas
 * @date 2019/9/3
 */
public class HitResultParser extends AbstractResultParser {

    public static String INDEX = "hits.hits";

    public HitResultParser(String[] title) {
        super(title);
    }

    public Results parse(JSONObject jsonObject){
        JSONArray jsonArray = (JSONArray) real(jsonObject, INDEX);
        HitResults results = new HitResults(title);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject source = jsonArray.getJSONObject(i).getJSONObject("_source");
            String[] result = doParse(source);
            results.add(result);
        }
        return results;
    }

    protected String[] doParse(JSONObject source){
        String[] results = new String[title.length-1];
        for (int j = 0; j < title.length; j++) {
            results[j] = source.getString(title[j]);
        }
        return results;
    }

    public static class HitResults implements Results{
        String[] title;
        List<String[]> result = new ArrayList<String[]>();
        int size;

        public HitResults(int size){
            this.size = size;
            title = new String[size];
        }
        public HitResults(String[] title){
            this.title = title;
            this.size = title.length;
        }

        public void add(String[] s){
            result.add(s);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(toString(title));
            builder.append("\n");
            for (String[] s : result){
                builder.append(s);
                builder.append("\n");
            }
            return builder.toString();
        }

        private String toString(String[] array){
            StringBuilder builder = new StringBuilder();
            for (String s : array){
                builder.append(s);
                builder.append(",");
            }
            return builder.substring(0,builder.length()-1);
        }

        public String[] getTitle() {
            return title;
        }

        public void setTitle(String[] title) {
            this.title = title;
        }

        public List<String[]> getResult() {
            return result;
        }

        public int width() {
            return size;
        }

        public void setResult(List<String[]> result) {
            this.result = result;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
