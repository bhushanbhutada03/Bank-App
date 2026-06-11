package repository;

import domain.Transaction;

import java.util.*;
import java.util.HashMap;


public class TransactionRepo {
    private final Map<String, List<Transaction>> txByAccount= new HashMap<>();

    public void add(Transaction transaction) {

        System.out.println("ADDING TX FOR ACCOUNT = "
                + transaction.getAccountNumber());

        List<Transaction> list= txByAccount.computeIfAbsent(transaction.getAccountNumber(),
                k -> new ArrayList<>());
        list.add(transaction);
    }

    public List<Transaction> findByAccount(String account) {
        System.out.println("SEARCHING ACCOUNT = " + account);
        System.out.println(txByAccount.keySet());
        return new ArrayList<>(txByAccount.getOrDefault(account,Collections.emptyList()));
    }
}
