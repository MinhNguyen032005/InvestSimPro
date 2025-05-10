package view;

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

    public PanelBankManagement() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(28, 26, 41));
        tabbedPane.setForeground(Color.WHITE);
        // ======= Panel Bank =======
        bankPanel = new JPanel(new GridBagLayout());
        bankPanel.setBackground(new Color(33, 33, 55));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblBalance = new JLabel("Số dư hiện tại:");
        lblBalance.setForeground(new Color(255, 255, 255));
        bankPanel.add(lblBalance, gbc);

        txtBalance = new JTextField("1000000", 15);
        txtBalance.setEditable(false);
        gbc.gridx = 1;
        bankPanel.add(txtBalance, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblDeposit = new JLabel("Nạp tiền:");
        lblDeposit.setForeground(new Color(255, 255, 255));
        bankPanel.add(lblDeposit, gbc);

        txtDepositAmount = new JTextField(15);
        gbc.gridx = 1;
        bankPanel.add(txtDepositAmount, gbc);

        btnDeposit = new JButton("Nạp tiền");
        btnDeposit.setBackground(new Color(26, 166, 124));
        btnDeposit.setForeground(Color.WHITE);
        gbc.gridx = 2;
        bankPanel.add(btnDeposit, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblWithdraw = new JLabel("Rút tiền:");
        lblWithdraw.setForeground(new Color(255, 255, 255));
        bankPanel.add(lblWithdraw, gbc);

        txtWithdrawAmount = new JTextField(15);
        gbc.gridx = 1;
        bankPanel.add(txtWithdrawAmount, gbc);

        btnWithdraw = new JButton("Rút tiền");
        btnWithdraw.setBackground(new Color(239, 83, 80));
        btnWithdraw.setForeground(Color.WHITE);
        gbc.gridx = 2;
        bankPanel.add(btnWithdraw, gbc);

        tabbedPane.addTab("💰 Ngân hàng", bankPanel);

        // ======= Panel Stock =======
        stockPanel = new JPanel(new BorderLayout());
        stockPanel.setBackground(new Color(33, 33, 55));
        String[] columns = {"Mã cổ phiếu", "Số lượng", "Giá trị"};
        stockModel = new DefaultTableModel(columns, 0);
        stockTable = new JTable(stockModel);
        stockTable.setBackground(new Color(28, 26, 41));
        stockTable.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(stockTable);
        scrollPane.getViewport().setBackground(new Color(28, 26, 41));
        stockPanel.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("📈 Danh sách cổ phiếu", stockPanel);

        // ======= Panel Account Details =======
        accountPanel = new JPanel(new BorderLayout());
        accountPanel.setBackground(new Color(33, 33, 55));
        JLabel accountLabel = new JLabel("Thông tin tài khoản của bạn sẽ hiển thị tại đây", JLabel.CENTER);
        accountLabel.setForeground(new Color(255, 255, 255));
        accountPanel.add(accountLabel, BorderLayout.CENTER);
        tabbedPane.addTab("🧾 Chi tiết tài khoản", accountPanel);
        setBackground(new Color(28, 26, 41));
        add(tabbedPane);
    }
}