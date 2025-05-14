package view;

import javax.swing.*;
import java.awt.*;

public class PanelLogin extends JPanel {
    private SignInPanel signInFrom;
    private HomePageUser homePageUser;

    public PanelLogin(SignInPanel signInFrom, HomePageUser homePageUser) {
        this.signInFrom = signInFrom;
        this.homePageUser = homePageUser;

        setBackground(new Color(28, 26, 41));
        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height));

        int formWidth = 400;
        int formHeight = 300;
        int formX = (width - formWidth) / 2;
        int formY = (height - formHeight) / 2;

        // 🔹 Thiết lập vị trí và kích thước cho các panel
        homePageUser.setBounds(0, 0, width, height);
        signInFrom.setBounds(formX, formY, formWidth, formHeight);
        signInFrom.setOpaque(false);  // Đảm bảo signInFrom không che khuất các phần còn lại

        // 🔹 Tạo lớp phủ mờ (overlay)
        JPanel overlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Độ mờ
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };

        overlayPanel.setBounds(0, 0, width, height);
        overlayPanel.setOpaque(false);

        // 🔹 Thêm vào layeredPane
        layeredPane.add(homePageUser, JLayeredPane.DEFAULT_LAYER); // Thêm homePageUser ở lớp nền
        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER); // Lớp phủ
        layeredPane.add(signInFrom, JLayeredPane.MODAL_LAYER);     // Form đăng nhập ở lớp trên cùng

        add(layeredPane, BorderLayout.CENTER);

        // Đảm bảo cả 2 panel đều được hiển thị
        homePageUser.setVisible(true);
        signInFrom.setVisible(true);
        revalidate();
        repaint();
    }
}
