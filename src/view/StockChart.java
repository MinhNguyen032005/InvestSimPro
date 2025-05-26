package view;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StockChart extends JPanel {
    private XYPlot candlestickPlot;
    private ChartPanel chartPanel;
    private JFreeChart chart;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String dataFolder;
    private XYPlot volumePlot;
    private Object[] objects;


    public StockChart(String dataFolder, Object[] objects) {
        this.objects = objects;
        this.dataFolder = dataFolder;
        chart = createStockChart();
        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);

        this.setBackground(new Color(17, 17, 27));
        this.setLayout(new java.awt.BorderLayout());
        this.add(chartPanel, java.awt.BorderLayout.CENTER);

        // Cập nhật dữ liệu sau mỗi 5 giây
        Timer timer = new Timer(5000, e -> updateChart());
        timer.start();
    }

    private JFreeChart createStockChart() {
        OHLCDataset ohlcDataset = createOHLCDataFromFiles(dataFolder);
        IntervalXYDataset volumeDataset = createVolumeDatasetFromFiles(dataFolder);

        // Candlestick Plot
        NumberAxis priceAxis = new NumberAxis("Giá");
        priceAxis.setAutoRangeIncludesZero(false);

        CandlestickRenderer renderer = new CandlestickRenderer();
        renderer.setUpPaint(Color.GREEN);
        renderer.setDownPaint(Color.RED);
        renderer.setCandleWidth(8);
        renderer.setDrawVolume(true);

        candlestickPlot = new XYPlot(ohlcDataset, null, priceAxis, renderer);
        candlestickPlot.setBackgroundPaint(new Color(30, 30, 30));
        candlestickPlot.setRangeGridlinePaint(Color.DARK_GRAY);

        // Volume Plot
        NumberAxis volumeAxis = new NumberAxis("Volume");
        volumeAxis.setAutoRangeIncludesZero(true);
        XYBarRenderer volumeRenderer = new XYBarRenderer();
        volumeRenderer.setSeriesPaint(0, new Color(0, 120, 215));
        volumePlot = new XYPlot(volumeDataset, null, volumeAxis, volumeRenderer);
        volumePlot.setBackgroundPaint(new Color(30, 30, 30));
        volumePlot.setRangeGridlinePaint(Color.DARK_GRAY);


        // Lấy ngày hôm nay
        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date sevenDaysAgo = calendar.getTime();


        // Combined Plot
        DateAxis domainAxis = new DateAxis("Ngày");
        domainAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM"));
        domainAxis.setVerticalTickLabels(true);
        domainAxis.setLowerMargin(0);
        domainAxis.setUpperMargin(0);
        domainAxis.setAutoRange(true);
        domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1));
        domainAxis.setAutoRange(false);
        domainAxis.setRange(sevenDaysAgo, today);


        CombinedDomainXYPlot combinedPlot = new CombinedDomainXYPlot(domainAxis);
        combinedPlot.add(candlestickPlot, 3);
        combinedPlot.add(volumePlot, 1);
        combinedPlot.setBackgroundPaint(new Color(20, 20, 20));

        JFreeChart chart = new JFreeChart("Biểu đồ Chứng khoán " + objects[0],
                new Font("Arial", Font.BOLD, 18),
                combinedPlot,
                false);
        chart.setBackgroundPaint(new Color(255, 255, 255));

        return chart;
    }


    private OHLCDataset createOHLCDataFromFiles(String dataFolder) {
        List<Object[]> dataList = readMinDataPerDay(dataFolder);

        if (dataList.isEmpty()) {
            return new DefaultHighLowDataset("Stock", new Date[0], new double[0], new double[0], new double[0], new double[0], new double[0]);
        }

        List<Date> dates = new ArrayList<>();
        List<Double> highs = new ArrayList<>();
        List<Double> lows = new ArrayList<>();
        List<Double> opens = new ArrayList<>();
        List<Double> closes = new ArrayList<>();
        List<Double> volumes = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Sắp xếp dữ liệu theo ngày
        dataList.sort((o1, o2) -> {
            try {
                Date d1 = sdf.parse(o1[0].toString());
                Date d2 = sdf.parse(o2[0].toString());
                return d1.compareTo(d2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });

        // ✅ Sử dụng Set để kiểm tra ngày trùng lặp
        Set<Date> dateSet = new HashSet<>();

        for (Object[] row : dataList) {
            try {
                if (row.length >= 6) {
                    Date date = sdf.parse(row[0].toString());

                    // 🔍 Kiểm tra trùng lặp
                    if (!dateSet.contains(date)) {
                        dateSet.add(date);  // Thêm vào Set để đánh dấu
                        dates.add(date);
                        opens.add(Double.parseDouble(row[1].toString()));
                        closes.add(Double.parseDouble(row[2].toString()));
                        volumes.add(Double.parseDouble(row[3].toString()));
                        highs.add(Double.parseDouble(row[4].toString()));
                        lows.add(Double.parseDouble(row[5].toString()));
                    } else {
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // ✅ Kiểm tra ngày liên tục (nếu không thì thêm khoảng trống)
        fillMissingDates(dates, highs, lows, opens, closes, volumes);

        // ✅ Cập nhật trục thời gian
        if (candlestickPlot != null) {
            DateAxis axis = (DateAxis) candlestickPlot.getDomainAxis();
            axis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1));  // Hiển thị từng ngày
            axis.setDateFormatOverride(new SimpleDateFormat("dd-MM"));
            axis.setVerticalTickLabels(true);  // Hiển thị dọc để dễ đọc
        }

        return new DefaultHighLowDataset("Stock",
                dates.toArray(new Date[0]),
                toPrimitive(highs),
                toPrimitive(lows),
                toPrimitive(opens),
                toPrimitive(closes),
                toPrimitive(volumes));
    }


    private void fillMissingDates(List<Date> dates, List<Double> highs, List<Double> lows,
                                  List<Double> opens, List<Double> closes, List<Double> volumes) {
        List<Date> filledDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date today = new Date();

        if (dates.isEmpty()) return;

        calendar.setTime(today);

        for (int i = 0; i < dates.size(); i++) {
            Date currentDate = dates.get(i);

            while (calendar.getTime().before(currentDate)) {
                filledDates.add(calendar.getTime());
                highs.add(0.0);  // Thêm giá trị mặc định
                lows.add(0.0);
                opens.add(0.0);
                closes.add(0.0);
                volumes.add(0.0);
                calendar.add(Calendar.DAY_OF_MONTH, -7);
            }

            filledDates.add(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        dates.clear();
        dates.addAll(filledDates);
    }


    private void updateChart() {
        try {
            List<Object[]> dataList = readMinDataPerDay(dataFolder);

            if (dataList != null && !dataList.isEmpty()) {
                updateChartFromData(dataList);
            } else {
                // Trường hợp không có dữ liệu hoặc bị null, ta có thể xóa biểu đồ cũ đi để tránh hiển thị sai
                candlestickPlot.setDataset(null);
                volumePlot.setDataset(null);
                chartPanel.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<Object[]> readMinDataPerDay(String directoryPath) {
        Map<String, Object[]> minDataPerDay = new HashMap<>();
        String targetCode = objects[0].toString();
        try {
            Files.walk(Path.of(directoryPath)).forEach(filePath -> {
                if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".txt")) {
                    List<Object[]> fileData = readDataFromFile(filePath.toString());
                    for (Object[] row : fileData) {
                        String code = row[6].toString();
                        String date = row[0].toString();
                        double lowPrice = Double.parseDouble(row[5].toString());
                        // Nếu chưa có dữ liệu ngày này hoặc lowPrice nhỏ hơn dữ liệu cũ thì cập nhật
                        if (code.equals(targetCode)) {
                            if (!minDataPerDay.containsKey(date) || lowPrice < Double.parseDouble(minDataPerDay.get(date)[5].toString())) {
                                minDataPerDay.put(date, row);
                            }
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Trả về danh sách các bản ghi có giá thấp nhất theo ngày
        return new ArrayList<>(minDataPerDay.values());
    }


    private static List<Object[]> readDataFromFile(String filePath) {
        List<Object[]> dataList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split("\\|");
                if (values.length >= 6) {  // Giảm số lượng yêu cầu dữ liệu để tránh lỗi
                    Object[] row = new Object[7];
                    row[0] = values[0];
                    row[1] = Double.parseDouble(values[8]);
                    row[2] = Double.parseDouble(values[10]);
                    row[3] = Double.parseDouble(values[17]);
                    row[4] = Double.parseDouble(values[18]);
                    row[5] = Double.parseDouble(values[16]);
                    row[6] = values[1];
                    dataList.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private void updateChartFromData(List<Object[]> dataList) {
        Set<Date> uniqueDates = new HashSet<>();
        List<Date> dates = new ArrayList<>();
        List<Double> highs = new ArrayList<>();
        List<Double> lows = new ArrayList<>();
        List<Double> opens = new ArrayList<>();
        List<Double> closes = new ArrayList<>();
        List<Double> volumes = new ArrayList<>();

        for (Object[] row : dataList) {
            try {
                Date date = sdf.parse(row[0].toString());
                if (uniqueDates.add(date)) { // Chỉ thêm nếu chưa tồn tại
                    dates.add(date);
                    opens.add((Double) row[1]);
                    closes.add((Double) row[2]);
                    volumes.add((Double) row[3]);
                    highs.add((Double) row[4]);
                    lows.add((Double) row[5]);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // ✅ Xóa dataset cũ trước khi cập nhật
        candlestickPlot.setDataset(null);
        volumePlot.setDataset(null);

        // ✅ Tạo dataset mới với dữ liệu đã loại bỏ trùng lặp
        OHLCDataset ohlcDataset = new DefaultHighLowDataset("Stock",
                dates.toArray(new Date[0]),
                toPrimitive(highs),
                toPrimitive(lows),
                toPrimitive(opens),
                toPrimitive(closes),
                toPrimitive(volumes));

        // ✅ Thêm lại dataset mới vào biểu đồ
        candlestickPlot.setDataset(ohlcDataset);

        // ✅ Cập nhật lại dữ liệu cho Volume Plot
        volumePlot.setDataset(createVolumeDatasetFromFiles(dataFolder));

        // ✅ Vẽ lại biểu đồ
        chartPanel.repaint();
    }


    private double[] toPrimitive(List<Double> list) {
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private IntervalXYDataset createVolumeDatasetFromFiles(String dataFolder) {
        XYSeries series = new XYSeries("Volume");
        List<Object[]> dataList = readMinDataPerDay(dataFolder);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Object[] row : dataList) {
            try {
                if (row.length >= 6) {
                    Date date = sdf.parse(row[0].toString());
                    double volume = Double.parseDouble(row[3].toString()); // Cột thứ 3 là Volume
                    series.add(date.getTime(), volume);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    public String getStockID() {
        return objects[0].toString();
    }

    public Object[] getObjects() {
        return objects;
    }
}
