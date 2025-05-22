package view;

import controller.IController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class PanelBankManagement extends JPanel {
    private JTabbedPane tabbedPane;
    private JPanel bankPanel, stockPanel, accountPanel;
    private JButton btnDeposit, btnWithdraw;
    private JTextField txtBalance, txtDepositAmount, txtWithdrawAmount;
    private JTable stockTable;
    private DefaultTableModel stockModel;
    private IController iController;

    public PanelBankManagement(IController iController) {
        this.iController = iController;
        tabbedPane = new JTabbedPane();
        bankPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        txtBalance = new JTextField("", 15);
        JLabel lblDeposit = new JLabel("Nạp tiền:");
        txtDepositAmount = new JTextField(15);
        btnDeposit = new JButton("Nạp tiền");
        JLabel lblWithdraw = new JLabel("Rút tiền:");
        txtWithdrawAmount = new JTextField(15);
        btnWithdraw = new JButton("Rút tiền");
        stockPanel = new JPanel(new BorderLayout());
        String[] columns = {"Mã cổ phiếu", "Số lượng", "Giá trị","Tổng tiền","Ngày"};
        stockModel = new DefaultTableModel(columns, 0);
        stockTable = new JTable(stockModel);
        accountPanel = new JPanel(new BorderLayout());
        JLabel accountLabel = new JLabel("Thông tin tài khoản của bạn sẽ hiển thị tại đây", JLabel.CENTER);
        if (iController.checkLogin()) {
            iController.bankUser(this);
        }
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JPanel getBankPanel() {
        return bankPanel;
    }

    public JPanel getStockPanel() {
        return stockPanel;
    }

    public JPanel getAccountPanel() {
        return accountPanel;
    }

    public JButton getBtnDeposit() {
        return btnDeposit;
    }

    public JButton getBtnWithdraw() {
        return btnWithdraw;
    }

    public JTextField getTxtBalance() {
        return txtBalance;
    }

    public JTextField getTxtDepositAmount() {
        return txtDepositAmount;
    }

    public JTextField getTxtWithdrawAmount() {
        return txtWithdrawAmount;
    }

    public JTable getStockTable() {
        return stockTable;
    }

    public DefaultTableModel getStockModel() {
        return stockModel;
    }
}