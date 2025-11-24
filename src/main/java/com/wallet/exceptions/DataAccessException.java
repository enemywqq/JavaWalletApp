package com.wallet.exceptions;

public class DataAccessException extends RuntimeException {

    public DataAccessException() {
        super("Ошибка доступа к данным.");
    }

    public DataAccessException(String message) {
        super(message);
    }
}