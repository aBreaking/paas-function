package com.sitech.esb.jbeat.runner;

/**
 *
 * @author liwei_paas
 * @date 2021/1/25
 */
public class JBeatConfigurationException extends RuntimeException{
    public JBeatConfigurationException() {
    }

    public JBeatConfigurationException(String message) {
        super(message);
    }

    public JBeatConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JBeatConfigurationException(Throwable cause) {
        super(cause);
    }

    public JBeatConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
