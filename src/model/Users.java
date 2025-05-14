package model;

import lap.decord.ObServer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Users extends Account implements Subject {
    private double balance;
    private List<Stock> existingStocks;
    private List<Observer> observers;
    private BankAccount bankAccount;

    public Users(String idAccount, String name, String nameAccount, String passwdAccount, String email) {
        super(idAccount, name, nameAccount, passwdAccount, email);
        existingStocks = new ArrayList<>();
        this.role = "user";
        this.balance = 0;
        this.bankAccount = new BankAccount(idAccount, fullName, this.balance);

    }


    public Users(String idAccount, String fullName, double balance, String nameAccount, String passwdAccount, String email, String role, LocalDate createAt) {
        super(idAccount, fullName, nameAccount, passwdAccount, email, role, createAt);
        this.balance = balance;
        this.bankAccount = new BankAccount(idAccount, fullName, this.balance);
    }

    public double getBalance() {
        return balance;
    }


    @Override
    public void addObServer(ObServer obServer) {

    }

    @Override
    public void removeObServer(ObServer obServer) {

    }

    @Override
    public void notiify() {

    }

    @Override
    public void updateThongBao(String ndTB) {

    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
