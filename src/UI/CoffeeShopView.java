package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Bien.BIEN;
import ConnectDB.ConnectDB;
import Dao.NhanVien_DAO;
import Model.NhanVien;

public class CoffeeShopView extends JFrame {
    private JPanel centerPanel; 
    private JPanel rightPanel; 
    private JPanel mainPanel; 
    private String maNhanVien; // Lưu mã nhân viên đăng nhập
    private boolean isQuanLy; // Lưu trạng thái quản lý của nhân viên

    public CoffeeShopView() {
        setTitle(BIEN.TENQUAN); 
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(BIEN.LOGO_QUAN.getImage());
        showLoginDialog(); // Hiển thị dialog đăng nhập khi khởi động
    }

    private void showLoginDialog() {
        JDialog loginDialog = new JDialog(this, "Đăng Nhập", true);
        loginDialog.setSize(400, 300);
        loginDialog.setLocationRelativeTo(this);
        loginDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        LoginPanel loginPanel = new LoginPanel();
        loginDialog.add(loginPanel);

        loginPanel.addLoginListener(e -> {
            String username = loginPanel.getUsername();
            String password = loginPanel.getPassword();

            // Kiểm tra đăng nhập bằng NhanVien_DAO
            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
            NhanVien nhanVien = nhanVienDAO.timNhanVienTheoMaNV(username);
            if (nhanVien != null && nhanVien.getMatKhau().equals(password)) {
                maNhanVien = nhanVien.getMaNV(); // Lưu mã nhân viên
                isQuanLy = nhanVien.isQuanly(); // Lưu trạng thái quản lý
                loginDialog.dispose();
                initializeMainUI(); // Khởi tạo giao diện chính
            } else {
                JOptionPane.showMessageDialog(loginPanel, "Mã nhân viên hoặc mật khẩu không đúng");
                loginPanel.clearFields();
            }
        });

        loginDialog.setVisible(true);
    }

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
        OrderPanel orderPanel = new OrderPanel(maNhanVien); // Truyền mã nhân viên
        
        centerPanel = new ProductListPanel(orderPanel);
        rightPanel = new RightPanel(orderPanel);

        mainPanel.add(leftMenu, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);

        JLabel footerLabel = new JLabel("Hệ Thống Quản Lý Quản Cafe - © 2025", SwingConstants.CENTER);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(footerLabel, BorderLayout.SOUTH);

        // Thêm sự kiện cho nút "Khách Hàng"
        leftMenu.setKhachHangButtonListener(() -> {
            mainPanel.remove(centerPanel);
            mainPanel.remove(rightPanel);
            centerPanel = new KhachHangPanel();
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        // Thêm sự kiện cho nút "Đơn hàng mới"
        leftMenu.setDonHangMoiButtonListener(() -> {
            connectDB();
            mainPanel.remove(centerPanel);
            centerPanel = new ProductListPanel(orderPanel);
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.add(rightPanel, BorderLayout.EAST);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        // Thêm sự kiện cho nút "Nhân Viên" (chỉ quản lý được truy cập)
        leftMenu.setNhanVienButtonListener(() -> {
            if (isQuanLy) {
                mainPanel.remove(centerPanel);
                mainPanel.remove(rightPanel);
                centerPanel = new NhanVienPanel();
                mainPanel.add(centerPanel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Chỉ quản lý mới có quyền truy cập chức năng này!", 
                                            "Lỗi quyền truy cập", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Thêm sự kiện cho nút "Thống Kê" (chỉ quản lý được truy cập)
        leftMenu.setThongKeButtonListener(() -> {
            if (isQuanLy) {
                mainPanel.remove(centerPanel);
                mainPanel.remove(rightPanel);
                centerPanel = new ThongKePanel();
                mainPanel.add(centerPanel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Chỉ quản lý mới có quyền truy cập chức năng này!", 
                                            "Lỗi quyền truy cập", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        leftMenu.setSanPhamButtonListener(() -> {
            mainPanel.remove(centerPanel);
            mainPanel.remove(rightPanel);
            centerPanel = new SanPhamPanel();
            mainPanel.add(centerPanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        
        leftMenu.setQuanLyDonButtonListener(() -> {
            mainPanel.remove(centerPanel);
            mainPanel.remove(rightPanel);
            centerPanel = new QuanLyDonPanel();
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
            new CoffeeShopView();
        });
    }
}