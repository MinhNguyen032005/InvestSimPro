package model;

import java.util.UUID;

public class Stock {
    private String stockID;
    private String stockName;
    private double price;

    public Stock(String symbol, double v) {
        this.stockID= UUID.randomUUID().toString();
        this.stockName=symbol;
        this.price =v;

    }

    public String getStockID() {
        return stockID;
    }

    public String getStockName() {
        return stockName;
    }

    public double getPrice() {
        return price;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
