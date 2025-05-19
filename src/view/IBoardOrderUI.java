package view;

import javax.swing.*;
import java.awt.*;

import controller.IController;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IBoardOrderUI extends JPanel {
    private DefaultCategoryDataset dataset;
    private DefaultTableModel historyModel;
    private Timer autoUpdateTimer;
    private JTextField txtSucmua, txtGiaDat, txtKhoiLuong, txtMuaBan, txtGiaKichHoat, txtKhoiLuongOco;
    private JComboBox<String> orderType, thaoTac;
    private JTable historyTable;
    private double currentPrice = 100.0;
    private Random random = new Random();
    private int editingRow = -1;
    private StockChart stockChart;
    private IController controller;
    private ScheduledExecutorService scheduler;

    public IBoardOrderUI(StockChart stockChart, IController controller) {
        this.stockChart = stockChart;
        this.controller = controller;
        setPreferredSize(new Dimension(1600, 800));
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createTitledBorder("Bảng Giá Đặt Lệnh"));

        String[] columns = {"Giá Mua", "KL Mua", "Giá Bán", "KL Bán"};
        Object[][] data = new Object[10][4];
        JTable table = new JTable(data, columns);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        orderPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Đặt Lệnh"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Sức mua:", "Mua/Bán tối đa:", "Giá Đặt:", "Khối Lượng:", "Loại Lệnh:", "Giá kích hoạt:", "Thao tác:"};
        JTextField[] textFields = {txtSucmua = new JTextField(), txtMuaBan = new JTextField(), txtGiaDat = new JTextField(), txtKhoiLuong = new JTextField(), txtGiaKichHoat = new JTextField()};
        String[] orderTypes = {"Up", "Down", "OCO", "BullBear", "T.Up", "T.Down"};
        orderType = new JComboBox<>(orderTypes);


        txtSucmua.setText(controller.setSucMua());
        txtSucmua.setEnabled(false);
        txtMuaBan.setText(maxMua() + "/" + maxBan());
        txtMuaBan.setEnabled(false);
        txtGiaDat.setEnabled(false);
        String[] thaoTacOptions = {"Mua", "Bán"};
        thaoTac = new JComboBox<>(thaoTacOptions);
        thaoTac.setSelectedIndex(0);
        double price3M = (Double) stockChart.getObjects()[4];

        double price2M = (Double) stockChart.getObjects()[6];

        double price1M = (Double) stockChart.getObjects()[8];

        double price1B = (Double) stockChart.getObjects()[10];

        double price2B = (Double) stockChart.getObjects()[12];

        double price3B = (Double) stockChart.getObjects()[14];
        txtGiaDat.setText(String.valueOf(Math.min(price1M, Math.min(price2M, price3M))));
        thaoTac.addActionListener(e -> {
            if (thaoTac.getSelectedItem().equals("Mua")) {
                txtGiaDat.setText(String.valueOf(Math.min(price1M, Math.min(price2M, price3M))));
            } else {
                txtGiaDat.setText(String.valueOf(Math.max(price1B, Math.max(price2B, price3B))));
            }
        });


        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            if (i == 4) {
                formPanel.add(orderType, gbc);
            } else if (i == 6) {
                formPanel.add(thaoTac, gbc);
            } else if (i == 5) {  // thêm trường Giá kích hoạt
                formPanel.add(txtGiaKichHoat, gbc);
            } else if (i < textFields.length) {
                formPanel.add(textFields[i], gbc);
            }
        }


        // Thêm các thành phần cho OCO ngay từ đầu, không xóa khi chọn loại lệnh khác
        JLabel lblKhoiLuongOco = new JLabel("Biên trượt");
        txtKhoiLuongOco = new JTextField();
        txtKhoiLuongOco.setVisible(false);
        setTextFieldNumericOnly(txtKhoiLuong);
        setTextFieldNumericOnly(txtGiaKichHoat);
        setTextFieldNumericOnly(txtKhoiLuongOco);

        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(lblKhoiLuongOco, gbc);
        gbc.gridx = 1;
        formPanel.add(txtKhoiLuongOco, gbc);

        formPanel.revalidate();
        formPanel.repaint();

        JButton btnXacNhan = new JButton("Xác nhận");
        JButton btnXoa = new JButton("Xóa Lệnh");
        JButton btnSua = new JButton("Sửa Lệnh");
        JButton btnCapNhat = new JButton("Cập Nhật Lệnh");
        btnXacNhan.addActionListener(e -> {
            if (!txtGiaKichHoat.getText().equals("") || !txtKhoiLuong.getText().equals("")) {
                datLenh(controller);
                txtGiaKichHoat.setText("");
                txtKhoiLuong.setText("");
                txtKhoiLuongOco.setText("");
            }
        });
        btnXoa.addActionListener(e -> xoaLenh());
        btnSua.addActionListener(e -> suaLenh());
        btnCapNhat.addActionListener(e -> capNhatLenh());

        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(btnXacNhan, gbc);
        gbc.gridx = 1;
        formPanel.add(btnXoa, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(btnSua, gbc);
        gbc.gridx = 1;
        formPanel.add(btnCapNhat, gbc);
        orderType.addActionListener(e -> {
            boolean isOCO = orderType.getSelectedItem().toString().equals("OCO");
            lblKhoiLuongOco.setVisible(isOCO);
            txtKhoiLuongOco.setVisible(isOCO);
            formPanel.revalidate();
            formPanel.repaint();
        });
        formPanel.revalidate();
        formPanel.repaint();
        mainPanel.add(orderPanel);
        mainPanel.add(formPanel);

        // History panel setup
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Lịch sử giao dịch"));
        String[] historyColumns = {"Thời gian", "Mã CP", "Loại Lệnh", "Giá", "Khối Lượng", "Tổng giá trị", "Trạng thái"};
        historyModel = new DefaultTableModel(historyColumns, 0);
        historyTable = new JTable(historyModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa ô nào hết
            }
        };
        historyTable.getTableHeader().setReorderingAllowed(false);
        historyTable.getTableHeader().setResizingAllowed(false);
        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        add(stockChart, BorderLayout.EAST);
        add(historyPanel, BorderLayout.SOUTH);

        autoUpdateTimer = new Timer();
        autoUpdateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateStockPrice();
            }
        }, 0, 5000);
        startAutoCheck();
    }


    private void updateStockPrice() {
        double change = (random.nextDouble() - 0.5) * 2;
        currentPrice += change;
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        if (dataset != null) {
            dataset.addValue(currentPrice, "Giá cổ phiếu", currentTime);
        } else {
            System.out.println("Dataset chưa được khởi tạo!");
        }
    }

    private void datLenh(IController controller) {
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String thaoTacValue = thaoTac.getSelectedItem().toString();
        double giaDat = Double.parseDouble(txtGiaDat.getText());
        double giaKichHoat = Double.parseDouble(txtGiaKichHoat.getText());
        double khoiLuong = Double.parseDouble(txtKhoiLuong.getText());
        double sucMua = Double.parseDouble(txtSucmua.getText());
        double thanhTien = khoiLuong * giaKichHoat;
        String trangThai = "";
        if ("Bán".equals(thaoTacValue)) {
            if (giaDat >= giaKichHoat && thanhTien <= sucMua) {
                trangThai = "Thành công";
                historyModel.addRow(new Object[]{
                        currentTime,
                        stockChart.getObjects()[0],
                        orderType.getSelectedItem(),
                        txtGiaDat.getText(),
                        txtKhoiLuong.getText(),
                        thanhTien,
                        "Thành công"
                });

                controller.actionTransactionStock(thaoTacValue, txtKhoiLuong.getText(), txtGiaKichHoat.getText());
            } else {
                trangThai = "Chờ giá hợp lý";
                historyModel.addRow(new Object[]{
                        currentTime,
                        stockChart.getObjects()[0],
                        orderType.getSelectedItem(),
                        txtGiaDat.getText(),
                        txtKhoiLuong.getText(),
                        thanhTien,
                        trangThai
                });
            }
        } else if ("Mua".equals(thaoTacValue)) {
            if (giaDat <= giaKichHoat && thanhTien <= sucMua) {
                // Nếu giá đặt <= giá kích hoạt và đủ tiền để giao dịch
                trangThai = "Thành công";
                historyModel.addRow(new Object[]{
                        currentTime,
                        stockChart.getObjects()[0],
                        orderType.getSelectedItem(),
                        txtGiaDat.getText(),
                        txtKhoiLuong.getText(),
                        thanhTien,
                        "Thành công"
                });

                controller.actionTransactionStock(thaoTacValue, txtKhoiLuong.getText(), txtGiaKichHoat.getText());
            } else {
                trangThai = "Chờ giá hợp lý";
                historyModel.addRow(new Object[]{
                        currentTime,
                        stockChart.getObjects()[0],
                        orderType.getSelectedItem(),
                        txtGiaDat.getText(),
                        txtKhoiLuong.getText(),
                        thanhTien,
                        "Chờ giá hợp lý"
                });
            }
        }
        controller.saveTransactionStock(currentTime, stockChart.getObjects()[0], thaoTacValue, String.valueOf(giaDat), String.valueOf(khoiLuong), String.valueOf(thanhTien), trangThai);
    }


    private void xoaLenh() {
        int selectedRow = historyTable.getSelectedRow();
        if (selectedRow != -1) {
            historyModel.removeRow(selectedRow);
        }
    }

    private void suaLenh() {
        editingRow = historyTable.getSelectedRow();
        if (editingRow != -1) {
//            txtMaCP.setText((String) historyModel.getValueAt(editingRow, 1));
            txtGiaDat.setText((String) historyModel.getValueAt(editingRow, 3));
            txtKhoiLuong.setText((String) historyModel.getValueAt(editingRow, 4));
            orderType.setSelectedItem(historyModel.getValueAt(editingRow, 2));
        }
    }

    private void capNhatLenh() {
        if (editingRow != -1) {
//            historyModel.setValueAt(txtMaCP.getText(), editingRow, 1);
            historyModel.setValueAt(orderType.getSelectedItem().toString(), editingRow, 2);
            historyModel.setValueAt(txtGiaDat.getText(), editingRow, 3);
            historyModel.setValueAt(txtKhoiLuong.getText(), editingRow, 4);
            editingRow = -1;
        }
    }

    public double maxMua() {
        return Math.max(((Number) stockChart.getObjects()[6]).doubleValue(), Math.max(((Number) stockChart.getObjects()[4]).doubleValue(), ((Number) stockChart.getObjects()[8]).doubleValue()));
    }

    public double maxBan() {
        return Math.max(((Number) stockChart.getObjects()[10]).doubleValue(), Math.max(((Number) stockChart.getObjects()[12]).doubleValue(), ((Number) stockChart.getObjects()[14]).doubleValue()));
    }

    public void setTextFieldNumericOnly(JTextField textField) {
        DocumentFilter filter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
                if (string.matches("\\d*")) { // Chỉ cho phép số
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
                if (text.matches("\\d*")) { // Chỉ cho phép số
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(filter);
    }

    private void startAutoCheck() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            for (int i = 0; i < historyModel.getRowCount(); i++) {
                String status = (String) historyModel.getValueAt(i, 6);
                String thaoTac = historyModel.getValueAt(i, 2).toString(); // Lấy thao tác (Mua/Bán)
                double currentPrice = Double.parseDouble(txtGiaDat.getText());
                double activationPrice = Double.parseDouble(txtGiaKichHoat.getText());
                double availableBalance = Double.parseDouble(txtSucmua.getText());
                int khoiLuong = Integer.parseInt((String) historyModel.getValueAt(i, 4));
                double totalCost = khoiLuong * activationPrice;

                // Chỉ kiểm tra những lệnh đang "Chờ giá hợp lý"
                if ("Chờ giá hợp lý".equals(status)) {
                    if ("Mua".equals(thaoTac)) {
                        // Với lệnh Mua: Giá thị trường <= Giá kích hoạt
                        if (currentPrice <= activationPrice && totalCost <= availableBalance) {
                            LocalTime currentTime = LocalTime.now();
                            historyModel.setValueAt(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")), i, 0);
                            historyModel.setValueAt("Thành công", i, 6);

                            // Gọi controller để thực hiện giao dịch
                            controller.actionTransactionStock(thaoTac, String.valueOf(khoiLuong), txtGiaKichHoat.getText());
                        }
                    } else if ("Bán".equals(thaoTac)) {
                        // Với lệnh Bán: Giá thị trường >= Giá kích hoạt
                        if (currentPrice >= activationPrice && totalCost <= availableBalance) {
                            LocalTime currentTime = LocalTime.now();
                            historyModel.setValueAt(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")), i, 0);
                            historyModel.setValueAt("Thành công", i, 6);

                            // Gọi controller để thực hiện giao dịch
                            controller.actionTransactionStock(thaoTac, String.valueOf(khoiLuong), txtGiaKichHoat.getText());
                        }
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS); // Kiểm tra mỗi giây
    }


}
