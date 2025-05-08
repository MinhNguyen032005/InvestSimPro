package lap.sigleton;

public class MainBankAccount   {
    private BankAccount account;
    private Transaction transaction;

    public static void main(String[] args) {
        BankAccount acc= BankAccount.getInstance(10000.0);
        Transaction transaction1=new WithDrawTransaction(acc,2000.0);
        transaction1.performanceTransaction();
        System.out.println(acc.getAmount());
        Transaction transaction2=new WithDrawTransaction(acc,9000.0);
        transaction2.performanceTransaction();
        //////////////////////////////////
        BankAccount acc1= BankAccount.getInstance(20000.0);
        Transaction transaction3=new DepositTransaction(acc1,2000.0);
        transaction3.performanceTransaction();
        System.out.println(acc1.getAmount());
        System.out.println(acc.getAmount());
    }
}
