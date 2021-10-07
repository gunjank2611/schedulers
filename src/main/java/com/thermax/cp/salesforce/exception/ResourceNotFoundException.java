package com.thermax.cp.salesforce.exception;

public class ResourceNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -9079454849611061074L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
