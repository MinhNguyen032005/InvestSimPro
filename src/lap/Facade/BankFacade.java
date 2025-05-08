package lap.Facade;

public class BankFacade {
    AccountManagement accountManagement=new AccountManagement();
    TransactionProcess transactionProcess=new TransactionProcess();

    public boolean withDraw(String id, String name, double amount) {
        Account account = accountManagement.loginAccount(id, name);
        Account account1 = accountManagement.veryfyAccount(account);
        if (account1 != null) {
            return transactionProcess.withDrawProcess(account1, amount);
        }
        return false;
    }

    public boolean deposit(String id, String name, double amount) {
        Account account = accountManagement.loginAccount(id, name);
        Account account1 = accountManagement.veryfyAccount(account);
        if (account1 != null) {
             transactionProcess.depositProcess(account1, amount);
        }
        return true;
    }

    public void printAll() {
        for (Account account : accountManagement.getList()) {
            System.out.print(account);
        }
    }

    public void printWithDraw() {
        for (Transaction transaction : transactionProcess.getTransactionList()) {
            if (transaction.getType().equals("withDraw")) {
                System.out.println(transaction);
            }
        }
    }

    public void printAllTransaction() {
        for (Transaction transaction : transactionProcess.getTransactionList()) {
            System.out.println(transaction);
        }
    }

    public void addAccount(Account acc) {
        accountManagement.addAccount(acc);
    }

}
