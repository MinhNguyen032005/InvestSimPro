package model;

import java.util.ArrayList;
import java.util.List;

public  class Admin extends Account implements Observer{
    private List<Users> managedUsers;
    private List<Transaction> allTransactions;

    public Admin(String idAccount, String name, String nameAccount, String passwdAccount, String email) {
        super(idAccount, name, nameAccount, passwdAccount, email);
        managedUsers=new ArrayList<>();
        allTransactions=new ArrayList<>();
        this.role="root";
    }
}
