package com.thermax.cp.salesforce.exception;

public class AssetDetailsNotFoundException extends RuntimeException {

    public AssetDetailsNotFoundException() {
        super();
    }

    public AssetDetailsNotFoundException(String message) {
        super(message);
    }
}
