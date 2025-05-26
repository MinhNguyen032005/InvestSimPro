package model;

import java.util.Objects;

public class HistoryTransactionStock {
    private String userName;
    private String Time;
    private String stockID;
    private String type;
    private String price;
    private String quantity;
    private String SumPrice;
    private String muaban;
    private String trangThai;


    public HistoryTransactionStock(String userName, String time, String stockID, String type, String price, String quantity, String sumPrice, String muaban, String trangThai) {
        this.userName = userName;
        Time = time;
        this.stockID = stockID;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        SumPrice = sumPrice;
        this.muaban = muaban;
        this.trangThai = trangThai;
    }

    public String getMuaban() {
        return muaban;
    }

    public String getUserName() {
        return userName;
    }

    public String getTime() {
        return Time;
    }

    public String getStockID() {
        return stockID;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSumPrice() {
        return SumPrice;
    }

    public String getTrangThai() {
        return trangThai;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        HistoryTransactionStock that = (HistoryTransactionStock) object;
        return Objects.equals(getUserName(), that.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUserName());
    }

    @Override
    public String toString() {
        return "HistoryTransactionStock{" +
                "userName='" + userName + '\'' +
                ", Time='" + Time + '\'' +
                ", stockID='" + stockID + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", SumPrice='" + SumPrice + '\'' +
                ", muaban='" + muaban + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }

    public void setMuaban(String muaban) {
        this.muaban = muaban;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setTime(String time) {
        Time = time;
    }
}
