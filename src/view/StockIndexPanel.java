package view;

import controller.IController;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class StockIndexPanel extends JPanel {
    private IController iController;

    public StockIndexPanel(Object[][] summary, IController iController) {
        this.iController = iController;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(new Color(20, 20, 30));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] times = {"09h", "10h", "11h", "12h", "13h", "14h", "15h"};
        Random rand = new Random();

        for (Object[] row : summary) {
            String indexName = (String) row[0];
            double basePrice = (double) row[1];
            int baseVolume = (int) (Double.parseDouble(row[3].toString()) * 1000); // triệu -> nghìn
            DefaultCategoryDataset priceDataset = new DefaultCategoryDataset();
            DefaultCategoryDataset volumeDataset = new DefaultCategoryDataset();

            double lastPrice = 0;
            int lastVolume = 0;

            for (int i = 0; i < times.length; i++) {
                double delta = (rand.nextDouble() - 0.5) * 10;
                double price = Math.round((basePrice + delta) * 100.0) / 100.0;
                int volume = baseVolume + rand.nextInt(200);

                priceDataset.addValue(price, "Điểm", times[i]);
                volumeDataset.addValue(volume, "KLGD", times[i]);

                if (i == times.length - 1) {
                    lastPrice = price;
                    lastVolume = volume;
                }
            }

            CategoryPlot plot = new CategoryPlot();
            plot.setDomainAxis(new CategoryAxis("Thời gian"));
            plot.setRangeGridlinePaint(Color.DARK_GRAY);
            plot.setBackgroundPaint(new Color(30, 30, 40));

            NumberAxis priceAxis = new NumberAxis("Điểm");
            priceAxis.setTickLabelPaint(Color.WHITE);
            priceAxis.setLabelPaint(Color.GRAY);
            priceAxis.setAutoRangeIncludesZero(false);
            plot.setRangeAxis(0, priceAxis);

            LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer(true, false);
            lineRenderer.setSeriesPaint(0, Color.GREEN);
            lineRenderer.setSeriesStroke(0, new BasicStroke(2f));
            plot.setDataset(0, priceDataset);
            plot.setRenderer(0, lineRenderer);
            plot.mapDatasetToRangeAxis(0, 0);

            NumberAxis volumeAxis = new NumberAxis("KLGD (Triệu)");
            volumeAxis.setTickLabelPaint(Color.GRAY);
            volumeAxis.setLabelPaint(Color.GRAY);
            plot.setRangeAxis(1, volumeAxis);

            BarRenderer barRenderer = new BarRenderer();
            barRenderer.setSeriesPaint(0, new Color(0, 200, 255, 80));
            barRenderer.setShadowVisible(false);
            barRenderer.setBarPainter(new StandardBarPainter());
            plot.setDataset(1, volumeDataset);
            plot.setRenderer(1, barRenderer);
            plot.mapDatasetToRangeAxis(1, 1);

            plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
            plot.setOrientation(PlotOrientation.VERTICAL);

            JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, false);
            chart.setBackgroundPaint(new Color(20, 20, 30));

            JPanel chartContainer = new JPanel(new BorderLayout());
            chartContainer.setBackground(new Color(20, 20, 30));

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(295, 170));
            chartPanel.setBackground(new Color(20, 20, 30));
            chartPanel.setPopupMenu(null);

            JLabel title = new JLabel(indexName + " ↑" + lastPrice + " (+0.15%)", JLabel.CENTER);
            title.setForeground(Color.GREEN);
            title.setFont(new Font("Segoe UI", Font.BOLD, 14));

            JLabel subInfo = new JLabel("KLGD: " + lastVolume + " | Đóng cửa", JLabel.CENTER);
            subInfo.setForeground(Color.LIGHT_GRAY);
            subInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));

            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            infoPanel.setBackground(new Color(20, 20, 30));
            infoPanel.add(title);
            infoPanel.add(subInfo);

            chartContainer.add(chartPanel, BorderLayout.CENTER);
            chartContainer.add(infoPanel, BorderLayout.SOUTH);

            add(chartContainer);
        }
    }
}
