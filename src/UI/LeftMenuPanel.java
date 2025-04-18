package UI;

import javax.swing.*;
import java.awt.*;

public class LeftMenuPanel extends JPanel {
    private JButton khachHangButton; // Lưu nút "Khách Hàng"
    private JButton sanPhamButton; // Lưu nút "Sản Phẩm"
    private JButton nhanVienButton; // Lưu nút "Nhân Viên"

    public LeftMenuPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(245, 245, 245));
        createUI();
    }

    private void createUI() {
        JLabel logoLabel = new JLabel("QUÁN CAFE", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        add(logoLabel, BorderLayout.NORTH);

        JPanel menuButtonsPanel = new JPanel(new GridLayout(0, 1, 0, 5));
        Font textFont = new Font("Arial", Font.PLAIN, 16);

        String[][] menuItems = {
                {"🏘️", "Bảng Điều Khiển"},
                {"📝", "Đơn Hàng Mới"},
                {"🛒", "Quản Lý Đơn"},
                {"☕", "Sản Phẩm"},
                {"👥", "Khách Hàng"},
                {"👨‍💼", "Nhân Viên"},
                {"📊", "Thống Kê"},
                {"⚙️", "Cài Đặt"}
        };

        for (String[] item : menuItems) {
            JButton button = new JButton();
            button.setLayout(new BorderLayout());
            button.setBackground(new Color(245, 245, 245));
            button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel emojiLabel = new JLabel(item[0], SwingConstants.CENTER);
            emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));

            JLabel textLabel = new JLabel(item[1], SwingConstants.LEFT);
            textLabel.setFont(textFont);

            button.add(emojiLabel, BorderLayout.WEST);
            button.add(textLabel, BorderLayout.CENTER);

            // Lưu các nút cần thiết
            if (item[1].equals("Khách Hàng")) {
                khachHangButton = button;
            } else if (item[1].equals("Đơn Hàng Mới")) {
                sanPhamButton = button;
            } else if (item[1].equals("Nhân Viên")) {
                nhanVienButton = button;
            }

            menuButtonsPanel.add(button);
        }

        JScrollPane scrollPane = new JScrollPane(menuButtonsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    // Phương thức để gắn sự kiện cho nút "Khách Hàng"
    public void setKhachHangButtonListener(Runnable action) {
        if (khachHangButton != null) {
            khachHangButton.addActionListener(e -> action.run());
        }
    }

    // Phương thức để gắn sự kiện cho nút "Sản Phẩm"
    public void setSanPhamButtonListener(Runnable action) {
        if (sanPhamButton != null) {
            sanPhamButton.addActionListener(e -> action.run());
        }
    }

    // Phương thức để gắn sự kiện cho nút "Nhân Viên"
    public void setNhanVienButtonListener(Runnable action) {
        if (nhanVienButton != null) {
            nhanVienButton.addActionListener(e -> action.run());
        }
    }
}