package com.litmus7.retail.exception;



public class RetailStoreException extends Exception {
    private static final long serialVersionUID = 1L;
    private final int statusCode;

    public RetailStoreException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public RetailStoreException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

