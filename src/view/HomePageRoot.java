package view;

import javax.swing.*;
import java.awt.*;

public class HomePageRoot extends JPanel {
    public HomePageRoot(JPanel panelMid, PanelLeftPageRoot panelLeftPageRoot, PanelBottomPageRoot panelBottomPageRoot) {
        setLayout(new BorderLayout());
        add(panelMid, BorderLayout.CENTER);
        add(panelBottomPageRoot, BorderLayout.SOUTH);
        add(panelLeftPageRoot, BorderLayout.WEST);
    }
}