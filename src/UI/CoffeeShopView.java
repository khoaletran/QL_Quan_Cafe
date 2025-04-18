package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CoffeeShopView extends JFrame {
    private JPanel centerPanel; // Lưu panel trung tâm hiện tại
    private JPanel rightPanel; // Lưu RightPanel
    private JPanel mainPanel; // Lưu mainPanel để quản lý bố cục

    public CoffeeShopView() {
        setTitle("Quản Lý Quán Cafe - Phiên Bản Hoàn Thiện");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
        setVisible(true);
    }

    private void createUI() {
        setLayout(new BorderLayout());

        mainPanel = new JPanel(new BorderLayout());
        LeftMenuPanel leftMenu = new LeftMenuPanel();
        centerPanel = new ProductListPanel(); // Panel trung tâm mặc định
        rightPanel = new RightPanel(); // Khởi tạo RightPanel

        mainPanel.add(leftMenu, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);

        JLabel footerLabel = new JLabel("Hệ Thống Quản Lý Quán Cafe - © 2025", SwingConstants.CENTER);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(footerLabel, BorderLayout.SOUTH);

        // Thêm sự kiện cho nút "Khách Hàng"
        leftMenu.setKhachHangButtonListener(() -> {
            mainPanel.remove(centerPanel);
            mainPanel.remove(rightPanel); // Ẩn RightPanel
            centerPanel = new KhachHangPanel();
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        // Thêm sự kiện cho nút "Sản Phẩm"
        leftMenu.setSanPhamButtonListener(() -> {
            mainPanel.remove(centerPanel);
            centerPanel = new ProductListPanel();
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(rightPanel, BorderLayout.EAST); // Hiển thị lại RightPanel
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        // Thêm sự kiện cho nút "Nhân Viên"
        leftMenu.setNhanVienButtonListener(() -> {
            mainPanel.remove(centerPanel);
            mainPanel.remove(rightPanel); // Ẩn RightPanel
            centerPanel = new NhanVienPanel();
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                mainPanel.getComponent(0).setPreferredSize(new Dimension(width / 4, 0));
                // Điều chỉnh kích thước centerPanel và rightPanel nếu rightPanel hiển thị
                if (mainPanel.getComponentCount() > 2) {
                    mainPanel.getComponent(1).setPreferredSize(new Dimension(width / 2, 0));
                    mainPanel.getComponent(2).setPreferredSize(new Dimension(width / 4, 0));
                } else {
                    mainPanel.getComponent(1).setPreferredSize(new Dimension(3 * width / 4, 0));
                }
                revalidate();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CoffeeShopView();
        });
    }
}