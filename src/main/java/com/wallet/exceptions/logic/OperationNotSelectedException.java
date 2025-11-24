package com.wallet.exceptions.logic;

import com.wallet.exceptions.BusinessLogicException;

public class OperationNotSelectedException extends BusinessLogicException {

    public OperationNotSelectedException() {
        super("Для операции необходимо выбрать операцию.");
    }

    public OperationNotSelectedException(String message) {
        super(message);
    }
}