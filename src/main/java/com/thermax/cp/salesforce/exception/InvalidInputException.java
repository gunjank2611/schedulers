package com.thermax.cp.salesforce.exception;

public class InvalidInputException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public InvalidInputException(String str) {
        super(str);
    }
}
