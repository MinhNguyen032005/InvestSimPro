package model;

import lap.decord.ObServer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Users extends Account implements Subject {
    private double balance;
    private List<Stock> existingStocks;
    private List<Observer> observers;


    public Users(String idAccount, String name, String nameAccount, String passwdAccount, String email) {
        super(idAccount, name, nameAccount, passwdAccount, email);
        existingStocks = new ArrayList<>();
        this.role = "user";
    }

    public Users(String idAccount, String fullName, double balance, String nameAccount, String passwdAccount, String email, String role, LocalDate createAt) {
        super(idAccount, fullName, nameAccount, passwdAccount, email, role, createAt);
        this.balance = balance;
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
}
