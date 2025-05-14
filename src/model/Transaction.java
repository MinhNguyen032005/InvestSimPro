package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Transaction {
    protected String transactionID;
    protected LocalDateTime transactionDate;
    protected double amount;
    protected BankAccount idAcc;
    protected String type;

    public Transaction(String transactionID, LocalDateTime transactionDate, double amount, String type) {
        this.transactionID = transactionID;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.type = type;
    }

    public Transaction(BankAccount idAcc, double amount) {
        this.transactionID = String.valueOf(UUID.randomUUID());
        this.transactionDate = LocalDateTime.now();
        this.idAcc = idAcc;
        this.amount = amount;
        this.type = "";
    }

    public abstract void performanceTransaction();

    public void setType(String type) {
        this.type = type;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public BankAccount getIdAcc() {
        return idAcc;
    }

    public String getType() {
        return type;
    }
}
