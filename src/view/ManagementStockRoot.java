package view;

import controller.IController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagementStockRoot extends JPanel {
    private IController iController;

    public ManagementStockRoot(StockBoardFull stockBoardFull, IController iController) {
        this.iController = iController;
        this.setLayout(new BorderLayout());
        JPanel jPanel1 = new JPanel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        stockBoardFull.getScrollPane().setPreferredSize(new Dimension(screenSize.width / 2 + (screenSize.width / 3) + 100, screenSize.height / 2 + 150));
        jPanel1.add(stockBoardFull);
        jPanel1.setPreferredSize(new Dimension(100, 400));
        this.add(jPanel1, BorderLayout.CENTER);
        JPanel jPanel = new JPanel(new FlowLayout());
        JButton jButton = new JButton("Thêm");
        JButton jButton1 = new JButton("Xóa");
        jPanel.add(jButton);
        jPanel.add(jButton1);
        this.add(jPanel, BorderLayout.SOUTH);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iController.addStock();
            }
        });
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iController.removeStock();
            }
        });
    }
}
