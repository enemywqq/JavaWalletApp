package com.wallet.model;

import com.wallet.exceptions.InsufficientBalanceException;

import java.math.BigDecimal;

public class Expense extends Operation {

    public Expense(Account account, BigDecimal amount, String category, String nameOperation) {
        super(account, amount, category, nameOperation);
    }

    @Override
    public void execute() {
        this.getAccount().withdraw(this.getAmount());
    }
}
