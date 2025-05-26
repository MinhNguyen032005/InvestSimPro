package view;

import controller.IController;

import javax.swing.*;
import java.awt.*;

public class FromSignUp extends JOptionPane {
    private IController iController;

    public FromSignUp(IController iController) {
        this.iController = iController;
        // Cấu hình giao diện UI
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Label.foreground", Color.BLACK);
        UIManager.put("Button.background", Color.red);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("TextField.foreground", Color.BLACK);
        UIManager.put("PasswordField.background", Color.WHITE);
        UIManager.put("PasswordField.foreground", Color.BLACK);

        // Tạo giao diện form đăng ký
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Họ và tên
        gbc.gridy++;
        JLabel nameLabel = new JLabel("Họ và tên:");
        nameLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField(16);
        nameField.setFont(new Font("Roboto", Font.PLAIN, 14));
        panel.add(nameField, gbc);

        // Tên tài khoản
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel accountLabel = new JLabel("Tên tài khoản:");
        accountLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        panel.add(accountLabel, gbc);

        gbc.gridx = 1;
        JTextField accountField = new JTextField(16);
        accountField.setFont(new Font("Roboto", Font.PLAIN, 14));
        panel.add(accountField, gbc);

        // Mật khẩu
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(16);
        passwordField.setFont(new Font("Roboto", Font.PLAIN, 14));
        panel.add(passwordField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(16);
        emailField.setFont(new Font("Roboto", Font.PLAIN, 14));
        panel.add(emailField, gbc);

        // Hiển thị hộp thoại đăng ký
        int option = JOptionPane.showConfirmDialog(null, panel, "Đăng ký tài khoản",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        // Xử lý sự kiện khi chọn OK hoặc Cancel
        if (option == JOptionPane.OK_OPTION) {
            if (nameField.getText().isEmpty() || accountField.getText().isEmpty()
                    || passwordField.getPassword().length == 0 || emailField.getText().isEmpty() || !emailField.getText().contains("@") || iController.checkContain(accountField.getText())) {
                JOptionPane.showMessageDialog(null, "Đăng ký không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "🎉 Đăng ký thành công! 🎉\n\n", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                this.signUp(this.iController, nameField, accountField,
                        passwordField,
                        emailField);
            }
        }
    }

    private void signUp(IController iController, JTextField nameField, JTextField accountField, JPasswordField passwordField, JTextField emailField) {
        iController.signUp(nameField.getText(), accountField.getText(), passwordField.getPassword(), emailField.getText());
    }
}
