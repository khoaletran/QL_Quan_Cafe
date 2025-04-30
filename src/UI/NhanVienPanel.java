package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import Dao.NhanVien_DAO;
import Model.NhanVien;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NhanVienPanel extends JPanel {
    ArrayList<NhanVien> dsnv = NhanVien_DAO.getAllNhanVien();
    private JTextField[] textFields;
    private JRadioButton radNam, radNu;
    private ButtonGroup gioiTinhGroup;
    DefaultTableModel tableModel;
    JTable table;
    private JCheckBox chkQuanLy;
	
	private JCheckBox chkLocNam;
	private JCheckBox chkLocNu;
    public NhanVienPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createUI();
    }

    private void createUI() {
        JLabel titleLabel = new JLabel("Quản Lý Nhân Viên", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 8, 40));
        inputPanel.setBackground(new Color(245, 245, 245));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JPanel inputWrapper = new JPanel(new BorderLayout());
        inputWrapper.setBackground(new Color(245, 245, 245));
        inputWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Thêm padding trái-phải
        inputWrapper.add(inputPanel, BorderLayout.CENTER);

        
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14); // Reduced font size
        
        String[] labels = {"Mã Nhân Viên:", "Tên Nhân Viên:", "Ngày Vào Làm:", "Số Điện Thoại:", "Địa Chỉ:", "Mật Khẩu:"};
        textFields = new JTextField[6];
        for (int i = 0; i < textFields.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(labelFont);
            inputPanel.add(label);

            textFields[i] = new JTextField();
            textFields[i].setFont(textFieldFont);
            textFields[i].setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            textFields[i].setPreferredSize(new Dimension(120, 10)); // Smaller text fields
            inputPanel.add(textFields[i]);
        }
        textFields[0].setEnabled(false);

        // Giới tính radio button
        JLabel lbGioiTinh = new JLabel("Giới Tính:");
        lbGioiTinh.setFont(labelFont);
        radNam = new JRadioButton("Nam");
        radNu = new JRadioButton("Nữ");
        Font radioFont = new Font("Arial", Font.PLAIN, 14);
        radNam.setFont(radioFont);
        radNu.setFont(radioFont);
        
        // Checkbox Quản lý
        JLabel lblQuanLy = new JLabel("Quản Lý:");
        lblQuanLy.setFont(labelFont);
        chkQuanLy = new JCheckBox();
        chkQuanLy.setBackground(new Color(245, 245, 245));
        
        gioiTinhGroup = new ButtonGroup();
        gioiTinhGroup.add(radNam);
        gioiTinhGroup.add(radNu);

        inputPanel.add(lbGioiTinh);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.setBackground(new Color(245, 245, 245));
        genderPanel.add(radNam);
        genderPanel.add(radNu);
        inputPanel.add(genderPanel);
        
        inputPanel.add(lblQuanLy);
        inputPanel.add(chkQuanLy);

        String[] columnNames = {"Mã Nhân Viên", "Tên Nhân Viên", "Ngày Vào Làm", "Giới Tính", "Số Điện Thoại", "Địa Chỉ", "Mật Khẩu", "Quản Lý"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);

        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            int selectedRow = table.getSelectedRow();
            if (!e.getValueIsAdjusting() && selectedRow >= 0) {
                textFields[0].setText((String) table.getValueAt(selectedRow, 0));
                textFields[1].setText((String) table.getValueAt(selectedRow, 1));
                textFields[2].setText((String) table.getValueAt(selectedRow, 2));
                String gt = (String) table.getValueAt(selectedRow, 3);
                if (gt.equalsIgnoreCase("Nam")) radNam.setSelected(true);
                else radNu.setSelected(true);
                textFields[3].setText((String) table.getValueAt(selectedRow, 4));
                textFields[4].setText((String) table.getValueAt(selectedRow, 5));
                textFields[5].setText((String) table.getValueAt(selectedRow, 6));
                String ql = (String) table.getValueAt(selectedRow, 7);
                chkQuanLy.setSelected(ql.equalsIgnoreCase("Có"));
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Sử dụng BorderLayout thay vì JSplitPane
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(inputWrapper, BorderLayout.EAST);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Panel chứa các nút
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        leftPanel.setBackground(new Color(245, 245, 245));
        leftPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Chức năng chính",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12),
            Color.DARK_GRAY
        ));
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        // Button Thêm
        JButton btnThem = new JButton("Thêm");
        btnThem.setFont(buttonFont);
        btnThem.setBackground(new Color(220, 220, 220));
        btnThem.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        btnThem.setPreferredSize(new Dimension(100, 40));
        btnThem.addActionListener(e -> themNhanVien());
        leftPanel.add(btnThem);

        // Button Xóa
        JButton btnXoa = new JButton("Xóa");
        btnXoa.setFont(buttonFont);
        btnXoa.setBackground(new Color(220, 220, 220));
        btnXoa.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        btnXoa.setPreferredSize(new Dimension(100, 40));
        btnXoa.addActionListener(e -> xoaNhanVien());
        leftPanel.add(btnXoa);

        // Button Sửa
        JButton btnSua = new JButton("Sửa");
        btnSua.setFont(buttonFont);
        btnSua.setBackground(new Color(220, 220, 220));
        btnSua.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        btnSua.setPreferredSize(new Dimension(100, 40));
        btnSua.addActionListener(e -> suaNhanVien());
        leftPanel.add(btnSua);

        // Button Xóa Trắng
        JButton btnXoaTrang = new JButton("Xóa Trắng");
        btnXoaTrang.setFont(buttonFont);
        btnXoaTrang.setBackground(new Color(220, 220, 220));
        btnXoaTrang.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        btnXoaTrang.setPreferredSize(new Dimension(100, 40));
        btnXoaTrang.addActionListener(e -> xoaTrang());
        leftPanel.add(btnXoaTrang);

        // Panel chứa combo lọc + nút lọc
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        rightPanel.setBackground(new Color(245, 245, 245));
        rightPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Lọc theo giới tính",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12),
            Color.DARK_GRAY
        ));

        chkLocNam = new JCheckBox("Nam");
        chkLocNam.setFont(new Font("Arial", Font.PLAIN, 15));
        chkLocNam.setBackground(new Color(245, 245, 245));
        chkLocNam.addItemListener(e -> {
            if (chkLocNam.isSelected()) chkLocNu.setSelected(false); // Uncheck Nữ when Nam is checked
            locTheoGioiTinh();
        });
        rightPanel.add(chkLocNam);
        
        chkLocNu = new JCheckBox("Nữ");
        chkLocNu.setFont(new Font("Arial", Font.PLAIN, 15));
        chkLocNu.setBackground(new Color(245, 245, 245));
        chkLocNu.addItemListener(e -> {
            if (chkLocNu.isSelected()) chkLocNam.setSelected(false); // Uncheck Nam when Nữ is checked
            locTheoGioiTinh();
        });
        rightPanel.add(chkLocNu);

        // Tạo JSplitPane để chia trái-phải
        JSplitPane buttonSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        buttonSplitPane.setDividerLocation(850);
        buttonSplitPane.setBorder(null);
        
        JPanel bottomWrapper = new JPanel(new BorderLayout());
        bottomWrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // cách top 10px
        bottomWrapper.add(buttonSplitPane, BorderLayout.CENTER);
        add(bottomWrapper, BorderLayout.SOUTH);


        reloadTable();
    }

    private void reloadTable() {
        tableModel.setRowCount(0);
        dsnv = NhanVien_DAO.getAllNhanVien();
        for (NhanVien nv : dsnv) {
            tableModel.addRow(new Object[]{
                nv.getMaNV(),
                nv.getTenNV(),
                nv.getNgayVaoLam().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                nv.isGioiTinh() ? "Nữ" : "Nam",
                nv.getSdt(),
                nv.getDiaChi(),
                nv.getMatKhau(),
                nv.isQuanly() ? "Có" : "Không"
            });
        }
    }
    private void locTheoGioiTinh() {
        tableModel.setRowCount(0);
        dsnv = NhanVien_DAO.getAllNhanVien();

        for (NhanVien nv : dsnv) {
            boolean shouldAdd = true;

            if (chkLocNam.isSelected()) {
                shouldAdd = !nv.isGioiTinh(); // Nam = false
            } else if (chkLocNu.isSelected()) {
                shouldAdd = nv.isGioiTinh(); // Nữ = true
            }

            if (shouldAdd) {
                tableModel.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getNgayVaoLam().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    nv.isGioiTinh() ? "Nữ" : "Nam",
                    nv.getSdt(),
                    nv.getDiaChi(),
                    nv.getMatKhau(),
                    nv.isQuanly() ? "Có" : "Không"
                });
            }
        }
    }

    private void xoaTrang() {
        for (JTextField tf : textFields) {
            tf.setText("");
        }
        gioiTinhGroup.clearSelection();
        table.clearSelection();
    }

    private void themNhanVien() {
        try {
            String ten = textFields[1].getText();
            String ngay = textFields[2].getText();
            String sdt = textFields[3].getText();
            String diaChi = textFields[4].getText();
            String matKhau = textFields[5].getText();
            boolean gt = radNu.isSelected();
            LocalDate nvl = LocalDate.parse(ngay, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            boolean ql = chkQuanLy.isSelected();


            NhanVien nv = new NhanVien(ten, diaChi, nvl, gt, sdt, matKhau, ql);
            boolean ok = new NhanVien_DAO().themNhanVien(nv);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Đã thêm nhân viên!");
                reloadTable();
                xoaTrang();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void xoaNhanVien() {
        int selected = table.getSelectedRow();
        if (selected >= 0) {
            String ma = (String) table.getValueAt(selected, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xóa nhân viên " + ma + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = new NhanVien_DAO().xoaNhanVien(ma);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Đã xóa!");
                    xoaTrang();
                    reloadTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chọn nhân viên cần xóa.");
        }
    }

    private void suaNhanVien() {
        try {
            String ma = textFields[0].getText();
            String ten = textFields[1].getText();
            String ngay = textFields[2].getText();
            String sdt = textFields[3].getText();
            String diaChi = textFields[4].getText();
            String matKhau = textFields[5].getText();
            boolean gt = radNu.isSelected();
            LocalDate nvl = LocalDate.parse(ngay, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            boolean ql = chkQuanLy.isSelected();

            NhanVien nv = new NhanVien(ma, ten, diaChi, nvl, gt, sdt, matKhau, ql);
            boolean ok = new NhanVien_DAO().suaNhanVien(nv);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                xoaTrang();
                reloadTable();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}