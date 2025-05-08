package lap.TemplateMethod;

public abstract class Transaction {
    protected BankAccount ac;
    protected double amount;

    public Transaction(BankAccount ac, double amount) {
        this.ac = ac;
        this.amount = amount;
    }

    final void processTransaction() {
        login();
        performanceTransaction();
        log();
    }

    public void login() {
        if (checkAccount()) {
            System.out.println("da dang nhap");
            return;
        }
        System.out.println("chua dang nhap");
    }
    public boolean checkAccount(){
        if (ac.getInstance() != null){
            return true;
        }
        return false;
    }
    public abstract void log();

    public abstract void performanceTransaction();
}
