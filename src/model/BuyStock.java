package model;

public class BuyStock extends StockTransaction {

    public BuyStock(StockIntermediary stockIntermediary, String nameAccount, Stock stock) {
        super(stockIntermediary, nameAccount, stock);
    }

    @Override
    public boolean execute() {
        stockIntermediary.buyStock(nameAccount, stock.getStockID(), stock.getQuantity(), stock.getPrice());
        return true;
    }
}
