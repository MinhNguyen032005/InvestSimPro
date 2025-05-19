package model;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Portfolio {
    private final String filePath = "src/data/file_data/portfolio.txt";
    private final Map<String, List<Stock>> portfolioMap = new HashMap<>();

    public Portfolio() {
        loadFromFile();
    }


    // Thêm hoặc cập nhật cổ phiếu cho nhà đầu tư (mua cổ phiếu)
    public void addOrUpdateStock(String investorName, String stockID, int quantity, double price) {
        List<Stock> stocks = portfolioMap.getOrDefault(investorName, new ArrayList<>());

        Optional<Stock> existingStockOpt = stocks.stream()
                .filter(s -> s.getStockID().equals(stockID))
                .findFirst();

        if (existingStockOpt.isPresent()) {
            Stock existingStock = existingStockOpt.get();
            existingStock.setQuantity(existingStock.getQuantity() + quantity);
            existingStock.setPrice(price);
            existingStock.setTimestamp(LocalDateTime.now());
        } else {
            stocks.add(new Stock(stockID, quantity, price, LocalDateTime.now()));
        }

        portfolioMap.put(investorName, stocks);
        saveToFile();
    }

    // Bán cổ phiếu: giảm số lượng hoặc xóa nếu số lượng = 0
    public boolean sellStock(String investorName, String stockID, int quantity) {
        List<Stock> stocks = portfolioMap.get(investorName);
        if (stocks == null) return false;

        Optional<Stock> stockOpt = stocks.stream()
                .filter(s -> s.getStockID().equals(stockID))
                .findFirst();

        if (stockOpt.isPresent()) {
            Stock stock = stockOpt.get();
            if (stock.getQuantity() >= quantity) {
                stock.setQuantity(stock.getQuantity() - quantity);
                if (stock.getQuantity() == 0) {
                    stocks.remove(stock);
                }
                saveToFile();
                return true;
            }
        }
        return false;
    }

    // Load dữ liệu từ file portfolio.txt
    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            portfolioMap.clear();
            String line;
            while ((line = br.readLine()) != null) {
                // Định dạng mỗi dòng: investorName;stockID,quantity,price,timestamp|stockID,quantity,price,timestamp|...
                String[] parts = line.split("||");
                if (parts.length != 2) continue;

                String investorName = parts[0];
                String stocksStr = parts[1];
                List<Stock> stocks = new ArrayList<>();

                String[] stockTokens = stocksStr.split("\\|");
                for (String token : stockTokens) {
                    String[] stockParts = token.split(",");
                    if (stockParts.length == 4) {
                        String stockID = stockParts[0];
                        int quantity = Integer.parseInt(stockParts[1]);
                        double price = Double.parseDouble(stockParts[2]);
                        LocalDateTime timestamp = LocalDateTime.parse(stockParts[3]);
                        stocks.add(new Stock(stockID, quantity, price, timestamp));
                    }
                }
                portfolioMap.put(investorName, stocks);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lưu dữ liệu vào file portfolio.txt
    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, List<Stock>> entry : portfolioMap.entrySet()) {
                String investorName = entry.getKey();
                List<Stock> stocks = entry.getValue();

                StringBuilder sb = new StringBuilder();
                sb.append(investorName).append("||");

                for (int i = 0; i < stocks.size(); i++) {
                    sb.append(stocks.get(i).toString());
                    if (i < stocks.size() - 1) {
                        sb.append("|");
                    }
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
