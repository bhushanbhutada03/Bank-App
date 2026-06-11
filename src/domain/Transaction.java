package domain;

import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private String accountNumber;
    private TransactionType transactionType;
    private Double ammount;
    private LocalDateTime timestamp;
    private String note;

    public Transaction(String id, String accountNumber, Double ammount, String note, LocalDateTime timestamp, TransactionType transactionType) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.ammount = ammount;
        this.timestamp = timestamp;
        this.note = note;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public TransactionType getType() {
        return transactionType;
    }

    public void setType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
}
