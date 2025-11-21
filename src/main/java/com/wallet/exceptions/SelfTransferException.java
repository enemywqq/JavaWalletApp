package com.wallet.exceptions;

public class SelfTransferException extends ValidationException {
    public SelfTransferException(String message) {
        super(message);
    }
}
