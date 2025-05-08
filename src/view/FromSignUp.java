package view;

import controller.IController;

import javax.swing.*;
import java.awt.*;

public class FromSignUp extends JOptionPane {
    public FromSignUp(IController iController) {
        UIManager.put("OptionPane.background", Color.DARK_GRAY);
        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("Button.background", new Color(248, 180, 0));
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("TextField.background", new Color(60, 63, 65));
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("PasswordField.background", new Color(60, 63, 65));
        UIManager.put("PasswordField.foreground", Color.WHITE);

        // Tạo giao diện form đăng ký
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel titleLabel = new JLabel("ĐĂNG KÝ TÀI KHOẢN");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 22));
        titleLabel.setForeground(Color.ORANGE);
        panel.add(titleLabel, gbc);

        // Họ và tên
        gbc.gridy++;
        panel.add(new JLabel("Họ và tên:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(16); // Tăng kích thước
        nameField.setFont(new Font("Roboto", Font.PLAIN, 16));
        panel.add(nameField, gbc);

        // Tên tài khoản
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Tên tài khoản:"), gbc);
        gbc.gridx = 1;
        JTextField accountField = new JTextField(16);
        accountField.setFont(new Font("Roboto", Font.PLAIN, 16));
        panel.add(accountField, gbc);

        // Mật khẩu
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(16);
        passwordField.setFont(new Font("Roboto", Font.PLAIN, 16));
        panel.add(passwordField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(16);
        emailField.setFont(new Font("Roboto", Font.PLAIN, 16));
        panel.add(emailField, gbc);

        // Hiển thị hộp thoại đăng ký
        int option = JOptionPane.showConfirmDialog(null, panel, "Đăng ký tài khoản",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            UIManager.put("OptionPane.background", Color.WHITE);
            UIManager.put("Panel.background", Color.WHITE);
            UIManager.put("Label.foreground", Color.BLACK);
            UIManager.put("Button.background", new Color(248, 180, 0));
            UIManager.put("Button.foreground", Color.WHITE);
            if (nameField.getText().isEmpty() || accountField.getText().isEmpty() || passwordField.getPassword() == null || emailField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Đăng ký không thành công");
            } else {
                JOptionPane.showMessageDialog(null, "🎉 Đăng ký thành công! 🎉\n\n", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                iController.signUp(nameField.getText(), accountField.getText(),
                        passwordField.getPassword(),
                        emailField.getText());
            }
        }

    }
}
