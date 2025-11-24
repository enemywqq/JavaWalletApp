package com.wallet.exceptions.data;

import com.wallet.exceptions.DataAccessException;

public class TransactionFailureException extends DataAccessException {

    public TransactionFailureException() {
        super("Не удалось сохранить операцию. Средства возвращены.");
    }

    public TransactionFailureException(String message) {
        super(message);
    }
}