package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Date;

public class StockMarketSwingUI extends JPanel {
    private StockChartExample stockChartExample;

    public StockMarketSwingUI() {
        stockChartExample = new StockChartExample();
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, 3));
        headerPanel.setBackground(new Color(34, 40, 49));
        headerPanel.add(createLabel("Mã cổ phiếu: FPT", Color.WHITE));
        headerPanel.add(createLabel("Giá hiện tại: 109.70", Color.GREEN));
        headerPanel.add(createLabel("Khối lượng: 3.926.800", Color.ORANGE));
        add(headerPanel, BorderLayout.NORTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        String[] columns = {"KL", "Giá Mua", "Giá Bán", "KL"};
        Object[][] data = {
                {"352,900", "37.40", "37.45", "30,800"},
                {"152,600", "37.35", "37.50", "63,800"},
                {"322,600", "37.30", "37.55", "123,400"}
        };
        // Chart Panel with Candlestick Chart
        mainSplitPane.setLeftComponent(stockChartExample);
        // Order Book & Depth Chart
        JPanel transactionPanel = new JPanel();
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new GridLayout(2, 1));
        JPanel jPanel = new JPanel();

        // Order Book
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable orderTable = new JTable(model);
        orderTable.setPreferredScrollableViewportSize(new Dimension(350, 48));
        JScrollPane scrollPane = new JScrollPane(orderTable);
//        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jPanel.setPreferredSize(new Dimension(100, 200));
        jPanel.add(scrollPane);
        jPanel.setBorder(BorderFactory.createTitledBorder("Độ sâu thị trường"));
        orderPanel.add(jPanel);

        orderPanel.setPreferredSize(new Dimension(110, 100));

        // Depth Chart
        JPanel depthChartPanel = new JPanel();
        depthChartPanel.setBorder(BorderFactory.createTitledBorder("Biểu đồ độ sâu thị trường"));
        DefaultCategoryDataset depthDataset = new DefaultCategoryDataset();
        depthDataset.addValue(200, "Mua", "109.60");
        depthDataset.addValue(1100, "Mua", "109.50");
        depthDataset.addValue(5800, "Mua", "109.40");
        depthDataset.addValue(7100, "Bán", "109.70");

        JFreeChart depthChart = ChartFactory.createBarChart("", "Giá", "", depthDataset);
        ChartPanel depthChartPanelComp = new ChartPanel(depthChart);
        depthChartPanel.setLayout(new BorderLayout());
        depthChartPanel.add(depthChartPanelComp, BorderLayout.CENTER);
        orderPanel.add(depthChartPanel);
        rightPanel.add(orderPanel, BorderLayout.CENTER);
        //transactionPanel
        transactionPanel.setLayout(new BorderLayout());
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Khớp lệnh"));
        String[] columns1 = {"Thời gian", "KL", "Giá", "+/-", "M/B"};
        Object[][] data1 = {
                {"10:01:20", "100", "109.70", "+0.05", "M"},
                {"10:03:15", "250", "109.65", "-0.05", "B"},
                {"10:05:30", "400", "109.60", "-0.10", "M"}
        };

        DefaultTableModel model1 = new DefaultTableModel(data1, columns1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable transactionTable = new JTable(model1);

        transactionTable.setPreferredScrollableViewportSize(new Dimension(280, 150));
        transactionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        transactionPanel.setPreferredSize(new Dimension(320, 200));
        transactionPanel.setLayout(new BorderLayout());

        transactionPanel.add(new JScrollPane(transactionTable), BorderLayout.CENTER);

        rightPanel.add(transactionPanel, BorderLayout.EAST);


        mainSplitPane.setRightComponent(rightPanel);
        add(mainSplitPane, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout());
        footerPanel.add(new JButton("Phân tích cơ bản"));
        footerPanel.add(new JButton("Đặt lệnh"));
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }
}
