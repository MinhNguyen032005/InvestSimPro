package view;

import controller.IController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(JPanel signInPanel, IController iController) throws Exception {
        setTitle("InvestSimPro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(28, 26, 41));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        add(signInPanel);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
