package view;

import data.FontLoader.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FindStock extends JPanel {
    private JTextField jTextField;
    private JButton jButton;

    public FindStock() {
        setBackground(new Color(41, 39, 55));
        Font robotoMedium = FontLoader.loadFont("src/data/font/Roboto-Light.ttf");
        List<String> strings = new ArrayList<>();
        strings.add("Đặt lệnh");
        setLayout(new BorderLayout());
        JPanel jPanel = new JPanel();
        JButton jButton1 = new JButton("");
        JTextField jTextField = new JTextField("Tìm kiếm CK", 15);
        jTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        jTextField.setBackground(new Color(28, 26, 41)); // Màu nền sáng nhẹ
        jTextField.setForeground(Color.gray);    // Màu chữ tối (xám đậm)
        jTextField.setCaretColor(Color.WHITE);              // Màu con trỏ
        jTextField.setBorder(BorderFactory.createLineBorder(new Color(63, 65, 96))); // Padding bên trong
        jTextField.addActionListener(e -> {
            jButton1.doClick();
        });
        jButton1.setFocusable(false);
        jButton1.setOpaque(false);
        jButton1.setBorder(null);
        jPanel.setLayout(new FlowLayout());
        jPanel.setBackground(new Color(41, 39, 55));
        jPanel.add(jTextField);
        jPanel.add(jButton1);
        this.add(jPanel, BorderLayout.WEST);
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel1.setBackground(new Color(41, 39, 55));
        for (int i = 0; i < strings.size(); i++) {
            jButton = new JButton(strings.get(i));
            jButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            jButton.setHorizontalAlignment(SwingConstants.CENTER);
            jButton.setFont(FontLoader.loadCustomizeFont(robotoMedium, 15));
            jButton.setPreferredSize(new Dimension(90, 50));
            jButton.setBackground(new Color(26, 166, 124));
            jButton.setForeground(Color.WHITE);
            jButton.setBorder(BorderFactory.createLineBorder(new Color(26, 166, 124), 2, true));
            jPanel1.add(jButton);
        }
        this.add(jPanel1, BorderLayout.EAST);
    }
}
