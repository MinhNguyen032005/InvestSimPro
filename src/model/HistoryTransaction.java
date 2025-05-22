package model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HistoryTransaction {
    private final String filePath = "src/data/file_data/histoty_stock_transaction";
    private final List<HistoryTransactionStock> historyList = new ArrayList<>();

    public HistoryTransaction() {
        loadFromFile();
    }

    // Thêm giao dịch mới
    public void addTransaction(String userName, String time, String stockID, String type, String price, String quantity, String sumPrice, String muaBan, String trangThai) {
        HistoryTransactionStock transaction = new HistoryTransactionStock(userName, time, stockID, type, price, quantity, sumPrice, muaBan, trangThai);
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
                if (parts.length == 9) {
                    String userName = parts[0];
                    String time = parts[1];
                    String stockID = parts[2];
                    String type = parts[3];
                    String price = parts[4];
                    String quantity = parts[5];
                    String sumPrice = parts[6];
                    String muaBan = parts[7];
                    String trangThai = parts[8];
                    historyList.add(new HistoryTransactionStock(userName, time, stockID, type, price, quantity, sumPrice, muaBan, trangThai));
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
                        transaction.getMuaban(),
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

    // Cập nhật trạng thái hủy cho lệnh đang chờ kích hoạt theo mã CK và loại lệnh
    public void cancelTransactionStock(String maChungKhoan, String loaiLenh) {
        for (HistoryTransactionStock t : historyList) {
            if (t.getStockID().equals(maChungKhoan)
                    && t.getMuaban().equals(loaiLenh)
                    && t.getTrangThai().equals("Chờ kích hoạt")) {
                t.setTrangThai("Đã hủy");
                break;  // Nếu chỉ hủy 1 lệnh thì break; nếu có thể nhiều thì bỏ break
            }
        }
    }

//    // Thực thi giao dịch khi được kích hoạt
//    public void actionTransactionStock(String thaoTac, String khoiLuongStr, String giaKichHoatStr, String maChungKhoan) {
//        int khoiLuong = Integer.parseInt(khoiLuongStr);
//        double giaKichHoat = Double.parseDouble(giaKichHoatStr);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        String currentTime = LocalDateTime.now().format(formatter);
//        for (HistoryTransactionStock t : historyList) {
//            if (t.getStockID().equals(maChungKhoan)
//                    && t.getMuaban().equals(thaoTac)
//                    && t.getTrangThai().equals("Chờ kích hoạt")
//                    && Integer.parseInt(t.getQuantity()) == khoiLuong
//                    && Double.parseDouble(t.getPrice()) == giaKichHoat) {
//                t.setTrangThai("Thành công");
//                t.setTime(currentTime);
//
//                // Nếu đây là lệnh OCO, thì hủy lệnh còn lại
//                if (t.getType().startsWith("OCO")) {
//                    String otherLoaiLenh = t.getMuaban().contains("Mua") ? t.getMuaban().replace("Mua", "Bán") : t.getMuaban().replace("Bán", "Mua");
//                    cancelTransactionStock(maChungKhoan, otherLoaiLenh);
//                }
//                break;
//            }
//        }
//    }
    public void removeTransaction(int index) {
        if (index >= 0 && index < historyList.size()) {
            historyList.remove(index);
            saveToFile();
        }
    }

    public HistoryTransactionStock getTransaction(int index) {
        if (index >= 0 && index < historyList.size()) {
            return historyList.get(index);
        }
        return null;
    }

    public void updateTransaction(int index, HistoryTransactionStock updated) {
        if (index >= 0 && index < historyList.size()) {
            historyList.set(index, updated);
            saveToFile();
        }
    }


}
