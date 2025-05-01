package UI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import Bien.BIEN;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.Flow;

import Dao.KhachHang_DAO;
import Dao.LoaiKhachHang_DAO;
import Dao.HangHoa_DAO;
import Dao.HoaDon_DAO;
import Dao.MaGiamGia_DAO;
import Model.KhachHang;
import Model.HangHoa;
import Model.ChiTietHoaDon;
import Model.DanhSach_LoaiKhachHang;
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
    private double customerDiscount = 0.0;
    private double total = 0.0;
    private double discountAmount = 0.0;
    private double totalDiscountPercentage = 0.0;
    private double finalTotal = 0.0;
    private DecimalFormat df = new DecimalFormat("#,##0đ");
    private JLabel discountStatusLabel;
    private String maNhanVien;
    private ArrayList<LoaiKhachHang> dslkh = LoaiKhachHang_DAO.getAllLoaiKhachHang();

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
            JOptionPane.showMessageDialog(this, "Đơn hàng trống! Vui lòng thêm sản phẩm.", 
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String phoneNumber = phoneField.getText().trim();
        String discountCode = discountCodeField.getText().trim();
        boolean isBankTransfer = bankTransferCheckBox.isSelected();

        ArrayList<ChiTietHoaDon> chiTietList = new ArrayList<>();
        HangHoa_DAO hangHoaDAO = new HangHoa_DAO();
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            String tenHH = (String) orderTableModel.getValueAt(i, 0);
            int soLuong = (Integer) orderTableModel.getValueAt(i, 1);

            HangHoa hh = null;
            hh = hangHoaDAO.timHangHoaTheoTen(tenHH);
            if (hh == null) {
                JOptionPane.showMessageDialog(this, "Hàng hóa '" + tenHH + "' không tồn tại trong CSDL.", 
                                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ChiTietHoaDon cthd = new ChiTietHoaDon(soLuong, hh);
            chiTietList.add(cthd);
        }

        KhachHang khachHang = null;
        double customerDiscount = 0;
        if (!phoneNumber.isEmpty()) {
            try {
                khachHang = KhachHang_DAO.timKhachHangTheoSDT_DT(phoneNumber);
                if (khachHang != null) {
                    customerDiscount = khachHang.getLoaiKhachHang().getGiamGia();
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi truy vấn khách hàng: " + e.getMessage(), 
                                            "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if (khachHang == null && !phoneNumber.isEmpty()) {
            khachHang = new KhachHang("Khách lẻ", phoneNumber, 0);
        }

        MaGiamGia maGiamGia = null;
        double discountPercentage = 0.0;
        if (!discountCode.isEmpty()) {
            try {
                MaGiamGia_DAO discountDAO = new MaGiamGia_DAO();
                maGiamGia = discountDAO.timMaGiamGia(discountCode);
                if (maGiamGia != null) {
                    discountPercentage = maGiamGia.getGiamGia();
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi truy vấn mã giảm giá: " + e.getMessage(), 
                                            "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        double totalDiscountPercentage = discountPercentage + customerDiscount;
        totalDiscountPercentage = Math.min(totalDiscountPercentage, 50.0);

        HoaDonBanHang hoaDon = new HoaDonBanHang(LocalDate.now(), maGiamGia, khachHang, 
                                                isBankTransfer, totalDiscountPercentage);
        for (ChiTietHoaDon cthd : chiTietList) {
            hoaDon.themChiTiet(cthd);
        }
        
        if(khachHang != null) {
        	khachHang.setDiemTL(hoaDon.getdiemTL_THD());
        	khachHang.setLoaiKhachHang(dslkh.getFirst());
        	
            if( KhachHang_DAO.timKhachHangTheoSDT_DT(phoneNumber) != null ) {
            	KhachHang_DAO.suaKhachHang(khachHang);
            }
            else {
            	KhachHang_DAO.themKhachHang(khachHang);
            }
        }
        
        HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
       
        if(khachHang != null) {
        	khachHang = KhachHang_DAO.timKhachHangTheoSDT_DT(phoneNumber);
            hoaDon.setKhachHang(khachHang);
        }
        
        
        String generatedMaHDBH = null;
        try {
            // Lưu hóa đơn vào database
            hoaDonDAO.saveOrderWithDetails(hoaDon, maNhanVien);
            
            // Lấy mã hóa đơn mới nhất từ database
            generatedMaHDBH = hoaDonDAO.getLatestMaHDBH();
            if (generatedMaHDBH != null) {
                hoaDon.setMaHDBH(generatedMaHDBH); // Cập nhật mã hóa đơn vào đối tượng hoaDon
            } else {
                throw new SQLException("Không thể lấy mã hóa đơn tự sinh từ database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + e.getMessage(), 
                                        "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // In hóa đơn sau khi mã hóa đơn đã được gán
        printHoaDon(hoaDon);

        // Xóa đơn hàng sau khi in
        clearOrder();
    }

    
    private void printHoaDon(HoaDonBanHang hoaDon) {
        JFrame invoiceFrame = new JFrame("Hóa Đơn Bán Hàng - " + BIEN.TENQUAN);
        invoiceFrame.setSize(520, 650);
        invoiceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        invoiceFrame.setLocationRelativeTo(null);
        invoiceFrame.setIconImage(BIEN.LOGO_QUAN.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // ===== TIÊU ĐỀ =====
        JLabel titleLabel = new JLabel("HÓA ĐƠN BÁN HÀNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(5));

        // Thêm thông tin quán
        JLabel tenQuanLabel = new JLabel("Tên quán: " + BIEN.TENQUAN, SwingConstants.CENTER);
        tenQuanLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(tenQuanLabel);

        JLabel diaChiLabel = new JLabel("Địa chỉ: Lê Đức Thọ", SwingConstants.CENTER);
        diaChiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(diaChiLabel);

        JLabel wifiLabel = new JLabel("WiFi: Ispace | Mật khẩu: camonquykhach", SwingConstants.CENTER);
        wifiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(wifiLabel);

        panel.add(Box.createVerticalStrut(5));
        panel.add(new JSeparator());
        
        // ===== THÔNG TIN CHUNG =====

	      JPanel infoPanel = new JPanel();
	      infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
	      infoPanel.setBackground(Color.WHITE);
	      infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
	      infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
	
	      Font infoFont = new Font("Arial", Font.PLAIN, 14);
	      
	      infoPanel.add(Box.createVerticalStrut(10));
	      infoPanel.add(createInfoLabel("Mã hóa đơn: " + hoaDon.getMaHDBH(), infoFont));
	      infoPanel.add(createInfoLabel("Ngày lập: " + hoaDon.getNgayLapHDBH(), infoFont));
	      infoPanel.add(createInfoLabel("Nhân viên: " + maNhanVien, infoFont));
	      if (BIEN.SDTMAU.equals(hoaDon.getKhachHang().getSoDienThoai())) {
	    	    infoPanel.add(createInfoLabel("Khách hàng: " + hoaDon.getKhachHang().getTenKH(), infoFont));
	    	} else {
	    	    infoPanel.add(createInfoLabel("Khách hàng: " + hoaDon.getKhachHang().getTenKH() +
	    	            " (SDT: " + hoaDon.getKhachHang().getSoDienThoai() + ")", infoFont));
	    	}
	      infoPanel.add(createInfoLabel("Hình thức thanh toán: " +
	              (hoaDon.isHinhThucThanhToan() ? "Chuyển khoản" : "Tiền mặt"), infoFont));
	      infoPanel.add(createInfoLabel("Mã giảm giá: " +
	              (hoaDon.getGiamGia() != null ?
	                      hoaDon.getGiamGia().getMaGiam() + " (" + hoaDon.getGiamGia().getGiamGia() + "%)" : "Không có"),
	              infoFont));
	      
	      JPanel separatorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	      separatorPanel.setBackground(new Color(245, 245, 245));
	      JSeparator infoSeparator = new JSeparator();
	      infoSeparator.setForeground(Color.BLACK);
	      infoSeparator.setPreferredSize(new Dimension(4000,2 ));
	      separatorPanel.add(infoSeparator);
	      infoPanel.add(separatorPanel);
	
	      // Đặt infoPanel vào một container trung gian để ép căn trái
	      JPanel infoWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	      infoWrapper.setBackground(new Color(245, 245, 245));
	      infoWrapper.add(infoPanel);
	      panel.add(infoWrapper);
	      panel.add(Box.createVerticalStrut(10));

        // ===== DANH SÁCH SẢN PHẨM =====
        JPanel productPanel = new JPanel(new GridBagLayout());
        productPanel.setBackground(Color.WHITE);
        productPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Chi tiết sản phẩm", 0, 0, new Font("Arial", Font.BOLD, 14)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font headerFont = new Font("Arial", Font.BOLD, 14);
        Font cellFont = new Font("Arial", Font.PLAIN, 13);

        String[] headers = {"Sản phẩm", "Số lượng", "Giá", "Thành tiền"};
        int[] weights = {3, 1, 2, 2};
        for (int i = 0; i < headers.length; i++) {
            gbc.gridx = i;
            gbc.gridy = 0;
            gbc.weightx = weights[i];
            JLabel headerLabel = new JLabel(headers[i]);
            headerLabel.setFont(headerFont);
            if (i > 0) headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            productPanel.add(headerLabel, gbc);
        }

        int row = 1;
        for (ChiTietHoaDon cthd : hoaDon.getChiTietHoaDonList()) {
            String tenHH = cthd.getHangHoa().getTenHH();
            if (tenHH.length() > 30) tenHH = tenHH.substring(0, 27) + "...";

            gbc.gridy = row;

            gbc.gridx = 0;
            gbc.weightx = weights[0];
            productPanel.add(createCellLabel(tenHH, cellFont, SwingConstants.LEFT), gbc);

            gbc.gridx = 1;
            gbc.weightx = weights[1];
            productPanel.add(createCellLabel(String.valueOf(cthd.getSoLuong()), cellFont, SwingConstants.CENTER), gbc);

            gbc.gridx = 2;
            gbc.weightx = weights[2];
            productPanel.add(createCellLabel(df.format(cthd.getHangHoa().getGiaSP()), cellFont, SwingConstants.RIGHT), gbc);

            gbc.gridx = 3;
            gbc.weightx = weights[3];
            productPanel.add(createCellLabel(df.format(cthd.getThanhTien()), cellFont, SwingConstants.RIGHT), gbc);

            row++;
        }

        panel.add(productPanel);
        panel.add(Box.createVerticalStrut(20));


        
      //========== TỔNG TIỀN VÀ CHIẾT KHẤU ==========
	      JPanel summaryPanel = new JPanel(new GridBagLayout());
	      summaryPanel.setBackground(Color.WHITE);
	      summaryPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	      GridBagConstraints sumGbc = new GridBagConstraints();
	      sumGbc.anchor = GridBagConstraints.EAST;
	      sumGbc.fill = GridBagConstraints.HORIZONTAL;
	      sumGbc.weightx = 1.0;
	      sumGbc.insets = new Insets(2, 0, 2, 10);
	
	      sumGbc.gridx = 0;
	      sumGbc.gridy = 0;
	      JLabel totalAmountLabel = new JLabel("Tổng tiền: " + df.format(total));
	      totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
	      totalAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	      summaryPanel.add(totalAmountLabel, sumGbc);
	
	      sumGbc.gridy = 1;
	      JLabel discountLabel = new JLabel("Chiết khấu: " + df.format(discountAmount) + " (" + (int)totalDiscountPercentage + "%)");
	      discountLabel.setFont(new Font("Arial", Font.BOLD, 14));
	      discountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	      summaryPanel.add(discountLabel, sumGbc);
	
	      sumGbc.gridy = 2;
	      JLabel finalAmountLabel = new JLabel("Thành tiền: " + df.format(finalTotal));
	      finalAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
	      finalAmountLabel.setForeground(new Color(204, 0, 0));
	      finalAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	      summaryPanel.add(finalAmountLabel, sumGbc);
	
	      sumGbc.gridy = 3;
	      JLabel pointsLabel = new JLabel("Điểm tích lũy: " + hoaDon.getdiemTL_THD());
	      pointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
	      pointsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	      summaryPanel.add(pointsLabel, sumGbc);
	
	      sumGbc.gridy = 4;
	      JSeparator bottomSeparator = new JSeparator();
	      bottomSeparator.setForeground(Color.BLACK);
	      sumGbc.anchor = GridBagConstraints.CENTER;
	      summaryPanel.add(bottomSeparator, sumGbc);
	
	      panel.add(summaryPanel);
	      
	      invoiceFrame.add(panel);
	      invoiceFrame.setVisible(true);

	      InHoaDon.printPanel(panel);
    }

    // ========== HÀM HỖ TRỢ ==========
    private JLabel createInfoLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel createCellLabel(String text, Font font, int alignment) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setHorizontalAlignment(alignment);
        return label;
    }

    private JLabel createRightLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setAlignmentX(Component.RIGHT_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }
    private JLabel createRightLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.RIGHT_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
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
        this.customerDiscount = customerDiscount;
        total = 0.0;
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            Double rowTotal = (Double) orderTableModel.getValueAt(i, 3);
            total += rowTotal;
        }

        totalDiscountPercentage = discountPercentage + customerDiscount;
        boolean isLimited = totalDiscountPercentage > 50.0;
        totalDiscountPercentage = Math.min(totalDiscountPercentage, 50.0);

        discountAmount = total * (totalDiscountPercentage / 100);
        finalTotal = total - discountAmount;

        totalLabel.setText("Tổng: " + df.format(total));
        discountAmountLabel.setText("Chiết khấu: " + df.format(discountAmount) + 
                                  " (" + (int)totalDiscountPercentage + "%)");
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
            updateTotalWithCustomerDiscount(customerDiscount); 
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
            e.printStackTrace();
            discountStatusLabel.setText("Lỗi truy vấn mã giảm giá: " + e.getMessage());
            discountStatusLabel.setForeground(Color.RED);
        }

        Timer timer = new Timer(10000, e -> { discountStatusLabel.setText(" "); });
        timer.setRepeats(false);
        timer.start();

        updateTotalWithCustomerDiscount(customerDiscount); 
    }

    private void checkPhoneNumber() {
        String phoneNumber = phoneField.getText().trim();
        customerDiscount = 0.0; 

        if (!phoneNumber.isEmpty()) {
            try {
                KhachHang kh = KhachHang_DAO.timKhachHangTheoSDT_DT(phoneNumber);
                if (kh != null) {
                    customerDiscount = kh.getLoaiKhachHang().getGiamGia();
                    discountStatusLabel.setText("Khách hàng: " + kh.getTenKH() + 
                                              " - Giảm " + (int)customerDiscount + "%");
                    discountStatusLabel.setForeground(new Color(0, 100, 0));
                } else {
                    discountStatusLabel.setText("Không tìm thấy khách hàng");
                    discountStatusLabel.setForeground(Color.RED);
                }
            } catch (Exception e) {
                e.printStackTrace();
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
        customerDiscount = 0.0; 
        total = 0.0;
        discountAmount = 0.0;
        totalDiscountPercentage = 0.0;
        finalTotal = 0.0;
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