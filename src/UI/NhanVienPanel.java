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
    private JTextField[] textFields; // Khai báo mảng textFields ở cấp lớp để truy cập trong sự kiện

    public NhanVienPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createUI();
    }

    private void createUI() {
        // Tiêu đề
        JLabel titleLabel = new JLabel("Quản Lý Nhân Viên", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Panel chứa ô nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(new Color(245, 245, 245));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] labels = {"Mã Nhân Viên:", "Tên Nhân Viên:", "Ngày Vào Làm:", "Giới Tính:", "Số Điện Thoại:"};
        textFields = new JTextField[5]; // Khởi tạo mảng textFields
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(labelFont);
            inputPanel.add(label);

            textFields[i] = new JTextField();
            textFields[i].setFont(textFieldFont);
            textFields[i].setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            inputPanel.add(textFields[i]);
        }

        // Bảng nhân viên
        String[] columnNames = {"Mã Nhân Viên", "Tên Nhân Viên", "Ngày Vào Làm", "Giới Tính", "Số Điện Thoại"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class; // Tất cả cột đều hiển thị dưới dạng String
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp
            }
        };

        // Dữ liệu mẫu
        for (NhanVien nv : dsnv) {
            Object[] row = {
                nv.getMaNV(),
                nv.getTenNV(),
                nv.getNgayVaoLam().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                nv.isGioiTinh() ? "Nam" : "Nữ",
                nv.getSdt()
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(30);
        table.setGridColor(new Color(200, 200, 200));
        table.setShowGrid(true);

        // Thêm sự kiện chọn hàng trong bảng
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Đảm bảo sự kiện chỉ được xử lý một lần khi chọn xong
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) { // Kiểm tra xem có hàng nào được chọn không
                        textFields[0].setText((String) table.getValueAt(selectedRow, 0)); // Mã NV
                        textFields[1].setText((String) table.getValueAt(selectedRow, 1)); // Tên NV
                        textFields[2].setText((String) table.getValueAt(selectedRow, 2)); // Ngày vào làm
                        textFields[3].setText((String) table.getValueAt(selectedRow, 3)); // Giới tính
                        textFields[4].setText((String) table.getValueAt(selectedRow, 4)); // SĐT
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Panel chứa bảng và ô nhập liệu
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        String[] buttonLabels = {"Thêm", "Xóa", "Sửa", "Tìm"};
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(buttonFont);
            button.setBackground(new Color(220, 220, 220));
            button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
            button.setPreferredSize(new Dimension(100, 40));
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.SOUTH);
    }
}