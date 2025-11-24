package com.wallet.exceptions.validation;

import com.wallet.exceptions.ValidationException;

public class InsufficientBalanceException extends ValidationException {

    public InsufficientBalanceException() {
        super("Недостаточно средств на счете для выполнения операции.");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}