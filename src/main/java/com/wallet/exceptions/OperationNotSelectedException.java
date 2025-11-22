package com.wallet.exceptions;

public class OperationNotSelectedException extends RuntimeException {
    public OperationNotSelectedException(String message) {
        super(message);
    }
}
