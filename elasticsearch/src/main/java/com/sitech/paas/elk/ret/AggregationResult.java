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
public class AggregationResult {

    String[] aggs = {"srvName","retCode","retMsg"};

    public AggregationResult(String[] aggs){
        this.aggs =aggs;
    }

    public List<Node> parse(JSONObject jsonObject){
        JSONObject aggregations = jsonObject.getJSONObject("aggregations");

        return parse(aggregations,0);
    }

    /**
     * 通过aggs的深度来解析 jsonObject
     * @param jsonObject
     * @param depth
     * @return
     */
    protected List<Node> parse(JSONObject jsonObject,int depth){
        List<Node> nodes = new ArrayList<Node>();
        if (depth>=aggs.length){
            return nodes;
        }
        JSONArray buckets = jsonObject.getJSONObject(aggs[depth]).getJSONArray("buckets");
        int nextDepth = depth+1;
        for (int i = 0; i < buckets.size(); i++) {
            JSONObject childJson = buckets.getJSONObject(i);
            Node node = new Node();
            node.setName(childJson.getString("key"));
            node.setDocCount(childJson.getInteger("doc_count"));
            node.setChildNode(parse(childJson,nextDepth));
            nodes.add(node);
        }
        return nodes;
    }

    public String[] getAggs() {
        return aggs;
    }

    public void setAggs(String[] aggs) {
        this.aggs = aggs;
    }

    public static class Node{
        String name;
        int docCount;
        List<Node> childNode = new ArrayList<Node>();

        //TODO
        @Override
        public String toString() {
            return super.toString();
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
    }

}

