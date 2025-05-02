package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import ConnectDB.ConnectDB;
import Dao.HoaDon_DAO;
import Model.ChiTietHoaDon;
import Model.HoaDonBanHang;

public class QuanLyDonPanel extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;
    private HoaDon_DAO hoaDon_dao;
    private JTextField txtMaHD;
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

    public QuanLyDonPanel() {
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        hoaDon_dao = new HoaDon_DAO();
        
        initUI();
        DocDuLieuDatabaseVaoTable();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Phần CENTER: JPanel chứa tiêu đề và JTable
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(240, 242, 245)); // Xám nhạt

        // Tiêu đề "Quản lý Hóa Đơn"
        JLabel lblTitle = new JLabel("Quản lý Hóa Đơn", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        tablePanel.add(lblTitle, BorderLayout.NORTH);

        // Bảng hiển thị hóa đơn
        String[] columns = { "Mã Hóa Đơn", "Mã NV", "Mã KH", "Ngày Lập", "Giảm Giá(%)", "Hình Thức Thanh Toán" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.addMouseListener(this);

        // Tăng kích thước chữ trong bảng
        Font tableFont = new Font("Arial", Font.PLAIN, 14);
        table.setFont(tableFont);

        // Làm đậm chữ trong header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        
        //căn giữa tên collumn
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Renderer tùy chỉnh cho các cột văn bản
        DefaultTableCellRenderer textRenderer = new DefaultTableCellRenderer();
        textRenderer.setFont(tableFont);
        textRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columns.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(textRenderer);
        }

        // Set chiều rộng cho từng cột
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // Mã Hóa Đơn
        table.getColumnModel().getColumn(1).setPreferredWidth(80);  // Mã NV
        table.getColumnModel().getColumn(2).setPreferredWidth(80);  // Mã KH
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Ngày Lập
        table.getColumnModel().getColumn(4).setPreferredWidth(80);  // Giảm Giá
        table.getColumnModel().getColumn(5).setPreferredWidth(120); // Hình Thức Thanh Toán
        

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);
        
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Hóa Đơn"));

        // Phần EAST: JPanel chứa thông tin chi tiết hóa đơn
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setPreferredSize(new java.awt.Dimension(400, 650));
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

        // Khởi tạo và thêm các JLabel
        lblMaHD = createInfoLabel("Mã hóa đơn: " + maHD, infoFont);
        lblNgayLap = createInfoLabel("Ngày lập: " + ngayLap, infoFont);
        lblMaNV = createInfoLabel("Nhân viên: " + maNV, infoFont);
        lblMaKH = createInfoLabel("Khách hàng: " + maKH, infoFont);
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
        infoSeparator.setPreferredSize(new java.awt.Dimension(4000, 2));
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

        // Dòng mẫu (chưa có dữ liệu)
        Font cellFont = new Font("Arial", Font.PLAIN, 13);
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
         lBl_TongTien = new JLabel("Tổng tiền: "+tongTienBanDau);
         lBl_TongTien.setFont(new Font("Arial", Font.BOLD, 14));
         lBl_TongTien.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(lBl_TongTien, sumGbc);

        sumGbc.gridy = 1;
         lBl_chietKhau = new JLabel("Chiết khấu: "+chietKhau+"("+phanTramGiamGia+")");
         lBl_chietKhau.setFont(new Font("Arial", Font.BOLD, 14));
         lBl_chietKhau.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(lBl_chietKhau, sumGbc);

        sumGbc.gridy = 2;
         lBl_thanhTien = new JLabel("Thành tiền: "+thanhTien);
         lBl_thanhTien.setFont(new Font("Arial", Font.BOLD, 14));
         lBl_thanhTien.setForeground(new Color(204, 0, 0));
         lBl_thanhTien.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(lBl_thanhTien, sumGbc);

        sumGbc.gridy = 3;
         lBl_diemTL = new JLabel("Điểm tích lũy: "+diemTL);
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

        // Phần tìm kiếm: Nhập mã HD, nút Tìm, nút In Hóa Đơn
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(Color.WHITE);
        GridBagConstraints searchGbc = new GridBagConstraints();
        searchGbc.insets = new Insets(5, 5, 5, 5);
        searchGbc.fill = GridBagConstraints.HORIZONTAL;

        // Nhãn "Nhập mã HD"
        searchGbc.gridx = 0;
        searchGbc.gridy = 0;
        searchGbc.weightx = 0;
        JLabel lblMaHDInput = new JLabel("Nhập mã HD:");
        lblMaHDInput.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(lblMaHDInput, searchGbc);

        // JTextField để nhập mã HD
        searchGbc.gridx = 1;
        searchGbc.gridy = 0;
        searchGbc.weightx = 1.0;
        txtMaHD = new JTextField(15);
        txtMaHD.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(txtMaHD, searchGbc);

        // Nút Tìm
        searchGbc.gridx = 2;
        searchGbc.gridy = 0;
        searchGbc.weightx = 0;
        JButton btnTim = new JButton("Tìm");
        btnTim.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(btnTim, searchGbc);

        // Nút In Hóa Đơn
        searchGbc.gridx = 3;
        searchGbc.gridy = 0;
        searchGbc.weightx = 0;
        JButton btnInHoaDon = new JButton("In Hóa Đơn");
        btnInHoaDon.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(btnInHoaDon, searchGbc);

        invoicePanel.add(searchPanel);

        inputPanel.add(invoicePanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.EAST);
    }

    // Hàm hỗ trợ
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
        lblMaKH.setText("Khách hàng: " + maKH);
        lblHinhThucThanhToan.setText("Hình thức thanh toán: " + hinhThucThanhToan);

        DecimalFormat df = new DecimalFormat("#,##0đ");
        double chietKhauValue = tongTienBanDauValue - thanhTienValue;
        chietKhauValue = Math.max(0, chietKhauValue);

        lBl_TongTien.setText("Tổng tiền: " + tongTienBanDau);
        lBl_thanhTien.setText("Thành tiền: " + thanhTien);
        lBl_chietKhau.setText("Chiết khấu: " + df.format(chietKhauValue) + " (" + phanTramGiamGia + "%)");
        lBl_diemTL.setText("Điểm tích lũy: "+diemTL);
    }

    public void DocDuLieuDatabaseVaoTable() {
        List<HoaDonBanHang> list = hoaDon_dao.getAllHoaDon();
        tableModel.setRowCount(0);
        for (HoaDonBanHang hd : list) {
            tableModel.addRow(new Object[] {
                hd.getMaHDBH(),
                hd.getMaNVGia(),
                hd.getMaKHGia(),
                hd.getNgayLapHDBH(),
                hd.getPhanTramGiamGia(),
                hd.isHinhThucThanhToan() ? "Chuyển khoản" : "Tiền mặt"
            });  
        }
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
                "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
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
            maKH = tableModel.getValueAt(selectedRow, 2).toString();
            ngayLap = tableModel.getValueAt(selectedRow, 3).toString();
            hinhThucThanhToan = tableModel.getValueAt(selectedRow, 5).toString();
            
            HoaDonBanHang hd = hoaDon_dao.getHoaDonTheoMa(maHD);
            if (hd != null) {
                DecimalFormat df = new DecimalFormat("#,##0đ");
                double thanhTienValue = hd.getTongtienGia();
                double phanTramGiam = hd.getPhanTramGiamGia() / 100.0;
                double tongTienBanDauValue = thanhTienValue / (1 - phanTramGiam);

                thanhTien = df.format(thanhTienValue);
                tongTienBanDau = df.format(tongTienBanDauValue);
                phanTramGiamGia = String.valueOf(hd.getPhanTramGiamGia());
                diemTL = String.valueOf(hd.getdiemTL());

                this.tongTienBanDauValue = tongTienBanDauValue;
                this.thanhTienValue = thanhTienValue;
            } else {
                thanhTien = "0đ";
                tongTienBanDau = "0đ";
                phanTramGiamGia = "0";
                this.tongTienBanDauValue = 0;
                this.thanhTienValue = 0;
            }

            loadHoaDon();
            updateProductPanel(maHD);
        }
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