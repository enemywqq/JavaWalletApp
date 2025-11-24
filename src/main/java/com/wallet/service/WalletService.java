package com.wallet.service;

import com.wallet.model.Account;
import com.wallet.model.Operation;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {


    List<Operation> filterBySearch(List<Operation> operations, String searchText);

    List<Operation> filterOutTransfers(List<Operation> operations);

//     List<Operation> sortByDateDescending(List<Operation> operations);
//
//     List<Operation> sortByDateAscending(List<Operation> operations);

    BigDecimal getTotalIncomeBalance();

    BigDecimal getTotalExpenseBalance();

    BigDecimal getTotalBalance();

    void createAccount(String name, BigDecimal initialBalance);

    List<Account> showListAccounts();

    void deleteAccount(String id);

    void makeIncome(String id, BigDecimal amount, String category, String nameOperation);

    void makeExpense(String id, BigDecimal amount, String category, String nameOperation);

    void makeTransfer(String sourceAccountId, String destinationAccountId, BigDecimal amount, String category, String nameOperation);

    List<Operation> showListOperations();

    void clearAllData();


}
