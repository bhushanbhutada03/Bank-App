# Bank-App

A simple Command Line Banking Application built using Core Java. The project demonstrates Object-Oriented Programming, Layered Architecture, Collections Framework, Exception Handling, and transaction management without using a database.

## Features

- Open New Bank Account
- Deposit Money
- Withdraw Money
- Transfer Money Between Accounts
- View Account Statement
- List All Accounts
- Search Accounts By Customer Name
- Custom Exception Handling

## Project Structure

```text
Bank-App
│
├── src
│   ├── app
│   │   └── Main.java
│   │
│   ├── domain
│   │   ├── Account.java
│   │   ├── Customer.java
│   │   ├── Transaction.java
│   │   └── TransactionType.java
│   │
│   ├── exceptions
│   │   ├── AccountNotFoundException.java
│   │   ├── InsufficientBalanceException.java
│   │   └── SameAccountTransferException.java
│   │
│   ├── repository
│   │   ├── AccountRepo.java
│   │   ├── CustomerRepo.java
│   │   └── TransactionRepo.java
│   │
│   └── service
│       ├── BankService.java
│       └── impl
│           └── BankServiceImpl.java
│
└── README.md
```

## Technologies Used

- Java
- OOP
- Collections Framework
- Exception Handling
- IntelliJ IDEA

## How To Run

1. Clone the repository

```bash
git clone https://github.com/bhushanbhutada03/Bank-App.git
```

2. Open the project in IntelliJ IDEA

3. Run:

```text
src/app/Main.java
```

## Available Operations

```text
1. Open Account
2. Deposit
3. Withdraw
4. Transfer
5. Account Statement
6. List Accounts
7. Search Account By Customer Name
0. Exit
```

## Concepts Demonstrated

- Object-Oriented Programming (OOP)
- Layered Architecture
- Repository Pattern
- Service Layer
- Custom Exceptions
- Java Collections Framework
- CLI Application Development
