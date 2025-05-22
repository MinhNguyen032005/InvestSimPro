package view;

import javax.swing.*;
import java.awt.*;

import controller.IController;
import model.HistoryTransactionStock;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.time.LocalDateTime;
import java.util.Arrays;
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
    private Object[] objects;
    private IController controller;
    private ScheduledExecutorService scheduler;

    public IBoardOrderUI(StockChart stockChart, Object[] objects, IController controller) {
        this.controller = controller;
        this.objects = objects;
        System.out.println(Arrays.toString(objects));
        setPreferredSize(new Dimension(1600, 800));
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createTitledBorder("Bảng Giá Đặt Lệnh"));

        String[] columns = {"KL Mua", "Giá Mua", "Giá Bán", "KL Bán"};
        Object[][] data = dataOrderTable(objects);
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
        String[] orderTypes = {"Up", "Down", "OCO", "T.Up", "T.Down"};
        orderType = new JComboBox<>(orderTypes);


        txtSucmua.setText(controller.setSucMua());
        txtSucmua.setEnabled(false);
        txtMuaBan.setText(maxMua() + "/" + maxBan());
        txtMuaBan.setEnabled(false);
        txtGiaDat.setEnabled(false);
        String[] thaoTacOptions = {"Mua", "Bán"};
        thaoTac = new JComboBox<>(thaoTacOptions);
        thaoTac.setSelectedIndex(0);
        double price3M = (Double) objects[4];

        double price2M = (Double) objects[6];

        double price1M = (Double) objects[8];

        double price1B = (Double) objects[10];

        double price2B = (Double) objects[12];

        double price3B = (Double) objects[14];
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
        String[] historyColumns = {"Thời gian", "Mã CP", "Loại Lệnh", "Giá", "Khối Lượng", "Tổng giá trị", "M/B", "Trạng thái"};
        historyModel = new DefaultTableModel(historyColumns, 0);
        historyTable = new JTable(historyModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa ô nào hết
            }
        };
        historyTable.getTableHeader().setReorderingAllowed(false);
        historyTable.getTableHeader().setResizingAllowed(false);
        controller.updateTable(historyModel, objects[0].toString());
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String currentTime = LocalDateTime.now().format(formatter);
        String thaoTacValue = orderType.getSelectedItem().toString();
        String muaBan = thaoTac.getSelectedItem().toString();
        double giaDat = Double.parseDouble(txtGiaDat.getText());
        double giaKichHoat = Double.parseDouble(txtGiaKichHoat.getText());
        double khoiLuong = Double.parseDouble(txtKhoiLuong.getText());
        double sucMua = Double.parseDouble(txtSucmua.getText());
        double thanhTien = khoiLuong * giaKichHoat;
        String trangThai = "";
        if (orderType.getSelectedItem().toString().equals("Up")) {
            if ("Bán".equals(muaBan)) {
                if (controller.checkBan(muaBan, txtKhoiLuong.getText(), txtGiaKichHoat.getText(), objects[0].toString())) {
                    if (giaDat >= giaKichHoat) {
                        trangThai = "Thành công";
                        historyModel.addRow(new Object[]{
                                currentTime,
                                objects[0],
                                orderType.getSelectedItem(),
                                txtGiaDat.getText(),
                                txtKhoiLuong.getText(),
                                thanhTien, muaBan,
                                trangThai
                        });
                        txtSucmua.setText(sucMua + thanhTien + "");
                        controller.actionTransactionStock(muaBan, txtKhoiLuong.getText(), txtGiaKichHoat.getText(), objects[0].toString());
                    } else {
                        trangThai = "Chờ giá hợp lý";
                        historyModel.addRow(new Object[]{
                                currentTime,
                                objects[0],
                                orderType.getSelectedItem(),
                                txtGiaDat.getText(),
                                txtKhoiLuong.getText(),
                                thanhTien, muaBan,
                                trangThai
                        });
                    }
                }
            } else if ("Mua".equals(muaBan)) {
                if (Double.parseDouble(txtGiaDat.getText()) <= Double.parseDouble(txtGiaKichHoat.getText())) {
                    if (thanhTien <= sucMua) {
                        trangThai = "Thành công";

                        // Trừ tiền sau khi giao dịch thành công
                        sucMua -= thanhTien;
                        txtSucmua.setText(sucMua + "");

                        historyModel.addRow(new Object[]{
                                currentTime,
                                objects[0],
                                orderType.getSelectedItem(),
                                txtGiaDat.getText(),
                                txtKhoiLuong.getText(),
                                thanhTien, muaBan,
                                trangThai
                        });

                        controller.actionTransactionStock(
                                muaBan,
                                txtKhoiLuong.getText(),
                                txtGiaKichHoat.getText(),
                                objects[0].toString()
                        );
                    } else {
                        trangThai = "Không đủ sức mua";
                        historyModel.addRow(new Object[]{
                                currentTime,
                                objects[0],
                                orderType.getSelectedItem(),
                                txtGiaDat.getText(),
                                txtKhoiLuong.getText(),
                                thanhTien, muaBan,
                                trangThai
                        });
                    }
                } else {
                    trangThai = "Chờ giá hợp lý";
                    historyModel.addRow(new Object[]{
                            currentTime,
                            objects[0],
                            orderType.getSelectedItem(),
                            txtGiaDat.getText(),
                            txtKhoiLuong.getText(),
                            thanhTien, muaBan,
                            trangThai
                    });
                }
            }
            controller.saveTransactionStock(currentTime, objects[0], thaoTacValue, String.valueOf(giaDat), String.valueOf(khoiLuong), String.valueOf(thanhTien), muaBan, trangThai);
            startAutoCheck();
        } else if (orderType.getSelectedItem().toString().equals("Down")) {
            if ("Bán".equals(muaBan)) {
                if (controller.checkBan(muaBan, txtKhoiLuong.getText(), txtGiaKichHoat.getText(), objects[0].toString())) {
                    // LỆNH DOWN: Giá thị trường (giaDat) <= Giá kích hoạt (giaKichHoat) thì mới kích hoạt
                    if (giaDat <= giaKichHoat) {
                        trangThai = "Thành công";
                        // Tăng sức mua sau khi bán
                        sucMua += thanhTien;
                        txtSucmua.setText(sucMua + "");
                        historyModel.addRow(new Object[]{
                                currentTime,
                                objects[0],
                                orderType.getSelectedItem(),
                                txtGiaDat.getText(),
                                txtKhoiLuong.getText(),
                                thanhTien, muaBan,
                                trangThai
                        });
                        controller.actionTransactionStock(
                                muaBan,
                                txtKhoiLuong.getText(),
                                txtGiaKichHoat.getText(),
                                objects[0].toString()
                        );
                    } else {
                        trangThai = "Chờ giá hợp lý";
                        historyModel.addRow(new Object[]{
                                currentTime,
                                objects[0],
                                orderType.getSelectedItem(),
                                txtGiaDat.getText(),
                                txtKhoiLuong.getText(),
                                thanhTien, muaBan,
                                trangThai
                        });
                    }
                }
            }
            startAutoCheck();
        } else if (orderType.getSelectedItem().toString().equals("OCO")) {
            String trangThai1 = "", trangThai2 = "";
            // Ví dụ 2 lệnh trong OCO:
            // Lệnh 1: mua với giá kích hoạt = giaKichHoat - biên trượt (txtKhoiLuongOco)
            // Lệnh 2: bán với giá kích hoạt = giaKichHoat + biên trượt
            double bienTruot = 0;
            try {
                bienTruot = Double.parseDouble(txtKhoiLuongOco.getText());
            } catch (NumberFormatException e) {
                bienTruot = 0;
            }
            double kichHoatLenh1 = giaKichHoat - bienTruot;  // kích hoạt lệnh mua
            double kichHoatLenh2 = giaKichHoat + bienTruot;  // kích hoạt lệnh bán

            // Đặt 2 lệnh OCO cùng lúc, trạng thái ban đầu là "Chờ kích hoạt"
            trangThai1 = "Chờ kích hoạt";
            trangThai2 = "Chờ kích hoạt";

            // Lưu lịch sử 2 lệnh OCO
            historyModel.addRow(new Object[]{
                    currentTime,
                    objects[0],
                    "OCO - Mua",
                    txtGiaDat.getText(),
                    txtKhoiLuong.getText(),
                    khoiLuong * kichHoatLenh1,
                    "Mua",
                    trangThai1
            });
            historyModel.addRow(new Object[]{
                    currentTime,
                    objects[0],
                    "OCO - Bán",
                    txtGiaDat.getText(),
                    txtKhoiLuong.getText(),
                    khoiLuong * kichHoatLenh2,
                    "Bán",
                    trangThai2
            });

            // Lưu vào controller 2 lệnh OCO (bạn cần mở rộng controller để lưu lệnh OCO nếu chưa có)
            controller.saveTransactionStock(currentTime, objects[0], "OCO - Mua", String.valueOf(giaDat), String.valueOf(khoiLuong), String.valueOf(khoiLuong * kichHoatLenh1), "Mua", trangThai1);
            controller.saveTransactionStock(currentTime, objects[0], "OCO - Bán", String.valueOf(giaDat), String.valueOf(khoiLuong), String.valueOf(khoiLuong * kichHoatLenh2), "Bán", trangThai2);

            startAutoCheckOCO();
        } else {
            String selectedOrderType = orderType.getSelectedItem().toString();
            if (("T.Up".equals(selectedOrderType) || "T.Down".equals(selectedOrderType)) && "Mua".equals(muaBan)) {
                double giaThiTruongHienTai = getGiaThiTruong(objects[0].toString());
                if (giaDat > giaThiTruongHienTai && giaKichHoat > giaThiTruongHienTai) {
                    trangThai = "chờ xử lý";
                    historyModel.addRow(new Object[]{
                            currentTime, objects[0], selectedOrderType, txtGiaDat.getText(), txtKhoiLuong.getText(), thanhTien, "Mua", trangThai
                    });

                    controller.saveTransactionStock(currentTime, objects[0], selectedOrderType,
                            String.valueOf(giaDat), String.valueOf(khoiLuong),
                            String.valueOf(thanhTien), "Mua", trangThai);

                    startAutoCheckTrailing(currentTime, selectedOrderType, giaDat, giaKichHoat, khoiLuong, objects[0].toString(), sucMua);
                } else {
                    JOptionPane.showMessageDialog(null, "Giá đặt và kích hoạt phải cao hơn giá thị trường.");
                }
            }
        }


    }


    private void xoaLenh() {
        int selectedRow = historyTable.getSelectedRow();
        if (selectedRow != -1) {
            controller.removeTrasaction(selectedRow);
            historyModel.removeRow(selectedRow);
        }
    }

    private void suaLenh() {
        editingRow = historyTable.getSelectedRow();
        if (editingRow != -1) {
            HistoryTransactionStock ht = controller.fixTransaction(editingRow);
            if (ht != null) {
                txtGiaDat.setText(ht.getPrice());
                txtKhoiLuong.setText(ht.getQuantity());
                orderType.setSelectedItem(ht.getType());
                // Nếu có các trường khác muốn sửa thì set tương tự
            }
            historyTable.repaint();
            historyTable.revalidate();
        }
    }

    private void capNhatLenh() {
        if (editingRow != -1) {
            String newType = orderType.getSelectedItem().toString();
            String newGiaDat = txtGiaDat.getText();
            String newKhoiLuong = txtKhoiLuong.getText();

            // Update trên table model
            historyModel.setValueAt(newType, editingRow, 2);
            historyModel.setValueAt(newGiaDat, editingRow, 3);
            historyModel.setValueAt(newKhoiLuong, editingRow, 4);

            // Lấy lại đối tượng cũ, tạo đối tượng mới với giá trị cập nhật (giữ nguyên các trường khác)
            HistoryTransactionStock oldTransaction = controller.fixTransaction(editingRow);
            if (oldTransaction != null) {
                HistoryTransactionStock updated = new HistoryTransactionStock(
                        oldTransaction.getUserName(),
                        oldTransaction.getTime(),
                        oldTransaction.getStockID(),
                        newType,
                        newGiaDat,
                        newKhoiLuong,
                        oldTransaction.getSumPrice(),  // Bạn có thể tính lại nếu muốn
                        oldTransaction.getMuaban(),
                        oldTransaction.getTrangThai()
                );

                // Cập nhật danh sách và lưu file
                controller.updateTransaction(editingRow, updated);
            }

            editingRow = -1;
            historyTable.repaint();
            historyTable.revalidate();
        }
    }

    public double maxMua() {
        return Math.max(((Number) objects[6]).doubleValue(), Math.max(((Number) objects[4]).doubleValue(), ((Number) objects[8]).doubleValue()));
    }

    public double maxBan() {
        return Math.max(((Number) objects[10]).doubleValue(), Math.max(((Number) objects[12]).doubleValue(), ((Number) objects[14]).doubleValue()));
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
                String status = (String) historyModel.getValueAt(i, 7);
                String thaoTac = historyModel.getValueAt(i, 6).toString(); // Lấy thao tác (Mua/Bán)
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
                            historyModel.setValueAt("Thành công", i, 7);

                            // Gọi controller để thực hiện giao dịch
                            controller.actionTransactionStock(thaoTac, String.valueOf(khoiLuong), txtGiaKichHoat.getText(), objects[0].toString());
                        }
                    } else if ("Bán".equals(thaoTac)) {
                        // Với lệnh Bán: Giá thị trường >= Giá kích hoạt
                        if (currentPrice >= activationPrice) {
                            LocalTime currentTime = LocalTime.now();
                            historyModel.setValueAt(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")), i, 0);
                            historyModel.setValueAt("Thành công", i, 7);
                            // Gọi controller để thực hiện giao dịch
                            controller.actionTransactionStock(thaoTac, String.valueOf(khoiLuong), txtGiaKichHoat.getText(), objects[0].toString());
                        }
                    }
                }
            }
        }, 0, 3, TimeUnit.SECONDS); // Kiểm tra mỗi giây
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

    private void startAutoCheckOCO() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                // Giả sử bạn có 1 biến/đối tượng lấy giá thị trường hiện tại, ví dụ:
                double currentMarketPrice = getCurrentMarketPrice(); // Bạn cần implement hàm này

                double availableBalance = Double.parseDouble(txtSucmua.getText());

                for (int i = 0; i < historyModel.getRowCount(); i++) {
                    String status = historyModel.getValueAt(i, 7).toString();
                    String thaoTac = historyModel.getValueAt(i, 6).toString(); // Mua/Bán
                    int khoiLuong = Integer.parseInt(historyModel.getValueAt(i, 4).toString());
                    double activationPrice = Double.parseDouble(historyModel.getValueAt(i, 5).toString());

                    // Kiểm tra trạng thái là "Chờ kích hoạt"
                    if ("Chờ kích hoạt".equals(status)) {
                        if ("Mua".equals(thaoTac)) {
                            // Lệnh Mua kích hoạt khi giá thị trường <= giá kích hoạt
                            if (currentMarketPrice <= activationPrice && (khoiLuong * activationPrice) <= availableBalance) {
                                LocalTime currentTime = LocalTime.now();
                                historyModel.setValueAt(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")), i, 0);
                                historyModel.setValueAt("Thành công", i, 7);

                                // Gọi controller để thực hiện giao dịch
                                controller.actionTransactionStock(thaoTac, String.valueOf(khoiLuong), String.valueOf(activationPrice), objects[0].toString());

                                // Hủy lệnh còn lại trong cặp OCO
                                cancelOtherOcoOrder(i);
                            }
                        } else if ("Bán".equals(thaoTac)) {
                            // Lệnh Bán kích hoạt khi giá thị trường >= giá kích hoạt
                            if (currentMarketPrice >= activationPrice) {
                                LocalTime currentTime = LocalTime.now();
                                historyModel.setValueAt(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")), i, 0);
                                historyModel.setValueAt("Thành công", i, 7);

                                controller.actionTransactionStock(thaoTac, String.valueOf(khoiLuong), String.valueOf(activationPrice), objects[0].toString());

                                // Hủy lệnh còn lại trong cặp OCO
                                cancelOtherOcoOrder(i);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    // Hàm hủy lệnh OCO còn lại khi 1 lệnh kích hoạt thành công
    private void cancelOtherOcoOrder(int activatedIndex) {
        String activatedOrderType = historyModel.getValueAt(activatedIndex, 2).toString(); // "OCO - Mua" hoặc "OCO - Bán"
        String maChungKhoan = historyModel.getValueAt(activatedIndex, 1).toString();

        for (int i = 0; i < historyModel.getRowCount(); i++) {
            if (i == activatedIndex) continue; // Bỏ qua lệnh đã kích hoạt

            String orderType = historyModel.getValueAt(i, 2).toString();
            String maCK = historyModel.getValueAt(i, 1).toString();
            String trangThai = historyModel.getValueAt(i, 7).toString();

            // Nếu cùng mã chứng khoán, cùng loại OCO, trạng thái chưa thành công thì hủy
            if (maCK.equals(maChungKhoan) &&
                    orderType.startsWith("OCO") &&
                    trangThai.equals("Chờ kích hoạt")) {
                historyModel.setValueAt("Đã hủy", i, 7);
                // Nếu cần, gọi controller để update trạng thái hủy lệnh trong DB
                controller.cancelTransactionStock(maCK, orderType);
            }
        }
    }

    // Giả sử bạn có phương thức lấy giá thị trường hiện tại
    private double getCurrentMarketPrice() {
        // TODO: Lấy giá thị trường thực tế từ API hoặc UI hoặc dữ liệu cập nhật
        return Double.parseDouble(txtGiaDat.getText()); // ví dụ lấy từ textbox txtGiaHienTai
    }

    private void startAutoCheckTrailing(String currenTime, String loaiLenh, double giaDat, double giaKichHoat, double khoiLuong, String maCoPhieu, double sucMua) {
        new Thread(() -> {
            double giaThiTruongBanDau = getGiaThiTruong(maCoPhieu);
            double giaDinh = giaThiTruongBanDau;  // cho T.Down
            double giaDay = giaThiTruongBanDau;  // cho T.Up
            double giaKichHoatHienTai = giaKichHoat;

            while (true) {
                try {
                    Thread.sleep(3000); // kiểm tra mỗi 3 giây
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                double giaMoi = getGiaThiTruong(maCoPhieu);

                if ("T.Down".equals(loaiLenh)) {
                    // Nếu giá mới cao hơn đỉnh cũ => cập nhật giá kích hoạt mới
                    if (giaMoi > giaDinh) {
                        double bienTruot = giaMoi - giaThiTruongBanDau;
                        giaDinh = giaMoi;
                        giaKichHoatHienTai = giaKichHoat + bienTruot;
                        System.out.println("T.Down - Cập nhật giá kích hoạt mới: " + giaKichHoatHienTai);
                    }

                    if (giaMoi <= giaKichHoatHienTai) {
                        double giaBanDieuChinh = giaDat + (giaKichHoatHienTai - giaKichHoat);
                        double thanhTien = khoiLuong * giaBanDieuChinh;
                        double finalSucMua = sucMua + thanhTien;

                        SwingUtilities.invokeLater(() -> {
                            txtSucmua.setText(finalSucMua + "");
                            historyModel.addRow(new Object[]{
                                    currenTime,
                                    maCoPhieu,
                                    "T.Down",
                                    giaBanDieuChinh,
                                    khoiLuong,
                                    thanhTien,
                                    "Bán",
                                    "Thành công"
                            });
                        });

                        controller.actionTransactionStock("Bán", String.valueOf(khoiLuong), String.valueOf(giaKichHoatHienTai), maCoPhieu);
                        break;
                    }

                } else if ("T.Up".equals(loaiLenh)) {
                    // Nếu giá mới thấp hơn đáy => cập nhật giá kích hoạt mới
                    if (giaMoi < giaDay) {
                        double bienTruot = giaThiTruongBanDau - giaMoi;
                        giaDay = giaMoi;
                        giaKichHoatHienTai = giaKichHoat - bienTruot;
                        System.out.println("T.Up - Cập nhật giá kích hoạt mới: " + giaKichHoatHienTai);
                    }

                    if (giaMoi >= giaKichHoatHienTai) {
                        double giaMuaDieuChinh = giaDat - (giaKichHoat - giaKichHoatHienTai);
                        double thanhTien = khoiLuong * giaMuaDieuChinh;
                        double finalSucMua = sucMua - thanhTien;

                        SwingUtilities.invokeLater(() -> {
                            txtSucmua.setText(finalSucMua + "");
                            historyModel.addRow(new Object[]{
                                    currenTime,
                                    maCoPhieu,
                                    "T.Up",
                                    giaMuaDieuChinh,
                                    khoiLuong,
                                    thanhTien,
                                    "Mua",
                                    "Thành công"
                            });
                        });

                        controller.actionTransactionStock("Mua", String.valueOf(khoiLuong), String.valueOf(giaKichHoatHienTai), maCoPhieu);
                        break;
                    }
                }
            }
        }).start();
    }


    private double getGiaThiTruong(String maCoPhieu) {
        // Giả lập ngẫu nhiên giá thị trường cho mã cổ phiếu (nên thay bằng API thực tế)
        return Math.round((50 + Math.random() * 10) * 100.0) / 100.0;
    }


}
