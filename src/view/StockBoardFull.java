package view;

import controller.IController;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class StockBoardFull extends JPanel {
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private IController iController;
    private StockMarketSwingUI stockMarketSwingUI;

    public StockBoardFull(Object[][] objects, IController iController) {
        this.iController = iController;
        this.stockMarketSwingUI = new StockMarketSwingUI();
        String[] columnNames = {
                "CK", "Trần", "Sàn", "TC",
                "Giá 3 (M)", "KL 3 (M)", "Giá 2 (M)", "KL 2 (M)", "Giá 1 (M)", "KL 1 (M)",
                "Giá 1 (B)", "KL 1 (B)", "Giá 2 (B)", "KL 2 (B)", "Giá 3 (B)", "KL 3 (B)",
                "Tổng KL", "Cao", "Thấp", "NN mua", "NN bán", "Room"
        };

        Object[][] data = objects;
        defaultTableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(defaultTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa ô nào hết
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                c.setBackground(Color.BLACK);
                c.setForeground(Color.WHITE);
                Object value = getValueAt(row, column);
                if (row % 2 == 0) {
                    c.setBackground(new Color(28, 26, 41));
                }
                if (column == 0) { // Mã CK
                    c.setForeground(Color.WHITE);
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                } else if (value == null) {
                    // Nếu đang load, thì hiển thị màu xám nhạt
                    c.setForeground(Color.LIGHT_GRAY);
                    ((JLabel) c).setText("..."); // hoặc để trống nếu bạn muốn
                } else if (column == 1) { // Trần
                    c.setForeground(new Color(255, 0, 255));
                } else if (column == 2) { // Sàn
                    c.setForeground(new Color(0, 255, 255));
                } else if (column == 3) { // TC
                    c.setForeground(Color.YELLOW);
                } else if (column == 12) { // +/- thay đổi
                    String change = value.toString();
                    if (change.startsWith("+")) c.setForeground(new Color(0, 255, 0));
                    else if (change.startsWith("-")) c.setForeground(new Color(255, 51, 51));
                    else c.setForeground(Color.YELLOW);
                } else if (value instanceof Double) {
                    double current = (double) value;
                    double reference = (double) getValueAt(row, 3); // TC
                    if (current > reference) c.setForeground(new Color(0, 255, 0));
                    else if (current < reference) c.setForeground(new Color(255, 51, 51));
                    else c.setForeground(Color.YELLOW);
                }

                ((JLabel) c).setHorizontalAlignment(SwingConstants.RIGHT);
                return c;
            }
        };
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false); // Không cho kéo cột
        table.getTableHeader().setResizingAllowed(false);   // Không cho resize cột
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(50, 50, 50));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setBorder(BorderFactory.createLineBorder(new Color(53, 54, 65)));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (col == 0 && row != -1) {
                    // Lấy kích thước màn hình
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                    // Tạo JDialog mới để chứa stockMarketSwingUI
                    JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(table), "", true);

                    // Kích thước dialog chiếm khoảng 90% chiều rộng và 85% chiều cao màn hình
                    int width = (int) (screenSize.getWidth() * 0.9);
                    int height = (int) (screenSize.getHeight() * 0.85);
                    dialog.setSize(width, height);

                    // Tính toán tọa độ để xuất hiện ở giữa màn hình
                    int x = (int) (screenSize.getWidth() / 2 - dialog.getWidth() / 2);
                    int y = (int) (screenSize.getHeight() / 2 - dialog.getHeight() / 2);
                    dialog.setLocation(x, y);

                    // Thêm panel giao diện vào dialog
                    dialog.add(stockMarketSwingUI);

                    // Hiển thị dialog
                    dialog.setVisible(true);
                }
            }
        });
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(i == 0 ? 70 : 77);
        }

        // ScrollPane bao bọc table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Nền tối
        scrollPane.setBackground(new Color(30, 30, 30));
        scrollPane.getViewport().setBackground(new Color(30, 30, 30));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Thanh cuộn tùy chỉnh
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(155, 153, 167);  // Màu của thanh kéo
                this.trackColor = new Color(50, 50, 50);      // Nền thanh cuộn
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(thumbColor);
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                return button;
            }
        });

        // Tùy chỉnh kích thước thanh cuộn
        verticalBar.setPreferredSize(new Dimension(8, Integer.MAX_VALUE));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension(screenSize.width, screenSize.height / 2 + 150));
        add(scrollPane, BorderLayout.CENTER);
        Timer timer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iController.setStockData(defaultTableModel);
            }
        });
        timer.start();
        setBackground(new Color(28, 26, 41));

    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getDefaultTableModel() {
        return defaultTableModel;
    }


}
