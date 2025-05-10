package view;

import controller.IController;
import data.FontLoader.FontLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;

public class SignInFrom extends JPanel {

    private JPanel infoPanel;
    private JTextField account;
    private JPasswordField passwd;
    private JButton button;

    public SignInFrom(IController iController) {
        this.setPreferredSize(new Dimension(1000,1000));
        Font robotoMedium = FontLoader.loadFont("src/data/font/Roboto-Light.ttf");
        this.setLayout(new BorderLayout());
        this.infoPanel = new JPanel(new BorderLayout());
        this.account = new JTextField(20);
        this.passwd = new JPasswordField(20);
        this.button = new JButton("Đăng nhập");
        this.button.setFont(new Font("Roboto", Font.BOLD, 16));   // Font chữ đẹp
        this.button.setBackground(Color.red);       // Màu xanh chủ đạo
        this.button.setForeground(Color.WHITE);                   // Chữ màu trắng
        this.button.setFocusPainted(false);                       // Bỏ khung focus
        this.button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));  // Khoảng cách padding
        this.button.setCursor(new Cursor(Cursor.HAND_CURSOR));    // Con trỏ hình bàn tay


        // Left Panel (Image)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(400, 420));

        // Right Panel (Login Form)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel loginLabel = new JLabel("Đăng Nhập");
        loginLabel.setFont(new Font("Roboto", Font.BOLD, 28));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setForeground(new Color(0, 0, 0)); // Màu vàng gold nổi bật
        loginLabel.setBounds(70, 20, 200, 40);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Roboto", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBounds(50, 180, 100, 100);

        JButton forgotPasswordButton = new JButton("Quên mật khẩu");
        forgotPasswordButton.setFont(new Font("Roboto", Font.PLAIN, 14).deriveFont(Font.PLAIN));
        forgotPasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPasswordButton.setForeground(new Color(190, 0, 0));
        forgotPasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordButton.setHorizontalAlignment(SwingConstants.LEFT);
        forgotPasswordButton.setFont(FontLoader.loadCustomizeFont(robotoMedium, 15f));
        forgotPasswordButton.setPreferredSize(new Dimension(190, 30));
        forgotPasswordButton.setBackground(new Color(0, 0, 0, 0));
        forgotPasswordButton.setFocusable(false);
        forgotPasswordButton.setOpaque(false);
        forgotPasswordButton.setBorder(null);

        JButton signUpButton = new JButton("Đăng Ký");
        signUpButton.setFont(new Font("Roboto", Font.PLAIN, 14).deriveFont(Font.PLAIN));
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.setForeground(new Color(190, 0, 0));
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpButton.setHorizontalAlignment(SwingConstants.LEFT);
        signUpButton.setFont(FontLoader.loadCustomizeFont(robotoMedium, 15f));
        signUpButton.setPreferredSize(new Dimension(190, 30));
        signUpButton.setBackground(new Color(0, 0, 0, 0));
        signUpButton.setFocusable(false);
        signUpButton.setOpaque(false);
        signUpButton.setBorder(null);


        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Làm trong suốt

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        buttonPanel.add(signUpButton, gbc);
        gbc.gridx = 1;
        buttonPanel.add(forgotPasswordButton, gbc);
//        forgotPasswordButton.addActionListener(iController.forgotPasswd());
        account.setMaximumSize(new Dimension(400, 20));
        passwd.setMaximumSize(new Dimension(400, 20));
        button.setMaximumSize(new Dimension(390, 1000));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS)); // Thiết lập layout cho leftPanel
//        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
//panel cua khung tai khoan
        JPanel accountPanel = new JPanel(new BorderLayout());
        accountPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(), // Không viền
                "Tài khoản", // Tiêu đề của Panel
                0, 0, // Vị trí tiêu đề: 0 = LEFT, 0 = TOP
                new Font("Arial", Font.BOLD, 14),
                new Color(0, 0, 0) // Màu tiêu đề
        ));
//        accountPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        accountPanel.setBackground(Color.WHITE);

        // Tạo JTextField với thiết kế đẹp mắt
        account = new JTextField(20);
        account.setFont(new Font("Arial", Font.PLAIN, 16));
        account.setBackground(new Color(240, 240, 240));
        account.setForeground(Color.BLACK);
        account.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        account.setPreferredSize(new Dimension(300, 40));

        // Hiệu ứng khi focus vào JTextField
        account.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                account.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(66, 135, 245), 2),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                account.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });

        accountPanel.add(account, BorderLayout.CENTER);

// panel cua khung mat khau
        JPanel passwdPanel = new JPanel(new BorderLayout());
        passwdPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(), // Không viền
                "Mật khẩu", // Tiêu đề của Panel
                0, 0, // Vị trí tiêu đề: 0 = LEFT, 0 = TOP
                new Font("Arial", Font.BOLD, 14),
                new Color(0, 0, 0) // Màu tiêu đề
        ));
        passwdPanel.setBackground(Color.WHITE);

        // Tạo PasswordField với thiết kế đẹp mắt
        passwd = new JPasswordField();
        passwd.setFont(new Font("Arial", Font.PLAIN, 16));
        passwd.setBackground(new Color(240, 240, 240));
        passwd.setForeground(Color.BLACK);
        passwd.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        passwd.setEchoChar('●');
        passwd.setPreferredSize(new Dimension(300, 40));

        // Hiệu ứng khi focus vào PasswordField
        passwd.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passwd.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(66, 135, 245), 2),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                passwd.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });

        passwdPanel.add(passwd, BorderLayout.CENTER);
        rightPanel.add(leftPanel);
        rightPanel.add(loginLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 7)));
        rightPanel.add(accountPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(passwdPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(button);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(buttonPanel);
        account.addActionListener(e -> button.doClick());
        passwd.addActionListener(e -> button.doClick());
        rightPanel.setPreferredSize(new Dimension(500, 400));
        infoPanel.add(rightPanel, BorderLayout.CENTER);
        rightPanel.setBackground(new Color(255, 255, 255));
        add(this.infoPanel, BorderLayout.CENTER);
//        setBackground(new Color(255, 255, 255, 100));
        setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 2));


        signUpButton.addActionListener(e -> {
            rightPanel.add(new FromSignUp(iController));
        });

        button.addActionListener(e -> {
            iController.login(account, passwd);
        });


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ hiệu ứng kính mờ
        g2d.setColor(new Color(0, 0, 0, 100)); // Đen mờ
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    }

}

