package model;

import lap.decord.ObServer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Users extends Account implements Observer {
    private double balance;
    private BankAccount bankAccount;
    private String message;

    public Users(String idAccount, String name, String nameAccount, String passwdAccount, String email) {
        super(idAccount, name, nameAccount, passwdAccount, email);
        this.role = "user";
        this.balance = 0;
        this.bankAccount = new BankAccount(idAccount, fullName, this.balance);
        this.bankAccount.getInstance(this.balance);

    }


    public Users(String idAccount, String fullName, double balance, String nameAccount, String passwdAccount, String email, String role, LocalDate createAt) {
        super(idAccount, fullName, nameAccount, passwdAccount, email, role, createAt);
        this.balance = balance;
        this.bankAccount = new BankAccount(idAccount, fullName, this.balance);
    }

    public double getBalance() {
        return balance;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    @Override
    public void update(String message) {
        this.message = message;
        System.out.println(this.message);
    }
    public String getMessage() {
        System.out.println(message);
        return message;
    }
}
