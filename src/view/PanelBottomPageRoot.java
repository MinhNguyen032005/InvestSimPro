package view;

import controller.IController;
import data.FontLoader.FontLoader;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

public class PanelBottomPageRoot extends JPanel {
    JButton btn;

    public PanelBottomPageRoot(IController iController) {
        Font robotoMedium = FontLoader.loadFont("src/data/font/Roboto-Light.ttf");
        ArrayList<String> stringButton = new ArrayList<>();
        stringButton.add("Đăng xuất");
        setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        for (int i = 0; i < stringButton.size(); i++) {
            ImageIcon originalIcon = new ImageIcon("src/data/img/Dangxuat.png");
            Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            btn = new JButton(stringButton.get(i));
            btn.setIcon(scaledIcon);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setFont(FontLoader.loadCustomizeFont(robotoMedium, 25));
            btn.setPreferredSize(new Dimension(190, 30));
            btn.setBackground(new Color(0, 0, 0, 0));
            btn.setFocusable(false);
            btn.setOpaque(false);
            btn.setBorder(null);
            btn.setSize(50, 75);
            btn.addActionListener(e->{
                iController.logout();
            });
            this.add(btn);
        }
    }
}
