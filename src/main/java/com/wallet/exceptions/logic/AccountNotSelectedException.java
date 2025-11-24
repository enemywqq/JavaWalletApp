package com.wallet.exceptions.logic;

import com.wallet.exceptions.BusinessLogicException;

public class AccountNotSelectedException extends BusinessLogicException {

    public AccountNotSelectedException() {
        super("Для операции необходимо выбрать счет.");
    }

    public AccountNotSelectedException(String message) {
        super(message);
    }
}