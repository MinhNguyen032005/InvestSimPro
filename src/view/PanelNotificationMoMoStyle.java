package view;

import controller.IController;
import model.Transaction;
import model.TransactionProcess;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class PanelNotificationMoMoStyle extends JPanel {
    private IController iController;

    public PanelNotificationMoMoStyle(IController iController) {
        this.iController = iController;

        setLayout(new BorderLayout());
        setBackground(new Color(28, 26, 41));

        if (iController.checkLogin()) iController.notifyTransaction(this);
    }

    // Tạo các thẻ thông báo
    public JPanel createNotificationCard(Transaction transaction) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(600, 80));
        card.setMaximumSize(new Dimension(600, 80));
        card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        card.setBackground(new Color(33, 33, 55));

        // Thêm hiệu ứng hover
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(45, 45, 68));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(33, 33, 55));
            }
        });

        // Định dạng số tiền
        DecimalFormat formatter = new DecimalFormat("#,### VNĐ");
        String formattedAmount = formatter.format(transaction.getAmount());

        // Nội dung thông báo
        String message = transaction.getType().equalsIgnoreCase("deposition") ?
                "Nạp " + formattedAmount + " vào tài khoản." :
                "Rút " + formattedAmount + " khỏi tài khoản.";

        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Hiển thị ngày giờ giao dịch
        JLabel dateLabel = new JLabel(String.valueOf(transaction.getTransactionDate().getYear()) + "-" + String.valueOf(transaction.getTransactionDate().getMonthValue()) + "-" + String.valueOf(transaction.getTransactionDate().getDayOfMonth()) + "_" + String.valueOf(transaction.getTransactionDate().getHour()) + ":" + String.valueOf(transaction.getTransactionDate().getMinute()));
        dateLabel.setForeground(new Color(180, 180, 180));
        dateLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        card.add(messageLabel, BorderLayout.CENTER);
        card.add(dateLabel, BorderLayout.SOUTH);

        return card;
    }
}
