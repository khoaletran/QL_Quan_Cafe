package UI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import Dao.KhachHang_DAO;
import Dao.HangHoa_DAO;
import Dao.HoaDon_DAO;
import Dao.MaGiamGia_DAO;
import Model.KhachHang;
import Model.HangHoa;
import Model.ChiTietHoaDon;
import Model.HoaDonBanHang;
import Model.MaGiamGia;
import Model.LoaiKhachHang;
import ConnectDB.ConnectDB;

public class OrderPanel extends JPanel {
    private DefaultTableModel orderTableModel;
    private JLabel totalLabel;
    private JLabel totalAfterDiscountLabel;
    private JLabel discountAmountLabel;
    private JTextField discountCodeField;
    private JTextField phoneField;
    private JCheckBox bankTransferCheckBox;
    private double discountPercentage = 0.0;
    private DecimalFormat df = new DecimalFormat("#,##0đ");
    private JLabel discountStatusLabel;
    private String maNhanVien;

    public OrderPanel(String maNhanVien) {
        if (maNhanVien == null || maNhanVien.isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được null hoặc rỗng");
        }
        this.maNhanVien = maNhanVien;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createUI();
    }

    private void createUI() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("ĐƠN HÀNG HIỆN TẠI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        phonePanel.add(new JLabel("Số điện thoại khách hàng:"));
        phoneField = new JTextField(10);
        phoneField.setPreferredSize(new Dimension(150, 25));
        phonePanel.add(phoneField);
        headerPanel.add(phonePanel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        String[] columns = {"Sản phẩm", "Số lượng", "Giá", "Tổng", "Xóa"};
        orderTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 1: return Integer.class;
                    case 2:
                    case 3: return Double.class;
                    default: return String.class;
                }
            }
        };
        JTable orderTable = new JTable(orderTableModel);
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        orderTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        orderTable.getColumnModel().getColumn(4).setPreferredWidth(50);

        orderTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        orderTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), this));

        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Tổng: 0đ");
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        totalPanel.add(totalLabel);
        summaryPanel.add(totalPanel);

        JPanel paymentMethodPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bankTransferCheckBox = new JCheckBox("Thanh toán chuyển khoản");
        bankTransferCheckBox.setFont(new Font("Arial", Font.BOLD, 14));
        paymentMethodPanel.add(bankTransferCheckBox);
        summaryPanel.add(paymentMethodPanel);

        JPanel discountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel discountCodeLabel = new JLabel("Mã giảm giá:");
        discountCodeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        discountCodeLabel.setForeground(Color.BLUE);
        discountCodeField = new JTextField(10);
        discountCodeField.setPreferredSize(new Dimension(100, 25));
        discountPanel.add(discountCodeLabel);
        discountPanel.add(discountCodeField);
        summaryPanel.add(discountPanel);

        JPanel discountAmountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        discountAmountLabel = new JLabel("Chiết khấu: 0đ");
        discountAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        discountAmountLabel.setForeground(Color.BLUE);
        discountAmountPanel.add(discountAmountLabel);
        summaryPanel.add(discountAmountPanel);

        JPanel totalAfterDiscountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalAfterDiscountLabel = new JLabel("Thành tiền: 0đ");
        totalAfterDiscountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalAfterDiscountLabel.setForeground(Color.RED);
        totalAfterDiscountPanel.add(totalAfterDiscountLabel);
        summaryPanel.add(totalAfterDiscountPanel);

        JPanel discountStatusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        discountStatusLabel = new JLabel(" ");
        discountStatusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        discountStatusLabel.setForeground(Color.BLUE);
        discountStatusPanel.add(discountStatusLabel);
        summaryPanel.add(discountStatusPanel);

        bottomPanel.add(summaryPanel, BorderLayout.NORTH);

        CustomButton checkoutButton = new CustomButton("Thanh Toán", new Color(34, 139, 34), Color.WHITE, 10);
        checkoutButton.addActionListener(e -> handleCheckout());
        bottomPanel.add(checkoutButton, BorderLayout.CENTER);

        discountCodeField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkDiscountCode();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkDiscountCode();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkDiscountCode();
            }
        });

        phoneField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkPhoneNumber();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkPhoneNumber();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkPhoneNumber();
            }
        });

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void handleCheckout() {
        if (orderTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Đơn hàng trống! Vui lòng thêm sản phẩm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy thông tin từ giao diện
        String phoneNumber = phoneField.getText().trim();
        String discountCode = discountCodeField.getText().trim();
        boolean isBankTransfer = bankTransferCheckBox.isSelected();

        // Tạo danh sách chi tiết hóa đơn
        ArrayList<ChiTietHoaDon> chiTietList = new ArrayList<>();
        HangHoa_DAO hangHoaDAO = new HangHoa_DAO();
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            String tenHH = (String) orderTableModel.getValueAt(i, 0);
            int soLuong = (Integer) orderTableModel.getValueAt(i, 1);
            double gia = (Double) orderTableModel.getValueAt(i, 2);

            // Tìm HangHoa từ CSDL dựa trên TENHH
            HangHoa hh = null;
            try {
                hh = hangHoaDAO.timHangHoaTheoTen(tenHH);
            } catch (SQLException e) {
                e.printStackTrace(); // Ghi log lỗi
                JOptionPane.showMessageDialog(this, "Lỗi khi truy vấn hàng hóa: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (hh == null) {
                JOptionPane.showMessageDialog(this, "Hàng hóa '" + tenHH + "' không tồn tại trong CSDL.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ChiTietHoaDon cthd = new ChiTietHoaDon(soLuong, hh);
            chiTietList.add(cthd);
        }

        // Tìm khách hàng
        KhachHang khachHang = null;
        if (!phoneNumber.isEmpty()) {
            try {
                khachHang = KhachHang_DAO.timKhachHangTheoSDT(phoneNumber);
            } catch (Exception e) {
                e.printStackTrace(); // Ghi log lỗi
                JOptionPane.showMessageDialog(this, "Lỗi khi truy vấn khách hàng: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if (khachHang == null) {
            // Khách lẻ với MAKH cố định
            khachHang = new KhachHang("KH0000", "Khách lẻ", "0000000000", 0, new LoaiKhachHang("LKH0001", "Thường", 0));
        }

        // Tìm mã giảm giá
        MaGiamGia maGiamGia = null;
        if (!discountCode.isEmpty()) {
            try {
                MaGiamGia_DAO discountDAO = new MaGiamGia_DAO();
                maGiamGia = discountDAO.timMaGiamGia(discountCode);
            } catch (Exception e) {
                e.printStackTrace(); // Ghi log lỗi
                JOptionPane.showMessageDialog(this, "Lỗi khi truy vấn mã giảm giá: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Tạo hóa đơn (MAHDBH sẽ được trigger sinh)
        HoaDonBanHang hoaDon = new HoaDonBanHang(null,LocalDate.now(), maGiamGia, khachHang,isBankTransfer);
        for (ChiTietHoaDon cthd : chiTietList) {
            hoaDon.themChiTiet(cthd);
        }
        hoaDon.setDiemTL(hoaDon.getdiemTL()); // Cập nhật điểm tích lũy

        // In hóa đơn ra console
        printHoaDon(hoaDon);

        // Lưu hóa đơn vào CSDL
        HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
        try {
            hoaDonDAO.saveOrderWithDetails(hoaDon, maNhanVien);
            JOptionPane.showMessageDialog(this, "Lưu hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log lỗi
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Xóa đơn hàng sau khi thanh toán
        clearOrder();
    }

    private void printHoaDon(HoaDonBanHang hoaDon) {
        System.out.println("===== HÓA ĐƠN BÁN HÀNG =====");
        System.out.println("Mã hóa đơn: " + hoaDon.getMaHDBH());
        System.out.println("Ngày lập: " + hoaDon.getNgayLapHDBH());
        System.out.println("Nhân viên: " + maNhanVien);
        System.out.println("Khách hàng: " + hoaDon.getKhachHang().getTenKH() + 
            " (SDT: " + hoaDon.getKhachHang().getSoDienThoai() + ")");
        System.out.println("Hình thức thanh toán: " + (hoaDon.isHinhThucThanhToan() ? "Chuyển khoản" : "Tiền mặt"));
        System.out.println("Mã giảm giá: " + (hoaDon.getGiamGia() != null ? 
            hoaDon.getGiamGia().getMaGiam() + " (" + hoaDon.getGiamGia().getGiamGia() + "%)" : "Không có"));
        System.out.println("------------------------");
        System.out.println("Danh sách sản phẩm:");
        for (ChiTietHoaDon cthd : hoaDon.getChiTietHoaDonList()) {
            System.out.println("- " + cthd.getHangHoa().getTenHH() + ": " + 
                cthd.getSoLuong() + " x " + df.format(cthd.getHangHoa().getGiaSP()) + 
                " = " + df.format(cthd.getThanhTien()));
        }
        System.out.println("------------------------");
        System.out.println("Tổng tiền: " + df.format(hoaDon.tinhTong()));
        double chiếtKhau = hoaDon.getGiamGia() != null ? hoaDon.tinhTong() * (hoaDon.getGiamGia().getGiamGia() / 100.0) : 0;
        System.out.println("Chiết khấu: " + df.format(chiếtKhau));
        System.out.println("Thành tiền: " + df.format(hoaDon.tinhTongThanhToan()));
        System.out.println("Điểm tích lũy: " + hoaDon.getDiemTL());
        System.out.println("===== KẾT THÚC HÓA ĐƠN =====");
    }

    public void addOrderItem(HangHoa hh, int quantity) {
        if (hh == null || quantity <= 0) {
            throw new IllegalArgumentException("Hàng hóa không được null và số lượng phải lớn hơn 0");
        }
        double price = hh.getGiaSP();
        double total = price * quantity;

        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            if (orderTableModel.getValueAt(i, 0).equals(hh.getTenHH())) {
                int existingQuantity = (Integer) orderTableModel.getValueAt(i, 1);
                int newQuantity = existingQuantity + quantity;
                orderTableModel.setValueAt(newQuantity, i, 1);
                orderTableModel.setValueAt(newQuantity * price, i, 3);
                updateTotalWithCustomerDiscount(0.0);
                return;
            }
        }

        Object[] row = { hh.getTenHH(), quantity, price, total, "Xóa" };
        orderTableModel.addRow(row);
        updateTotalWithCustomerDiscount(0.0);
    }

    public void updateTotalWithCustomerDiscount(double customerDiscount) {
        double total = 0.0;
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            Double rowTotal = (Double) orderTableModel.getValueAt(i, 3);
            total += rowTotal;
        }

        double totalDiscountPercentage = discountPercentage + customerDiscount;
        boolean isLimited = totalDiscountPercentage > 50.0;
        totalDiscountPercentage = Math.min(totalDiscountPercentage, 50.0);

        double discountAmount = total * (totalDiscountPercentage / 100);
        double finalTotal = total - discountAmount;

        totalLabel.setText("Tổng: " + df.format(total));
        discountAmountLabel.setText("Chiết khấu: " + df.format(discountAmount));
        totalAfterDiscountLabel.setText("Thành tiền: " + df.format(finalTotal));

        if (isLimited) {
            discountStatusLabel.setText("Tổng giảm giá được giới hạn ở 50%");
            discountStatusLabel.setForeground(Color.BLUE);
            Timer timer = new Timer(10000, e -> { discountStatusLabel.setText(" "); });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void checkDiscountCode() {
        String discountCode = discountCodeField.getText().trim();
        if (discountCode.isEmpty()) {
            discountPercentage = 0.0;
            discountStatusLabel.setText(" ");
            discountStatusLabel.setForeground(Color.BLUE);
            updateTotalWithCustomerDiscount(0.0);
            return;
        }

        try {
            MaGiamGia_DAO discountDAO = new MaGiamGia_DAO();
            MaGiamGia discount = discountDAO.timMaGiamGia(discountCode);
            if (discount != null) {
                discountPercentage = discount.getGiamGia();
                discountStatusLabel.setText("Áp dụng thành công: Giảm " + (int)discountPercentage + "%");
                discountStatusLabel.setForeground(new Color(0, 100, 0));
            } else {
                discountPercentage = 0.0;
                discountStatusLabel.setText("Mã giảm giá không hợp lệ");
                discountStatusLabel.setForeground(Color.RED);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi
            discountStatusLabel.setText("Lỗi truy vấn mã giảm giá: " + e.getMessage());
            discountStatusLabel.setForeground(Color.RED);
        }

        Timer timer = new Timer(10000, e -> { discountStatusLabel.setText(" "); });
        timer.setRepeats(false);
        timer.start();

        updateTotalWithCustomerDiscount(0.0);
    }

    private void checkPhoneNumber() {
        String phoneNumber = phoneField.getText().trim();
        double customerDiscount = 0.0;

        if (!phoneNumber.isEmpty()) {
            try {
                KhachHang kh = KhachHang_DAO.timKhachHangTheoSDT(phoneNumber);
                if (kh != null) {
                    customerDiscount = kh.getLoaiKhachHang().getGiamGia();
                    discountStatusLabel.setText("Khách hàng: " + kh.getTenKH() + " - Giảm " + (int)customerDiscount + "%");
                    discountStatusLabel.setForeground(new Color(0, 100, 0));
                } else {
                    discountStatusLabel.setText("Không tìm thấy khách hàng");
                    discountStatusLabel.setForeground(Color.RED);
                }
            } catch (Exception e) {
                e.printStackTrace(); // Ghi log lỗi
                discountStatusLabel.setText("Lỗi truy vấn khách hàng: " + e.getMessage());
                discountStatusLabel.setForeground(Color.RED);
            }
        } else {
            discountStatusLabel.setText(" ");
        }

        Timer timer = new Timer(10000, e -> { discountStatusLabel.setText(" "); });
        timer.setRepeats(false);
        timer.start();

        updateTotalWithCustomerDiscount(customerDiscount);
    }

    public void clearOrder() {
        orderTableModel.setRowCount(0);
        discountPercentage = 0.0;
        discountCodeField.setText("");
        phoneField.setText("");
        discountStatusLabel.setText(" ");
        totalLabel.setText("Tổng: 0đ");
        discountAmountLabel.setText("Chiết khấu: 0đ");
        totalAfterDiscountLabel.setText("Thành tiền: 0đ");
        updateTotalWithCustomerDiscount(0.0);
    }

    public DefaultTableModel getOrderTableModel() {
        return orderTableModel;
    }
}
