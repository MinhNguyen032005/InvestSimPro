package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MarketStock {
    private String symbol;               // CK
    private double ceilingPrice;          // Trần
    private double floorPrice;            // Sàn
    private double referencePrice;        // TC
    private double price3M;               // Giá 3 (M)
    private int volume3M;                 // KL 3 (M)
    private double price2M;               // Giá 2 (M)
    private int volume2M;                 // KL 2 (M)
    private double price1M;               // Giá 1 (M)
    private int volume1M;                 // KL 1 (M)
    private double matchPrice;            // Giá khớp
    private int matchVolume;              // KL khớp
    private double price1B;               // Giá 1 (B)
    private int volume1B;                 // KL 1 (B)
    private double price2B;               // Giá 2 (B)
    private int volume2B;                 // KL 2 (B)
    private double price3B;               // Giá 3 (B)
    private int volume3B;                 // KL 3 (B)
    private int totalVolume;              // Tổng KL
    private double high;                  // Cao
    private double low;                   // Thấp
    private int foreignBuy;               // NN mua
    private int foreignSell;              // NN bán
    private int room;                     // Room

    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final String currentDate = sdfDate.format(new Date());
    private static final String filePath = "src/data/file_data/market_data_" + currentDate + ".txt";


    public MarketStock(String symbol, double referencePrice) {
        this.symbol = symbol;
        this.referencePrice = referencePrice;
        this.ceilingPrice = Math.round(referencePrice * 1.1);
        this.floorPrice = Math.round(referencePrice * 0.9);
        // Tạo ra các giá trị ngẫu nhiên cho các thuộc tính
        Random rand = new Random();
        this.price3M = randomPrice();
        this.volume3M = rand.nextInt(1000) + 100;
        this.price2M = randomPrice();
        this.volume2M = rand.nextInt(1000) + 100;
        this.price1M = randomPrice();
        this.volume1M = rand.nextInt(1000) + 100;
        this.matchPrice = randomPrice();
        this.matchVolume = rand.nextInt(5000) + 1000;
        this.price1B = randomPrice();
        this.volume1B = rand.nextInt(1000) + 100;
        this.price2B = randomPrice();
        this.volume2B = rand.nextInt(1000) + 100;
        this.price3B = randomPrice();
        this.volume3B = rand.nextInt(1000) + 100;
        this.totalVolume = volume1M + volume2M + volume3M + volume1B + volume2B + volume3B;
        this.high = Math.round(referencePrice * 1.2);
        this.low = Math.round(referencePrice * 0.8);
        this.foreignBuy = rand.nextInt(10000);
        this.foreignSell = rand.nextInt(10000);
        this.room = 1000000 - foreignBuy;
    }


    public MarketStock(String symbol, double ceilingPrice, double floorPrice, double referencePrice, double price3M, int volume3M, double price2M, int volume2M, double price1M, int volume1M, double matchPrice, int matchVolume, double price1B, int volume1B, double price2B, int volume2B, double price3B, int volume3B, int totalVolume, double high, double low, int foreignBuy, int foreignSell, int room) {
        this.symbol = symbol;
        this.ceilingPrice = ceilingPrice;
        this.floorPrice = floorPrice;
        this.referencePrice = referencePrice;
        this.price3M = price3M;
        this.volume3M = volume3M;
        this.price2M = price2M;
        this.volume2M = volume2M;
        this.price1M = price1M;
        this.volume1M = volume1M;
        this.matchPrice = matchPrice;
        this.matchVolume = matchVolume;
        this.price1B = price1B;
        this.volume1B = volume1B;
        this.price2B = price2B;
        this.volume2B = volume2B;
        this.price3B = price3B;
        this.volume3B = volume3B;
        this.totalVolume = totalVolume;
        this.high = high;
        this.low = low;
        this.foreignBuy = foreignBuy;
        this.foreignSell = foreignSell;
        this.room = room;
    }

    // Hàm tạo giá ngẫu nhiên
    private double randomPrice() {
        Random rand = new Random();
        return Math.round((10 + rand.nextDouble() * 90) * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return String.format(
                "%s|%.2f|%.2f|%.2f|%.2f|%d|%.2f|%d|%.2f|%d|%.2f|%d|%.2f|%d|%.2f|%d|%.2f|%d|%d|%.2f|%.2f|%d|%d|%d",
                symbol, referencePrice, ceilingPrice, floorPrice, matchPrice, matchVolume,
                price3M, volume3M, price2M, volume2M, price1M, volume1M,
                price1B, volume1B, price2B, volume2B, price3B, volume3B,
                totalVolume, high, low, foreignBuy, foreignSell, room);
    }


    // Getter và Setter cho các thuộc tính (nếu cần)
    public String getSymbol() {
        return symbol;
    }

    public double getCeilingPrice() {
        return ceilingPrice;
    }

    public double getFloorPrice() {
        return floorPrice;
    }

    public double getReferencePrice() {
        return referencePrice;
    }

    public double getPrice3M() {
        return price3M;
    }

    public int getVolume3M() {
        return volume3M;
    }

    public static List<MarketStock> generateFakeStocks(int number) {
        List<MarketStock> stocks = new ArrayList<>();
        Map<String, Double> priceMap = new HashMap<>();

        // 1️⃣ Đọc dữ liệu từ file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 2) {
                    String symbol = parts[1];
                    double referencePrice = Double.parseDouble(parts[2]);
                    priceMap.put(symbol, referencePrice);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 2️⃣ Tạo đối tượng MarketStock
        for (int i = 0; i < number; i++) {
            String symbol = "CK" + (100 + i);
            double referencePrice;

            if (priceMap.containsKey(symbol)) {
                referencePrice = priceMap.get(symbol);
            } else {
                referencePrice = Math.round((10 + new Random().nextDouble() * 90) * 100.0) / 100.0;
            }
            // Thêm vào danh sách cổ phiếu
            stocks.add(new MarketStock(symbol, referencePrice));
        }

        return stocks;
    }

    // API Method để cập nhật thông tin cổ phiếu
    public void updateStock(List<MarketStock> stocks, String symbol, double newMatchPrice, int newMatchVolume) {
        for (MarketStock stock : stocks) {
            if (stock.getSymbol().equals(symbol)) {
                stock.matchPrice = newMatchPrice;
                stock.matchVolume = newMatchVolume;
                return;
            }
        }
    }

    // API Method để xóa cổ phiếu
    public void removeStock(List<MarketStock> stocks, String symbol) {
        stocks.removeIf(stock -> stock.getSymbol().equals(symbol));
    }

    // API Method để lấy thông tin cổ phiếu
    public MarketStock getStockInfo(List<MarketStock> stocks, String symbol) {
        for (MarketStock stock : stocks) {
            if (stock.getSymbol().equals(symbol)) {
                return stock;
            }
        }
        return null;
    }


    public void setMatchPrice(double matchPrice) {
        this.matchPrice = matchPrice;
    }

    public void setMatchVolume(int matchVolume) {
        this.matchVolume = matchVolume;
    }

    public double getPrice2M() {
        return price2M;
    }

    public int getVolume2M() {
        return volume2M;
    }

    public double getPrice1M() {
        return price1M;
    }

    public int getVolume1M() {
        return volume1M;
    }

    public double getMatchPrice() {
        return matchPrice;
    }

    public int getMatchVolume() {
        return matchVolume;
    }

    public double getPrice1B() {
        return price1B;
    }

    public int getVolume1B() {
        return volume1B;
    }

    public double getPrice2B() {
        return price2B;
    }

    public int getVolume2B() {
        return volume2B;
    }

    public double getPrice3B() {
        return price3B;
    }

    public int getVolume3B() {
        return volume3B;
    }

    public int getTotalVolume() {
        return totalVolume;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public int getForeignBuy() {
        return foreignBuy;
    }

    public int getForeignSell() {
        return foreignSell;
    }

    public int getRoom() {
        return room;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MarketStock stock = (MarketStock) o;
        return getSymbol().equals(stock.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSymbol());
    }

    public double maxPriceBuy() {
        return Math.max(this.price1M, Math.max(this.price2M, this.price3M));
    }

    public double maxPriceSell() {
        return Math.max(this.price1B, Math.max(this.price2B, this.price3B));
    }
}
