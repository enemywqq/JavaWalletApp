package com.wallet.service;

import com.wallet.exceptions.AccountDeletionException;
import com.wallet.exceptions.ValidationException;
import com.wallet.model.*;
import com.wallet.storage.Storage;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WalletServiceImpl implements WalletService {


    private final Storage storage;


    public WalletServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public List<Operation> filterBySearch(List<Operation> operations, String searchText){
        if (searchText == null || searchText.trim().isEmpty()){
            return operations;
        }

        final String searchLower = searchText.toLowerCase();
        return operations.stream()
                .filter(operation -> operation.getName().toLowerCase().contains(searchLower))
                .collect(Collectors.toList());

    }

    public List<Operation> filterOutTransfers(List<Operation> operations){
        return operations.stream()
                .filter(operation -> !(operation instanceof Transfer))
                .collect(Collectors.toList());


    }

//    public List<Operation> sortByDateDescending(List<Operation> operations){
//        return operations.stream()
//                .sorted(Comparator.comparing())
//
//    }
//
//    public List<Operation> sortByDateAscending(List<Operation> operations);

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
    public void makeIncome(String accountId, BigDecimal amount, String category, String nameOperation) {
        Account account = storage.findAccountById(accountId);
        Income newIncome = new Income(account, amount, category, nameOperation);

        boolean executedSuccessfully = false;

        try {
            newIncome.execute();
            executedSuccessfully = true;
            storage.addOperation(newIncome);
            storage.save(account);
        } catch (Exception e){
            if (executedSuccessfully){
                account.withdraw(amount);
            }
        }

    }

    @Override
    public void makeExpense(String accountId, BigDecimal amount, String category, String nameOperation){
        Account account = storage.findAccountById(accountId);
        Expense newExpense = new Expense(account, amount, category, nameOperation);
        newExpense.execute();

        try {
            storage.addOperation(newExpense);
            storage.save(account);
        } catch (Exception e){
            account.deposit(amount);
        }


    }


    @Override
    public void makeTransfer(String sourceAccountId, String destinationAccountId, BigDecimal amount, String category, String nameOperation){

        if (sourceAccountId.equals(destinationAccountId)){
            throw new ValidationException("Нельзя переводить средства на тот же счет.");
        }
        Account sourceAccount = storage.findAccountById(sourceAccountId);
        Account destinationAccount = storage.findAccountById(destinationAccountId);


        Transfer newTransfer = new Transfer(sourceAccount, destinationAccount, amount, category, nameOperation);
        newTransfer.execute();

        try{
            storage.addOperation(newTransfer);
            storage.save(sourceAccount);
            storage.save(destinationAccount);

        } catch (Exception e){
            sourceAccount.deposit(amount);
            destinationAccount.withdraw(amount);
        }

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

    public BigDecimal getTotalIncomeBalance(){
        List<Operation> allOperations = showListOperations();
        BigDecimal totalIncome = BigDecimal.ZERO;

        for (Operation operation : allOperations){
            if (operation instanceof Income){
                totalIncome = totalIncome.add(operation.getAmount());
            }
        }
        return totalIncome;


    }

    public BigDecimal getTotalExpenseBalance(){
        List<Operation> allOperations = showListOperations();
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Operation operation : allOperations){
            if (operation instanceof Expense){
                totalExpense = totalExpense.add(operation.getAmount());
            }
        }
        return totalExpense;


    }

    @Override
    public void clearAllData() {
        storage.deleteAllData();
    }






}
