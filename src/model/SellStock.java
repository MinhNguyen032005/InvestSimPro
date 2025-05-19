package model;

public class SellStock extends StockTransaction {

    public SellStock(StockIntermediary stockIntermediary, String nameAccount, Stock stock) {
        super(stockIntermediary, nameAccount, stock);
    }

    @Override
    public boolean execute() {
        stockIntermediary.sellStock(nameAccount, stock.getStockID(), stock.getQuantity(), stock.getPrice());
        return true;
    }
}
