package model;

import java.io.*;
import java.util.*;

public class HistoryTransaction {
    private static final String filePath = "src/data/file_data/histoty_stock_transaction";
    private final List<HistoryTransactionStock> historyList = new ArrayList<>();

    public HistoryTransaction() {
        loadFromFile();
    }

    // Thêm giao dịch mới
    public void addTransaction(String userName, String time, String stockID, String type, String price, String quantity, String sumPrice, String trangThai) {
        HistoryTransactionStock transaction = new HistoryTransactionStock(userName, time, stockID, type, price, quantity, sumPrice, trangThai);
        historyList.add(transaction);
        saveToFile();
    }

    // Load lịch sử giao dịch từ file history.txt
    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            historyList.clear();
            String line;
            while ((line = br.readLine()) != null) {
                // Định dạng mỗi dòng: userName;time;stockID;type;price;quantity;sumPrice;trangThai
                String[] parts = line.split(";");
                if (parts.length == 8) {
                    String userName = parts[0];
                    String time = parts[1];
                    String stockID = parts[2];
                    String type = parts[3];
                    String price = parts[4];
                    String quantity = parts[5];
                    String sumPrice = parts[6];
                    String trangThai = parts[7];
                    historyList.add(new HistoryTransactionStock(userName, time, stockID, type, price, quantity, sumPrice, trangThai));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lưu lịch sử giao dịch vào file history.txt
    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (HistoryTransactionStock transaction : historyList) {
                String line = String.join(";",
                        transaction.getUserName(),
                        transaction.getTime(),
                        transaction.getStockID(),
                        transaction.getType(),
                        transaction.getPrice(),
                        transaction.getQuantity(),
                        transaction.getSumPrice(),
                        transaction.getTrangThai()
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lấy toàn bộ lịch sử giao dịch
    public List<HistoryTransactionStock> getHistoryList() {
        return historyList;
    }
}
