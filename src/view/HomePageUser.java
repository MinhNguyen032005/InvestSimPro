package view;

import javax.swing.*;

import controller.IController;
import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomePageUser extends JPanel {
    public HomePageUser(StockBoardFull stockBoardFull, StockBoardUI stockBoardUI, StockIndexPanel stockIndexPanel, FindStock findStock, IBoardPanel iBoardPanel, IController iController) {
        setBackground(new Color(28, 26, 41));
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(28, 26, 41));
        stockIndexPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        stockIndexPanel.setBackground(new Color(28, 26, 41));
        stockBoardUI.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
        stockBoardUI.setBackground(new Color(28, 26, 41));
        topPanel.add(iBoardPanel, BorderLayout.NORTH);
        topPanel.add(stockIndexPanel, BorderLayout.WEST);
        topPanel.add(stockBoardUI, BorderLayout.EAST);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        findStock.getjButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iController.findStock(findStock.getjTextField().getText(), stockBoardFull.getTable(), stockBoardFull.getDefaultTableModel());
            }
        });
        findStock.getjTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                iController.newTable(findStock.getjTextField().getText(), stockBoardFull.getDefaultTableModel());
            }
        });
        stockBoardFull.setPreferredSize(new Dimension(screenSize.width, screenSize.height / 2));
        add(topPanel, BorderLayout.NORTH);              // panel trên cùng
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.add(findStock, BorderLayout.NORTH);
        jPanel.add(stockBoardFull, BorderLayout.CENTER);
        add(jPanel, BorderLayout.CENTER);       // bảng giá full width và nửa màn hình
    }


}
