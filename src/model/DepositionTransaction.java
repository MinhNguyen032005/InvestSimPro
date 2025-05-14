package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DepositionTransaction extends Transaction implements Calculator {
    public DepositionTransaction(BankAccount idAcc, double amount) {
        super(idAcc, amount);

    }

    public DepositionTransaction(String transactionID, LocalDateTime transactionDate, double amount) {
        super(transactionID, transactionDate, amount, "deposition");
    }

    @Override
    public void performanceTransaction() {
        idAcc.deposit(amount);
        this.type = "deposition";
    }
}
