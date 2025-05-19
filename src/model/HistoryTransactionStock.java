package model;

public class HistoryTransactionStock {
    private String userName;
    private String Time;
    private String stockID;
    private String type;
    private String price;
    private String quantity;
    private String SumPrice;
    private String trangThai;



    public HistoryTransactionStock(String userName, String time, String stockID, String type, String price, String quantity, String sumPrice, String trangThai) {
        this.userName = userName;
        Time = time;
        this.stockID = stockID;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        SumPrice = sumPrice;
        this.trangThai = trangThai;
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
}
