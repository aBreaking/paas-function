package com.sitech.esb.jssh.esblog;

/**
 *
 * @author liwei_paas
 * @date 2021/1/12
 */
public enum SrvLogEnum {
    SRV_NAME(0)
    ,FUN_NAME(1)
    ,END_USRID(2)  
    ,COMM_PROTOCOL(3)
    ,SERVER_IP(4)
    ,SERVER_PORT(5)
    ,CLIENT_IP(6)
    ,ENDUSR_IP(7)
    ,ESBRETCODE(8)
    ,RETCODE(9)
    ,RETMSG(10)
    ,CALL_BEGINTIME(11)
    ,EXECUTE_TIME(12)
    ,TXDO_CALL_BEGINTIME (13)
    ,TXDO_EXECUTE_TIME (14)
    ,CHAN_ID(15)
    ,FLOW_CODE (16)
    ,ROUTE_KEY (17)
    ,ROUTE_VALUE (18)
    ,POOL_ID (19)
    ,DB_ID (20)
    ,ENDPOINT (21)
    ,REGION_CODE(22)
    ,PCALLID (23)
    ,CALLID (24)
    ,REQLENGTH (25)
    ,RESPLENGH (26)

    ,logfileName(-1);
    ;

    int index;

    SrvLogEnum(int index) {
        this.index = index;
    }
}
