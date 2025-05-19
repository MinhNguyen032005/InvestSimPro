package controller;

import view.PanelBankManagement;
import view.PanelNotificationMoMoStyle;
import view.StockChart;
import view.StockMarketHeaderUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public interface IController {
    void login(JTextField account, JPasswordField passwd);

    void signUp(String name, String nameAccount, char[] password, String email);

    void logout();

    void functionAdmin(String actionCommand);

    void setStockData(DefaultTableModel model);

    void setStockExchange(DefaultTableModel model);

    void sendRequest(String text, JLabel jLabel);

    void findStock(String text, JTable table, DefaultTableModel defaultTableModel);

    void newTable(String text, DefaultTableModel defaultTableModel);

    void bankUser(PanelBankManagement panelBankManagement);

    boolean checkLogin();

    boolean checkContain(String text);

    void notifyTransaction(PanelNotificationMoMoStyle panelNotificationMoMoStyle);

    void headerMarketStock(StockMarketHeaderUI stockMarketHeaderUI, Object[] objects);

    void getStockChartExample(StockChart stockChartExample);

    String setSucMua();

    void actionTransactionStock(String actionCommand, String text, String text1);

    void saveTransactionStock(String currentTime, Object object, String thaoTacValue, String s, String s1, String s2, String trangThai);
}
