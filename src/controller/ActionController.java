package controller;

import model.AccountManagement;
import model.ManagementMarketStock;
import model.MarketStock;
import view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class ActionController implements IController {
    static String role;
    HomePageUser homePageUser;
    PanelLoginWithVideoBackground panelLoginWithVideoBackground;
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


    public ActionController() throws Exception {
        role = "";
        managementMarketStock = new ManagementMarketStock();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        findStock = new FindStock();
        iBoardPanel = new IBoardPanel();
        stockBoardFull = new StockBoardFull(managementMarketStock.mapToArray(), this);
        stockBoardUI = new StockBoardUI(managementMarketStock.generateSummaryData("table"), this);
        stockIndexPanel = new StockIndexPanel(managementMarketStock.generateSummaryData(""), this);
        homePageUser = new HomePageUser(stockBoardFull, stockBoardUI, stockIndexPanel, findStock, iBoardPanel);
        panelBottomPageRoot = new PanelBottomPageRoot(this);
        panelLeftPageRoot = new PanelLeftPageRoot(this);
        panelMidPageRoot = new PanelMidPageRoot(this);
        accountManagement = new AccountManagement();
        signInFrom = new SignInFrom(this);
        signInPanel = new SignInPanel(signInFrom);
        panelLoginWithVideoBackground = new PanelLoginWithVideoBackground(signInPanel, homePageUser);
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
                    break;
                }
                case "00": {
                    homePageUser = new HomePageUser(stockBoardFull, stockBoardUI, stockIndexPanel, findStock, iBoardPanel);
                    cardPanel.add(homePageUser, "user");
                    cardLayout.show(cardPanel, "user");
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
            double maxValue = Math.max(Math.max(Math.max((double) model.getValueAt(row, 17), stock.getLow()), Math.max(stock.getPrice1B(), stock.getPrice1M())),
                    Math.max(Math.max(stock.getPrice2B(), stock.getPrice2M()), Math.max(stock.getPrice3B(), stock.getPrice3M())));
            model.setValueAt(maxValue, row, 17); // "Cao"
            double minValue = Math.min(Math.min(Math.min((double) model.getValueAt(row, 18), stock.getLow()), Math.min(stock.getPrice1B(), stock.getPrice1M())),
                    Math.min(Math.min(stock.getPrice2B(), stock.getPrice2M()), Math.min(stock.getPrice3B(), stock.getPrice3M())));
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
}
