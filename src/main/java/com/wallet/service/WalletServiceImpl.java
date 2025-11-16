package com.wallet.service;

import com.wallet.exceptions.ValidationException;
import com.wallet.model.Account;
import com.wallet.model.Income;
import com.wallet.model.Operation;
import com.wallet.storage.Storage;

import java.math.BigDecimal;
import java.util.List;

public class WalletServiceImpl implements WalletService {


    private final Storage storage;


    public WalletServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Account createAccount(String name, BigDecimal initialBalance) {
        Account newAccount = new Account(name, initialBalance);
        storage.save(newAccount);
        return newAccount;
    }

    @Override
    public List<Account> showListAccounts() {
        return storage.findAllAccounts();
    }


    public BigDecimal showBalance(String id) {
        return storage.findAccountById(id).getBalance();
    }

    public void deleteAccount(Account account) {
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new ValidationException("нельзя удалить аккаунт пока там блаблабла");
        }
        storage.delete(account.getId());
    }

    public void makeIncome(Account account, BigDecimal amount, String category) {
        Income newIncome = new Income(account, amount, category);
        newIncome.execute();
    }


    public void makeExpense(Account account, BigDecimal amount, String category);

    public void makeTransfer(Account sourceAccount, Account destinationAccount, BigDecimal amount, String category);

    public List<Operation> showListOperations();
}
