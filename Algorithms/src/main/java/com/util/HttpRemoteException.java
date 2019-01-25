package com.util;

/**
 * HTTP客户端异常
 * 
 * @author zhangbo
 */
public class HttpRemoteException extends RuntimeException {
    
    private static final long serialVersionUID = 6344467394880482503L;

    public HttpRemoteException() {
    }

    public HttpRemoteException(String message) {
        super(message);
    }

    public HttpRemoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRemoteException(Throwable cause) {
        super(cause);
    }

    public HttpRemoteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
