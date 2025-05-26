package view;

import controller.IController;
import model.AccountManagement;
import model.Users;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThongKeUserPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private List<Users> danhSachUser;
    private ChartPanel chartPanel;
    private IController iController;

    public ThongKeUserPanel(IController iController) {
        this.iController = iController;
        danhSachUser = new ArrayList<>();
        setLayout(new BorderLayout(12, 12));
        setBackground(new Color(245, 248, 250));

        // ========== TIÊU ĐỀ ==========
        JLabel lblTitle = new JLabel("BẢNG THỐNG KÊ NGƯỜI DÙNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Inter", Font.BOLD, 24));
        lblTitle.setBorder(new EmptyBorder(10, 0, 5, 0));
        add(lblTitle, BorderLayout.NORTH);

        // ========== BẢNG ==========
        model = new DefaultTableModel(new Object[]{"Tên người dùng", "Tổng số tiền (VNĐ)", "Tổng lượt chơi"}, 0);
        table = new JTable(model);
        cauHinhBang();
        JScrollPane spTable = new JScrollPane(table);
        spTable.setBorder(new TitledBorder("Danh sách người dùng"));

        // ========== BIỂU ĐỒ ==========
        chartPanel = new ChartPanel(null);  // khởi tạo rỗng, lát nữa setChart
        chartPanel.setPreferredSize(new Dimension(420, 330));
        chartPanel.setBorder(new TitledBorder("Biểu đồ số tiền người dùng"));

        JPanel center = new JPanel(new GridLayout(1, 2, 10, 0));
        center.setOpaque(false);
        center.add(spTable);
        center.add(chartPanel);
        add(center, BorderLayout.CENTER);

        // ========== NÚT ==========
        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.setBackground(new Color(0, 123, 255));
        btnRefresh.setForeground(Color.white);
        btnRefresh.setFont(btnRefresh.getFont().deriveFont(Font.BOLD, 14f));
        btnRefresh.addActionListener(e -> capNhatDuLieu());

        JPanel south = new JPanel();
        south.setOpaque(false);
        south.add(btnRefresh);
        add(south, BorderLayout.SOUTH);

        // DỮ LIỆU DEMO
        themUserMau();
        capNhatDuLieu();
    }

    private void cauHinhBang() {
        table.setRowHeight(28);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }
    }

    private JFreeChart taoBieuDo() {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        for (Users u : danhSachUser) {
            ds.addValue(u.getBalance(), "Số tiền", u.getFullName());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Tổng số tiền theo người dùng",
                "Người dùng",
                "Số tiền (VNĐ)",
                ds,
                PlotOrientation.VERTICAL,
                false, true, false);
        chart.getTitle().setFont(new Font("Inter", Font.BOLD, 16));
        chart.getCategoryPlot().getDomainAxis().setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        chart.getCategoryPlot().getRangeAxis().setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        return chart;
    }

    private void capNhatDuLieu() {
        model.setRowCount(0);
        for (Users u : danhSachUser) {
            model.addRow(new Object[]{
                    u.getFullName(),
                    String.format("%,.0f", u.getBalance()),
                    iController.getLuotChoi(u.getNameAccount())
            });
        }
        chartPanel.setChart(taoBieuDo());
    }

    private void themUserMau() {
        danhSachUser.addAll(iController.getUser());
    }

}
