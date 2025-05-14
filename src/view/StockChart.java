package view;

import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StockChart extends JPanel {
    public StockChart() {
        JFreeChart chart = createStockChart();
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panel.setDomainZoomable(true);
        panel.setRangeZoomable(true);
        this.setBackground(new Color(17, 17, 27));
        this.add(panel);
    }

    private static JFreeChart createStockChart() {
        OHLCDataset ohlcDataset = createOHLCData();
        IntervalXYDataset volumeDataset = createVolumeDataset();


        // Candlestick Plot
        NumberAxis priceAxis = new NumberAxis("Giá");
        priceAxis.setAutoRangeIncludesZero(false);
        CandlestickRenderer renderer = new CandlestickRenderer();
        renderer.setUpPaint(Color.GREEN);
        renderer.setDownPaint(Color.RED);
        renderer.setCandleWidth(5);
        XYPlot candlestickPlot = new XYPlot(ohlcDataset, null, priceAxis, renderer);
        candlestickPlot.setBackgroundPaint(new Color(30, 30, 30));
        candlestickPlot.setRangeGridlinePaint(Color.DARK_GRAY);

        // Volume Plot
        NumberAxis volumeAxis = new NumberAxis("Volume");
        volumeAxis.setAutoRangeIncludesZero(true);
        XYBarRenderer volumeRenderer = new XYBarRenderer();
        volumeRenderer.setSeriesPaint(0, new Color(0, 120, 215));
        XYPlot volumePlot = new XYPlot(volumeDataset, null, volumeAxis, volumeRenderer);
        volumePlot.setBackgroundPaint(new Color(30, 30, 30));
        volumePlot.setRangeGridlinePaint(Color.DARK_GRAY);

        // Combined Plot
        DateAxis domainAxis = new DateAxis("Ngày");
        domainAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));
        domainAxis.setTickLabelPaint(Color.BLACK);

        CombinedDomainXYPlot combinedPlot = new CombinedDomainXYPlot(domainAxis);
        combinedPlot.add(candlestickPlot, 3);
        combinedPlot.add(volumePlot, 1);
        combinedPlot.setBackgroundPaint(new Color(20, 20, 20));

        JFreeChart chart = new JFreeChart("Biểu đồ Chứng khoán Demo",
                new Font("Arial", Font.BOLD, 18),
                combinedPlot,
                false);
        chart.setBackgroundPaint(new Color(255,255,255));

        return chart;
    }

    private static OHLCDataset createOHLCData() {
        int num = 30;
        Date[] date = new Date[num];
        double[] high = new double[num];
        double[] low = new double[num];
        double[] open = new double[num];
        double[] close = new double[num];
        double[] volume = new double[num];

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -num);

        double prevClose = 100;
        for (int i = 0; i < num; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            date[i] = calendar.getTime();
            open[i] = prevClose;
            double change = (Math.random() - 0.5) * 10;
            close[i] = open[i] + change;
            high[i] = Math.max(open[i], close[i]) + Math.random() * 5;
            low[i] = Math.min(open[i], close[i]) - Math.random() * 5;
            volume[i] = Math.random() * 1000 + 500;
            prevClose = close[i];
        }

        return new DefaultHighLowDataset("Stock", date, high, low, open, close, volume);
    }

    private static IntervalXYDataset createVolumeDataset() {
        XYSeries series = new XYSeries("Volume");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        double prevClose = 100;
        for (int i = 0; i < 30; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            double volume = Math.random() * 1000 + 500;
            series.add(calendar.getTime().getTime(), volume);
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }
}
