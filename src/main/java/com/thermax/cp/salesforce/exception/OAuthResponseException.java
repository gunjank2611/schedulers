package com.thermax.cp.salesforce.exception;

public class OAuthResponseException extends RuntimeException {
    public OAuthResponseException() {
    }

    public OAuthResponseException(String message) {
        super(message);
    }
}
