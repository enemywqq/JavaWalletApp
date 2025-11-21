package com.wallet.exceptions;

public class InsufficientBalanceException extends ValidationException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

