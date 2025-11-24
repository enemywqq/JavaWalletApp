package com.wallet.exceptions.logic;

import com.wallet.exceptions.BusinessLogicException;

public class AccountDeletionException extends BusinessLogicException {

    public AccountDeletionException() {
        super("Невозможно удалить счет. Убедитесь, что его баланс равен нулю.");
    }

    public AccountDeletionException(String message) {
        super(message);
    }
}