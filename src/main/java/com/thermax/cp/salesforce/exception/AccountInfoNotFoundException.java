package com.thermax.cp.salesforce.exception;

public class AccountInfoNotFoundException extends RuntimeException {

    public AccountInfoNotFoundException() {
        super();
    }

    public AccountInfoNotFoundException(String message) {
        super(message);
    }
}
