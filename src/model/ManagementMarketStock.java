package model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ManagementMarketStock {
    private final SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String currentTime = sdfDateTime.format(new Date());
    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final String currentDate = sdfDate.format(new Date());
    private static final String filePath = "src/data/file_data/market_data_" + currentDate + ".txt";
    private static Map<String, List<MarketStock>> marketStockMap = new HashMap<>();
    private int number;
    private Thread apiThread;
    private volatile boolean isRunning = false;
    private List<MarketStock> stocks;
    private static List<String> indexNames = new ArrayList<>();

    public List<String> getIndexNames() {
        return indexNames;
    }

    public ManagementMarketStock() {
        indexNames.add("VNINDEX");
        indexNames.add("HNXUPCOM");
        indexNames.add("HNXINDEX");
        indexNames.add("VNALL");
        indexNames.add("HNX30");
        indexNames.add("VN30");
        number = 30;
        startAPIStockUpdater();
    }

    public static void loadDataMarketStock() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, "|");
                String currentTime = st.nextToken().trim(); // lấy timestamp (không cần định dạng lại)
                MarketStock marketStock = new MarketStock(st.nextToken().trim(),                        // symbol (String)
                        Double.parseDouble(st.nextToken()),            // ceilingPrice (double)
                        Double.parseDouble(st.nextToken()),            // floorPrice (double)
                        Double.parseDouble(st.nextToken()),            // referencePrice (double)
                        Double.parseDouble(st.nextToken()),            // price3M (double)
                        Integer.parseInt(st.nextToken()),              // volume3M (int)
                        Double.parseDouble(st.nextToken()),            // price2M (double)
                        Integer.parseInt(st.nextToken()),              // volume2M (int)
                        Double.parseDouble(st.nextToken()),            // price1M (double)
                        Integer.parseInt(st.nextToken()),              // volume1M (int)
                        Double.parseDouble(st.nextToken()),            // matchPrice (double)
                        Integer.parseInt(st.nextToken()),              // matchVolume (int)
                        Double.parseDouble(st.nextToken()),            // price1B (double)
                        Integer.parseInt(st.nextToken()),              // volume1B (int)
                        Double.parseDouble(st.nextToken()),            // price2B (double)
                        Integer.parseInt(st.nextToken()),              // volume2B (int)
                        Double.parseDouble(st.nextToken()),            // price3B (double)
                        Integer.parseInt(st.nextToken()),              // volume3B (int)
                        Integer.parseInt(st.nextToken()),              // totalVolume (int)
                        Double.parseDouble(st.nextToken()),            // high (double)
                        Double.parseDouble(st.nextToken()),            // low (double)
                        Integer.parseInt(st.nextToken()),              // foreignBuy (int)
                        Integer.parseInt(st.nextToken()),              // foreignSell (int)
                        Integer.parseInt(st.nextToken())               // room (int)
                );
                marketStockMap.putIfAbsent(currentTime, new ArrayList<>());
                marketStockMap.get(currentTime).add(marketStock);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object[][] mapToArray() {
        loadDataMarketStock();
        int totalStocks = marketStockMap.values().stream().mapToInt(List::size).sum();
        Object[][] array = new Object[totalStocks][22];
        Object[][] array1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String currentTimeKey = LocalDateTime.now().format(formatter);
        List<MarketStock> realTimeStocks = marketStockMap.get(currentTimeKey);
        if (realTimeStocks == null || realTimeStocks.isEmpty()) {
            return new Object[0][0]; // không có dữ liệu tại thời điểm hiện tại
        }
        int i = 0;
        for (MarketStock stock : realTimeStocks) {
            array[i][0] = stock.getSymbol();         // "CK"
            array[i][1] = stock.getCeilingPrice();   // "Trần"
            array[i][2] = stock.getFloorPrice();     // "Sàn"
            array[i][3] = stock.getReferencePrice(); // "TC"
            array[i][4] = stock.getPrice3M();        // "Giá 3 (M)"
            array[i][5] = stock.getVolume3M();       // "KL 3 (M)"
            array[i][6] = stock.getPrice2M();        // "Giá 2 (M)"
            array[i][7] = stock.getVolume2M();       // "KL 2 (M)"
            array[i][8] = stock.getPrice1M();        // "Giá 1 (M)"
            array[i][9] = stock.getVolume1M();       // "KL 1 (M)"
            array[i][10] = stock.getPrice1B();       // "Giá 1 (B)"
            array[i][11] = stock.getVolume1B();      // "KL 1 (B)"
            array[i][12] = stock.getPrice2B();       // "Giá 2 (B)"
            array[i][13] = stock.getVolume2B();      // "KL 2 (B)"
            array[i][14] = stock.getPrice3B();       // "Giá 3 (B)"
            array[i][15] = stock.getVolume3B();      // "KL 3 (B)"
            array[i][16] = stock.getTotalVolume();   // "Tổng KL"
            double maxValue = Math.max(Math.max(stock.getHigh(), Math.max(stock.getPrice1B(), stock.getPrice1M())),
                    Math.max(Math.max(stock.getPrice2B(), stock.getPrice2M()), Math.max(stock.getPrice3B(), stock.getPrice3M())));
            double minValue = Math.min(Math.min(stock.getLow(), Math.min(stock.getPrice1B(), stock.getPrice1M())),
                    Math.min(Math.min(stock.getPrice2B(), stock.getPrice2M()), Math.min(stock.getPrice3B(), stock.getPrice3M())));
            array[i][17] = maxValue;          // "Cao"
            array[i][18] = minValue;           // "Thấp"
            array[i][19] = stock.getForeignBuy();    // "NN mua"
            array[i][20] = stock.getForeignSell();   // "NN bán"
            array[i][21] = stock.getRoom();          // "Room"
            i++;
            if (i == number) {
                array1 = Arrays.copyOf(array, i);
                return array1;
            }

        }
        return array;
    }

    public void startAPIStockUpdater() {
        if (isRunning) return;
        isRunning = true;
        apiThread = new Thread(this::runAPIStock);
        apiThread.start();
    }

    public void stopAPIStockUpdater() {
        isRunning = false;
        if (apiThread != null && apiThread.isAlive()) {
            apiThread.interrupt();
        }
    }

    public void runAPIStock() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Random rand = new Random();
        String lastCheckedTime = "";
        while (isRunning) {
            String currentTimeKey = LocalDateTime.now().format(formatter);
            if (!currentTimeKey.equals(lastCheckedTime) && !marketStockMap.containsKey(currentTimeKey)) {
                System.out.println(2);
                stocks = MarketStock.generateFakeStocks(number);
                for (MarketStock stock : stocks) {
                    double newMatchPrice = Math.round((stock.getReferencePrice() * (0.9 + rand.nextDouble() * 0.2)) * 100.0) / 100.0;
                    int newMatchVolume = rand.nextInt(5000) + 500;
                    stock.setMatchPrice(newMatchPrice);
                    stock.setMatchVolume(newMatchVolume);
                    if (newMatchPrice > stock.getHigh()) stock.setHigh(newMatchPrice);
                    if (newMatchPrice < stock.getLow()) stock.setLow(newMatchPrice);
                }

                saveStocksToFile(stocks);
                marketStockMap.put(currentTimeKey, stocks);
                lastCheckedTime = currentTimeKey;

            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }


    private void saveStocksToFile(List<MarketStock> stocks) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdfDate.format(new Date());
        String filename = "src/data/file_data/market_data_" + currentDate + ".txt";
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentTime = sdfDateTime.format(new Date());

        File file = new File(filename);

        try {
            // ✅ Tạo thư mục cha nếu chưa tồn tại
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            // ✅ Tạo file nếu chưa tồn tại
            if (!file.exists()) {
                file.createNewFile();
            }

            // ✅ Viết vào file
            try (FileWriter writer = new FileWriter(file, true)) { // true = ghi thêm
                for (MarketStock stock : stocks) {
                    writer.write(currentTime + "|" + stock.toString() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // In lỗi nếu có
        }
    }

    public Object[][] generateSummaryData(String string) {
        if (string.equals("table")) {
            Object[][] summary = new Object[indexNames.size()][7];
            Random rand = new Random();
            for (int i = 0; i < indexNames.size(); i++) {
                MarketStock stock = stocks.get(i); // giả định mỗi chỉ số ứng với 1 stock
                double reference = stock.getReferencePrice();
                double match = stock.getMatchPrice();
                double change = Math.round((match - reference) * 100.0) / 100.0;
                double totalVolume = stock.getTotalVolume() / 1_000_000.0; // triệu
                double totalValue = (stock.getMatchPrice() * stock.getMatchVolume()) / 1_000_000_000.0; // tỷ
                int stocksUp = rand.nextInt(350); // số CK tăng giả lập
                int stocksDown = rand.nextInt(200); // số CK giảm giả lập
                summary[i][0] = indexNames.get(i);
                summary[i][1] = match;
                summary[i][2] = change;
                summary[i][3] = Math.round(totalVolume * 1000.0) / 1000.0;
                summary[i][4] = Math.round(totalValue * 1000.0) / 1000.0;
                summary[i][5] = String.valueOf(stocksUp);
                summary[i][6] = String.valueOf(stocksDown);
            }
            return summary;
        } else {
            Object[][] summary = new Object[4][7];
            Random rand = new Random();
            for (int i = 0; i < 4; i++) {
                MarketStock stock = stocks.get(i); // giả định mỗi chỉ số ứng với 1 stock
                double reference = stock.getReferencePrice();
                double match = stock.getMatchPrice();
                double change = Math.round((match - reference) * 100.0) / 100.0;
                double totalVolume = stock.getTotalVolume() / 1_000_000.0; // triệu
                double totalValue = (stock.getMatchPrice() * stock.getMatchVolume()) / 1_000_000_000.0; // tỷ
                int stocksUp = rand.nextInt(350); // số CK tăng giả lập
                int stocksDown = rand.nextInt(200); // số CK giảm giả lập
                summary[i][0] = indexNames.get(i);
                summary[i][1] = match;
                summary[i][2] = change;
                summary[i][3] = Math.round(totalVolume * 1000.0) / 1000.0;
                summary[i][4] = Math.round(totalValue * 1000.0) / 1000.0;
                summary[i][5] = String.valueOf(stocksUp);
                summary[i][6] = String.valueOf(stocksDown);
            }
            return summary;
        }
    }


    public Map<String, List<MarketStock>> getMarketStockMap() {
        return marketStockMap;
    }

}
