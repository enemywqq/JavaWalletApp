package com.wallet.service;

import com.wallet.exceptions.data.TransactionFailureException;
import com.wallet.exceptions.logic.AccountDeletionException;
import com.wallet.exceptions.ValidationException;
import com.wallet.exceptions.validation.SelfTransferException;
import com.wallet.model.*;
import com.wallet.storage.Storage;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class WalletServiceImpl implements WalletService {


    private final Storage storage;


    public WalletServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public List<Operation> filterBySearch(List<Operation> operations, String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return operations;
        }

        final String searchLower = searchText.toLowerCase();
        return operations.stream()
                .filter(operation -> operation.getName().toLowerCase().contains(searchLower))
                .collect(Collectors.toList());

    }

    public List<Operation> filterOutTransfers(List<Operation> operations) {
        return operations.stream()
                .filter(operation -> !(operation instanceof Transfer))
                .collect(Collectors.toList());


    }


    @Override
    public void createAccount(String name, BigDecimal initialBalance) {
        Account newAccount = new Account(name, initialBalance);
        storage.save(newAccount);
    }

    @Override
    public List<Account> showListAccounts() {
        return storage.findAllAccounts();
    }


    @Override
    public void deleteAccount(String accountId) {
        Account account = storage.findAccountById(accountId);
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new AccountDeletionException();
        }
        storage.delete(account.getId());
    }


    @Override
    public void makeIncome(String accountId, BigDecimal amount, String category, String nameOperation) {
        Account account = storage.findAccountById(accountId);
        Income newIncome = new Income(account, amount, category, nameOperation);


        newIncome.execute();

        try {
            storage.addOperation(newIncome);
            storage.save(account);
        } catch (Exception e) {
            account.withdraw(amount);
            throw new TransactionFailureException();
        }


    }

    @Override
    public void makeExpense(String accountId, BigDecimal amount, String category, String nameOperation) {
        Account account = storage.findAccountById(accountId);
        Expense newExpense = new Expense(account, amount, category, nameOperation);
        newExpense.execute();

        try {
            storage.addOperation(newExpense);
            storage.save(account);

        } catch (Exception e) {
            account.deposit(amount);
            throw new TransactionFailureException();
        }


    }


    @Override
    public void makeTransfer(String sourceAccountId, String destinationAccountId, BigDecimal amount, String category, String nameOperation) {

        if (sourceAccountId.equals(destinationAccountId)) {
            throw new SelfTransferException();
        }
        Account sourceAccount = storage.findAccountById(sourceAccountId);
        Account destinationAccount = storage.findAccountById(destinationAccountId);


        Transfer newTransfer = new Transfer(sourceAccount, destinationAccount, amount, category, nameOperation);
        newTransfer.execute();

        try {
            storage.addOperation(newTransfer);
            storage.save(sourceAccount);
            storage.save(destinationAccount);

        } catch (Exception e) {
            sourceAccount.deposit(amount);
            destinationAccount.withdraw(amount);
            throw new TransactionFailureException();
        }


    }

    @Override
    public List<Operation> showListOperations() {
        return storage.findAllOperations();

    }


    @Override
    public BigDecimal getTotalBalance() {
        return showListAccounts().stream()
                .map(account -> account.getBalance())
                .reduce(BigDecimal.ZERO, (total, balance) -> total.add(balance));
    }


    @Override
    public BigDecimal getTotalIncomeBalance() {
        return showListOperations().stream()
                .filter(operation -> operation instanceof Income)
                .map(operation -> operation.getAmount())
                .reduce(BigDecimal.ZERO, (total, amount) -> total.add(amount));
    }


    @Override
    public BigDecimal getTotalExpenseBalance() {
        return showListOperations().stream()
                .filter(operation -> operation instanceof Expense)
                .map(operation -> operation.getAmount())
                .reduce(BigDecimal.ZERO, (total, amount) -> total.add(amount));

    }

    @Override
    public void clearAllData() {
        storage.deleteAllData();
    }


}
