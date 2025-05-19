package model;

public abstract class StockTransaction {
    protected StockIntermediary stockIntermediary;
    protected String nameAccount;
    protected Stock stock;

    public StockTransaction(StockIntermediary stockIntermediary, String nameAccount, Stock stock) {
        this.stockIntermediary = stockIntermediary;
        this.nameAccount = nameAccount;
        this.stock = stock;
    }

    public abstract boolean execute();

}
