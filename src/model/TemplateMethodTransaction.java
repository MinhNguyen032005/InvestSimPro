package model;

public class TemplateMethodTransaction {
    private AccountManagement acc;
    private Transaction transaction;

    public TemplateMethodTransaction(AccountManagement acc) {
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
}
