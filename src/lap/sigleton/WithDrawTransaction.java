package lap.sigleton;

public class WithDrawTransaction extends Transaction{
    public WithDrawTransaction(BankAccount ac, double amount) {
        super(ac, amount);
    }

    @Override
    public void performanceTransaction() {
        ac.withdraw(amount);
        System.out.println(ac);
    }
}
