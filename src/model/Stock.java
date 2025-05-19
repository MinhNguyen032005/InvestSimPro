package model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Stock {
    private String stockID;
    private int quantity;
    private double price;
    private LocalDateTime timestamp;

    public Stock(String stockID, int quantity, double price, LocalDateTime timestamp) {
        this.stockID = stockID;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getStockID() {
        return stockID;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Stock stock = (Stock) object;
        return Objects.equals(getStockID(), stock.getStockID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getStockID());
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockID='" + stockID + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", timestamp=" + timestamp +
                '}';
    }
}
