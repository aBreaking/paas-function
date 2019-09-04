package com.sitech.paas.elk.ret;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liwei_paas
 * @date 2019/9/2
 */
public class AggregationResultParser extends AbstractResultParser {

    public static String INDEX = "aggregations";

    public AggregationResultParser(String[] aggs){
        super(aggs);
    }

    public Results parse(JSONObject jsonObject){
        JSONObject aggregations = (JSONObject) real(jsonObject,INDEX);

        final List<Node> nodes = doParse(aggregations, 0);
        return new Results() {
            public List<String[]> getResult() {
                List<String[]> result = new ArrayList<String[]>();
                for (Node node : nodes){
                    List<String> list = node.toList();
                    for (String s : list){
                        result.add(s.split(","));
                    }
                }
                return result;
            }

            public int width() {
                return title.length+1;
            }
        };
    }


    /**
     * 通过aggs的深度来解析 jsonObject
     * @param jsonObject
     * @param depth
     * @return
     */
    protected List<Node> doParse(JSONObject jsonObject,int depth){
        List<Node> nodes = new ArrayList<Node>();
        if (depth>=title.length){
            return nodes;
        }
        JSONArray buckets = jsonObject.getJSONObject(title[depth]).getJSONArray("buckets");
        int nextDepth = depth+1;
        for (int i = 0; i < buckets.size(); i++) {
            JSONObject childJson = buckets.getJSONObject(i);
            Node node = new Node();
            node.setTitle(title[depth]);
            node.setName(childJson.getString("key"));
            node.setDocCount(childJson.getInteger("doc_count"));
            node.setChildNode(doParse(childJson,nextDepth));
            nodes.add(node);
        }
        return nodes;
    }

    /**
     * 这个设计得还是不太好，需要改造，考虑深度遍历
     * @author liwei_paas
     * @date 2019/9/3
     */
    public static class Node{
        String title;
        private String name;
        private int docCount;
        private List<Node> childNode = new ArrayList<Node>();

        public List<String> toList(){
            StringBuilder builder = new StringBuilder(name);
            List<String> list = new ArrayList<String>();
            for (Node node : childNode){
                node.compress(builder,list);
            }
            return list;
        }

        private void compress(StringBuilder builder,List<String> list){
            StringBuilder _builder = new StringBuilder(builder);
            _builder.append(",").append(name);
            if (childNode.isEmpty()){
                _builder.append(",").append(docCount);
                list.add(_builder.toString());
                return;
            }
            for (Node n : childNode){
                n.compress(_builder,list);
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDocCount() {
            return docCount;
        }

        public void setDocCount(int docCount) {
            this.docCount = docCount;
        }

        public List<Node> getChildNode() {
            return childNode;
        }

        public void setChildNode(List<Node> childNode) {
            this.childNode = childNode;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}

