package view;

import controller.IController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

import static view.PanelLeftPageRoot.toBufferedImage;

public class IBoardPanel extends JPanel implements Runnable {
    private JLabel timeLabel;
    private PanelBankManagement panelBankManagement;
    private PanelNotificationMoMoStyle panelNotificationMoMoStyle;
    private IController iController;

    public IBoardPanel(IController iController) {
        this.iController = iController;
        this.panelNotificationMoMoStyle = new PanelNotificationMoMoStyle();
        this.panelBankManagement = new PanelBankManagement();
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 40));
        setBorder(new EmptyBorder(5, 10, 5, 10));
        // Center Panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        centerPanel.setOpaque(false);

        // Thời gian
        timeLabel = new JLabel();
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        centerPanel.add(timeLabel);

        // Icons
        JButton bellIcon = new JButton();
        ImageIcon originalIcon = new ImageIcon("src/data/img/bell.png");
        BufferedImage bufferedImage = toBufferedImage(originalIcon.getImage());
        applyColorFilter(bufferedImage, Color.WHITE); // Red with transparency
        ImageIcon imageIcon1 = new ImageIcon(bufferedImage);
        Image scaledImage = imageIcon1.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        bellIcon.setIcon(scaledIcon);
        bellIcon.setBackground(new Color(0, 0, 0, 0));
        bellIcon.setForeground(Color.WHITE);
        bellIcon.setFocusable(false);
        bellIcon.setOpaque(false);
        bellIcon.setBorder(null);
        bellIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bellIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                // Tạo JDialog mới để chứa stockMarketSwingUI
                JDialog dialog = new JDialog();
                // Kích thước của dialog
                dialog.setSize(480, 500);
                // Tính toán tọa độ để xuất hiện ở giữa màn hình
                int x = (int) (screenSize.getWidth() / 2 - (double) dialog.getWidth() / 2);
                int y = (int) (screenSize.getHeight() / 2 - (double) dialog.getHeight() / 2);
                dialog.setLocation(x, y);
                // Thêm panel vào JDialog
                dialog.add(panelNotificationMoMoStyle, BorderLayout.CENTER);
                // Kích thước tối đa cho panel (bằng với kích thước của dialog)
                dialog.setLocationRelativeTo(null);
                // Cập nhật layout để nó lấp đầy không gian
                dialog.setBackground(new Color(28, 26, 41));
                //
                dialog.pack();
                // Đặt modal để focus vào Dialog
                dialog.setModal(true);
                // Hiển thị dialog
                dialog.setVisible(true);
            }
        });
        centerPanel.add(bellIcon);
        JLabel flagIcon = new JLabel();
        imageIcon1 = new ImageIcon("src/data/img/world.png");
        scaledImage = imageIcon1.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(scaledImage);
        flagIcon.setIcon(scaledIcon);
        centerPanel.add(flagIcon);

        // Right Panel
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        JButton openAccountButton = new JButton(" ");
        JButton loginButton = new JButton("Đăng nhập");
        ImageIcon originalIcon1 = new ImageIcon("src/data/img/user.png");
        BufferedImage bufferedImage1 = toBufferedImage(originalIcon1.getImage());
        applyColorFilter(bufferedImage1, Color.WHITE); // Red with transparency
        ImageIcon imageIcon2 = new ImageIcon(bufferedImage1);
        Image scaledImage1 = imageIcon2.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        openAccountButton.setIcon(scaledIcon1);
        openAccountButton.setBackground(new Color(0, 0, 0, 0));
        openAccountButton.setForeground(Color.WHITE);
        openAccountButton.setFocusable(false);
        openAccountButton.setOpaque(false);
        openAccountButton.setBorder(null);
        openAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        openAccountButton.setForeground(Color.WHITE);
        openAccountButton.setBackground(Color.RED);
        openAccountButton.setFocusPainted(false);
        openAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                // Tạo JDialog mới để chứa stockMarketSwingUI
                JDialog dialog = new JDialog();
                // Kích thước của dialog
                dialog.setSize(480, 500);
                // Tính toán tọa độ để xuất hiện ở giữa màn hình
                int x = (int) (screenSize.getWidth() / 2 - (double) dialog.getWidth() / 2);
                int y = (int) (screenSize.getHeight() / 2 - (double) dialog.getHeight() / 2);
                dialog.setLocation(x, y);
                // Thêm panel vào JDialog
                dialog.add(panelBankManagement, BorderLayout.CENTER);
                // Kích thước tối đa cho panel (bằng với kích thước của dialog)
                dialog.setLocationRelativeTo(null);
                // Cập nhật layout để nó lấp đầy không gian
                dialog.setBackground(new Color(28, 26, 41));
                //
                dialog.pack();
                // Đặt modal để focus vào Dialog
                dialog.setModal(true);
                // Hiển thị dialog
                dialog.setVisible(true);
            }
        });

        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.RED);
        loginButton.setFocusPainted(false);

        rightPanel.add(centerPanel);
        rightPanel.add(openAccountButton);
        rightPanel.add(loginButton);

        add(rightPanel, BorderLayout.EAST);

        // Khởi động luồng cập nhật thời gian
        Thread timeThread = new Thread(this);
        timeThread.start();
    }

    @Override
    public void run() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        while (true) {
            timeLabel.setText(formatter.format(new Date()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void applyColorFilter(BufferedImage image, Color color) {
        Graphics2D g2d = image.createGraphics();
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.setColor(color);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.dispose();
    }
}

