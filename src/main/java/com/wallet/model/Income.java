package com.wallet.model;

import java.math.BigDecimal;

public class Income extends Operation{



    public Income(Account account, BigDecimal amount, String IncomeCategory){
        super(account, amount, IncomeCategory);
    }

    @Override
    public void execute() {
        // получается Income это операция ДОХОДА то есть депозита на счет баланса Account, то есть
        // мне нужно получить сумму оперцаии, и если это инком сделать депозит этой суммы на баланс аккуанта

        this.getAccount().deposit(this.getAmount());

    }
}
