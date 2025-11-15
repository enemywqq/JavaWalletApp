package com.wallet.storage;

import com.wallet.model.Account;
import com.wallet.model.Operation;

import java.util.*;

public class InMemoryStorageImpl implements Storage{

    private final Map<String, Account> accounts = new HashMap<>();
    private final List<Operation> operations = new ArrayList<>();



    @Override
    public void save(Account account){
        accounts.put(account.getId(), account);
    }

    @Override
    public void delete(String accountId){
        accounts.remove(accountId);
    }

    @Override
    public void addOperation(Operation operation){
        operations.add(operation);
    }

    @Override
    public List<Account> findAllAccounts(){
        return new ArrayList<>(accounts.values());
    }

    @Override
    public List<Operation> findAllOperations(){
        return new ArrayList<>(operations);
    }

    @Override
    public Optional<Account> findAccountById(String accountId){
        return Optional.ofNullable(accounts.get(accountId));
    }

}
