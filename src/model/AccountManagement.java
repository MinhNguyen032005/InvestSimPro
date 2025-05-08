package model;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.*;

public class AccountManagement {
    private static final String FILE_PATH = "src/data/file_data/account";
    private Map<String, Account> accountMap;
    private static AccountManagement instance;
    private String token;

    public static AccountManagement getInstance() {
        if (instance == null) {
            instance = new AccountManagement();
        }
        return instance;
    }

    public AccountManagement() {
        this.accountMap = new HashMap<>();
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
                System.out.println(token);
                return true;
            }
        }
        return false;
    }

    public void log(String maString) {
        System.out.println("[" + this.hashCode() + "]" + maString);
    }

    public String getToken() {
        return token;
    }

    public String[] getColumnNames() {
        return new String[]{
                "ID", "Họ tên", "Số dư", "Tài khoản", "Mật khẩu", "Email", "Vai trò", "Ngày tạo"
        };
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


}
