package com.sitech.paas.inparam.inparam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.inparam.util.FilterUtil;
import com.sitech.paas.inparam.util.StringUtils;
import com.sitech.paas.inparam.resovler.Resolver;
import java.util.HashMap;
import java.util.Map;

/**
 * 将入参文件中每一条inparam记录解析成inparam对象
 */
public  class InparamResolver implements Resolver<Inparam> {

    private static final String NOT_FIND = "no parameter find!";
    private String srvName;
    private String[] args;
    public InparamResolver(String srvName,String[] args){
        this.srvName = srvName;
        this.args = args;
    }

    /**
     * 将入参语句解析成Inparam对象
     * @param inparamStatement
     * @return
     */
    public Inparam resolve(String inparamStatement){

        if(!FilterUtil.filter(inparamStatement,srvName)){
            return null;
        }

        Inparam inparam = new Inparam();
        String prefix = inparamStatement.substring(0, inparamStatement.indexOf("~!~IN="));
        resolvePrefix(inparam,prefix,srvName);

        String jsonStr = cut(inparamStatement);
        JSONObject jsonObject = null;
        try{
            jsonObject = JSON.parseObject(jsonStr);
        }catch (JSONException e){
            return inparam;
        }

        if(jsonObject==null){
            return inparam;
        }
        JSONObject root = jsonObject.getJSONObject("ROOT");
        if(root==null){
            return inparam;
        }
        JSONObject header = root.getJSONObject("HEADER");
        resolvedHeader(inparam,header);

        JSONObject body = root.getJSONObject("BODY");
        if(body==null){
            return inparam;
        }
        resolvedBody(inparam,body,args);

        return inparam;
    }

    public String cut(String inparam){
        int inPosition = inparam.indexOf("~!~IN=");
        int outPosition = inparam.indexOf("~!~OUT=");
        String json = "";
        if(outPosition>0){
            json = inparam.substring(inPosition+"~!~IN=".length(), outPosition);
        }else{
            json = inparam.substring(inPosition+"~!~IN=".length(), inparam.length());
        }
        return json;
    }

    private void resolvePrefix(Inparam Inparam, String prefix, String serviceName){
        String[] split = prefix.split("~!~");
        String name= StringUtils.isBlank(serviceName)?split[0]:serviceName;
        Inparam.setServiceName(name);
        Inparam.setFunName(split[1]);
        Inparam.setClientIp(split[2]);
        Inparam.setCallBeginTime(split[3]);
        Inparam.setRetCode(split[4]);
    }

    private void resolvedHeader(Inparam Inparam, JSONObject header){
        JSONObject routing = header.getJSONObject("ROUTING");
        if(routing==null){
            return;
        }
        Inparam.setRouteKey(routing.getString("ROUTE_KEY"));
        Inparam.setRouteValue(routing.getString("ROUTE_VALUE"));
        Inparam.setChannelId(header.getString("CHANNEL_ID"));
        Inparam.setUsername(header.getString("USERNAME"));
        Inparam.setPassword(header.getString("PASSWORD"));
        Inparam.setPoolId(header.getString("POOL_ID"));
    }

    private void resolvedBody(Inparam Inparam, JSONObject body, String...findStr){
        Map<String,String> map = new HashMap<String, String>();
        for (String find : findStr){
            if(find.indexOf("->")!=-1){
                JSONObject tempJsonObject = body;
                String[] split = find.split("->");
                for (int i = 0; i < split.length-1; i++) {
                	if(null==tempJsonObject) {
                		break;
                	}
                    tempJsonObject = tempJsonObject.getJSONObject(split[i]);
                }
                if(tempJsonObject==null){
                    map.put(find,NOT_FIND);
                    continue;
                }
                Object o = tempJsonObject.get(split[split.length-1]);
                if(o==null){
                    map.put(find,NOT_FIND);
                }else {
                    map.put(find, o.toString().trim());
                }
                continue;
            }else{
                Object value = body.get(find);
                if(value==null){
                    map.put(find,NOT_FIND);
                }else{
                    map.put(find,value.toString().trim());
                }
                continue;
            }
        }
        Inparam.setParamMap(map);
    }


}
