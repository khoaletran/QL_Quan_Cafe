package UI;

import javax.swing.*;

import Bien.BIEN;

import java.awt.*;

public class LeftMenuPanel extends JPanel {
    private JButton khachHangButton; // Lưu nút "Khách Hàng"
    private JButton donHangMoiButton; // Lưu nút "Sản Phẩm"
    private JButton nhanVienButton; // Lưu nút "Nhân Viên"
    private JButton thongKeButton;
    private JButton sanPhamButton;

    public LeftMenuPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(245, 245, 245));
        createUI();
    }

    private void createUI() {
    	ImageIcon logoIcon = new ImageIcon(BIEN.LOGO_QUAN.getImage());
    	Image scaledImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    	JLabel logoLabel = new JLabel(new ImageIcon(scaledImage), SwingConstants.CENTER);
    	logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
    	add(logoLabel, BorderLayout.NORTH);

        JPanel menuButtonsPanel = new JPanel(new GridLayout(0, 1, 0, 5));
        Font textFont = new Font("Arial", Font.PLAIN, 16);

        String[][] menuItems = {
//                {"🏘️", "Bảng Điều Khiển"},
                {"📝", "Đơn Hàng Mới"},
                {"🛒", "Quản Lý Đơn"},
                {"☕", "Sản Phẩm"},
                {"👥", "Khách Hàng"},
                {"👨‍💼", "Nhân Viên"},
                {"📊", "Thống Kê"},
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
                donHangMoiButton = button;
            } else if (item[1].equals("Nhân Viên")) {
                nhanVienButton = button;
            }else if (item[1].equals("Thống Kê")) {
                thongKeButton = button;
            }else if (item[1].equals("Sản Phẩm")) {
                sanPhamButton = button;
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
    public void setDonHangMoiButtonListener(Runnable action) {
        if (donHangMoiButton != null) {
            donHangMoiButton.addActionListener(e -> action.run());
        }
    }

    // Phương thức để gắn sự kiện cho nút "Nhân Viên"
    public void setNhanVienButtonListener(Runnable action) {
        if (nhanVienButton != null) {
            nhanVienButton.addActionListener(e -> action.run());
        }
    }
    
    //Phương thức thêm doanh thu
    // Phương thức để gắn sự kiện cho nút "Nhân Viên"
    public void setThongKeButtonListener(Runnable action) {
        if (thongKeButton != null) {
            thongKeButton.addActionListener(e -> action.run());
        }
    }
    public void setSanPhamButtonListener(Runnable action) {
        if (sanPhamButton != null) {
        	sanPhamButton.addActionListener(e -> action.run());
        }
    }
}