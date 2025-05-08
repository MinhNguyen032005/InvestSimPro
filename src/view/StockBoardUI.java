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

public class StockBoardUI extends JPanel {
    private IController iController;

    public StockBoardUI(Object[][] objects, IController iController) {
        this.iController = iController;
        String[] columnNames = {
                "Chỉ số", "Điểm", "+/-", "KLGD (Triệu)", "GTGD (Tỷ)", "CK Tăng", "CK Giảm"
        };

        Object[][] data = objects;
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho chỉnh sửa
            }
        };
        // Tùy biến màu cho từng cột
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object val, boolean isSelected,
                                                           boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, isSelected, hasFocus, row, col);
                c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                setHorizontalAlignment(SwingConstants.CENTER);
                c.setForeground(Color.WHITE);
                c.setBackground(new Color(30, 30, 30));

                // Màu riêng cho từng cột
                if (col == 1) setForeground(new Color(0, 255, 255)); // Điểm
                else if (col == 2) {
                    double value = Double.parseDouble(val.toString());
                    setForeground(value > 0 ? Color.GREEN : (value < 0 ? Color.RED : Color.YELLOW));
                } else if (col == 5) setForeground(Color.GREEN);
                else if (col == 6) setForeground(Color.RED);
                return c;
            }
        });

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false); // Không cho kéo cột
        table.getTableHeader().setResizingAllowed(false);   // Không cho resize cột
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(50, 50, 50));
        table.getTableHeader().setForeground(Color.WHITE);

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
        scrollPane.setPreferredSize(new Dimension(730, 189));
        add(scrollPane, BorderLayout.CENTER);

        // Sự kiện click hiển thị JOptionPane
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                Object val = table.getValueAt(row, col);
                JOptionPane.showMessageDialog(null, "Bạn chọn: " + val, "Thông tin chi tiết", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        Timer timer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iController.setStockExchange(model);
            }
        });
        timer.start();
    }

}
