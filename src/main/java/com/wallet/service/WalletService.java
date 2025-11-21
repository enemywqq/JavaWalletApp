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


    public BigDecimal geTotalBalance();

    public BigDecimal showBalance(String id);

    public Account createAccount(String name, BigDecimal initialBalance);

    public List<Account> showListAccounts();

    public void deleteAccount(String id);

    public void makeIncome(String id, BigDecimal amount, String category);

    public void makeExpense(String id, BigDecimal amount, String category);

    public void makeTransfer(String sourceAccountId, String destinationAccountId, BigDecimal amount, String category);

    public List<Operation> showListOperations();


}
