package view;

import controller.IController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgotPasswordSwing extends JOptionPane {
    private JTextField emailField;
    private IController iController;

    public ForgotPasswordSwing(IController iController) {
        this.iController = iController;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel jLabel = new JLabel();
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(jLabel);
        add(mainPanel);
        int option = JOptionPane.showConfirmDialog(null, mainPanel, "Quên mật khẩu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            iController.sendRequest(emailField.getText(), jLabel);
            JOptionPane.showMessageDialog(null, "Mật khẩu của bạn: " + jLabel.getText(), "Thành công", JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
