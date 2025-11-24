package com.wallet.exceptions;

public class ValidationException extends RuntimeException {

    public ValidationException() {
        super("Произошла ошибка валидации данных.");
    }

    public ValidationException(String message) {
        super(message);
    }
}