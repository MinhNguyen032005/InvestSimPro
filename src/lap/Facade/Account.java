package lap.Facade;

public class Account {
    private String idAccount;
    private String name;
    private double amount;

    public Account(String idAccount, String name, double amount) {
        this.idAccount = idAccount;
        this.name = name;
        this.amount = amount;
    }

    public boolean withDraw(double amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        this.amount += amount;
    }

    public boolean checkLogin(String id, String name) {
        return id.equals(idAccount) && this.name.equals(name);
    }

    public boolean checkVerify(Account account) {
        return this.amount == account.amount;
    }

    public boolean checkID(String idAcc) {
        return this.idAccount.equalsIgnoreCase(idAcc);
    }

    public String getIdAccount() {
        return idAccount;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "idAccount='" + idAccount + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
