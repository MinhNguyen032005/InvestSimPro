package lap.sigleton;

public abstract class Transaction {
   protected BankAccount ac;
   protected double amount;

   public Transaction(BankAccount ac, double amount) {
      this.ac = ac;
      this.amount = amount;
   }

   public abstract void performanceTransaction();
}
