package view;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PanelNotificationMoMoStyle extends JPanel {
    public PanelNotificationMoMoStyle() {
        setLayout(new BorderLayout());
        setBackground(new Color(28, 26, 41));

        // Tiêu đề
        JLabel titleLabel = new JLabel("🔔 Thông Báo", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Danh sách thông báo
        JPanel notificationList = new JPanel();
        notificationList.setLayout(new BoxLayout(notificationList, BoxLayout.Y_AXIS));
        notificationList.setBackground(new Color(28, 26, 41));

        // Dữ liệu mẫu
        List<String> notifications = getNotifications();
        for (String message : notifications) {
            notificationList.add(createNotificationCard(message));
        }

        // ScrollPane để cuộn
        JScrollPane scrollPane = new JScrollPane(notificationList);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Tạo các thẻ thông báo
    private JPanel createNotificationCard(String message) {
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

        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Hiển thị ngày giờ
        JLabel dateLabel = new JLabel(getCurrentDateTime());
        dateLabel.setForeground(new Color(180, 180, 180));
        dateLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        card.add(messageLabel, BorderLayout.CENTER);
        card.add(dateLabel, BorderLayout.SOUTH);

        return card;
    }

    // Lấy thời gian hiện tại
    private String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    // Danh sách thông báo mẫu
    private List<String> getNotifications() {
        List<String> list = new ArrayList<>();
        list.add("Nạp 500.000 VNĐ thành công vào tài khoản MoMo.");
        list.add("Bạn vừa chuyển 100.000 VNĐ cho Nguyễn Văn A.");
        list.add("Khuyến mãi 20% khi thanh toán bằng MoMo tại Circle K.");
        list.add("Số dư của bạn đã thay đổi, vui lòng kiểm tra.");
        list.add("Gói bảo hiểm vừa được gia hạn thành công.");
        return list;
    }

    // Main để chạy thử
    public static void main(String[] args) {
        JFrame frame = new JFrame("Thông Báo MoMo Style");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.add(new PanelNotificationMoMoStyle());
        frame.setVisible(true);
    }
}
