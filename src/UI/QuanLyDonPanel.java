package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
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
import Dao.HangHoa_DAO;
import Dao.HoaDon_DAO;
import Model.HoaDonBanHang;


public class QuanLyDonPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;
    private HoaDon_DAO hoaDon_dao;

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

        // Tăng kích thước chữ trong bảng
        Font tableFont = new Font("Arial", Font.PLAIN, 14);
        table.setFont(tableFont);

        // Làm đậm chữ trong header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));

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
        infoPanel.add(createInfoLabel("Mã hóa đơn: [Chưa chọn]", infoFont));
        infoPanel.add(createInfoLabel("Ngày lập: [Chưa chọn]", infoFont));
        infoPanel.add(createInfoLabel("Nhân viên: [Chưa chọn]", infoFont));
        infoPanel.add(createInfoLabel("Khách hàng: [Chưa chọn]", infoFont));
        infoPanel.add(createInfoLabel("Hình thức thanh toán: [Chưa chọn]", infoFont));
        infoPanel.add(createInfoLabel("Mã giảm giá: [Chưa chọn]", infoFont));

        JPanel separatorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        separatorPanel.setBackground(new Color(245, 245, 245));
        JSeparator infoSeparator = new JSeparator();
        infoSeparator.setForeground(Color.BLACK);
        infoSeparator.setPreferredSize(new java.awt.Dimension(4000, 2));//kích thước đường kẻ ngang
        separatorPanel.add(infoSeparator);
        infoPanel.add(separatorPanel);

        JPanel infoWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        infoWrapper.setBackground(new Color(245, 245, 245));
        infoWrapper.add(infoPanel);
        invoicePanel.add(infoWrapper);
        invoicePanel.add(Box.createVerticalStrut(10));

        // Danh sách sản phẩm
        JPanel productPanel = new JPanel(new GridBagLayout());
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
        JLabel totalAmountLabel = new JLabel("Tổng tiền: 0");
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(totalAmountLabel, sumGbc);

        sumGbc.gridy = 1;
        JLabel discountLabel = new JLabel("Chiết khấu: 0 (0%)");
        discountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        discountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(discountLabel, sumGbc);

        sumGbc.gridy = 2;
        JLabel finalAmountLabel = new JLabel("Thành tiền: 0");
        finalAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        finalAmountLabel.setForeground(new Color(204, 0, 0));
        finalAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(finalAmountLabel, sumGbc);

        sumGbc.gridy = 3;
        JLabel pointsLabel = new JLabel("Điểm tích lũy: 0");
        pointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        pointsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        summaryPanel.add(pointsLabel, sumGbc);

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
        JLabel lblMaHD = new JLabel("Nhập mã HD:");
        lblMaHD.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(lblMaHD, searchGbc);

        // JTextField để nhập mã HD
        searchGbc.gridx = 1;
        searchGbc.gridy = 0;
        searchGbc.weightx = 1.0;
        JTextField txtMaHD = new JTextField(15);
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
    
    public void DocDuLieuDatabaseVaoTable() {
		List<HoaDonBanHang> list = hoaDon_dao.getAllHoaDon();
		for (HoaDonBanHang hd : list) {
			tableModel.addRow(new Object[] {hd.getMaHDBH(),hd.getMaKHGia(),hd.getMaKHGia(),hd.getNgayLapHDBH(),hd.getPhanTramGiamGia(),hd.isHinhThucThanhToan() ? "Chuyển khoản" : "Tiền mặt"});
		}
	}
}