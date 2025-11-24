package com.wallet.exceptions.logic;

import com.wallet.exceptions.BusinessLogicException;

public class OperationDeletionException extends BusinessLogicException {

    public OperationDeletionException() {
        super("Невозможно удалить операцию.");
    }

    public OperationDeletionException(String message) {
        super(message);
    }
}