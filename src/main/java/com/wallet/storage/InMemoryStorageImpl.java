package com.wallet.storage;

import com.wallet.exceptions.EntityNotFoundException;
import com.wallet.model.Account;
import com.wallet.model.Operation;

import java.util.*;

public class InMemoryStorageImpl implements Storage {

    private final Map<String, Account> accounts = new HashMap<>();
    private final Map<String, Operation> operations = new HashMap<>();


    @Override
    public void save(Account account) {
        accounts.put(account.getId(), account);
    }

    @Override
    public void delete(String accountId) {
        accounts.remove(accountId);
    }

    @Override
    public void addOperation(Operation operation) {
        operations.put(operation.getId(), operation);
    }

    @Override
    public List<Account> findAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public List<Operation> findAllOperations() {

        return new ArrayList<>(operations.values());
    }

    @Override
    public Operation findOperationById(String operationId){
        return operations.get(operationId);
    }


    @Override
    public Account findAccountById(String accountId) {
        return accounts.get(accountId);
    }

    @Override
    public void deleteAllData() {
        accounts.clear();
        operations.clear();
    }

}
