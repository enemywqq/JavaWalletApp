package com.wallet.exceptions;

public class BusinessLogicException extends RuntimeException {

    public BusinessLogicException() {
        super("Ошибка выполнения бизнес-логики.");
    }

    public BusinessLogicException(String message) {
        super(message);
    }
}
