package com.wallet.model;

import java.math.BigDecimal;

public class Transfer extends Operation{

    private final Account destinationAccount;

    public Transfer(Account sourceAccount, Account destinationAccount, BigDecimal amount, String category){
        super(sourceAccount, amount, category);
        this.destinationAccount = destinationAccount;
    }

    @Override
    public void execute() {
        // мне нужно вывести деньги с соурс аккаунта и сделать deposit для destinationAccount

        this.getAccount().withdraw(getAmount());
        getDestinationAccount().deposit(getAmount());
    }

    public Account getDestinationAccount(){
        return destinationAccount;
    }
}
