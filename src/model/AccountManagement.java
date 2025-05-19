package model;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class AccountManagement {
    private static final String FILE_PATH = "src/data/file_data/account";
    private Map<String, Account> accountMap;
    private static AccountManagement instance;
    private String token;
    private TemplateMethodTransaction templateMethodTransaction;
    private TemplateStockTransactionProcess transactionProcess;

    public static AccountManagement getInstance() {
        if (instance == null) {
            instance = new AccountManagement();
        }
        return instance;
    }

    public AccountManagement() {
        templateMethodTransaction = new TemplateMethodTransaction(this);
        transactionProcess = new TemplateStockTransactionProcess(this);
        accountMap = new HashMap<>();
        loadDataAccount();
    }

    public void addAccount(String name,
                           String nameAccount,
                           String passwdAccount,
                           String email) {
        if (name.isEmpty() || nameAccount.isEmpty() || passwdAccount.isEmpty() || email.isEmpty()) return;
        Users accountNew = new Users(checkKeyNotContain(), name, nameAccount, passwdAccount, email);
        accountMap.put(accountNew.getIdAccount(), accountNew);
        writeToFile(FILE_PATH, accountMap);
    }

    public void deleteAccount(String nameAccount) {
        boolean isDeleted = false;

        // 🔹 Sử dụng Iterator để tránh ConcurrentModificationException
        Iterator<Map.Entry<String, Account>> iterator = accountMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Account> entry = iterator.next();
            Account account = entry.getValue();
            if (account.getNameAccount().equals(nameAccount)) {
                iterator.remove();  // ✅ Xóa một cách an toàn
                isDeleted = true;
            }
        }

        // 2️⃣ Đọc lại toàn bộ file và lưu vào List
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|\\|");
                if (parts.length > 4 && parts[3].equals(nameAccount)) {
                    isDeleted = true;
                    continue; // Bỏ qua dòng cần xóa
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Nếu không tìm thấy tài khoản trong file
        if (!isDeleted) {
            return;
        }

        // 3️⃣ Ghi đè lại file với danh sách mới
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeToFile(String fileName, Map<String, Account> accountMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String key : accountMap.keySet()) {
                Users account = (Users) accountMap.get(key);
                String data = String.join("||",
                        account.getIdAccount(),
                        account.getFullName(),
                        String.valueOf(account.getBalance()),
                        account.getNameAccount(),
                        account.getPasswdAccount(),
                        account.getEmail(),
                        account.getRole(),
                        String.valueOf(account.getCreateAt())
                );
                writer.write(data);
                writer.newLine();
            }
        } catch (IOException e) {
        }
    }


    private String checkKeyNotContain() {
        String re = "";
        for (int i = 2; i <= accountMap.size() + 1; i++) {
            re = String.valueOf(i);
            if (!accountMap.keySet().contains(String.valueOf(i))) {
                return re;
            }
        }
        return re;
    }

    public void loadDataAccount() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, "||");
                while (st.hasMoreTokens()) {
                    Users account = new Users(st.nextToken(), st.nextToken(), Double.parseDouble(st.nextToken()), st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), LocalDate.parse(st.nextToken()));
                    accountMap.put(account.getIdAccount(), account);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserNamePassWD(String text, char[] password) {
        loadDataAccount();
        for (Account account : accountMap.values()) {
            if (account.getNameAccount().equals(text) && account.getPasswdAccount().equals(String.valueOf(password))) {
                if (account.role.equals("user")) {
                    this.token = "00";
                } else {
                    this.token = "000";
                }
                return true;
            }
        }
        return false;
    }

    public String getToken() {
        return token;
    }

    public Object[][] convertMapToMatrixFixed() {
        if (accountMap == null || accountMap.isEmpty()) {
            return new Object[0][0];
        }

        Object[][] matrix = new Object[accountMap.size()][8];
        int row = 0;

        for (Account acc1 : accountMap.values()) {
            Users acc = (Users) acc1;
            matrix[row][0] = acc.idAccount;
            matrix[row][1] = acc.fullName;
            matrix[row][2] = acc.getBalance();
            matrix[row][3] = acc.nameAccount;
            matrix[row][4] = acc.passwdAccount;
            matrix[row][5] = acc.email;
            matrix[row][6] = acc.role;
            matrix[row][7] = acc.createAt;
            row++;
        }
        return matrix;
    }

    public Users getAccount(String name) {
        for (Account account : accountMap.values()) {
            Users acc = (Users) account;
            if (acc.getNameAccount().equals(name)) {
                return acc;
            }
        }
        return null;
    }

    public Users getAccountID(String id) {
        return (Users) accountMap.get(id);
    }

    public Map<String, Account> getAccountMap() {
        return accountMap;
    }

    public void changePassword(String nameAccount, char[] newPassword) {
        boolean isUpdated = false;

        // 1️⃣ Tìm tài khoản trong accountMap và kiểm tra mật khẩu cũ
        for (Account account : accountMap.values()) {
            if (account.getNameAccount().equals(nameAccount)) {
                // 2️⃣ Cập nhật mật khẩu mới trong accountMap
                account.setPasswdAccount(String.valueOf(newPassword));
                isUpdated = true;
                break;
            }
        }
        // Nếu không tìm thấy tài khoản hoặc mật khẩu cũ không đúng
        if (!isUpdated) {
            return;
        }
        // 3️⃣ Đọc lại toàn bộ file và lưu vào List
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|\\|");
                // Kiểm tra nếu dòng này là của tài khoản cần thay đổi mật khẩu
                if (parts.length > 4 && parts[3].equals(nameAccount)) {
                    // Cập nhật mật khẩu mới vào dòng này
                    parts[4] = String.valueOf(newPassword); // Giả sử cột 4 là mật khẩu
                    String updatedLine = String.join("||", parts);
                    lines.add(updatedLine); // Thêm dòng đã cập nhật mật khẩu mới
                } else {
                    lines.add(line); // Giữ lại các dòng không bị thay đổi
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 4️⃣ Ghi đè lại file với danh sách mới
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void withDraw(String id, double amount) {
        if (templateMethodTransaction.withDraw(id, amount)) {
            Users acc = (Users) accountMap.get(id);
            double newBalance = acc.getBankAccount().getAmount();
            updateBalance(id, newBalance);
        }
    }

    public void deposition(String id, double amount) {
        templateMethodTransaction.deposit(id, amount);
        Users acc = (Users) accountMap.get(id);
        double newBalance = acc.getBankAccount().getAmount();
        updateBalance(id, newBalance);
    }

    public void updateBalance(String id, double newBalance) {
        boolean isUpdated = false;

        // 1️⃣ Tìm tài khoản trong accountMap và cập nhật số tiền
        Users acc = (Users) accountMap.get(id);
        acc.setBalance(newBalance);
        isUpdated = true;

        // Nếu không tìm thấy tài khoản thì thoát khỏi hàm
        if (!isUpdated) {
            System.out.println("Không tìm thấy tài khoản.");
            return;
        }

        // 2️⃣ Đọc lại toàn bộ file và lưu vào List
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|\\|");
                // Kiểm tra nếu dòng này là của tài khoản cần thay đổi số tiền
                if (parts.length > 3 && parts[0].equals(id)) {
                    // Cập nhật số tiền mới vào cột 3 (index 2)
                    parts[2] = String.valueOf(newBalance);
                    String updatedLine = String.join("||", parts);
                    lines.add(updatedLine); // Thêm dòng đã cập nhật số tiền mới
                } else {
                    lines.add(line); // Giữ lại các dòng không bị thay đổi
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3️⃣ Ghi đè lại file với danh sách mới
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buyStock(String id, Stock stock) {
        transactionProcess.buyStock(id, stock);
        Users acc = (Users) accountMap.get(id);
        double newBalance = acc.getBankAccount().getAmount();
        updateBalance(id, newBalance);
    }

    public void sellStock(String id, Stock stock) {
        transactionProcess.sellStock(id, stock);
        Users acc = (Users) accountMap.get(id);
        double newBalance = acc.getBankAccount().getAmount();
        updateBalance(id, newBalance);
    }


}
