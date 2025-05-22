package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StockIntermediary {
    private final Portfolio portfolio;
    private final BankAccount bankAccount;

    public StockIntermediary(Portfolio portfolio, BankAccount bankAccount) {
        this.portfolio = portfolio;
        this.bankAccount = bankAccount;
    }

    // Thực hiện giao dịch mua cổ phiếu
    public boolean buyStock(String nameAccount, String stockID, int quantity, double price1) {

        double price = price1;
        double totalPrice = price * quantity;

        if (bankAccount.getAmount() >= totalPrice) {
            if (bankAccount.withdraw(totalPrice)) {
                portfolio.addOrUpdateStock(nameAccount, stockID, quantity, price);
                return true;
            }
        } else {
            System.out.println("❌ Không đủ số dư để mua cổ phiếu!");
            return false;
        }
        System.out.println("❌ Cổ phiếu không tồn tại trên thị trường.");
        return false;
    }

    // Thực hiện giao dịch bán cổ phiếu
    public boolean sellStock(String nameAccount, String stockID, int quantity, double price1) {
        double price = price1;
        if (portfolio.sellStock(nameAccount, stockID, quantity)) {
            double totalPrice = price * quantity;
            bankAccount.deposit(totalPrice);
            return true;
        } else {
            return false;
        }
    }
}
