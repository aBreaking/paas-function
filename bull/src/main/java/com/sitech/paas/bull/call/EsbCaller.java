package com.sitech.paas.bull.call;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class EsbCaller {
    private String esbIpPort;
    public EsbCaller(String ipPort){
        this.esbIpPort = ipPort;
    }

    /**
     * 返回esb的调用结果
     * @param srvName
     * @param pin
     * @return
     */
    public EsbResult call(String srvName,String pin){
        String esbUrl = "http://"+esbIpPort+"/esbWS/rest/"+srvName;
        EsbResult esbResult = new EsbResult();
        try {
            System.out.println("开始调用服务"+esbUrl+"参数为："+pin);
            String retJson = HttpUtils.sendPost(esbUrl, pin);
            System.out.println("服务:"+esbUrl+"调用成功....");
            //调用成功
            esbResult.setSrvName(srvName);
            JSONObject jsonObject = null;
            try{
                jsonObject = JSON.parseObject(retJson);
            }catch (JSONException e){
                throw  new RuntimeException(e);
            }
            if(jsonObject==null){
                return esbResult;
            }
            JSONObject root = jsonObject.getJSONObject("ROOT");
            String retmsg = root.getString("RETURN_MSG");
            String esbretcode = root.getString("ESBRETCODE");
            String retcode = root.getString("RETURN_CODE");
            if(esbretcode==null){
                JSONObject body = root.getJSONObject("BODY");
                if(body!=null){
                    retmsg =body.getString("RETURN_MSG");
                    esbretcode =body.getString("ESBRETCODE");
                    retcode =body.getString("RETURN_CODE");
                }
            }
            esbResult.setRetmsg(retmsg);
            esbResult.setEsbretcode(esbretcode);
            esbResult.setRetCode(retcode);
            return esbResult;
        }catch (Exception e){
            //调用失败x
            e.printStackTrace();
            return esbResult;

        }

    }

}
