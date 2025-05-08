package model;

import java.time.LocalDate;

public abstract class Transaction {
    protected String transactionID;
    protected LocalDate transactionDate;
    protected double amount;
    protected BankAccount idAcc;
    protected String type;
}
