package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;

import controller.IController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class StockMarketSwingUI extends JPanel {
    private StockChart stockChartExample;
    private StockMarketHeaderUI stockMarketHeaderUI;
    private IController iController;
    private DefaultTableModel model;
    private DefaultTableModel model1;
    private DefaultCategoryDataset depthDataset;

    public StockMarketSwingUI(IController iController, Object[] objects) {
        this.iController = iController;
        setLayout(new BorderLayout());
        this.stockChartExample = new StockChart("src/data/file_data", objects);
        this.stockMarketHeaderUI = new StockMarketHeaderUI(iController, objects);
        if (iController.checkLogin()) {
            // Header Panel
            iController.getStockChartExample(objects,stockChartExample);
            iController.getStock(objects);
            add(stockMarketHeaderUI, BorderLayout.NORTH);

            JPanel rightPanel = new JPanel(new BorderLayout());
            JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            String[] columns = {"KL", "Giá Mua", "Giá Bán", "KL"};
            Object[][] data = dataOrderTable(objects);
            // Chart Panel with Candlestick Chart
            mainSplitPane.setLeftComponent(stockChartExample);
            // Order Book & Depth Chart
            JPanel orderPanel = new JPanel();
            orderPanel.setLayout(new GridLayout(2, 1));
            JPanel jPanel = new JPanel();

            // Order Book
            model = new DefaultTableModel(data, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTable orderTable = new JTable(model);
            orderTable.setPreferredScrollableViewportSize(new Dimension(650, 48));
            JScrollPane scrollPane = new JScrollPane(orderTable);
            orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            jPanel.setPreferredSize(new Dimension(
                    500, 200));
            jPanel.add(scrollPane);
            jPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEmptyBorder(), // Không viền
                    "Độ sâu thị trường", // Tiêu đề của Panel
                    0, 0, // Vị trí tiêu đề: 0 = LEFT, 0 = TOP
                    new Font("Arial", Font.BOLD, 14),
                    new Color(0, 0, 0) // Màu tiêu đề
            ));
            orderPanel.add(jPanel);

            orderPanel.setPreferredSize(new Dimension(500, 100));

            // Depth Chart
            JPanel depthChartPanel = new JPanel();
            depthChartPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEmptyBorder(), // Không viền
                    "Biểu đồ độ sâu thị trường", // Tiêu đề của Panel
                    0, 0, // Vị trí tiêu đề: 0 = LEFT, 0 = TOP
                    new Font("Arial", Font.BOLD, 14),
                    new Color(0, 0, 0) // Màu tiêu đề
            ));
            depthDataset = new DefaultCategoryDataset();
            updateDepthDataset(dataSetDepth(objects));

            JFreeChart depthChart = ChartFactory.createBarChart("", "Giá", "", depthDataset);
            ChartPanel depthChartPanelComp = new ChartPanel(depthChart);
            depthChartPanel.setLayout(new BorderLayout());
            depthChartPanel.add(depthChartPanelComp, BorderLayout.CENTER);
            orderPanel.add(depthChartPanel);
            rightPanel.add(orderPanel, BorderLayout.CENTER);


            mainSplitPane.setRightComponent(rightPanel);
            add(mainSplitPane, BorderLayout.CENTER);
            setVisible(true);
        }
    }

    private Object[][] dataOrderTable(Object[] object) {
        Object[][] re = new Object[3][4];
        int[] indices = {5, 4, 10, 11, 7, 6, 12, 13, 9, 8, 14, 15};

        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                re[i][j] = object[indices[index++]];
            }
        }
        return re;
    }


    private Object[][] dataSetDepth(Object[] object) {
        Object[][] temp = dataOrderTable(object);
        Object[][] re = {{temp[0][0], "Mua", temp[0][1]}, {temp[1][0], "Mua", temp[1][1]}, {temp[2][0], "Mua", temp[2][1]}, {temp[0][3], "Ban", temp[0][2]}, {temp[1][3], "Ban", temp[1][2]}, {temp[2][3], "Ban", temp[2][2]}};
        return re;
    }


    public void updateData(Object[] objects) {
        // Cập nhật lại dữ liệu cho các bảng
        Object[][] updatedOrderData = dataOrderTable(objects);
        model.setDataVector(updatedOrderData, new Object[]{"KL", "Giá Mua", "Giá Bán", "KL"});

        // Cập nhật lại biểu đồ
        updateDepthDataset(dataSetDepth(objects));
    }

    private void updateDepthDataset(Object[][] dataSetDepth) {
        depthDataset.clear();
        for (Object[] row : dataSetDepth) {
            int j = 0;
            if (j < row.length) {
                depthDataset.addValue((Number) row[j], (Comparable) row[++j], (Comparable) row[++j]);
            }
        }
    }


}
