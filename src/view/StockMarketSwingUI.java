package view;

import javax.swing.*;
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
        stockChartExample=new StockChartExample();
//        setSize(1400, 900);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, 3));
        headerPanel.setBackground(new Color(34, 40, 49));
        headerPanel.add(createLabel("Mã cổ phiếu: FPT", Color.WHITE));
        headerPanel.add(createLabel("Giá hiện tại: 109.70", Color.GREEN));
        headerPanel.add(createLabel("Khối lượng: 3.926.800", Color.ORANGE));
        add(headerPanel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 3));

        // Chart Panel with Candlestick Chart
        JPanel chartPanel = new JPanel();
        chartPanel.setBorder(BorderFactory.createTitledBorder("Biểu đồ giá"));
        OHLCDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createCandlestickChart(
                "Giá cổ phiếu FPT",
                "Thời gian",
                "Giá",
                dataset,
                false
        );
        ChartPanel candlestickPanel = new ChartPanel(chart);
        chartPanel.setLayout(new BorderLayout());
        chartPanel.add(candlestickPanel, BorderLayout.CENTER);
        mainPanel.add(chartPanel);

        // Order Book & Depth Chart
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new GridLayout(2, 1));

        // Order Book
        JTable orderTable = new JTable(new Object[][]{
                {"200", "109.60", "109.70"},
                {"1,100", "109.50", "109.80"},
                {"5,800", "109.40", "109.90"}
        }, new String[]{"KL", "Giá Mua", "Giá Bán"});
        orderPanel.add(new JScrollPane(orderTable));

        // Depth Chart
        JPanel depthChartPanel = new JPanel();
        depthChartPanel.setBorder(BorderFactory.createTitledBorder("Biểu đồ độ sâu thị trường"));
        DefaultCategoryDataset depthDataset = new DefaultCategoryDataset();
        depthDataset.addValue(200, "Mua", "109.60");
        depthDataset.addValue(1100, "Mua", "109.50");
        depthDataset.addValue(5800, "Mua", "109.40");
        depthDataset.addValue(7100, "Bán", "109.70");

        JFreeChart depthChart = ChartFactory.createBarChart(
                "Độ sâu thị trường",
                "Giá",
                "Khối lượng",
                depthDataset
        );
        ChartPanel depthChartPanelComp = new ChartPanel(depthChart);
        depthChartPanel.setLayout(new BorderLayout());
        depthChartPanel.add(depthChartPanelComp, BorderLayout.CENTER);

        orderPanel.add(depthChartPanel);
        mainPanel.add(orderPanel);

        // Transaction Panel
        JPanel transactionPanel = new JPanel();
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Khớp lệnh"));
        JTable transactionTable = new JTable(new Object[][]{
                {"100", "109.70", "10:01:20"},
                {"250", "109.65", "10:03:15"},
                {"400", "109.60", "10:05:30"}
        }, new String[]{"Khối lượng", "Giá", "Thời gian"});
        transactionPanel.setLayout(new BorderLayout());
        transactionPanel.add(new JScrollPane(transactionTable), BorderLayout.CENTER);

        mainPanel.add(transactionPanel);
        add(mainPanel, BorderLayout.CENTER);

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

    private OHLCDataset createDataset() {
        int count = 30;
        Date[] dates = new Date[count];
        double[] opens = new double[count];
        double[] highs = new double[count];
        double[] lows = new double[count];
        double[] closes = new double[count];
        double[] volumes = new double[count];

        for (int i = 0; i < count; i++) {
            dates[i] = new Date(System.currentTimeMillis() - (count - i) * 86400000L);
            opens[i] = 100 + Math.random() * 10;
            highs[i] = opens[i] + Math.random() * 5;
            lows[i] = opens[i] - Math.random() * 5;
            closes[i] = opens[i] + Math.random() * 2 - 1;
            volumes[i] = 1000 + Math.random() * 1000;
        }

        OHLCDataItem[] dataItems = new OHLCDataItem[count];
        for (int i = 0; i < count; i++) {
            dataItems[i] = new OHLCDataItem(dates[i], opens[i], highs[i], lows[i], closes[i], volumes[i]);
        }

        return new DefaultOHLCDataset("FPT", dataItems);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StockMarketSwingUI::new);
    }
}
