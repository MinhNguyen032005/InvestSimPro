package model;

public class Facade {
    private AccountManagement acc;
    private Transaction transaction;

    public Facade(AccountManagement acc) {
        this.acc = acc;
    }

    public boolean withDraw(String id, double amount) {
        if (acc.getAccountMap().containsKey(id)) {
            transaction = new WithDrawTransaction(acc.getAccountID(id).getBankAccount(), amount);
            transaction.performanceTransaction();
            return true;
        }
        return false;
    }

    public void deposit(String id, double amount) {
        if (acc.getAccountMap().containsKey(id)) {
            transaction = new DepositionTransaction(acc.getAccountID(id).getBankAccount(), amount);
            transaction.performanceTransaction();
        }
    }

    public void printAll() {

    }

    public void printWithDrave() {

    }

    public void printAllTransaction() {

    }

}
