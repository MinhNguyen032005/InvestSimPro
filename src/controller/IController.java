package controller;

import model.HistoryTransactionStock;
import model.Users;
import view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Collection;

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

    void getStockChartExample(Object[] stockChartExample, StockChart stockChart);

    String setSucMua();

    void actionTransactionStock(String actionCommand, String text, String text1, String string);

    void saveTransactionStock(String currentTime, Object object, String thaoTacValue, String s, String s1, String s2, String muaBan, String trangThai);


    void updateTable(DefaultTableModel historyModel, String historyColumn);

    Object[] getStock(Object[] stockChartExample);

    boolean checkBan(String muaBan, String text, String text1, String string);

    void cancelTransactionStock(String maCK, String orderType);

    HistoryTransactionStock fixTransaction(int editingRow);

    void removeTrasaction(int selectedRow);

    void updateTransaction(int editingRow, HistoryTransactionStock updated);

    Collection<? extends Users> getUser();

    Object getLuotChoi(String nameAccount);

    void addStock();

    void removeStock();
}
