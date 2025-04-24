package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ConnectDB.ConnectDB;

public class CoffeeShopView extends JFrame {
    private JPanel centerPanel; // Lưu panel trung tâm hiện tại
    private JPanel rightPanel; // Lưu RightPanel
    private JPanel mainPanel; // Lưu mainPanel để quản lý bố cục
    private ProductDetailPanel productDetailPanel; // Thêm để truyền vào ProductListPanel

    public CoffeeShopView() {
        setTitle("Quản Lý Quán Cafe - Phiên Bản Hoàn Thiện");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
//        showLoginDialog();
    }
//    private void showLoginDialog() {
//        JDialog loginDialog = new JDialog(this, "Đăng Nhập", true);
//        loginDialog.setSize(400, 300);
//        loginDialog.setLocationRelativeTo(this);
//        loginDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//
//        LoginPanel loginPanel = new LoginPanel();
//        loginDialog.add(loginPanel);
//
//        loginPanel.addLoginListener(e -> {
//            String username = loginPanel.getUsername();
//            String password = loginPanel.getPassword();
//
//            // Giả lập kiểm tra đăng nhập (thay bằng logic thật nếu có)
//            if ("a".equals(username) && "1".equals(password)) {
//                loginDialog.dispose();
//                initializeMainUI();
//            } else {
//                loginPanel.setMessage("Tên đăng nhập hoặc mật khẩu không đúng!");
//                loginPanel.clearFields();
//            }
//        });
//
//        loginDialog.setVisible(true);
//    }

    private void initializeMainUI() {
        connectDB();
        createUI();
        setVisible(true);
    }

    private void createUI() {
        setLayout(new BorderLayout());
        connectDB();

        mainPanel = new JPanel(new BorderLayout());
        LeftMenuPanel leftMenu = new LeftMenuPanel();

        // Khởi tạo các panel cần thiết
        OrderPanel orderPanel = new OrderPanel();
        productDetailPanel = new ProductDetailPanel(orderPanel);
        centerPanel = new ProductListPanel(productDetailPanel,orderPanel);
        rightPanel = new RightPanel(orderPanel);

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
            connectDB();
            mainPanel.remove(centerPanel);
            centerPanel = new ProductListPanel(productDetailPanel,orderPanel); // Sửa lỗi: truyền ProductDetailPanel
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
        
     // Thêm sự kiện cho nút "Thống Kê"
        leftMenu.setThongKeButtonListener(() -> {
            mainPanel.remove(centerPanel);
            mainPanel.remove(rightPanel); // Ẩn RightPanel
            centerPanel = new ThongKePanel();
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }

    public static void connectDB() {
        try {
            ConnectDB.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        connectDB();
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CoffeeShopView().setVisible(true);
        });
    }
}