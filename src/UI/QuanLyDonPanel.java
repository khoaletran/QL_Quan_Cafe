package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Bien.BIEN;
import ConnectDB.ConnectDB;
import Dao.HoaDon_DAO;
import Dao.KhachHang_DAO;
import Model.ChiTietHoaDon;
import Model.HoaDonBanHang;
import Model.KhachHang;

public class QuanLyDonPanel extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;
    private HoaDon_DAO hoaDon_dao;
    private KhachHang_DAO khachHang_dao;
    private JTextField txtMaHD,txtSDT;
    private String maHD = "---";
    private String ngayLap = "---";
    private String maNV = "---";
    private String maKH = "---";
    private String hinhThucThanhToan = "---";
    private String maGiamGia = "---";
    private String sanPham = "Chưa có sản phẩm";
    private String sLuong = "0";
    private String gia = "0";
    private String thanhTien = "0";
    private String tongTienBanDau = "0";
    private String phanTramGiamGia = "0";
    private String chietKhau = "0";
    private String diemTL = "0";
    private String tenKH = "---";
    private String sdtKH = "---";
    private JLabel lblMaHD;
    private JLabel lblNgayLap;
    private JLabel lblMaNV;
    private JLabel lblMaKH;
    private JLabel lblHinhThucThanhToan;
    private JLabel lblMaGiamGia;
    private JPanel productPanel;
    private JLabel lBl_TongTien;
    private JLabel lBl_chietKhau;
    private JLabel lBl_thanhTien;
    private JLabel lBl_diemTL;
    private double tongTienBanDauValue;
    private double thanhTienValue;
    private JCheckBox jCb_TienMat;
    private JCheckBox jCb_ChuyenKhoan;
    private JComboBox<String> jCb_LocTheo;
    private JTextField jTf_ThoiGian;
    private JTextField jTf_TongTienTu;
    private JTextField jTf_TongTienDen;
    private JButton jBt_Loc;
    private JButton jBt_Reset;
    private List<HoaDonBanHang> list;

    public QuanLyDonPanel() {
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        hoaDon_dao = new HoaDon_DAO();
        khachHang_dao = new KhachHang_DAO();
        
        initUI();
        DocDuLieuDatabaseVaoTable();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Phần CENTER: JPanel chứa tiêu đề, JTable và bộ lọc
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(240, 242, 245));

        // Tiêu đề "Quản lý Hóa Đơn"
        JLabel lblTitle = new JLabel("Quản lý Hóa Đơn", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        tablePanel.add(lblTitle, BorderLayout.NORTH);

        // Bảng hiển thị hóa đơn
        String[] columns = { "Mã Hóa Đơn", "Mã NV", "SDT Khách Hàng", "Ngày Lập", "Tổng Tiền", "Thanh Toán" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.addMouseListener(this);

        Font tableFont = new Font("Arial", Font.PLAIN, 14);
        table.setFont(tableFont);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        DefaultTableCellRenderer textRenderer = new DefaultTableCellRenderer();
        textRenderer.setFont(tableFont);
        textRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columns.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(textRenderer);
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Hóa Đơn"));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Phần lọc bên dưới bảng
        JPanel jPn_Loc = new JPanel(new GridBagLayout());
        jPn_Loc.setBackground(new Color(240, 242, 245));
        jPn_Loc.setBorder(BorderFactory.createTitledBorder("Bộ Lọc"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Dòng 1: Checkbox hình thức thanh toán
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel jLb_ThanhToan = new JLabel("Hình thức thanh toán:");
        jLb_ThanhToan.setFont(new Font("Arial", Font.PLAIN, 14));
        jPn_Loc.add(jLb_ThanhToan, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        JPanel jPn_ThanhToan = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        jPn_ThanhToan.setBackground(new Color(240, 242, 245));
        jCb_TienMat = new JCheckBox("Tiền mặt");
        jCb_TienMat.setFont(new Font("Arial", Font.PLAIN, 14));
        jCb_TienMat.setBackground(new Color(240, 242, 245));
        jPn_ThanhToan.add(jCb_TienMat);
        jCb_ChuyenKhoan = new JCheckBox("Chuyển khoản");
        jCb_ChuyenKhoan.setFont(new Font("Arial", Font.PLAIN, 14));
        jCb_ChuyenKhoan.setBackground(new Color(240, 242, 245));
        jPn_ThanhToan.add(jCb_ChuyenKhoan);
        jPn_Loc.add(jPn_ThanhToan, gbc);

        // Dòng 2: Lọc theo thời gian
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel jLb_LocTheo = new JLabel("Lọc theo thời gian:");
        jLb_LocTheo.setFont(new Font("Arial", Font.PLAIN, 14));
        jPn_Loc.add(jLb_LocTheo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        jCb_LocTheo = new JComboBox<>(new String[]{"Không lọc", "Lọc theo ngày", "Lọc theo tháng", "Lọc theo năm"});
        jCb_LocTheo.setFont(new Font("Arial", Font.PLAIN, 14));
        jCb_LocTheo.setSelectedIndex(0);
        jPn_Loc.add(jCb_LocTheo, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        jTf_ThoiGian = new JTextField(10);
        jTf_ThoiGian.setFont(new Font("Arial", Font.PLAIN, 14));
        jTf_ThoiGian.setEnabled(false);
        jPn_Loc.add(jTf_ThoiGian, gbc);

        // Dòng 3: Lọc theo tổng tiền
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel jLb_TongTien = new JLabel("Tổng tiền (VNĐ):");
        jLb_TongTien.setFont(new Font("Arial", Font.PLAIN, 14));
        jPn_Loc.add(jLb_TongTien, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        JPanel jPn_TongTien = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        jPn_TongTien.setBackground(new Color(240, 242, 245));
        jTf_TongTienTu = new JTextField(8);
        jTf_TongTienTu.setFont(new Font("Arial", Font.PLAIN, 14));
        jPn_TongTien.add(jTf_TongTienTu);
        JLabel jLb_Den = new JLabel("đến");
        jLb_Den.setFont(new Font("Arial", Font.PLAIN, 14));
        jPn_TongTien.add(jLb_Den);
        jTf_TongTienDen = new JTextField(8);
        jTf_TongTienDen.setFont(new Font("Arial", Font.PLAIN, 14));
        jPn_TongTien.add(jTf_TongTienDen);
        jPn_Loc.add(jPn_TongTien, gbc);

        // Dòng 4: Nút Lọc và Reset
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.gridwidth = 3;
        JPanel jPn_NutLoc = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        jPn_NutLoc.setBackground(new Color(240, 242, 245));

        jBt_Loc = new JButton("Lọc");
        jBt_Loc.setFont(new Font("Arial", Font.PLAIN, 14));
        jPn_NutLoc.add(jBt_Loc);
        jBt_Loc.addActionListener(e -> locHoaDon());

        jBt_Reset = new JButton("Reset");
        jBt_Reset.setFont(new Font("Arial", Font.PLAIN, 14));
        jPn_NutLoc.add(jBt_Reset);
        jBt_Reset.addActionListener(e -> resetLoc());

        jPn_Loc.add(jPn_NutLoc, gbc);

        // Thêm ItemListener cho jCb_LocTheo để bật/tắt jTf_ThoiGian
        jCb_LocTheo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selected = jCb_LocTheo.getSelectedItem().toString();
                jTf_ThoiGian.setEnabled(!selected.equals("Không lọc"));
                jTf_ThoiGian.setText("");
                if (selected.equals("Lọc theo ngày")) {
                    jTf_ThoiGian.setToolTipText("Nhập định dạng YYYY-MM-DD");
                } else if (selected.equals("Lọc theo tháng")) {
                    jTf_ThoiGian.setToolTipText("Nhập định dạng YYYY-MM");
                } else if (selected.equals("Lọc theo năm")) {
                    jTf_ThoiGian.setToolTipText("Nhập định dạng YYYY");
                } else {
                    jTf_ThoiGian.setToolTipText(null);
                }
            }
        });

        tablePanel.add(jPn_Loc, BorderLayout.SOUTH);
        add(tablePanel, BorderLayout.CENTER);

        // Phần EAST: JPanel chứa thông tin chi tiết hóa đơn
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setPreferredSize(new Dimension(400, 650));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Tiêu đề "Hóa Đơn Bán Hàng"
        JLabel lblDetailTitle = new JLabel("HÓA ĐƠN BÁN HÀNG", JLabel.CENTER);
        lblDetailTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblDetailTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        inputPanel.add(lblDetailTitle, BorderLayout.NORTH);

        // Panel chính chứa thông tin hóa đơn
        JPanel invoicePanel = new JPanel();
        invoicePanel.setLayout(new BoxLayout(invoicePanel, BoxLayout.Y_AXIS));
        invoicePanel.setBackground(Color.WHITE);

        // Separator
        invoicePanel.add(new JSeparator());

        // Thông tin chung
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        Font infoFont = new Font("Arial", Font.PLAIN, 14);
        infoPanel.add(Box.createVerticalStrut(10));

        lblMaHD = createInfoLabel("Mã hóa đơn: " + maHD, infoFont);
        lblNgayLap = createInfoLabel("Ngày lập: " + ngayLap, infoFont);
        lblMaNV = createInfoLabel("Nhân viên: " + maNV, infoFont);
        lblMaKH = createInfoLabel("Khách hàng: " + tenKH + "(SDT: " + sdtKH + ")", infoFont);
        lblHinhThucThanhToan = createInfoLabel("Hình thức thanh toán: " + hinhThucThanhToan, infoFont);
        lblMaGiamGia = createInfoLabel("Mã giảm giá: " + maGiamGia, infoFont);

        infoPanel.add(lblMaHD);
        infoPanel.add(lblNgayLap);
        infoPanel.add(lblMaNV);
        infoPanel.add(lblMaKH);
        infoPanel.add(lblHinhThucThanhToan);
        infoPanel.add(lblMaGiamGia);

        JPanel separatorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        separatorPanel.setBackground(new Color(245, 245, 245));
        JSeparator infoSeparator = new JSeparator();
        infoSeparator.setForeground(Color.BLACK);
        infoSeparator.setPreferredSize(new Dimension(4000, 2));
        separatorPanel.add(infoSeparator);
        infoPanel.add(separatorPanel);

        JPanel infoWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        infoWrapper.setBackground(new Color(245, 245, 245));
        infoWrapper.add(infoPanel);
        invoicePanel.add(infoWrapper);
        invoicePanel.add(Box.createVerticalStrut(10));

        // Danh sách sản phẩm
        productPanel = new JPanel(new GridBagLayout());
        productPanel.setBackground(Color.WHITE);
        productPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), 
            "Chi tiết sản phẩm", 0, 0, new Font("Arial", Font.BOLD, 14)));

        GridBagConstraints gbcProduct = new GridBagConstraints();
        gbcProduct.insets = new Insets(4, 4, 4, 4);
        gbcProduct.fill = GridBagConstraints.HORIZONTAL;

        Font headerFont = new Font("Arial", Font.BOLD, 14);
        String[] headers = {"Sản phẩm", "Số lượng", "Giá", "Thành tiền"};
        int[] weights = {3, 1, 2, 2};
        for (int i = 0; i < headers.length; i++) {
            gbcProduct.gridx = i;
            gbcProduct.gridy = 0;
            gbcProduct.weightx = weights[i];
            JLabel headerLabel = new JLabel(headers[i]);
            headerLabel.setFont(headerFont);
            if (i > 0) headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            productPanel.add(headerLabel, gbcProduct);
        }

        Font cellFont = new Font("Arial", Font.PLAIN, 13);
        gbcProduct.gridy = 1;
        gbcProduct.gridx = 0;
        gbcProduct.weightx = weights[0];
        productPanel.add(createCellLabel("[Chưa có sản phẩm]", cellFont, SwingConstants.LEFT), gbcProduct);
        gbcProduct.gridx = 1;
        gbcProduct.weightx = weights[1];
        productPanel.add(createCellLabel("0", cellFont, SwingConstants.CENTER), gbcProduct);
        gbcProduct.gridx = 2;
        gbcProduct.weightx = weights[2];
        productPanel.add(createCellLabel("0", cellFont, SwingConstants.RIGHT), gbcProduct);
        gbcProduct.gridx = 3;
        gbcProduct.weightx = weights[3];
        productPanel.add(createCellLabel("0", cellFont, SwingConstants.RIGHT), gbcProduct);

        invoicePanel.add(productPanel);
        invoicePanel.add(Box.createVerticalStrut(20));

        // Tổng tiền và chiết khấu
        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBackground(Color.WHITE);
        GridBagConstraints sumGbc = new GridBagConstraints();
        sumGbc.anchor = GridBagConstraints.EAST;
        sumGbc.fill = GridBagConstraints.HORIZONTAL;
        sumGbc.weightx = 1.0;
        sumGbc.insets = new Insets(2, 0, 2, 10);

        sumGbc.gridx = 0;
        sumGbc.gridy = 0;
        lBl_TongTien = new JLabel("Tổng tiền: " + tongTienBanDau);
        lBl_TongTien.setFont(new Font("Arial", Font.BOLD, 14));
        lBl_TongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(lBl_TongTien, sumGbc);

        sumGbc.gridy = 1;
        lBl_chietKhau = new JLabel("Chiết khấu: " + chietKhau + "(" + phanTramGiamGia + ")");
        lBl_chietKhau.setFont(new Font("Arial", Font.BOLD, 14));
        lBl_chietKhau.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(lBl_chietKhau, sumGbc);

        sumGbc.gridy = 2;
        lBl_thanhTien = new JLabel("Thành tiền: " + thanhTien);
        lBl_thanhTien.setFont(new Font("Arial", Font.BOLD, 14));
        lBl_thanhTien.setForeground(new Color(204, 0, 0));
        lBl_thanhTien.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(lBl_thanhTien, sumGbc);

        sumGbc.gridy = 3;
        lBl_diemTL = new JLabel("Điểm tích lũy: " + diemTL);
        lBl_diemTL.setFont(new Font("Arial", Font.PLAIN, 14));
        lBl_diemTL.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(lBl_diemTL, sumGbc);

        sumGbc.gridy = 4;
        JSeparator bottomSeparator = new JSeparator();
        bottomSeparator.setForeground(Color.BLACK);
        sumGbc.anchor = GridBagConstraints.CENTER;
        summaryPanel.add(bottomSeparator, sumGbc);

        invoicePanel.add(summaryPanel);
        invoicePanel.add(Box.createVerticalStrut(20));

        // Phần tìm kiếm: Nhập mã HD, nút Tìm, In Hóa Đơn
        JPanel jPn_TimKiem = new JPanel(new GridBagLayout());
        jPn_TimKiem.setBackground(Color.WHITE);
        jPn_TimKiem.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Chức năng tìm kiếm",
            0, 0, new Font("Arial", Font.BOLD, 14)));
        GridBagConstraints searchGbc = new GridBagConstraints();
        searchGbc.insets = new Insets(5, 5, 5, 5);
        searchGbc.fill = GridBagConstraints.HORIZONTAL;

        searchGbc.gridx = 0;
        searchGbc.gridy = 0;
        searchGbc.weightx = 0;
        JLabel jLb_MaHDInput = new JLabel("Nhập mã HD:");
        jLb_MaHDInput.setFont(new Font("Arial", Font.PLAIN, 14));
        jPn_TimKiem.add(jLb_MaHDInput, searchGbc);

        searchGbc.gridx = 1;
        searchGbc.gridy = 0;
        searchGbc.weightx = 1.0;
        txtMaHD = new JTextField(15);
        txtMaHD.setFont(new Font("Arial", Font.PLAIN, 14));
        jPn_TimKiem.add(txtMaHD, searchGbc);
        txtMaHD.getDocument().addDocumentListener(timKiemDongMaHD());
        
        searchGbc.gridx = 0;
        searchGbc.gridy = 1;
        searchGbc.weightx = 0;
        JLabel jLB_timsdt = new JLabel("Nhập mã SDT:");
        jLB_timsdt.setFont(new Font("Arial", Font.PLAIN, 14));
        jPn_TimKiem.add(jLB_timsdt, searchGbc);

        searchGbc.gridx = 1;
        searchGbc.gridy = 1;
        searchGbc.weightx = 1.0;
        txtSDT = new JTextField(15);
        txtSDT.setFont(new Font("Arial", Font.PLAIN, 14));
        jPn_TimKiem.add(txtSDT, searchGbc);
        txtSDT.getDocument().addDocumentListener(timKiemDongSDT());

        searchGbc.gridx = 1;
        searchGbc.gridy = 2;
        searchGbc.weightx = 0;
        JButton jBt_InHoaDon = new JButton("In Hóa Đơn ( Ctrl + P )");
        jBt_InHoaDon.setFont(new Font("Arial", Font.PLAIN, 14));
        jBt_InHoaDon.setToolTipText("Ctrl + P để in");
        jPn_TimKiem.add(jBt_InHoaDon, searchGbc);
        jBt_InHoaDon.addActionListener(e -> printHoaDon());

        invoicePanel.add(jPn_TimKiem);

        inputPanel.add(invoicePanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.EAST);

        // Thêm in cho Ctrl + P
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke("control P"), "printInvoice");
        getActionMap().put("printInvoice", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printHoaDon();
            }
        });
    }

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

    private void loadHoaDon() {
        lblMaHD.setText("Mã hóa đơn: " + maHD);
        lblNgayLap.setText("Ngày lập: " + ngayLap);
        lblMaNV.setText("Nhân viên: " + maNV);
        lblMaKH.setText("Khách hàng: " + tenKH + "(SDT: " + sdtKH + ")");
        lblHinhThucThanhToan.setText("Hình thức thanh toán: " + hinhThucThanhToan);

        DecimalFormat df = new DecimalFormat("#,##0đ");
        double chietKhauValue = tongTienBanDauValue - thanhTienValue;
        chietKhauValue = Math.max(0, chietKhauValue);

        lBl_TongTien.setText("Tổng tiền: " + tongTienBanDau);
        lBl_thanhTien.setText("Thành tiền: " + thanhTien);
        lBl_chietKhau.setText("Chiết khấu: " + df.format(chietKhauValue) + " (" + phanTramGiamGia + "%)");
        lBl_diemTL.setText("Điểm tích lũy: " + diemTL);
    }

    public void DocDuLieuDatabaseVaoTable() {
        list = hoaDon_dao.getAllHoaDon();
        tableModel.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,##0đ");
        for (HoaDonBanHang hd : list) {
            String sdtKH = "---";
            KhachHang kh = KhachHang_DAO.getKhachHangTheoMaKH(hd.getMaKHGia());
            if (kh != null && !BIEN.SDTMAU.equals(kh.getSoDienThoai())) {
                sdtKH = kh.getSoDienThoai();
            }

            double thanhTienValue = hd.getTongtienGia();
            double phanTramGiam = hd.getPhanTramGiamGia() / 100.0;
            double tongTienBanDauValue = thanhTienValue / (1 - phanTramGiam);

            tableModel.addRow(new Object[] {
                hd.getMaHDBH(),
                hd.getMaNVGia(),
                sdtKH,
                hd.getNgayLapHDBH(),
                df.format(tongTienBanDauValue),
                hd.isHinhThucThanhToan() ? "Chuyển khoản" : "Tiền mặt"
            });
        }
    }
    
    public void reload() {
        tableModel.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,##0đ");
        for (HoaDonBanHang hd : list) {
            String sdtKH = "---";
            KhachHang kh = KhachHang_DAO.getKhachHangTheoMaKH(hd.getMaKHGia());
            if (kh != null && !BIEN.SDTMAU.equals(kh.getSoDienThoai())) {
                sdtKH = kh.getSoDienThoai();
            }

            double thanhTienValue = hd.getTongtienGia();
            double phanTramGiam = hd.getPhanTramGiamGia() / 100.0;
            double tongTienBanDauValue = thanhTienValue / (1 - phanTramGiam);

            tableModel.addRow(new Object[] {
                hd.getMaHDBH(),
                hd.getMaNVGia(),
                sdtKH,
                hd.getNgayLapHDBH(),
                df.format(tongTienBanDauValue),
                hd.isHinhThucThanhToan() ? "Chuyển khoản" : "Tiền mặt"
            });
        }
    }
    
    private DocumentListener timKiemDongMaHD() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                thucHienTimKiem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                thucHienTimKiem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                thucHienTimKiem();
            }

            private void thucHienTimKiem() {
                String mahd = txtMaHD.getText().trim();
                if (mahd.isEmpty()) {
                	DocDuLieuDatabaseVaoTable();
                } else {
                    list = HoaDon_DAO.getDSHoaDonTheoMa(mahd);
                }
                reload();
            }
        };
    }
    
    private DocumentListener timKiemDongSDT() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                thucHienTimKiem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                thucHienTimKiem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                thucHienTimKiem();
            }

            private void thucHienTimKiem() {
                String sdt = txtSDT.getText().trim();
                if (sdt.isEmpty()) {
                	DocDuLieuDatabaseVaoTable();
                } else {
                    list = HoaDon_DAO.getDSHoaDonTheoSDT(sdt);
                }
                reload();
            }
        };
    }

    private void updateProductPanel(String maHDBH) {
        List<ChiTietHoaDon> chiTietList;
        try {
            chiTietList = hoaDon_dao.getChiTietSanPhamTheoMaHD(maHDBH);
        } catch (RuntimeException e) {
            e.printStackTrace();
            chiTietList = new ArrayList<>();
            JOptionPane.showMessageDialog(this, 
                "Không thể tải chi tiết sản phẩm: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        productPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font headerFont = new Font("Arial", Font.BOLD, 14);
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

        Font cellFont = new Font("Arial", Font.PLAIN, 13);
        int row = 1;
        DecimalFormat df = new DecimalFormat("#,##0đ");
        for (ChiTietHoaDon ct : chiTietList) {
            gbc.gridy = row;

            gbc.gridx = 0;
            gbc.weightx = weights[0];
            productPanel.add(createCellLabel(ct.getHangHoa().getTenHH(), cellFont, SwingConstants.LEFT), gbc);

            gbc.gridx = 1;
            gbc.weightx = weights[1];
            productPanel.add(createCellLabel(String.valueOf(ct.getSoLuong()), cellFont, SwingConstants.CENTER), gbc);

            gbc.gridx = 2;
            gbc.weightx = weights[2];
            productPanel.add(createCellLabel(df.format(ct.getHangHoa().getGiaSP()), cellFont, SwingConstants.RIGHT), gbc);

            gbc.gridx = 3;
            gbc.weightx = weights[3];
            productPanel.add(createCellLabel(df.format(ct.getThanhTien()), cellFont, SwingConstants.RIGHT), gbc);

            row++;
        }

        if (chiTietList.isEmpty()) {
            gbc.gridy = 1;
            gbc.gridx = 0;
            gbc.weightx = weights[0];
            productPanel.add(createCellLabel("[Chưa có sản phẩm]", cellFont, SwingConstants.LEFT), gbc);
            gbc.gridx = 1;
            gbc.weightx = weights[1];
            productPanel.add(createCellLabel("0", cellFont, SwingConstants.CENTER), gbc);
            gbc.gridx = 2;
            gbc.weightx = weights[2];
            productPanel.add(createCellLabel("0", cellFont, SwingConstants.RIGHT), gbc);
            gbc.gridx = 3;
            gbc.weightx = weights[3];
            productPanel.add(createCellLabel("0", cellFont, SwingConstants.RIGHT), gbc);
        }

        productPanel.revalidate();
        productPanel.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            txtMaHD.setText(tableModel.getValueAt(selectedRow, 0).toString());
            
            maHD = tableModel.getValueAt(selectedRow, 0).toString();
            maNV = tableModel.getValueAt(selectedRow, 1).toString();
            sdtKH = tableModel.getValueAt(selectedRow, 2).toString();
            ngayLap = tableModel.getValueAt(selectedRow, 3).toString();
            hinhThucThanhToan = tableModel.getValueAt(selectedRow, 5).toString();
            
            HoaDonBanHang hd = hoaDon_dao.getHoaDonTheoMa(maHD);
            if (hd != null) {
                maKH = hd.getMaKHGia();
                KhachHang kh = khachHang_dao.getKhachHangTheoMaKH(maKH);
                DecimalFormat df = new DecimalFormat("#,##0đ");
                double thanhTienValue = hd.getTongtienGia();
                double phanTramGiam = hd.getPhanTramGiamGia() / 100.0;
                double tongTienBanDauValue = thanhTienValue / (1 - phanTramGiam);

                thanhTien = df.format(thanhTienValue);
                tongTienBanDau = df.format(tongTienBanDauValue);
                phanTramGiamGia = String.valueOf(hd.getPhanTramGiamGia());
                diemTL = String.valueOf(hd.getdiemTL());
                tenKH = kh != null ? kh.getTenKH() : "Khách lẻ";
                sdtKH = kh != null && !BIEN.SDTMAU.equals(kh.getSoDienThoai()) ? kh.getSoDienThoai() : "---";

                this.tongTienBanDauValue = tongTienBanDauValue;
                this.thanhTienValue = thanhTienValue;
            } else {
                maKH = "---";
                tenKH = "---";
                sdtKH = "---";
                thanhTien = "0đ";
                tongTienBanDau = "0đ";
                phanTramGiamGia = "0";
                this.tongTienBanDauValue = 0;
                this.thanhTienValue = 0;
                diemTL = "0";
            }

            loadHoaDon();
            updateProductPanel(maHD);
        }
    }

    private void timHoaDon() {
    	// CHÓ NHÃ NGU
        String maHDInput = txtMaHD.getText().trim();
        
        if (maHDInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hóa đơn!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        HoaDonBanHang hd = hoaDon_dao.getHoaDonTheoMa(maHDInput);
        if (hd != null) {
            maHD = hd.getMaHDBH();
            maNV = hd.getMaNVGia();
            maKH = hd.getMaKHGia();
            ngayLap = hd.getNgayLapHDBH().toString();
            hinhThucThanhToan = hd.isHinhThucThanhToan() ? "Chuyển khoản" : "Tiền mặt";
            maGiamGia = (hd.getGiamGia() != null) ? hd.getGiamGia().getMaGiam() : "Không có";
            
            DecimalFormat df = new DecimalFormat("#,##0đ");
            thanhTienValue = hd.getTongtienGia();
            double phanTramGiam = hd.getPhanTramGiamGia() / 100.0;
            tongTienBanDauValue = thanhTienValue / (1 - phanTramGiam);

            KhachHang kh = khachHang_dao.getKhachHangTheoMaKH(maKH);
            tenKH = kh != null ? kh.getTenKH() : "Khách lẻ";
            sdtKH = kh != null && !BIEN.SDTMAU.equals(kh.getSoDienThoai()) ? kh.getSoDienThoai() : "---";

            thanhTien = df.format(thanhTienValue);
            tongTienBanDau = df.format(tongTienBanDauValue);
            phanTramGiamGia = String.valueOf(hd.getPhanTramGiamGia());
            diemTL = String.valueOf(hd.getdiemTL());

            loadHoaDon();
            updateProductPanel(maHD);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(maHD)) {
                    table.setRowSelectionInterval(i, i);
                    table.scrollRectToVisible(table.getCellRect(i, 0, true));
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn với mã: " + maHDInput, 
                                         "Lỗi", JOptionPane.ERROR_MESSAGE);
            maHD = "---";
            maNV = "---";
            maKH = "---";
            ngayLap = "---";
            hinhThucThanhToan = "---";
            maGiamGia = "---";
            thanhTien = "0đ";
            tongTienBanDau = "0đ";
            phanTramGiamGia = "0";
            diemTL = "0";
            tenKH = "---";
            sdtKH = "---";
            tongTienBanDauValue = 0;
            thanhTienValue = 0;

            loadHoaDon();
            updateProductPanel(null);
            table.clearSelection();
        }
    }

    private void printHoaDon() {
        if (maHD.equals("---")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoặc tìm một hóa đơn để in!", 
                                         "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        HoaDonBanHang hoaDon = hoaDon_dao.getHoaDonTheoMa(maHD);
        if (hoaDon == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn với mã: " + maHD, 
                                         "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame invoiceFrame = new JFrame("Hóa Đơn Bán Hàng - " + BIEN.TENQUAN);
        invoiceFrame.setSize(520, 650);
        invoiceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        invoiceFrame.setLocationRelativeTo(null);
        invoiceFrame.setIconImage(BIEN.LOGO_QUAN.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("HÓA ĐƠN BÁN HÀNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(5));

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

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        Font infoFont = new Font("Arial", Font.PLAIN, 14);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(createInfoLabel("Mã hóa đơn: " + hoaDon.getMaHDBH(), infoFont));
        infoPanel.add(createInfoLabel("Ngày lập: " + hoaDon.getNgayLapHDBH(), infoFont));
        infoPanel.add(createInfoLabel("Nhân viên: " + hoaDon.getMaNVGia(), infoFont));
        
        String khachHangInfo = tenKH;
        if (!"---".equals(sdtKH)) {
            khachHangInfo += " (SDT: " + sdtKH + ")";
        }
        infoPanel.add(createInfoLabel("Khách hàng: " + khachHangInfo, infoFont));
        
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
        infoSeparator.setPreferredSize(new Dimension(4000, 2));
        separatorPanel.add(infoSeparator);
        infoPanel.add(separatorPanel);

        JPanel infoWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        infoWrapper.setBackground(new Color(245, 245, 245));
        infoWrapper.add(infoPanel);
        panel.add(infoWrapper);
        panel.add(Box.createVerticalStrut(10));

        JPanel productPanel = new JPanel(new GridBagLayout());
        productPanel.setBackground(Color.WHITE);
        productPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), 
            "Chi tiết sản phẩm", 0, 0, new Font("Arial", Font.BOLD, 14)));

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
        DecimalFormat df = new DecimalFormat("#,##0đ");
        List<ChiTietHoaDon> chiTietList = hoaDon_dao.getChiTietSanPhamTheoMaHD(maHD);
        for (ChiTietHoaDon cthd : chiTietList) {
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

        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBackground(Color.WHITE);
        GridBagConstraints sumGbc = new GridBagConstraints();
        sumGbc.anchor = GridBagConstraints.EAST;
        sumGbc.fill = GridBagConstraints.HORIZONTAL;
        sumGbc.weightx = 1.0;
        sumGbc.insets = new Insets(2, 0, 2, 10);

        sumGbc.gridx = 0;
        sumGbc.gridy = 0;
        JLabel totalAmountLabel = new JLabel("Tổng tiền: " + df.format(tongTienBanDauValue));
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(totalAmountLabel, sumGbc);

        sumGbc.gridy = 1;
        JLabel discountLabel = new JLabel("Chiết khấu: " + df.format(tongTienBanDauValue - thanhTienValue) + 
                                         " (" + phanTramGiamGia + "%)");
        discountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        discountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(discountLabel, sumGbc);

        sumGbc.gridy = 2;
        JLabel finalAmountLabel = new JLabel("Thành tiền: " + df.format(thanhTienValue));
        finalAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        finalAmountLabel.setForeground(new Color(204, 0, 0));
        finalAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(finalAmountLabel, sumGbc);

        sumGbc.gridy = 3;
        JLabel pointsLabel = new JLabel("Điểm tích lũy: " + hoaDon.getdiemTL());
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

    private void locHoaDon() {
        List<HoaDonBanHang> list = hoaDon_dao.getAllHoaDon();
        List<HoaDonBanHang> filteredList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#,##0đ");

        boolean filterTienMat = jCb_TienMat.isSelected();
        boolean filterChuyenKhoan = jCb_ChuyenKhoan.isSelected();
        String locTheo = (String) jCb_LocTheo.getSelectedItem();
        String thoiGian = jTf_ThoiGian.getText().trim();
        String tongTienTu = jTf_TongTienTu.getText().trim();
        String tongTienDen = jTf_TongTienDen.getText().trim();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        for (HoaDonBanHang hd : list) {
            boolean match = true;

            if (filterTienMat || filterChuyenKhoan) {
                if (filterTienMat && filterChuyenKhoan) {
                } else if (filterTienMat && hd.isHinhThucThanhToan()) {
                    match = false;
                } else if (filterChuyenKhoan && !hd.isHinhThucThanhToan()) {
                    match = false;
                }
            }

            // Lọc theo thời gian
            if (!locTheo.equals("Không lọc") && !thoiGian.isEmpty()) {
                try {
                    String hdDateStr = hd.getNgayLapHDBH().toString();
                    if (locTheo.equals("Lọc theo ngày")) {
                        if (!hdDateStr.equals(thoiGian)) {
                            match = false;
                        }
                    } else if (locTheo.equals("Lọc theo tháng")) {
                        String hdMonth = monthFormat.format(dateFormat.parse(hdDateStr));
                        if (!hdMonth.equals(thoiGian)) {
                            match = false;
                        }
                    } else if (locTheo.equals("Lọc theo năm")) {
                        String hdYear = yearFormat.format(dateFormat.parse(hdDateStr));
                        if (!hdYear.equals(thoiGian)) {
                            match = false;
                        }
                    }
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(this, "Định dạng thời gian không hợp lệ!", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Lọc theo tổng tiền
            double thanhTienValue = hd.getTongtienGia();
            double phanTramGiam = hd.getPhanTramGiamGia() / 100.0;
            double tongTienBanDauValue = thanhTienValue / (1 - phanTramGiam);
            try {
                if (!tongTienTu.isEmpty()) {
                    double tu = Double.parseDouble(tongTienTu);
                    if (tongTienBanDauValue < tu) {
                        match = false;
                    }
                }
                if (!tongTienDen.isEmpty()) {
                    double den = Double.parseDouble(tongTienDen);
                    if (tongTienBanDauValue > den) {
                        match = false;
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tổng tiền phải là số hợp lệ!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (match) {
                filteredList.add(hd);
            }
        }

        // Cập nhật bảng
        tableModel.setRowCount(0);
        for (HoaDonBanHang hd : filteredList) {
            String sdtKH = "---";
            KhachHang kh = khachHang_dao.getKhachHangTheoMaKH(hd.getMaKHGia());
            if (kh != null && !BIEN.SDTMAU.equals(kh.getSoDienThoai())) {
                sdtKH = kh.getSoDienThoai();
            }

            double thanhTienValue = hd.getTongtienGia();
            double phanTramGiam = hd.getPhanTramGiamGia() / 100.0;
            double tongTienBanDauValue = thanhTienValue / (1 - phanTramGiam);

            tableModel.addRow(new Object[] {
                hd.getMaHDBH(),
                hd.getMaNVGia(),
                sdtKH,
                hd.getNgayLapHDBH(),
                df.format(tongTienBanDauValue),
                hd.isHinhThucThanhToan() ? "Chuyển khoản" : "Tiền mặt"
            });
        }

        if (filteredList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn phù hợp!", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void resetLoc() {
        jCb_TienMat.setSelected(false);
        jCb_ChuyenKhoan.setSelected(false);
        jCb_LocTheo.setSelectedIndex(0);
        jTf_ThoiGian.setText("");
        jTf_TongTienTu.setText("");
        jTf_TongTienDen.setText("");
        txtMaHD.setText("");
        table.clearSelection();
        DocDuLieuDatabaseVaoTable();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}