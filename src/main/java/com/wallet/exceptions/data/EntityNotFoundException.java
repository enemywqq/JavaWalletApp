package com.wallet.exceptions.data;

import com.wallet.exceptions.DataAccessException;

public class EntityNotFoundException extends DataAccessException {

    public EntityNotFoundException() {
        super("Сущность не найдена в хранилище.");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}