package com.sitech.paas.bull.inparam;

/**
 * 解析完之后，将符合条件的入参保存成文件之后。
 * 一些东西：服务名、报文需要保存下来，供下次调用时使用
 */
public class InparamOut {
    String jsonPin;
    String srvName;
    String phoneNo;
    String callBeginTime;
    //在配置文件中配置的条件
    String condition;

    public InparamOut(){};

    public InparamOut(String jsonPin, String srvName, String phoneNo,String callBeginTime) {
        this.jsonPin = jsonPin;
        this.srvName = srvName;
        this.phoneNo = phoneNo;
        this.callBeginTime = callBeginTime;
    }

    public String getJsonPin() {
        return jsonPin;
    }

    public void setJsonPin(String jsonPin) {
        this.jsonPin = jsonPin;
    }

    public String getSrvName() {
        return srvName;
    }

    public void setSrvName(String srvName) {
        this.srvName = srvName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCallBeginTime() {
        return callBeginTime;
    }

    public void setCallBeginTime(String callBeginTime) {
        this.callBeginTime = callBeginTime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
