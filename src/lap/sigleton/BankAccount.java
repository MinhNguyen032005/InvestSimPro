package lap.sigleton;

public class BankAccount {
    private static BankAccount instance;
    private static double amount;

    public BankAccount() {
    }

    private BankAccount(double amount) {
        this.amount = amount;
    }

    public static BankAccount getInstance(double amount) {
        if (instance == null) {
            synchronized (BankAccount.class){
                if (instance==null){
                    instance=new BankAccount(amount);
                }
            }
        }
        return instance;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public synchronized boolean withdraw(double amount) {
        if (this.amount>=amount){
            System.out.println("rut tien thanh cong voi so tien:"+amount);
            this.amount -= amount;
            return true;
        }
        System.out.println("vot qua so tien trong tai khoan");
        return false;
    }

    public synchronized void deposit(double amount) {
        this.amount += amount;
        System.out.println(this.amount);
    }

    @Override
    public String toString() {
        return "BankAccount{}";
    }
}
