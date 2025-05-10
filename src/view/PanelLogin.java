package view;

import javax.swing.*;
import java.awt.*;

public class PanelLogin extends JPanel {
    public PanelLogin(SignInPanel signInFrom, HomePageUser homePageUser) {
        setBackground(new Color(28, 26, 41));
        setLayout(new BorderLayout()); // hoặc null
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height)); // QUAN TRỌNG

        int formWidth = 400;
        int formHeight = 300;
        int formX = (width - formWidth) / 2;
        int formY = (height - formHeight) / 2;

        homePageUser.setBounds(0, 0, width, height);
        signInFrom.setBounds(formX, formY, formWidth, formHeight);
        signInFrom.setOpaque(false);

        layeredPane.add(homePageUser, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(signInFrom, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }
}
