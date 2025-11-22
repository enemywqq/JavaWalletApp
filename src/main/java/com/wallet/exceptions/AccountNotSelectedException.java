package com.wallet.exceptions;

public class AccountNotSelectedException extends RuntimeException {
    public AccountNotSelectedException() {
        super("Для операции удаления необходимо выбрать счет.");
    }
    public AccountNotSelectedException(String message) {
        super(message);
    }

}
