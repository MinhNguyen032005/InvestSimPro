package lap.TemplateMethod;

public class WithDrawTransaction extends Transaction {
    public WithDrawTransaction(BankAccount ac, double amount) {
        super(ac, amount);
    }

    @Override
    public void log() {
        System.out.println("da rut tien:"+amount);
    }

    @Override
    public void performanceTransaction() {
        ac.withdraw(amount);
        System.out.println(ac);
    }
}
