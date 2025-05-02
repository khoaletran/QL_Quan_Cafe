package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Dao.KhachHang_DAO;
import Model.KhachHang;

public class KhachHangPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<KhachHang> dskh = KhachHang_DAO.getAllKhachHang();
    private JTextField txtMaKH, txtTenKH, txtSoDienThoai, txtDiemTL, txtLoaiKH;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTimKiem;

    public KhachHangPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createUI();
    }

    private void createUI() {
        JLabel titleLabel = new JLabel("Quản Lý Khách Hàng", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 8, 75));
        inputPanel.setBackground(new Color(245, 245, 245));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);

        // Mã Khách Hàng
        JLabel lblMaKH = new JLabel("Mã Khách Hàng:");
        lblMaKH.setFont(labelFont);
        inputPanel.add(lblMaKH);
        txtMaKH = new JTextField();
        txtMaKH.setFont(textFieldFont);
        txtMaKH.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        txtMaKH.setPreferredSize(new Dimension(120, 10));
        txtMaKH.setEnabled(false);
        inputPanel.add(txtMaKH);

        // Tên Khách Hàng
        JLabel lblTenKH = new JLabel("Tên Khách Hàng:");
        lblTenKH.setFont(labelFont);
        inputPanel.add(lblTenKH);
        txtTenKH = new JTextField();
        txtTenKH.setFont(textFieldFont);
        txtTenKH.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        txtTenKH.setPreferredSize(new Dimension(120, 10));
        inputPanel.add(txtTenKH);

        // Số Điện Thoại
        JLabel lblSoDienThoai = new JLabel("Số Điện Thoại:");
        lblSoDienThoai.setFont(labelFont);
        inputPanel.add(lblSoDienThoai);
        txtSoDienThoai = new JTextField();
        txtSoDienThoai.setFont(textFieldFont);
        txtSoDienThoai.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        txtSoDienThoai.setPreferredSize(new Dimension(120, 10));
        inputPanel.add(txtSoDienThoai);

        // Điểm Tích Lũy
        JLabel lblDiemTL = new JLabel("Điểm Tích Lũy:");
        lblDiemTL.setFont(labelFont);
        inputPanel.add(lblDiemTL);
        txtDiemTL = new JTextField();
        txtDiemTL.setFont(textFieldFont);
        txtDiemTL.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        txtDiemTL.setPreferredSize(new Dimension(120, 10));
        inputPanel.add(txtDiemTL);

        // Loại Khách Hàng
        JLabel lblLoaiKH = new JLabel("Loại Khách Hàng:");
        lblLoaiKH.setFont(labelFont);
        inputPanel.add(lblLoaiKH);
        txtLoaiKH = new JTextField();
        txtLoaiKH.setFont(textFieldFont);
        txtLoaiKH.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        txtLoaiKH.setPreferredSize(new Dimension(120, 10));
        txtLoaiKH.setEnabled(false);
        inputPanel.add(txtLoaiKH);

        // Bọc inputPanel trong panel có tiêu đề
        JPanel inputWrapperBorder = new JPanel(new BorderLayout());
        inputWrapperBorder.setBackground(new Color(245, 245, 245));
        inputWrapperBorder.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), "Thông Tin Khách Hàng", TitledBorder.LEFT,
                TitledBorder.TOP, new Font("Arial", Font.BOLD, 12), Color.DARK_GRAY));
        inputWrapperBorder.add(inputPanel, BorderLayout.CENTER);

        JPanel inputWrapper = new JPanel(new BorderLayout());
        inputWrapper.setBackground(new Color(245, 245, 245));
        inputWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        inputWrapper.add(inputWrapperBorder, BorderLayout.CENTER);

        String[] columnNames = {"Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại", "Điểm Tích Lũy", "Loại Khách Hàng"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        //căn giữa tên collumn
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        
        table.setRowHeight(30);
        table.setGridColor(new Color(200, 200, 200));
        table.setShowGrid(true);
        

        // Renderer tùy chỉnh để căn giữa nội dung
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setFont(new Font("Arial", Font.PLAIN, 14));
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txtMaKH.setText((String) table.getValueAt(selectedRow, 0));
                    txtTenKH.setText((String) table.getValueAt(selectedRow, 1));
                    txtSoDienThoai.setText((String) table.getValueAt(selectedRow, 2));
                    txtDiemTL.setText(String.valueOf(table.getValueAt(selectedRow, 3)));
                    txtLoaiKH.setText((String) table.getValueAt(selectedRow, 4));
                }
            }
        });


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Bọc scrollPane trong panel có tiêu đề
        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(new Color(245, 245, 245));
        tableWrapper.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), "Danh Sách Khách Hàng", TitledBorder.LEFT,
                TitledBorder.TOP, new Font("Arial", Font.BOLD, 12), Color.DARK_GRAY));
        tableWrapper.add(scrollPane, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(inputWrapper, BorderLayout.EAST);
        centerPanel.add(tableWrapper, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        leftPanel.setBackground(new Color(245, 245, 245));
        leftPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), "Chức Năng Chính", TitledBorder.LEFT,
                TitledBorder.TOP, new Font("Arial", Font.BOLD, 12), Color.DARK_GRAY));
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        JButton btnThem = new JButton("Thêm");
        btnThem.setFont(buttonFont);
        btnThem.setBackground(new Color(220, 220, 220));
        btnThem.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        btnThem.setPreferredSize(new Dimension(100, 40));
        btnThem.addActionListener(e -> xuLySuKienThemKhachHang());
        leftPanel.add(btnThem);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setFont(buttonFont);
        btnXoa.setBackground(new Color(220, 220, 220));
        btnXoa.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        btnXoa.setPreferredSize(new Dimension(100, 40));
        btnXoa.addActionListener(e -> xuLySuKienXoaKhachHang());
        leftPanel.add(btnXoa);

        JButton btnSua = new JButton("Sửa");
        btnSua.setFont(buttonFont);
        btnSua.setBackground(new Color(220, 220, 220));
        btnSua.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        btnSua.setPreferredSize(new Dimension(100, 40));
        btnSua.addActionListener(e -> xuLySuKienSuaKhachHang());
        leftPanel.add(btnSua);

        JButton btnXoaTrang = new JButton("Xóa Trắng");
        btnXoaTrang.setFont(buttonFont);
        btnXoaTrang.setBackground(new Color(220, 220, 220));
        btnXoaTrang.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        btnXoaTrang.setPreferredSize(new Dimension(100, 40));
        btnXoaTrang.addActionListener(e -> xuLySuKienLamMoi());
        leftPanel.add(btnXoaTrang);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        rightPanel.setBackground(new Color(245, 245, 245));
        rightPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), "Tìm Kiếm Khách Hàng", TitledBorder.LEFT,
                TitledBorder.TOP, new Font("Arial", Font.BOLD, 12), Color.DARK_GRAY));

        JLabel lblTimKiem = new JLabel("Số Điện Thoại:");
        lblTimKiem.setFont(new Font("Arial", Font.PLAIN, 13));
        txtTimKiem = new JTextField(10);
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 13));
        txtTimKiem.getDocument().addDocumentListener(timKiemDong());
        rightPanel.add(lblTimKiem);
        rightPanel.add(txtTimKiem);

        JSplitPane buttonSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        buttonSplitPane.setDividerLocation(850);
        buttonSplitPane.setBorder(null);

        JPanel bottomWrapper = new JPanel(new BorderLayout());
        bottomWrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        bottomWrapper.add(buttonSplitPane, BorderLayout.CENTER);
        add(bottomWrapper, BorderLayout.SOUTH);

        loadDataToTable();
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        for (KhachHang kh : dskh) {
            Object[] row = {
                kh.getMaKH(),
                kh.getTenKH(),
                kh.getSoDienThoai(),
                kh.getDiemTL(),
                kh.getLoaiKhachHang().getTenLKH()
            };
            tableModel.addRow(row);
        }
    }

    private void updateTable() {
        dskh = KhachHang_DAO.getAllKhachHang();
        loadDataToTable();
    }

    private void xuLySuKienThemKhachHang() {
        try {
            String tenKH = txtTenKH.getText().trim();
            String soDienThoai = txtSoDienThoai.getText().trim();

            if (tenKH.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (soDienThoai.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int diemTL;
            try {
                diemTL = Integer.parseInt(txtDiemTL.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Điểm tích lũy phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            KhachHang newKH = new KhachHang(tenKH, soDienThoai, diemTL);
            boolean result = KhachHang_DAO.themKhachHang(newKH);
            if (result) {
                dskh.add(newKH);
                updateTable();
                lamRong();
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại! Số điện thoại đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void xuLySuKienXoaKhachHang() {
        String maKH = txtMaKH.getText().trim();
        if (maKH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean daXoa = KhachHang_DAO.xoaKhachHang(maKH);
                if (daXoa) {
                    dskh.removeIf(kh -> kh.getMaKH().equals(maKH));
                    updateTable();
                    lamRong();
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Không thể xóa khách hàng vì đã có hóa đơn liên quan!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xuLySuKienSuaKhachHang() {
        try {
            String maKH = txtMaKH.getText().trim();
            if (maKH.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tenKH = txtTenKH.getText().trim();
            String soDienThoai = txtSoDienThoai.getText().trim();

            if (tenKH.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (soDienThoai.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int diemTL;
            try {
                diemTL = Integer.parseInt(txtDiemTL.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Điểm tích lũy phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn cập nhật thông tin khách hàng này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            
            KhachHang newKH = new KhachHang(maKH, tenKH, soDienThoai, diemTL);
            boolean ok = KhachHang_DAO.suaKhachHang(newKH);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!");
                updateTable();
                lamRong();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void xuLySuKienLamMoi() {
        dskh = KhachHang_DAO.getAllKhachHang();
        loadDataToTable();
        txtTimKiem.setText("");
        lamRong();
    }

    private DocumentListener timKiemDong() {
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
                String soDienThoai = txtTimKiem.getText().trim();
                dskh = KhachHang_DAO.timKhachHangTheoSDT(soDienThoai);
                loadDataToTable();
            }
        };
    }

    private void lamRong() {
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtSoDienThoai.setText("");
        txtDiemTL.setText("");
        txtLoaiKH.setText("");
    }
}