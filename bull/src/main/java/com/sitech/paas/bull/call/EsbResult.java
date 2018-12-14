package com.sitech.paas.bull.call;

import com.sitech.paas.inparam.jdbc.Column;

/**
 * 调用时间，srvname，esbretcode，retcode，retmsg,phone_no
 */

public class EsbResult {

    @Column(colName = "call_begintime" , size = 32)
    String callBegintime; //调用时间
    @Column(colName = "srv_name",size = 32)
    String srvName; //服务名
    @Column(colName = "phone_no",size = 16)
    String phoneNo;  //ROOT.HEADER.ROUTE_VALUE   serviceParameters.routeValue

    @Column(colName = "esbretcode",size = 32)
    String esbretcode; //
    @Column(colName = "retcode",size = 32)
    String retCode;  //out  ROOT.RETURN_CODE
    @Column(colName = "retmsg",size = 128)
    String retmsg;   //out  ROOT.RETURN_MSG

    public String getCallBegintime() {
        return callBegintime;
    }

    public void setCallBegintime(String callBegintime) {
        this.callBegintime = callBegintime;
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

    public String getEsbretcode() {
        return esbretcode;
    }

    public void setEsbretcode(String esbretcode) {
        this.esbretcode = esbretcode;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }
}
