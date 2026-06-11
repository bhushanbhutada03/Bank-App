# Bank-App

A simple Command Line Banking Application built using Core Java. The project demonstrates Object-Oriented Programming, Layered Architecture, Collections Framework, Exception Handling, and basic transaction management without using a database.

## Features

* Open New Bank Account
* Deposit Money
* Withdraw Money
* Transfer Money Between Accounts
* View Account Statement
* List All Accounts
* Search Accounts By Customer Name
* Custom Exception Handling

## Project Structure

src

├── app

│   └── Main.java

├── domain

│   ├── Account.java

│   ├── Customer.java

│   ├── Transaction.java

│   └── TransactionType.java

├── exceptions

│   ├── AccountNotFoundException.java

│   ├── InsufficientBalanceException.java

│   └── SameAccountTransferException.java

├── repository

│   ├── AccountRepo.java

│   ├── CustomerRepo.java

│   └── TransactionRepo.java

├── service

│   ├── BankService.java

│   └── impl

│       └── BankServiceImpl.java

## Technologies Used

* Java
* Collections Framework
* OOP Concepts
* Exception Handling
* IntelliJ IDEA

## How To Run

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Run Main.java.
4. Use the menu-driven interface.

## Sample Operations

1. Open Account
2. Deposit
3. Withdraw
4. Transfer
5. Account Statement
6. List Accounts
7. Search Account By Customer Name
8. Exit

## Learning Outcomes

* Layered Architecture
* Repository Pattern
* Service Layer Design
* Custom Exceptions
* Java Collections
* Data Modeling
* CLI Application Development
