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


    public Account createAccount(String name, BigDecimal initialBalance);

    public List<Account> showListAccounts();

    public BigDecimal showBalance(Account account);

    public void deleteAccount(Account Account);

    public void makeIncome(Account account, BigDecimal amount, String category);

    public void makeExpense(Account account, BigDecimal amount, String category);

    public void makeTransfer(Account sourceAccount, Account destinationAccount, BigDecimal amount, String category);

    public List<Operation> showListOperations();


}
