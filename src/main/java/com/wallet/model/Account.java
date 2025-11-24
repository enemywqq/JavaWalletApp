package com.wallet.model;

import java.util.UUID;

import com.wallet.exceptions.validation.InsufficientBalanceException;
import com.wallet.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private final String id;
    private final String name;
    private final List<Operation> historyOperation;
    private BigDecimal balance;


    public Account(String name, BigDecimal initialBalance) {
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Нельзя создать счет с отрицательным балансом");
        }
        this.name = name;
        this.balance = initialBalance;
        this.id = UUID.randomUUID().toString();
        historyOperation = new ArrayList<>();


    }


    @Override
    public String toString() {
        return this.name;
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Сумма вывода должна быть строго положительной");
        }
        if (amount.compareTo(balance) == 1) {
            throw new InsufficientBalanceException();
        }
        balance = this.balance.subtract(amount);

    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Сумма пополнения должна быть строго положительной");
        }
        balance = this.balance.add(amount);
    }

    public void addOperation(Operation operation) {
        historyOperation.add(operation);
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public List<Operation> getHistory() {
        return historyOperation;
    }


}
