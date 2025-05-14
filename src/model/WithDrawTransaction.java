package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WithDrawTransaction extends Transaction implements Calculator {

    public WithDrawTransaction(BankAccount idAcc, double amount) {
        super(idAcc, amount);

    }

    public WithDrawTransaction(String transactionID, LocalDateTime transactionDate, double amount) {
        super(transactionID, transactionDate, amount, "with draw");
    }

    @Override
    public void performanceTransaction() {
        idAcc.withdraw(amount);
        this.type = "with draw";
    }

    @Override
    public String toString() {
        return "WithDrawTransaction{" +
                "transactionID='" + transactionID + '\'' +
                ", transactionDate=" + transactionDate +
                ", amount=" + amount +
                ", idAcc=" + idAcc +
                ", type='" + type + '\'' +
                '}';
    }
}
