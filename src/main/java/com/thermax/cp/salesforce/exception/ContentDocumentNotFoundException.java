package com.thermax.cp.salesforce.exception;

public class ContentDocumentNotFoundException extends RuntimeException {

    public ContentDocumentNotFoundException() {
    }

    public ContentDocumentNotFoundException(String message) {
        super(message);
    }
}
