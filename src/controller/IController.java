package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

public interface IController {
    void login(JTextField account, JPasswordField passwd);

    void signUp(String name, String nameAccount, char[] password, String email);

    void logout();

    void functionAdmin(String actionCommand);

    void setStockData(DefaultTableModel model);

    void setStockExchange(DefaultTableModel model);
}
