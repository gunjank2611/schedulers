package com.thermax.cp.salesforce.exception;

public class FileEntityUploadException extends RuntimeException {
    public FileEntityUploadException() {
    }

    public FileEntityUploadException(String message) {
        super(message);
    }
}
