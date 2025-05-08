package view;

import controller.IController;
import data.FontLoader.FontLoader;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PanelLeftPageRoot extends JPanel {

    JButton btn;

    public PanelLeftPageRoot(IController iController) {
        Font robotoMedium = FontLoader.loadFont("src/data/font/Roboto-Light.ttf");

        ArrayList<String> danhSachChucNang = new ArrayList<>();
        danhSachChucNang.add("Cổ phiếu");
        danhSachChucNang.add("Người dùng");
        danhSachChucNang.add("Thống kê");

        ArrayList<String> danhSachIcon = new ArrayList<>();
        danhSachIcon.add("src/data/img/ListStock.png");
        danhSachIcon.add("src/data/img/ListAccount.png");
        danhSachIcon.add("src/data/img/report.png");
        setBorder(new MatteBorder(0, 0, 0, 1, Color.BLACK));
        // Tạo panel chứa các button
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));

        for (int i = 0; i < danhSachChucNang.size(); i++) {
            ImageIcon originalIcon = new ImageIcon(danhSachIcon.get(i));
            BufferedImage bufferedImage = toBufferedImage(originalIcon.getImage());
            ImageIcon imageIcon1 = new ImageIcon(bufferedImage);
            Image scaledImage = imageIcon1.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            panelButtons.add(Box.createVerticalStrut(40));
            btn = new JButton(danhSachChucNang.get(i));
            btn.setIcon(scaledIcon);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setFont(FontLoader.loadCustomizeFont(robotoMedium, 20));
            btn.setPreferredSize(new Dimension(190, 30));
            btn.setBackground(new Color(0, 0, 0, 0));
            btn.setForeground(Color.WHITE);
            btn.setFocusable(false);
            btn.setOpaque(false);
            btn.setBorder(null);
            btn.setSize(50, 75);
            btn.setIconTextGap(20);
            btn.addActionListener(e->{
                iController.functionAdmin(e.getActionCommand());
            });
            panelButtons.add(btn);
        }
        panelButtons.setBackground(new Color(33, 45, 62));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Sắp xếp dọc
        mainPanel.setBackground(new Color(34, 44, 52)); // Màu nền

        // Phần ảnh đại diện và thông tin tài khoản
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Sắp xếp ngang
        userPanel.setBackground(new Color(33, 45, 62)); // Màu nền

        // Ảnh đại diện
        ImageIcon imageIcon = new ImageIcon("src/data/img/root.png");
        Image scaledImage = imageIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel avatarLabel = new JLabel();
        avatarLabel.setIcon(new ImageIcon(scaledImage));// Đường dẫn đến ảnh
        avatarLabel.setPreferredSize(new Dimension(50, 50));

        // Thông tin tài khoản
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS)); // Sắp xếp dọc
        userInfoPanel.setBackground(new Color(33, 44, 52)); // Màu nền

        JLabel nameLabel = new JLabel("Amin");
        nameLabel.setForeground(Color.WHITE); // Màu chữ
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel roleLabel = new JLabel("Quản trị viên");
        roleLabel.setForeground(new Color(124, 185, 232)); // Màu chữ
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        userInfoPanel.add(nameLabel);
        userInfoPanel.add(roleLabel);

        // Trạng thái
        ImageIcon icon = new ImageIcon("src/data/img/dot.png");
        BufferedImage bufferedImage = toBufferedImage(icon.getImage());
        // Apply color filter (e.g., red tint)
        applyColorFilter(bufferedImage, new Color(0, 255, 0)); // Red with transparency
        ImageIcon imageIcon1 = new ImageIcon(bufferedImage);
        Image scaledImageImage = imageIcon1.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        JLabel statusLabel = new JLabel();
        statusLabel.setIcon(new ImageIcon(scaledImageImage)); // Đường dẫn đến biểu tượng trạng thái
        statusLabel.setPreferredSize(new Dimension(10, 10));

        userPanel.add(avatarLabel);
        userPanel.add(Box.createHorizontalStrut(5)); // Khoảng cách giữa ảnh và thông tin
        userPanel.add(statusLabel);
        userPanel.add(userInfoPanel);

        mainPanel.add(userPanel);

        setLayout(new BorderLayout());
        panelButtons.add(Box.createRigidArea(new Dimension(50, 500)));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        add(mainPanel, BorderLayout.NORTH);
        add(panelButtons, BorderLayout.CENTER);


    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bufferedImage = new BufferedImage(
                img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics g = bufferedImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return bufferedImage;
    }

    public static void applyColorFilter(BufferedImage image, Color color) {
        Graphics2D g2d = image.createGraphics();
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.setColor(color);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.dispose();
    }
}



