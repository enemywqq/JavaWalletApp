package com.wallet.storage;

import com.wallet.model.Account;
import com.wallet.model.Operation;

import java.util.List;
import java.util.Optional;

public interface Storage {

    void save(Account account);

    void delete(String accountId);

    void addOperation(Operation operation);

    List<Account> findAllAccounts();

    Account findAccountById(String accountId);

    List<Operation> findAllOperations();

}
