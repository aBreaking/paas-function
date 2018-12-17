package com.sitech.paas.bull.inparam;

import com.sitech.paas.bull.mq.Bull;
import com.sitech.paas.inparam.db.ServiceParameterResolver;
import com.sitech.paas.inparam.db.ServiceParameters;
import com.sitech.paas.inparam.resovler.Resolver;
import com.sitech.paas.inparam.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 解析每一条inpram
 */
public class InparamBullResolver implements Resolver<String> {

    /**
     * 服务，指定的参数。参数可以多个
     * 服务不指定，参数：匹配所有的服务，匹配符合的参数
     * 服务指定，参数不指定：匹配指定的服务，不考虑参数
     * 格式如下
     *   srvName=args1:value1,args2:value2
     *   s4000Cfm=AUTHEN_CODE:10008,Login_NO:123
     */
    Map<String,String[]> srvArgsMap;

    /**
     * 某个时间范围内的记录
     */
    String timeRange ;

    public InparamBullResolver(Map<String,String[]> srvArgMap,String timeRange){
        this.srvArgsMap = srvArgMap;
        this.timeRange = timeRange;
    }


    /**
     *
     * @param statement  该条语句
     * @return 服务名srvName=该条报文jsonPin
     */
    public String resolve(String statement) {
        for(String srvName : srvArgsMap.keySet()){
            //如果只有一条记录，没有指定服务名，那么就匹配所有的服务.
            if(!StringUtils.isBlank(srvName)&&!statement.startsWith(srvName)){
                //只要这个服务的入参
                continue;
            }

            //如果是这个服务
            //再看参数中是否符合
            String keyValues[] = srvArgsMap.get(srvName);
            //将所有的的key全部解析出来
            String[] args = new String[keyValues.length];
            for (int i=0;i<keyValues.length;i++){
                String[] split = keyValues[i].split(":");
                args[i] = split[0];
            }
            //使用ServiceParameterResolver解析器去解析
            ServiceParameterResolver serviceParameterResolver = new ServiceParameterResolver(srvName,args);
            ServiceParameters serviceParameters = serviceParameterResolver.resolve(statement);
            //时间范围是否符合
            String callBeginTime = serviceParameters.getCallBeginTime();
            try {
                //筛选某个时间段记录，timeRange为null那就所有
                if(!StringUtils.isBlank(timeRange)&&!onRange(callBeginTime,timeRange)){
                    continue;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //从serviceParameters结果中确认下，是否参数值符合
            Map<String, String> paramMap = serviceParameters.getParamMap();
            //如果没有指定keyValue参数，那么也符合条件
            String condition = "";
            if(keyValues==null||(condition=match(paramMap,keyValues))!=null){
                //如果是符合条件的
                //拿到该条入参的服务名等信息InparamOut，并加入到队列中去，等待被执行
                String jsonPin = serviceParameterResolver.cut(statement);
                //允许服务名为空，那么匹配所有的服务
                if(StringUtils.isBlank(srvName)){
                    srvName = serviceParameters.getServiceName();
                }
                InparamOut inparamOut = new InparamOut(jsonPin,srvName,serviceParameters.getRouteValue(),serviceParameters.getCallBeginTime());
                inparamOut.setCondition(condition);

                Bull.queue.add(inparamOut);
                //返回该条入参
                return statement;
            }
        }
        return null;
    }

    private String match(Map<String, String> paramMap,String keyValues[]){
        StringBuilder builder = new StringBuilder();
        if(paramMap.size()==0){
            return null;
        }
        for (String arg :keyValues){
            String[] split = arg.split(":");
            String key = split[0];
            String value = split[1];
            if(!paramMap.containsKey(key)){
                return null;
            }
            //value可以取或
            boolean hasVal = false;
            String[] vals = value.split("\\|");
            for(String v : vals){
                if(v.equals(paramMap.get(key))){
                    hasVal = true;
                    builder.append(key);
                    builder.append(":");
                    builder.append(v);
                    builder.append("&");
                    break;
                }
                hasVal = false;
            }
            if(!hasVal){
                return null;
            }
        }
        if(builder==null){
            return null;
        }
        return builder.substring(0,builder.length()-1);
    }

    /**
     * 这两个是否匹配，值必须都匹配，key一样，value也是一样的
     * @param paramMap 普通的map，key=value...
     * @param keyValues  [key:value,key:value]
     * @return
     */
    private boolean match_bak(Map<String, String> paramMap,String keyValues[]){
        if(paramMap.size()==0){
            return false;
        }
        for (String arg :keyValues){
            String[] split = arg.split(":");
            String key = split[0];
            String value = split[1];
            if(!paramMap.containsKey(key)){
                return false;
            }
            //value可以取或
            boolean hasVal = false;
            String[] vals = value.split("\\|");
            for(String v : vals){
                if(v.equals(paramMap.get(key))){
                    hasVal = true;
                    break;
                }
                hasVal = false;
            }
            if(!hasVal){
                return false;
            }
        }
        return true;
    }

    public boolean onRange(String time,String timeRange) throws ParseException {
        String[] split = time.split(" ");
        String[] timeSplit = timeRange.split("~");
        time = split[1].trim();
        String startTime = timeSplit[0].trim();
        String endTime = timeSplit[1].trim();
        Date startDate = new SimpleDateFormat("HH:mm:ss").parse(startTime);
        Date endDate = new SimpleDateFormat("HH:mm:ss").parse(endTime);
        Date date = new SimpleDateFormat("HH:mm:ss").parse(time);
        return  date.after(startDate)&&date.before(endDate);
    }

    public static void main(String args[]){
        Map<String,String[]> map = new HashMap<>();
        map.put("first",new String[]{"a:v1|v2|v3","b:v1|v2"});
        map.put("second",new String[]{"a:v1","b:v2","c:v3"});

        //"3"已经匹配
        String[] firsts = map.get("first");
        String[] st = new String[firsts.length-1];

    }
}
