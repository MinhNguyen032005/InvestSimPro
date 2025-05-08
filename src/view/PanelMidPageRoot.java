package view;

import controller.IController;

import javax.swing.*;
import java.awt.*;

public class PanelMidPageRoot extends JPanel {
    public PanelMidPageRoot(IController iController) {
        setLayout(new GridBagLayout());
        JLabel jLabel = new JLabel("Hãy chọn chức năng bạn muốn thực hiện");
        jLabel.setFont(new Font("a", Font.PLAIN, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(jLabel, gbc);
    }
}

