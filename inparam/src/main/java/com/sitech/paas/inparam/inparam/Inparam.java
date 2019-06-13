package com.sitech.paas.inparam.inparam;

import java.util.HashMap;
import java.util.Map;

/**
 * esb入参文件inParam.log*中HEADER、BODY里面基本的参数
 * HEADER参数：非Map的属性
 * BODY参数: Map里面的
 */
public class Inparam {
    private Long id;
    private String serviceName;
    private String funName;
    private String clientIp;
    private String callBeginTime;
    private String retCode;
    private String routeKey;
    private String routeValue;
    private String channelId;
    private String username;
    private String password;
    private String poolId;
    //BODY里面的参数
    private Map<String,String> paramMap = new HashMap<String, String>();
    public void put(String key,String value){
        this.paramMap.put(key,value);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getCallBeginTime() {
        return callBeginTime;
    }

    public void setCallBeginTime(String callBeginTime) {
        this.callBeginTime = callBeginTime;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public String getRouteValue() {
        return routeValue;
    }

    public void setRouteValue(String routeValue) {
        this.routeValue = routeValue;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }
}
