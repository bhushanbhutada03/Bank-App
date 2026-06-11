package service.impl;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.TransactionType;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientBalanceException;
import exceptions.SameAccountTransferException;
import repository.AccountRepo;
import repository.CustomerRepo;
import repository.TransactionRepo;
import service.BankService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {

    private final AccountRepo accountRepo = new AccountRepo();
    private final TransactionRepo transactionRepo = new TransactionRepo();
    private final CustomerRepo customerRepo = new CustomerRepo();

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    public String openAccount(String name, String email, String accountType) {

        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Customer name cannot be empty");

        if (name.trim().length() < 2)
            throw new IllegalArgumentException("Customer name must contain at least 2 characters");

        if (email == null || email.trim().isEmpty())
            throw new IllegalArgumentException("Email cannot be empty");

        if (!EMAIL_PATTERN.matcher(email.trim()).matches())
            throw new IllegalArgumentException("Invalid email format");

        if (accountType == null || accountType.trim().isEmpty())
            throw new IllegalArgumentException("Account type cannot be empty");

        accountType = accountType.trim().toUpperCase();

        if (!accountType.equals("SAVINGS") && !accountType.equals("CURRENT"))
            throw new IllegalArgumentException("Account type must be SAVINGS or CURRENT");

        String customerId = UUID.randomUUID().toString();
        String accountNumber = getAccountNumber();

        Customer customer = new Customer(
                customerId,
                name.trim(),
                email.trim()
        );

        customerRepo.save(customer);

        Account account = new Account(
                accountNumber,
                customerId,
                0.0,
                accountType
        );

        accountRepo.save(account);

        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {

        return accountRepo.findAll().stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {

        if (accountNumber == null || accountNumber.trim().isEmpty())
            throw new IllegalArgumentException("Account number cannot be empty");

        if (amount == null)
            throw new IllegalArgumentException("Amount cannot be null");

        if (amount <= 0)
            throw new IllegalArgumentException("Deposit amount must be greater than 0");

        Account account = accountRepo.findByNumber(accountNumber.trim())
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account Not Found: " + accountNumber));

        account.setBalance(account.getBalance() + amount);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                account.getAccountNumber(),
                amount,
                (note == null || note.trim().isEmpty()) ? "Deposit" : note.trim(),
                LocalDateTime.now(),
                TransactionType.DEPOSIT
        );

        transactionRepo.add(transaction);
    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {

        if (accountNumber == null || accountNumber.trim().isEmpty())
            throw new IllegalArgumentException("Account number cannot be empty");

        if (amount == null)
            throw new IllegalArgumentException("Amount cannot be null");

        if (amount <= 0)
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0");

        Account account = accountRepo.findByNumber(accountNumber.trim())
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account Not Found: " + accountNumber));

        if (account.getBalance().compareTo(amount) < 0)
            throw new InsufficientBalanceException("Insufficient Balance");

        account.setBalance(account.getBalance() - amount);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                account.getAccountNumber(),
                amount,
                (note == null || note.trim().isEmpty()) ? "Withdraw" : note.trim(),
                LocalDateTime.now(),
                TransactionType.WITHDRAW
        );

        transactionRepo.add(transaction);
    }

    @Override
    public void transfer(String fromAcc, String toAcc, Double amount, String note) {

        if (fromAcc == null || fromAcc.trim().isEmpty())
            throw new IllegalArgumentException("Source account number cannot be empty");

        if (toAcc == null || toAcc.trim().isEmpty())
            throw new IllegalArgumentException("Destination account number cannot be empty");

        if (amount == null)
            throw new IllegalArgumentException("Amount cannot be null");

        if (amount <= 0)
            throw new IllegalArgumentException("Transfer amount must be greater than 0");

        final String fromAccount = fromAcc.trim();
        final String toAccount = toAcc.trim();

        if (fromAccount.equals(toAccount))
            throw new SameAccountTransferException(
                    "Source and destination accounts cannot be same");

        Account from = accountRepo.findByNumber(fromAccount)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account Not Found: " + fromAccount));

        Account to = accountRepo.findByNumber(toAccount)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account Not Found: " + toAccount));


        if (from.getBalance().compareTo(amount) < 0)
            throw new InsufficientBalanceException("Insufficient Balance");

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        Transaction fromTransaction = new Transaction(
                UUID.randomUUID().toString(),
                from.getAccountNumber(),
                amount,
                (note == null || note.trim().isEmpty()) ? "Transfer Out" : note.trim(),
                LocalDateTime.now(),
                TransactionType.TRANSFER_OUT
        );

        transactionRepo.add(fromTransaction);

        Transaction toTransaction = new Transaction(
                UUID.randomUUID().toString(),
                to.getAccountNumber(),
                amount,
                (note == null || note.trim().isEmpty()) ? "Transfer In" : note.trim(),
                LocalDateTime.now(),
                TransactionType.TRANSFER_IN
        );

        transactionRepo.add(toTransaction);
    }

    @Override
    public List<Transaction> getStatement(String account) {

        if (account == null || account.trim().isEmpty())
            throw new IllegalArgumentException("Account number cannot be empty");

        accountRepo.findByNumber(account.trim())
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account Not Found: " + account));

        return transactionRepo.findByAccount(account.trim()).stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> searchAccountsByCustomerName(String name) {

        if (name == null)
            throw new IllegalArgumentException("Customer name cannot be null");

        String query = name.trim().toLowerCase();

        if (query.isEmpty())
            throw new IllegalArgumentException("Customer name cannot be empty");

        List<Account> result = new ArrayList<>();

        for (Customer c : customerRepo.findAll()) {

            if (c == null)
                continue;

            if (c.getName() == null)
                continue;

            if (c.getName().toLowerCase().contains(query)) {
                result.addAll(accountRepo.findByCustomerId(c.getId()));
            }
        }

        result.sort(Comparator.comparing(Account::getAccountNumber));

        return result;
    }

    private String getAccountNumber() {

        int size = accountRepo.findAll().size() + 1;

        return String.format("AC%06d", size);
    }
}