package com.wallet.model;

import com.wallet.exceptions.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Operation {
    private final String id;
    private final LocalDateTime timestamp;
    private final BigDecimal amount;
    private final String category;
    private final Account account;
    private final String name;


    public Operation(Account account, BigDecimal amount, String operationCategory, String name) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Сумма операции не может быть отрицательной или равна нулю");
        }
        this.amount = amount;
        this.category = operationCategory;
        this.timestamp = LocalDateTime.now();
        this.id = UUID.randomUUID().toString();
        this.account = account;
        this.name = name;

    }

    public abstract void execute();


    public String getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public Account getAccount() {
        return account;
    }

    public String getName(){
        return name;
    }
}
