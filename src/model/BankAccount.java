package model;

import java.util.Objects;

public class BankAccount {
    private String bankID;
    private String bankName;
    private double amount;
    private BankAccount instance;

    public BankAccount(String bankID, String bankName, double amount) {
        this.bankID = bankID;
        this.bankName = bankName;
        this.amount = amount;
    }

    public BankAccount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(bankID, that.bankID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bankID);
    }

    public BankAccount getInstance(double amount) {
        if (instance == null) {
            synchronized (BankAccount.class) {
                if (instance == null) {
                    instance = new BankAccount(amount);
                }
            }
        }
        return instance;
    }

    public synchronized boolean withdraw(double amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
            return true;
        }
        return false;
    }

    public synchronized void deposit(double amount) {
        this.amount += amount;
    }

    public String getBankID() {
        return bankID;
    }

    public String getBankName() {
        return bankName;
    }

    public double getAmount() {
        return amount;
    }

}
