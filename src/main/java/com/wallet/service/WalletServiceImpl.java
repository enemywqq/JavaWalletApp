package com.wallet.service;

import com.wallet.exceptions.AccountDeletionException;
import com.wallet.exceptions.ValidationException;
import com.wallet.model.*;
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

    @Override
    public BigDecimal showBalance(String id) {
        return storage.findAccountById(id).getBalance();
    }


    @Override
    public void deleteAccount(String accountId) {
        Account account = storage.findAccountById(accountId);
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new AccountDeletionException("Невозможно удалить счет, так как его баланс не равен нулю.");
        }
        storage.delete(account.getId());
    }

    @Override
    public void makeIncome(String accountId, BigDecimal amount, String category) {
        Account account = storage.findAccountById(accountId);
        Income newIncome = new Income(account, amount, category);
        newIncome.execute();
        storage.addOperation(newIncome);
        storage.save(account);
    }

    @Override
    public void makeExpense(String accountId, BigDecimal amount, String category){
        Account account = storage.findAccountById(accountId);
        Expense newExpense = new Expense(account, amount, category);
        newExpense.execute();
        storage.addOperation(newExpense);
        storage.save(account);

    }


    @Override
    public void makeTransfer(String sourceAccountId, String destinationAccountId, BigDecimal amount, String category){

        if (sourceAccountId.equals(destinationAccountId)){
            throw new ValidationException("Нельзя переводить средства на тот же счет.");
        }
        Account sourceAccount = storage.findAccountById(sourceAccountId);
        Account destinationAccount = storage.findAccountById(destinationAccountId);


        Transfer newTransfer = new Transfer(sourceAccount, destinationAccount, amount, category);
        newTransfer.execute();
        storage.addOperation(newTransfer);
        storage.save(sourceAccount);
        storage.save(destinationAccount);
    }

    @Override
    public List<Operation> showListOperations(){
        return storage.findAllOperations();

    }

    @Override
    public BigDecimal geTotalBalance(){
        List<Account> allAccounts = showListAccounts();
        BigDecimal result = BigDecimal.ZERO;

        for (Account account : allAccounts){
            result = result.add(account.getBalance());
        }
        return result;
    }



}
