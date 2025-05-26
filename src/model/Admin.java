package model;

import java.util.ArrayList;
import java.util.List;

public class Admin extends Account implements Subject {
    private List<Observer> observers;
    private String message;

    public Admin(String idAccount, String name, String nameAccount, String passwdAccount, String email) {
        super(idAccount, name, nameAccount, passwdAccount, email);
        observers = new ArrayList<>();
        this.role = "root";
    }


    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void setMessage(String message) {
        this.message = message;
        notifyObservers();
    }
}
