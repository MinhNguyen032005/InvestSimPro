package lap.Facade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionProcess {
    private List<Transaction> transactionList = new ArrayList<>();


    public boolean withDrawProcess(Account account, double amount) {
        if (account.withDraw(amount)) {
            Transaction transaction = new Transaction("WithDraw", account.getIdAccount(), amount);
            transactionList.add(transaction);
            return true;
        }
        return false;
    }

    public void depositProcess(Account account, double amount) {
        Transaction transaction = new Transaction("Deposit", account.getIdAccount(), amount);
        transactionList.add(transaction);
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }
}
