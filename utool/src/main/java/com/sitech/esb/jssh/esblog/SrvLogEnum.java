package com.sitech.esb.jssh.esblog;

/**
 * esb srvlog 的字段解析对照
 * @author liwei_paas
 * @date 2021/1/12
 */
public enum SrvLogEnum {
    SRV_NAME(0)
    ,FUN_NAME(1)
    ,END_USRID(2)
    ,CHAN_ID(3)
    ,COMM_PROTOCOL(4)
    ,SERVER_IP(5)
    ,SERVER_PORT(6,"int")
    ,CLIENT_IP(7)
    ,ENDUSR_IP(8)
    ,ESBRETCODE(9,"int")
    ,RETCODE(10)
    ,CALL_BEGINTIME(11,"timestamp","yyyyMMdd HH:mm:ss")
    ,EXECUTE_TIME(12,"int")
    ,TXDO_CALL_BEGINTIME (13,"timestamp","yyyyMMdd HH:mm:ss")
    ,TXDO_EXECUTE_TIME (14,"int")
    ,RETMSG(15)
    ,ROUTE_KEY (16)
    ,FLOW_CODE (17)
    ,ROUTE_VALUE (18)
    ,POOL_ID (19)
    ,DB_ID (20)
    ,REGION_CODE (21)
    ,ENDPOINT(22)
    ,PCALLID (23)
    ,CALLID (24)
    ,REQLENGTH (25)
    ,RESPLENGH (26)
    ;

    int index;
    int varcharLength ;
    String type;
    String format;

    SrvLogEnum(int index) {
        this(index,255);
    }

    SrvLogEnum(int index, int varcharLength) {
        this.index = index;
        this.type = "varchar";
        this.varcharLength = varcharLength;
    }

    SrvLogEnum(int index, String type) {
        this.index = index;
        this.type = type;
    }

    SrvLogEnum(int index, String type, String format) {
        this.index = index;
        this.type = type;
        this.format = format;
    }
}
