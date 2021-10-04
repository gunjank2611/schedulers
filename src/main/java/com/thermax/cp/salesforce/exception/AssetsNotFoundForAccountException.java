package com.thermax.cp.salesforce.exception;

public class AssetsNotFoundForAccountException extends RuntimeException {

    public AssetsNotFoundForAccountException() {
    }

    public AssetsNotFoundForAccountException(String message) {
        super(message);
    }
}
