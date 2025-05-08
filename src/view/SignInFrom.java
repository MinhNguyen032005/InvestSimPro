package view;

import controller.IController;
import data.FontLoader.FontLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SignInFrom extends JPanel {

    private JPanel infoPanel;
    private JTextField account;
    private JPasswordField passwd;
    private JButton button;

    public SignInFrom(IController iController) {
        Font robotoMedium = FontLoader.loadFont("src/data/font/Roboto-Light.ttf");
        this.setLayout(new BorderLayout());
        this.infoPanel = new JPanel(new BorderLayout());
        this.account = new JTextField(20);
        this.passwd = new JPasswordField(20);
        this.button = new JButton("Đăng nhập");
        this.button.setBackground(new Color(248, 180, 0));

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
        loginLabel.setForeground(new Color(248, 180, 0)); // Màu vàng gold nổi bật
        loginLabel.setBounds(70, 20, 200, 40);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Roboto", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBounds(50, 180, 100, 100);
        button.setBorder(BorderFactory.createLineBorder(new Color(218, 165, 32), 2));
//        button.addActionListener(iController.login(account, passwd, iController));

        JButton forgotPasswordButton = new JButton("Quên mật khẩu");
        forgotPasswordButton.setFont(new Font("Roboto", Font.PLAIN, 14).deriveFont(Font.PLAIN));
        forgotPasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPasswordButton.setForeground(Color.GRAY);
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
        signUpButton.setForeground(Color.GRAY);
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
        account.setMaximumSize(new Dimension(400, 40));
        passwd.setMaximumSize(new Dimension(400, 40));
        button.setMaximumSize(new Dimension(400, 1000));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS)); // Thiết lập layout cho leftPanel
//        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
//panel cua khung tai khoan
        JPanel accountPanel = new JPanel(new BorderLayout());
        JLabel accountLabel = new JLabel("Tài khoản:");
        accountLabel.setFont(FontLoader.loadCustomizeFont(robotoMedium, 16f));
        accountLabel.setForeground(Color.BLACK);

        account = new JTextField(20);
        account.setFont(FontLoader.loadCustomizeFont(robotoMedium, 16f));
        account.setBackground(Color.BLACK);
        account.setForeground(Color.WHITE);
        account.setOpaque(true);
        account.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2), BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        accountPanel.add(accountLabel, BorderLayout.WEST);
        accountPanel.add(account, BorderLayout.CENTER);

// panel cua khung mat khau
        JPanel passwdPanel = new JPanel(new BorderLayout());
        JLabel passwdLabel = new JLabel("Mật khẩu:");
        passwdLabel.setFont(FontLoader.loadCustomizeFont(robotoMedium, 16f));
        passwdLabel.setForeground(Color.BLACK);

        passwd.setFont(FontLoader.loadCustomizeFont(robotoMedium, 16f));
        passwd.setBackground(Color.BLACK);
        passwd.setForeground(Color.WHITE);
        passwd.setOpaque(true);
        passwd.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2), BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        passwdPanel.add(passwdLabel, BorderLayout.WEST);
        passwdPanel.add(passwd, BorderLayout.CENTER);

        rightPanel.add(leftPanel);
        rightPanel.add(loginLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        rightPanel.add(accountPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(passwdPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        rightPanel.add(button);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(buttonPanel);
        account.addActionListener(e -> button.doClick());
        passwd.addActionListener(e -> button.doClick());
        rightPanel.setPreferredSize(new Dimension(500, 400));
        infoPanel.add(rightPanel, BorderLayout.CENTER);
        rightPanel.setBackground(new Color(10, 25, 47, 120));
        add(this.infoPanel, BorderLayout.CENTER);
        setBackground(new Color(0, 0, 0, 100));
        setBorder(BorderFactory.createLineBorder(new Color(248, 180, 0), 2));


        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(211, 47, 47)); // Đỏ đô khi hover
                button.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(248, 180, 0)); // Quay lại màu vàng gold
                button.setForeground(Color.BLACK);
            }
        });
        signUpButton.addActionListener(e -> {
            rightPanel.add(new FromSignUp(iController));
        });

        button.addActionListener(e -> {
            iController.login(account,passwd);
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

