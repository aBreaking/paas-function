package com.sitech.paas.inparam.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.inparam.util.FilterUtil;
import com.sitech.paas.inparam.util.StringUtils;
import com.sitech.paas.inparam.resovler.Resolver;

import java.util.HashMap;
import java.util.Map;

public  class ServiceParameterResolver implements Resolver<ServiceParameters> {

    private static final String NOT_FIND = "no parameter find!";
    private String srvName;
    private String[] args;
    public ServiceParameterResolver(String srvName,String[] args){
        this.srvName = srvName;
        this.args = args;
    }

    public ServiceParameters resolve(String inparam){

        if(!FilterUtil.filter(inparam,srvName)){
            return null;
        }

        ServiceParameters serviceParameters = new ServiceParameters();
        String prefix = inparam.substring(0, inparam.indexOf("~!~IN="));
        resolvePrefix(serviceParameters,prefix,srvName);

        String jsonStr = cut(inparam);
        JSONObject jsonObject = null;
        try{
            jsonObject = JSON.parseObject(jsonStr);
        }catch (JSONException e){
            return serviceParameters;
        }

        if(jsonObject==null){
            return serviceParameters;
        }
        JSONObject root = jsonObject.getJSONObject("ROOT");
        if(root==null){
            return serviceParameters;
        }
        JSONObject header = root.getJSONObject("HEADER");
        resolvedHeader(serviceParameters,header);

        JSONObject body = root.getJSONObject("BODY");
        if(body==null){
            return serviceParameters;
        }
        resolvedBody(serviceParameters,body,args);

        return serviceParameters;
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

    private void resolvePrefix(ServiceParameters serviceParameters, String prefix, String serviceName){
        String[] split = prefix.split("~!~");
        String name= StringUtils.isBlank(serviceName)?split[0]:serviceName;
        serviceParameters.setServiceName(name);
        serviceParameters.setFunName(split[1]);
        serviceParameters.setClientIp(split[2]);
        serviceParameters.setCallBeginTime(split[3]);
        serviceParameters.setRetCode(split[4]);
    }

    private void resolvedHeader(ServiceParameters serviceParameters, JSONObject header){
        JSONObject routing = header.getJSONObject("ROUTING");
        if(routing==null){
            return;
        }
        serviceParameters.setRouteKey(routing.getString("ROUTE_KEY"));
        serviceParameters.setRouteValue(routing.getString("ROUTE_VALUE"));
        serviceParameters.setChannelId(header.getString("CHANNEL_ID"));
        serviceParameters.setUsername(header.getString("USERNAME"));
        serviceParameters.setPassword(header.getString("PASSWORD"));
        serviceParameters.setPoolId(header.getString("POOL_ID"));
    }

    private void resolvedBody(ServiceParameters serviceParameters, JSONObject body, String...findStr){
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
        serviceParameters.setParamMap(map);
    }


}
