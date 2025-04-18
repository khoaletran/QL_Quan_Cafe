package UI;

import javax.swing.*;
import java.awt.*;

public class LeftMenuPanel extends JPanel {
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
            
            // Tạo label cho emoji
            JLabel emojiLabel = new JLabel(item[0], SwingConstants.CENTER);
            emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            
            // Tạo label cho text
            JLabel textLabel = new JLabel(item[1]);
            textLabel.setFont(textFont);
            
            // Thêm cả hai vào button
            button.add(emojiLabel, BorderLayout.WEST);
            button.add(textLabel, BorderLayout.CENTER);
            
            menuButtonsPanel.add(button);
        }

        JScrollPane scrollPane = new JScrollPane(menuButtonsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }
}