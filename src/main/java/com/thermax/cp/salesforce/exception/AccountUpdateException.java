package com.thermax.cp.salesforce.exception;

public class AccountUpdateException extends RuntimeException {

    public AccountUpdateException() {
    }

    public AccountUpdateException(String message) {
        super(message);
    }
}
