package lap.sigleton;

public class DepositTransaction extends Transaction{
    public DepositTransaction(BankAccount ac, double amount) {
        super(ac, amount);
    }

    @Override
    public void performanceTransaction() {
        ac.deposit(amount);
        System.out.println();
    }
}
