package com.wallet.exceptions.validation;

import com.wallet.exceptions.ValidationException;

public class SelfTransferException extends ValidationException {

    public SelfTransferException() {
        super("Нельзя переводить средства на тот же счет.");
    }

    public SelfTransferException(String message) {
        super(message);
    }
}