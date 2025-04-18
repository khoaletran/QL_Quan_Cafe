package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Dao.KhachHang_DAO;
import Model.KhachHang;

import java.awt.*;
import java.util.ArrayList;

public class KhachHangPanel extends JPanel {
    ArrayList<KhachHang> dskh = KhachHang_DAO.getAllKhachHang();
    private JTextField[] textFields; // Khai báo mảng textFields ở cấp lớp để truy cập trong sự kiện

    public KhachHangPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createUI();
    }

    private void createUI() {
        // Tiêu đề
        JLabel titleLabel = new JLabel("Quản Lý Khách Hàng", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Panel chứa ô nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(new Color(245, 245, 245));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] labels = {"Mã Khách Hàng:", "Tên Khách Hàng:", "Số Điện Thoại:", "Điểm Tích Lũy:", "Loại Khách Hàng:"};
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

        // Bảng khách hàng
        String[] columnNames = {"Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại", "Điểm Tích Lũy", "Loại Khách Hàng"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 3 -> Integer.class; // Điểm tích lũy là int
                    default -> String.class;
                };
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp
            }
        };

        // Dữ liệu từ SQL
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
                        textFields[0].setText((String) table.getValueAt(selectedRow, 0)); // Mã KH
                        textFields[1].setText((String) table.getValueAt(selectedRow, 1)); // Tên KH
                        textFields[2].setText((String) table.getValueAt(selectedRow, 2)); // SĐT
                        textFields[3].setText(String.valueOf(table.getValueAt(selectedRow, 3))); // Điểm tích lũy
                        textFields[4].setText((String) table.getValueAt(selectedRow, 4)); // Loại KH
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