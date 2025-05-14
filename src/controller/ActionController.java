package controller;

import model.*;
import view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ActionController implements IController {
    static String role;
    HomePageUser homePageUser;
    PanelLogin panelLoginWithVideoBackground;
    PanelBottomPageRoot panelBottomPageRoot;
    PanelMidPageRoot panelMidPageRoot;
    PanelLeftPageRoot panelLeftPageRoot;
    HomePageRoot homePageRoot;
    MainFrame mainFrame;
    SignInPanel signInPanel;
    SignInFrom signInFrom;
    AccountManagement accountManagement;
    JPanel cardPanel;
    CardLayout cardLayout;
    TableUserHighlight tableUserHighlight;
    ManagementMarketStock managementMarketStock;
    StockBoardFull stockBoardFull;
    StockBoardUI stockBoardUI;
    StockIndexPanel stockIndexPanel;
    FindStock findStock;
    IBoardPanel iBoardPanel;
    private DefaultTableModel oldTableModel = null;
    private String userName;
    private boolean checkLogin = false;
    private TransactionProcess transactionProcess;


    public ActionController() throws Exception {
        role = "";
        transactionProcess = new TransactionProcess();
        managementMarketStock = new ManagementMarketStock();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        findStock = new FindStock(this);
        stockBoardFull = new StockBoardFull(managementMarketStock.mapToArray(), this);
        stockBoardUI = new StockBoardUI(managementMarketStock.generateSummaryData("table"), this);
        stockIndexPanel = new StockIndexPanel(managementMarketStock.generateSummaryData(""), this);
        panelBottomPageRoot = new PanelBottomPageRoot(this);
        panelLeftPageRoot = new PanelLeftPageRoot(this);
        panelMidPageRoot = new PanelMidPageRoot(this);
        accountManagement = new AccountManagement();
        iBoardPanel = new IBoardPanel(this);
        homePageUser = new HomePageUser(stockBoardFull, stockBoardUI, stockIndexPanel, findStock, iBoardPanel, this);
        signInFrom = new SignInFrom(this);
        signInPanel = new SignInPanel(signInFrom);
        panelLoginWithVideoBackground = new PanelLogin(signInPanel, homePageUser);
        cardPanel.add(panelLoginWithVideoBackground, "login");
        mainFrame = new MainFrame(cardPanel, this);
    }


    public static void main(String[] args) throws Exception {
        new ActionController();
    }


    @Override
    public void login(JTextField account, JPasswordField passwd) {
        if (!accountManagement.checkUserNamePassWD(account.getText(), passwd.getPassword())) {
            UIManager.put("OptionPane.background", Color.WHITE);
            UIManager.put("Panel.background", Color.WHITE);
            UIManager.put("Label.foreground", Color.BLACK);
            UIManager.put("Button.background", new Color(248, 180, 0));
            UIManager.put("Button.foreground", Color.WHITE);
            JOptionPane.showMessageDialog(null, "Tài khoản không đúng");
        } else {
            role = accountManagement.getToken();
            switch (role) {
                case "000": {
                    homePageRoot = new HomePageRoot(panelMidPageRoot, panelLeftPageRoot, panelBottomPageRoot);
                    cardPanel.add(homePageRoot, "root");
                    cardLayout.show(cardPanel, "root");
                    checkLogin = true;
                    break;
                }
                case "00": {
                    homePageUser = new HomePageUser(stockBoardFull, stockBoardUI, stockIndexPanel, findStock, iBoardPanel, this);
                    cardPanel.add(homePageUser, "user");
                    cardLayout.show(cardPanel, "user");
                    userName = account.getText();
                    checkLogin = true;
                    break;
                }
                default: {
                    JOptionPane.showMessageDialog(null, "Tài khoản không đúng");
                }

            }
        }
    }

    @Override
    public void signUp(String name, String nameAccount, char[] password, String email) {
        accountManagement.addAccount(name, nameAccount, String.valueOf(password), email);
    }

    @Override
    public void logout() {
        int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát", "Thông Báo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            cardLayout.show(cardPanel, "login");
        }
    }

    @Override
    public void functionAdmin(String actionCommand) {
        switch (actionCommand) {
            case "Cổ phiếu": {

                break;
            }
            case "Người dùng": {
                tableUserHighlight = new TableUserHighlight();
                tableUserHighlight.setData(accountManagement.convertMapToMatrixFixed());
                homePageRoot = new HomePageRoot(tableUserHighlight, panelLeftPageRoot, panelBottomPageRoot);
                cardPanel.add(homePageRoot, "root");
                cardLayout.show(cardPanel, "root");
                break;
            }
            case "Thống kê": {

                break;
            }
        }
    }

    @Override
    public void setStockData(DefaultTableModel model) {
        ManagementMarketStock.loadDataMarketStock();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String currentTimeKey = LocalDateTime.now().format(formatter);
        List<MarketStock> realTimeStocks = managementMarketStock.getMarketStockMap().get(currentTimeKey);
        int rowCount = Math.min(model.getRowCount(), realTimeStocks.size());
        for (int row = 0; row < rowCount; row++) {
            MarketStock stock = realTimeStocks.get(row);
            model.setValueAt(stock.getPrice3M(), row, 4); // "Giá 3 (M)"
            model.setValueAt(stock.getVolume3M(), row, 5); // "KL 3 (M)"
            model.setValueAt(stock.getPrice2M(), row, 6); // "Giá 2 (M)"
            model.setValueAt(stock.getVolume2M(), row, 7); // "KL 2 (M)"
            model.setValueAt(stock.getPrice1M(), row, 8); // "Giá 1 (M)"
            model.setValueAt(stock.getVolume1M(), row, 9); // "KL 1 (M)"
            model.setValueAt(stock.getPrice1B(), row, 10); // "Giá 1 (B)"
            model.setValueAt(stock.getVolume1B(), row, 11); // "KL 1 (B)"
            model.setValueAt(stock.getPrice2B(), row, 12); // "Giá 2 (B)"
            model.setValueAt(stock.getVolume2B(), row, 13); // "KL 2 (B)"
            model.setValueAt(stock.getPrice3B(), row, 14); // "Giá 3 (B)"
            model.setValueAt(stock.getVolume3B(), row, 15); // "KL 3 (B)"
            model.setValueAt(stock.getTotalVolume(), row, 16); // "Tổng KL"
            double maxValue = Math.max(Math.max(Math.max((double) model.getValueAt(row, 17), stock.getLow()), Math.max(stock.getPrice1B(), stock.getPrice1M())), Math.max(Math.max(stock.getPrice2B(), stock.getPrice2M()), Math.max(stock.getPrice3B(), stock.getPrice3M())));
            model.setValueAt(maxValue, row, 17); // "Cao"
            double minValue = Math.min(Math.min(Math.min((double) model.getValueAt(row, 18), stock.getLow()), Math.min(stock.getPrice1B(), stock.getPrice1M())), Math.min(Math.min(stock.getPrice2B(), stock.getPrice2M()), Math.min(stock.getPrice3B(), stock.getPrice3M())));
            model.setValueAt(minValue, row, 18);
            model.setValueAt(stock.getForeignBuy(), row, 19); // "NN mua"
            model.setValueAt(stock.getForeignSell(), row, 20); // "NN bán"
            model.setValueAt(stock.getRoom(), row, 21); // "Room"
        }
    }

    @Override
    public void setStockExchange(DefaultTableModel model) {
        LocalTime now = LocalTime.now();
        if (!now.isBefore(LocalTime.of(9, 0)) && !now.isAfter(LocalTime.of(15, 0))) {
            Object[][] updatedData = managementMarketStock.generateSummaryData("");
            int modelColCount = model.getColumnCount();
            int dataRowCount = updatedData.length;
            int dataColCount = updatedData[0].length;
            while (model.getRowCount() < dataRowCount) {
                model.addRow(new Object[modelColCount]);
            }
            for (int i = 0; i < dataRowCount; i++) {
                for (int j = 0; j < dataColCount && j < modelColCount; j++) {
                    model.setValueAt(updatedData[i][j], i, j);
                }
            }
        }
    }

    @Override
    public void sendRequest(String text, JLabel jLabel) {
        for (Account account : accountManagement.getAccountMap().values()) {
            if (account.getEmail().equals(text)) {
                jLabel.setText(account.getPasswdAccount());
                return;
            }
        }
        jLabel.setText("Không tìm thấy tài khoản");
    }

    @Override
    public void findStock(String text, JTable table, DefaultTableModel defaultTableModel) {
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentTime = sdfDateTime.format(new Date());
        if (text == null || text.trim().isEmpty()) {
            return;
        }
        // Sử dụng Stream API để lọc cổ phiếu theo ký hiệu và khớp với giờ hiện tại
        Set<MarketStock> list = managementMarketStock.getMarketStockMap().entrySet().stream().filter(entry -> entry.getKey().startsWith(currentTime.substring(0, 16))) // So khớp đến phút
                .flatMap(entry -> entry.getValue().stream()).filter(stock -> stock.getSymbol().toLowerCase().contains(text.toLowerCase())).collect(Collectors.toSet());

        // Cập nhật lại bảng
        updateTable(defaultTableModel, list, "find");
    }

    private void updateTable(DefaultTableModel defaultTableModel, Set<MarketStock> list, String type) {
        // Nếu bản sao cũ chưa có, tiến hành lưu trữ
        if (oldTableModel == null) {
            oldTableModel = new DefaultTableModel();
            for (int col = 0; col < defaultTableModel.getColumnCount(); col++) {
                oldTableModel.addColumn(defaultTableModel.getColumnName(col));
            }
            for (int row = 0; row < defaultTableModel.getRowCount(); row++) {
                oldTableModel.addRow(getRowData(defaultTableModel, row));
            }
        }

        if ("find".equals(type)) {
            defaultTableModel.setRowCount(0); // Xóa toàn bộ dữ liệu cũ
            list.forEach(stock -> defaultTableModel.addRow(new Object[]{stock.getSymbol(), stock.getFloorPrice(), stock.getReferencePrice(), stock.getCeilingPrice(), stock.getPrice3M(), stock.getVolume3M(), stock.getPrice2M(), stock.getVolume2M(), stock.getPrice1M(), stock.getVolume1M(), stock.getPrice1B(), stock.getVolume1B(), stock.getPrice2B(), stock.getVolume2B(), stock.getPrice3B(), stock.getVolume3B(), stock.getTotalVolume(), stock.getHigh(), stock.getLow(), stock.getForeignBuy(), stock.getForeignSell(), stock.getRoom()}));
        } else {
            // Nếu type khác, khôi phục lại dữ liệu cũ
            defaultTableModel.setRowCount(0); // Xóa toàn bộ
            for (int row = 0; row < oldTableModel.getRowCount(); row++) {
                defaultTableModel.addRow(getRowData(oldTableModel, row));
            }
        }
    }

    // Hàm tiện ích để lấy dữ liệu của một hàng
    private Object[] getRowData(DefaultTableModel model, int rowIndex) {
        Object[] rowData = new Object[model.getColumnCount()];
        for (int col = 0; col < model.getColumnCount(); col++) {
            rowData[col] = model.getValueAt(rowIndex, col);
        }
        return rowData;
    }


    @Override
    public void newTable(String text, DefaultTableModel defaultTableModel) {
        if (findStock.getjTextField().getText().equals("")) {
            updateTable(defaultTableModel, null, "new table");
            findStock.getjButton1().setEnabled(true);
        }
    }

    @Override
    public void bankUser(PanelBankManagement panelBankManagement) {
        Users acc = accountManagement.getAccount(userName);
        if (acc.getNameAccount().equals(userName)) {
            panelBankManagement.getTabbedPane().setBackground(new Color(28, 26, 41));
            panelBankManagement.getTabbedPane().setForeground(Color.WHITE);
            // ======= Panel Bank =======
            panelBankManagement.getBankPanel().setBackground(new Color(33, 33, 55));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridx = 0;
            gbc.gridy = 0;
            JLabel lblBalance = new JLabel("Số dư hiện tại:");
            lblBalance.setForeground(new Color(255, 255, 255));
            panelBankManagement.getBankPanel().add(lblBalance, gbc);
            panelBankManagement.getTxtBalance().setText(String.valueOf(acc.getBalance()));
            panelBankManagement.getTxtBalance().setEditable(false);
            gbc.gridx = 1;
            panelBankManagement.getBankPanel().add(panelBankManagement.getTxtBalance(), gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            JLabel lblDeposit = new JLabel("Nạp tiền:");
            lblDeposit.setForeground(new Color(255, 255, 255));
            panelBankManagement.getBankPanel().add(lblDeposit, gbc);

            gbc.gridx = 1;
            panelBankManagement.getBankPanel().add(panelBankManagement.getTxtDepositAmount(), gbc);

            panelBankManagement.getBtnDeposit().setBackground(new Color(26, 166, 124));
            panelBankManagement.getBtnDeposit().setForeground(Color.WHITE);
            panelBankManagement.getBtnDeposit().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    accountManagement.deposition(acc.getIdAccount(), Double.parseDouble(panelBankManagement.getTxtDepositAmount().getText()));
                    JOptionPane.showMessageDialog(panelBankManagement,
                            "Bạn đã nạp:" + panelBankManagement.getTxtDepositAmount().getText(),
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    panelBankManagement.getTxtBalance().setText(String.valueOf(acc.getBankAccount().getAmount()));
                    transactionProcess.addTransaction(acc.getIdAccount(), Double.parseDouble(panelBankManagement.getTxtDepositAmount().getText()), "deposition");
                    panelBankManagement.getTxtDepositAmount().setText("");
                }
            });
            gbc.gridx = 2;
            panelBankManagement.getBankPanel().add(panelBankManagement.getBtnDeposit(), gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            JLabel lblWithdraw = new JLabel("Rút tiền:");
            lblWithdraw.setForeground(new Color(255, 255, 255));
            panelBankManagement.getBankPanel().add(lblWithdraw, gbc);

            gbc.gridx = 1;
            panelBankManagement.getBankPanel().add(panelBankManagement.getTxtWithdrawAmount(), gbc);

            panelBankManagement.getBtnWithdraw().setBackground(new Color(239, 83, 80));
            panelBankManagement.getBtnWithdraw().setForeground(Color.WHITE);
            panelBankManagement.getBtnWithdraw().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Double.parseDouble(panelBankManagement.getTxtWithdrawAmount().getText()) > Double.parseDouble(panelBankManagement.getTxtBalance().getText())) {
                        JOptionPane.showMessageDialog(panelBankManagement,
                                "Vượt quá số tiền rút!",
                                "Thông báo",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        accountManagement.withDraw(acc.getIdAccount(), Double.parseDouble(panelBankManagement.getTxtWithdrawAmount().getText()));
                        JOptionPane.showMessageDialog(panelBankManagement,
                                "Bạn đã rút:" + panelBankManagement.getTxtWithdrawAmount().getText(),
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);

                        panelBankManagement.getTxtBalance().setText(String.valueOf(acc.getBalance()));
                        transactionProcess.addTransaction(acc.getIdAccount(), Double.parseDouble(panelBankManagement.getTxtWithdrawAmount().getText()), "with draw");
                        panelBankManagement.getTxtWithdrawAmount().setText("");
                    }
                }
            });
            gbc.gridx = 2;
            panelBankManagement.getBankPanel().add(panelBankManagement.getBtnWithdraw(), gbc);

            panelBankManagement.getTabbedPane().addTab("Ngân hàng", panelBankManagement.getBankPanel());

            // ======= Panel Stock =======
            panelBankManagement.getStockPanel().setBackground(new Color(33, 33, 55));
            panelBankManagement.getStockTable().setBackground(new Color(28, 26, 41));
            panelBankManagement.getStockTable().setForeground(Color.WHITE);
            JScrollPane scrollPane = new JScrollPane(panelBankManagement.getStockTable());
            scrollPane.getViewport().setBackground(new Color(28, 26, 41));
            panelBankManagement.getStockPanel().add(scrollPane, BorderLayout.CENTER);
            panelBankManagement.getTabbedPane().addTab("Danh sách cổ phiếu", panelBankManagement.getStockPanel());

            // ======= Panel Account Details =======
            JTextField txtName, txtEmail, txtUsername;
            JPasswordField txtPassword;
            JLabel lblCreatedDate;
            JButton btnUpdate, btnChangePassword, btnDelete;
            JPanel panel = new JPanel(new GridLayout(9, 2, 0, 0));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            panel.add(new JLabel("Họ tên:"));
            txtName = new JTextField(acc.getFullName());
            txtName.setEnabled(false);
            panel.add(txtName);

            panel.add(new JLabel("Email:"));
            txtEmail = new JTextField(acc.getEmail());
            txtEmail.setEnabled(false);
            panel.add(txtEmail);

            panel.add(new JLabel("Tên đăng nhập:"));
            txtUsername = new JTextField(acc.getNameAccount());
            txtUsername.setEnabled(false);
            panel.add(txtUsername);

            panel.add(new JLabel("Mật khẩu:"));
            txtPassword = new JPasswordField(acc.getPasswdAccount());
            txtPassword.setEnabled(false);
            panel.add(txtPassword);

            panel.add(new JLabel("Ngày tạo:"));
            lblCreatedDate = new JLabel(String.valueOf(acc.getCreateAt()));
            panel.add(lblCreatedDate);

            JPanel buttonPanel = new JPanel();
            btnUpdate = new JButton("Cập nhật");
            btnChangePassword = new JButton("Đổi mật khẩu");
            btnDelete = new JButton("Xóa tài khoản");

            buttonPanel.add(btnUpdate);
            buttonPanel.add(btnChangePassword);
            buttonPanel.add(btnDelete);
            panel.add(buttonPanel);
            btnUpdate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    accountManagement.changePassword(acc.getNameAccount(), txtPassword.getPassword());
                    txtPassword.setEnabled(false);
                }
            });

            btnChangePassword.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    txtPassword.setEnabled(true);
                }
            });

            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa tài khoản?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, "Tài khoản đã bị xóa!");
                        iBoardPanel.getDialog().setVisible(false);
                        panelLoginWithVideoBackground = new PanelLogin(signInPanel, homePageUser);
                        cardPanel.add(panelLoginWithVideoBackground, "login1");
                        cardLayout.show(cardPanel, "login1");
                        accountManagement.deleteAccount(acc.getNameAccount());
                    }
                }
            });

            panelBankManagement.getAccountPanel().setBackground(new Color(33, 33, 55));
            panelBankManagement.getAccountPanel().add(panel, BorderLayout.CENTER);
            panelBankManagement.getTabbedPane().addTab("Chi tiết tài khoản", panelBankManagement.getAccountPanel());
            panelBankManagement.setBackground(new Color(28, 26, 41));
            panelBankManagement.add(panelBankManagement.getTabbedPane());
            panelBankManagement.repaint();
        }
    }

    @Override
    public boolean checkLogin() {
        return checkLogin;
    }

    @Override
    public boolean checkContain(String text) {
        return accountManagement.getAccount(text) != null;
    }

    @Override
    public void notifyTransaction(PanelNotificationMoMoStyle panelNotificationMoMoStyle) {
        Users acc = accountManagement.getAccount(userName);
        if (acc.getNameAccount().equals(userName)) {
            JLabel titleLabel = new JLabel("Thông Báo", JLabel.CENTER);
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            panelNotificationMoMoStyle.add(titleLabel, BorderLayout.NORTH);

            // Danh sách thông báo
            JPanel notificationList = new JPanel();
            notificationList.setLayout(new BoxLayout(notificationList, BoxLayout.Y_AXIS));
            notificationList.setBackground(new Color(28, 26, 41));

            // Lấy dữ liệu giao dịch của user
            List<Transaction> transactions = transactionProcess.getTransactionsByUserId(acc.getIdAccount());
            if (transactions.isEmpty()) {
                // Hiển thị thông báo "Không có giao dịch"
                JPanel jPanel = new JPanel();
                jPanel.setLayout(new BorderLayout());
                jPanel.setPreferredSize(new Dimension(600, 50));
                jPanel.setBackground(new Color(28, 26, 41));

                JLabel jLabel = new JLabel("Không có giao dịch", JLabel.CENTER);
                jLabel.setForeground(Color.WHITE);
                jLabel.setFont(new Font("Arial", Font.ITALIC, 14));

                jPanel.add(jLabel, BorderLayout.CENTER);
                notificationList.add(jPanel);
            } else {
                // Sắp xếp giao dịch theo thời gian giảm dần
                transactions.sort(Comparator.comparing(Transaction::getTransactionDate).reversed());
                for (Transaction transaction : transactions) {
                    notificationList.add(panelNotificationMoMoStyle.createNotificationCard(transaction));
                }
            }

            // ScrollPane để cuộn
            JScrollPane scrollPane = new JScrollPane(notificationList);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            panelNotificationMoMoStyle.add(scrollPane, BorderLayout.CENTER);
        }
    }
}
