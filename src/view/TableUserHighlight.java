package view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class TableUserHighlight extends JPanel {
    private JTable table;
    private int selectedRow = -1; // lưu dòng đang được chọn
    Object[][] data;

    public TableUserHighlight() {
        String[] columns = {
                "ID", "Họ tên", "Số dư", "Tài khoản", "Mật khẩu", "Email", "Vai trò", "Ngày tạo"
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30); // Tăng chiều cao dòng
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Font lớn hơn
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setCellSelectionEnabled(true);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            Font boldFont = new Font("Segoe UI", Font.BOLD, 14);
            Font normalFont = new Font("Segoe UI", Font.PLAIN, 14);

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row == selectedRow) {
                    c.setBackground(new Color(200, 230, 255));
                    c.setFont(boldFont);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setFont(normalFont);
                }
                return c;
            }
        };

        // Gán renderer cho toàn bộ cột
        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn col = table.getColumnModel().getColumn(i);
            col.setCellRenderer(renderer);
            col.setPreferredWidth(100); // Set chiều rộng cột lớn hơn
        }

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                selectedRow = table.getSelectedRow();
                table.repaint();
            }
        });

        // Đặt kích thước tối thiểu cho scrollPane chứa bảng
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 400)); // chỉnh bảng lớn hơn

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(1100, 450)); // chỉnh JPanel lớn hơn
        table.getTableHeader().setResizingAllowed(false);
        // Không cho resize hoặc reorder cột
        table.getTableHeader().setReorderingAllowed(false);

        JPanel panelButton = new JPanel();
        JButton buttonFix = new JButton("Fix");
        JButton buttonSave = new JButton("Save");
        panelButton.add(buttonFix);
        panelButton.add(buttonSave);
        add(panelButton, BorderLayout.SOUTH);

    }

    public void setData(Object[][] data) {
        this.data = data;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(data, new Object[]{
                "ID", "Họ tên", "Số dư", "Tài khoản", "Mật khẩu", "Email", "Vai trò", "Ngày tạo"
        });
    }
}
