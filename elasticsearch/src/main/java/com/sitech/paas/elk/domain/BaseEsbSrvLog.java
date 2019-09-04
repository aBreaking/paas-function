package com.sitech.paas.elk.domain;

/**
 *
 * @author liwei_paas
 * @date 2019/9/4
 */
public class BaseEsbSrvLog {
    private String srvName;
    private String retCode;
    private String retMsg;
    private String callBegintime;
    private String chanId;
    private String clientIp;

    public String getSrvName() {
        return srvName;
    }

    public void setSrvName(String srvName) {
        this.srvName = srvName;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getCallBegintime() {
        return callBegintime;
    }

    public void setCallBegintime(String callBegintime) {
        this.callBegintime = callBegintime;
    }

    public String getChanId() {
        return chanId;
    }

    public void setChanId(String chanId) {
        this.chanId = chanId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
