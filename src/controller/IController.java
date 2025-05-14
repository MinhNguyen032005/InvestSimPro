package controller;

import view.PanelBankManagement;
import view.PanelNotificationMoMoStyle;

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
}
