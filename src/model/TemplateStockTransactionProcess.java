package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TemplateStockTransactionProcess {
    AccountManagement accountManagement;
    StockTransaction stockTransaction;
    Portfolio portfolio;


    public TemplateStockTransactionProcess(AccountManagement accountManagement) {
        this.accountManagement = accountManagement;
        this.portfolio = new Portfolio();
    }

    // Thực hiện giao dịch mua cổ phiếu
    public boolean buyStock(String id, Stock stock) {
        if (accountManagement.getAccountMap().containsKey(id)) {
            StockIntermediary stockIntermediary = new StockIntermediary(portfolio, accountManagement.getAccountID(id).getBankAccount());
            stockTransaction = new BuyStock(stockIntermediary, accountManagement.getAccountID(id).getNameAccount(), stock);
            stockTransaction.execute();
            return true;
        }
        return false;
    }

    // Thực hiện giao dịch bán cổ phiếu
    public boolean sellStock(String id, Stock stock) {
        if (accountManagement.getAccountMap().containsKey(id)) {
            StockIntermediary stockIntermediary = new StockIntermediary(portfolio, accountManagement.getAccountID(id).getBankAccount());
            stockTransaction = new SellStock(stockIntermediary, accountManagement.getAccountID(id).getNameAccount(), stock);
            stockTransaction.execute();
            return true;
        }
        return false;
    }
}
