package com.wallet.service;

import com.wallet.model.Account;
import com.wallet.model.Operation;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {


    //Управление счетами:
    //
    //Создание счета.
    //
    //Просмотр списка счетов.
    //
    //Просмотр баланса.
    //
    //Удаление счета.
    //
    //
    //Финансовые операции:
    //
    //Доход.
    //
    //Расход.
    //
    //Перевод.
    //
    //Просмотр истории операций.


    public List<Operation> filterBySearch(List<Operation> operations, String searchText);

    public List<Operation> filterOutTransfers(List<Operation> operations);

//    public List<Operation> sortByDateDescending(List<Operation> operations);
//
//    public List<Operation> sortByDateAscending(List<Operation> operations);

    public BigDecimal getTotalIncomeBalance();

    public BigDecimal getTotalExpenseBalance();

    public BigDecimal geTotalBalance();

    public BigDecimal showBalance(String id);

    public void createAccount(String name, BigDecimal initialBalance);

    public List<Account> showListAccounts();

    public void deleteAccount(String id);

    public void makeIncome(String id, BigDecimal amount, String category, String nameOperation);

    public void makeExpense(String id, BigDecimal amount, String category, String nameOperation);

    public void makeTransfer(String sourceAccountId, String destinationAccountId, BigDecimal amount, String category, String nameOperation);

    public List<Operation> showListOperations();

    public void clearAllData();


}
