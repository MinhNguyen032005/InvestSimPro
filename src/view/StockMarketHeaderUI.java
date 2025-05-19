package view;

import controller.IController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StockMarketHeaderUI extends JPanel {
    private IController iController;

    public StockMarketHeaderUI(IController iController,Object[] objects) {
        this.iController = iController;

        setLayout(new BorderLayout());
        setBackground(new Color(28, 26, 41));
        setBorder(new EmptyBorder(10, 20, 10, 20));
        // Left Section (Search + Stock Info)
        if (iController.checkLogin())iController.headerMarketStock(this,objects);
    }


}
