package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StockMarketHeaderUI extends JPanel {

    public StockMarketHeaderUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(17, 17, 27));
        setBorder(new EmptyBorder(10, 20, 10, 20));

        // Left Section (Search + Stock Info)

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);

        JTextField searchField = new JTextField("ACB");
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        searchField.setForeground(Color.WHITE);
        searchField.setBackground(new Color(34, 34, 50));
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        leftPanel.add(searchField, BorderLayout.NORTH);


        // Center Section (Price + Details)
        JPanel centerPanel = new JPanel(new GridLayout(2, 3));
        centerPanel.setOpaque(false);

        centerPanel.add(createLabel("24.15", new Color(255, 215, 0), 32));
        centerPanel.add(createLabel("0.00", Color.YELLOW, 14));
        centerPanel.add(createLabel("0.00%", Color.YELLOW, 14));

        centerPanel.add(createLabel("MỞ CỬA/Trung bình: 24.45/24.21", Color.GREEN, 14));
        centerPanel.add(createLabel("THẤP/CAO: 24.10/24.45", new Color(255, 71, 87), 14));
        centerPanel.add(createLabel("TỔNG KL: 5,618,200", Color.WHITE, 14));

        // Right Section (Buttons)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        JButton analysisButton = new JButton("Phân tích cơ bản");
        analysisButton.setBackground(new Color(60, 60, 80));
        analysisButton.setForeground(Color.WHITE);

        JButton orderButton = new JButton("Đặt lệnh");
        orderButton.setBackground(new Color(0, 150, 136));
        orderButton.setForeground(Color.WHITE);
        rightPanel.setOpaque(false);
        JPanel rightPanel1 = new JPanel(new GridLayout(2, 3));
        rightPanel1.setBackground(new Color(17, 17, 27));

        rightPanel1.add(createLabel("Trần", new Color(170, 0, 255), 14));
        rightPanel1.add(createLabel("Sàn", new Color(0, 206, 209), 14));
        rightPanel1.add(createLabel("Tham chiếu", new Color(255, 215, 0), 14));

        rightPanel1.add(createLabel("36.05", new Color(170, 0, 255), 16));
        rightPanel1.add(createLabel("31.35", new Color(0, 206, 209), 16));
        rightPanel1.add(createLabel("33.70", new Color(255, 215, 0), 16));
        rightPanel.add(rightPanel1);
        rightPanel.add(analysisButton);
        rightPanel.add(orderButton);

        // Bottom Tab Section
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        tabPanel.setOpaque(false);

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(tabPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text, Color color, int fontSize) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        return label;
    }

}
