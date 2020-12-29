package com.unn.util;

public class NotificationFailedException extends RuntimeException {
    private static final long serialVersionUID = 1619395151932840205L;

    public NotificationFailedException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
