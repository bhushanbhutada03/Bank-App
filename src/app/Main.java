package app;

import service.BankService;
import service.impl.BankServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BankService bankService=new BankServiceImpl();

        boolean flag=true;

        System.out.println("Welcome to the World Bank !");

        while(flag) {

            System.out.println("""
                    
                    1) OPEN ACCOUNT
                    2) DEPOSIT
                    3) WITHDRAW
                    4) TRANSFER
                    5) ACCOUNT STATEMENT
                    6) LIST ACCOUNTS 
                    7) SEARCH ACCOUNT BY CUSTOMER NAME
                    0) EXIT
                    """);

            System.out.println("Enter Choice :");
            String s=sc.nextLine().trim();

            try {
                switch (s) {
                    case "0" -> flag = false;
                    case "1" -> openAccount(sc, bankService);
                    case "2" -> deposit(sc, bankService);
                    case "3" -> withdraw(sc, bankService);
                    case "4" -> transfer(sc, bankService);
                    case "5" -> accountStatement(sc, bankService);
                    case "6" -> listAccounts(sc, bankService);
                    case "7" -> searchAccount(sc, bankService);
                    default -> System.out.println("Invalid Choice");
                }
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private static void openAccount(Scanner sc,BankService bankService) {
        System.out.println("Customer name: ");
        String name = sc.nextLine().trim();

        System.out.println("Customer email: ");
        String email = sc.nextLine().trim();

        System.out.println("Account Type (SAVINGS/CURRENT): ");
        String type = sc.nextLine().trim();

        System.out.println("Initial deposit (optional, blank for 0): ");
        String amountStr = sc.nextLine().trim();

        Double initial = Double.valueOf(amountStr);

        String accountNum = bankService.openAccount(name,email,type );

        if(initial>0)
            bankService.deposit(accountNum,initial,"Initial Deposit");


        System.out.println();
        System.out.println("Account Opened: " + accountNum);
    }
    private static void deposit(Scanner sc,BankService bankService) {
        System.out.println("Account Number: ");
        String accountNumber =sc.nextLine().trim();
        System.out.println("Amount: ");
        Double amount =Double.valueOf(sc.nextLine().trim());
        bankService.deposit(accountNumber,amount,"Deposit");
        System.out.println("Amount Deposited");
    }
    private static void withdraw(Scanner sc,BankService bankService) {
        System.out.println("Account Number: ");
        String accountNumber =sc.nextLine().trim();
        System.out.println("Amount: ");
        Double amount =Double.valueOf(sc.nextLine().trim());
        bankService.withdraw(accountNumber,amount,"Withdraw");
        System.out.println("Amount WithDrawn");
    }
    private static void transfer(Scanner sc ,BankService bankService) {
        System.out.println("From Account: ");
        String from =sc.nextLine().trim();

        System.out.println("To Account: ");
        String to =sc.nextLine().trim();

        System.out.println("Amount: ");
        Double amount =Double.valueOf(sc.nextLine().trim());

        bankService.transfer(from,to,amount,"transfer");
    }
    private static void accountStatement(Scanner sc, BankService bankService) {
        System.out.println("Account Number: ");
        String account =sc.nextLine().trim();

        bankService.getStatement(account).forEach(t -> {
            System.out.println(t.getTimestamp() + " | " + t.getType() + " | " + t.getAmmount()+ " | " + t.getNote());
                });
    }
    private static void listAccounts(Scanner sc, BankService bankService) {
        bankService.listAccounts().forEach(a -> {
            System.out.println(a.getAccountNumber() + " | " + a.getAccountType() + " | " + a.getBalance());
        });
    }
    private static void searchAccount(Scanner sc, BankService bankService) {
        System.out.println("Customer name Contains: ");
        String name = sc.nextLine().trim();

        var accounts = bankService.searchAccountsByCustomerName(name);

        System.out.println("Found: " + accounts.size());

        accounts.forEach(account ->
                System.out.println(account.getAccountNumber() + " | " +
                        account.getAccountType() + " | " +
                        account.getBalance())
        );
    }




}
