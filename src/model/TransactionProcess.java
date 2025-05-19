package model;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class TransactionProcess {
    private Map<String, List<Transaction>> transactionMap = new HashMap<>();
    private static final String FILE_PATH = "src/data/file_data/transaction";

    public TransactionProcess() {
        loadDataTransaction();
    }

    public void addTransaction(String idUser, double amount, String type) {
        if (type.equals("deposition")) {
            Transaction transaction = new DepositionTransaction(String.valueOf(UUID.randomUUID()), LocalDateTime.now(), amount);
            transactionMap.putIfAbsent(idUser, new ArrayList<>());
            transactionMap.get(idUser).add(transaction);
        } else {
            Transaction transaction = new WithDrawTransaction(String.valueOf(UUID.randomUUID()), LocalDateTime.now(), amount);
            transactionMap.putIfAbsent(idUser, new ArrayList<>());
            transactionMap.get(idUser).add(transaction);
        }
        saveDataTransaction();
    }


    public void loadDataTransaction() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, "||");
                // Đọc từng giá trị từ file
                String userID = st.nextToken();
                String transactionID = st.nextToken();
                LocalDateTime transactionDate = LocalDateTime.parse(st.nextToken());
                double amount = Double.parseDouble(st.nextToken());
                String type = st.nextToken();

                // **Phân biệt loại giao dịch**
                Transaction transaction;
                if (type.equalsIgnoreCase("deposition")) {
                    transaction = new DepositionTransaction(transactionID, transactionDate, amount);
                } else if (type.equalsIgnoreCase("with draw")) {
                    transaction = new WithDrawTransaction(transactionID, transactionDate, amount);
                } else {
                    System.out.println("Giao dịch không hợp lệ: " + type);
                    continue;
                }

                // Thêm vào Map
                transactionMap.putIfAbsent(userID, new ArrayList<>());
                transactionMap.get(userID).add(transaction);
            }
            System.out.println("✅ Load dữ liệu thành công!");
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi đọc file: " + e.getMessage());
        }
    }

    /**
     * 📌 **Lưu dữ liệu từ Map vào file**
     */
    public void saveDataTransaction() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, List<Transaction>> entry : transactionMap.entrySet()) {
                String userID = entry.getKey();
                for (Transaction transaction : entry.getValue()) {
                    String line = userID + "||"
                            + transaction.getTransactionID() + "||"
                            + transaction.getTransactionDate() + "||"
                            + transaction.getAmount() + "||"
                            + transaction.getType();
                    bw.write(line);
                    bw.newLine();
                }
            }
            System.out.println("✅ Lưu dữ liệu thành công!");
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi ghi file: " + e.getMessage());
        }
    }

    public List<Transaction> getTransactionsByUserId(String userId) {
        return transactionMap.getOrDefault(userId, new ArrayList<>());
    }

}
