package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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

        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        inputPanel.setBackground(new Color(245, 245, 245));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);
        
        String[] labels = {"Mã Nhân Viên:", "Tên Nhân Viên:", "Ngày Vào Làm:", "Số Điện Thoại:", "Địa Chỉ:", "Mật Khẩu:"};
        textFields = new JTextField[6];
        for (int i = 0; i < textFields.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(labelFont);
            inputPanel.add(label);

            textFields[i] = new JTextField();
            textFields[i].setFont(textFieldFont);
            textFields[i].setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            inputPanel.add(textFields[i]);
        }
        textFields[0].setEnabled(false);

        // Giới tính radio button
        JLabel lbGioiTinh = new JLabel("Giới Tính:");
        lbGioiTinh.setFont(labelFont);
        radNam = new JRadioButton("Nam");
        radNu = new JRadioButton("Nữ");
        Font radioFont = new Font("Arial", Font.PLAIN, 16); // hoặc 18, 20 tùy ý
        radNam.setFont(radioFont);
        radNu.setFont(radioFont);
        
        //check box quản lý
        JLabel lblQuanLy = new JLabel("Quản lý:");
        lblQuanLy.setFont(labelFont);
        chkQuanLy = new JCheckBox();
        
        
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

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        String[] buttonLabels = {"Thêm", "Xóa", "Sửa", "Xóa Trắng"};
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(buttonFont);
            button.setBackground(new Color(220, 220, 220));
            button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
            button.setPreferredSize(new Dimension(100, 40));
            buttonPanel.add(button);

            button.addActionListener(e -> {
                switch (e.getActionCommand()) {
                    case "Thêm" -> themNhanVien();
                    case "Xóa" -> xoaNhanVien();
                    case "Sửa" -> suaNhanVien();
                    case "Xóa Trắng" -> xoaTrang();
                }
            });
        }
        add(buttonPanel, BorderLayout.SOUTH);

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