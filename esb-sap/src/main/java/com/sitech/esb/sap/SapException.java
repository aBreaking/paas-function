package com.sitech.esb.sap;

/**
 * 服务同步过程中的exception
 * @author liwei_paas 
 * @date 2020/4/7
 */
public class SapException extends Exception {
    public SapException(String message) {
        super(message);
    }

    public SapException(String message, Throwable cause) {
        super(message, cause);
    }

    public SapException(Throwable cause) {
        super(cause);
    }

    public SapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
