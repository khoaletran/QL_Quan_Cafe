package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Dao.KhachHang_DAO;
import Dao.LoaiKhachHang_DAO;
import Model.KhachHang;
import Model.LoaiKhachHang;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class KhachHangPanel extends JPanel {
    private ArrayList<KhachHang> dskh = KhachHang_DAO.getAllKhachHang();
    private JTextField[] textFields;
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

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(new Color(245, 245, 245));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] labels = {"Mã Khách Hàng:", "Tên Khách Hàng:", "Số Điện Thoại:", "Điểm Tích Lũy:", "Loại Khách Hàng:"};
        textFields = new JTextField[5];
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 20);

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(labelFont);
            inputPanel.add(label);

            textFields[i] = new JTextField();
            textFields[i].setFont(textFieldFont);
            textFields[i].setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            inputPanel.add(textFields[i]);
        }
        //2 dong nay khong cho nhap
        textFields[0].setEditable(false);
        textFields[4].setEditable(false);

        String[] columnNames = {"Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại", "Điểm Tích Lũy", "Loại Khách Hàng"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 3 ? Integer.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(30);
        table.setGridColor(new Color(200, 200, 200));
        table.setShowGrid(true);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    textFields[0].setText((String) table.getValueAt(selectedRow, 0));
                    textFields[1].setText((String) table.getValueAt(selectedRow, 1));
                    textFields[2].setText((String) table.getValueAt(selectedRow, 2));
                    textFields[3].setText(String.valueOf(table.getValueAt(selectedRow, 3)));
                    textFields[4].setText((String) table.getValueAt(selectedRow, 4));
                }
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
        String[] buttonLabels = {"Thêm", "Xóa", "Sửa", "Làm Mới", "Tìm"};
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        
        //
        JLabel lblTimKiem = new JLabel("NHẬP SỐ ĐIỆN THOẠI KHÁCH HÀNG CẦN TÌM:");
        txtTimKiem = new JTextField(20);
        JPanel pTimKiem = new JPanel(new GridLayout(2,1,2,2));
        pTimKiem.add(lblTimKiem);
        pTimKiem.add(txtTimKiem);
        
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(buttonFont);
            button.setBackground(new Color(220, 220, 220));
            button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
            button.setPreferredSize(new Dimension(100, 40));

            button.addActionListener(e -> {
                switch (e.getActionCommand()) {
                    case "Thêm" -> xuLySuKienThemKhachHang();
                    case "Xóa" -> xuLySuKienXoaKhachHang();
                    case "Sửa" -> xuLySuKienSuaKhachHang();
                    case "Làm Mới" -> xuLySuKienLamMoi();
                    case "Tìm" -> xuLySuKienTimKiemKhachHang();
                }
            });

            buttonPanel.add(button);
            buttonPanel.add(pTimKiem);
        }

        add(buttonPanel, BorderLayout.SOUTH);

        loadDataToTable(); // Load dữ liệu ban đầu
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
            String tenKH = textFields[1].getText();
            String soDienThoai = textFields[2].getText();
            int diemTL = Integer.parseInt(textFields[3].getText());
            KhachHang newKH = new KhachHang(tenKH, soDienThoai, diemTL);
           
            
            if (newKH.getLoaiKhachHang() == null) {
                JOptionPane.showMessageDialog(this, "Không tìm được loại khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            textFields[4].setText(newKH.getLoaiKhachHang().getTenLKH());
            
            boolean result = KhachHang_DAO.themKhachHang(newKH);
            if (result) {
                dskh.add(newKH);
                updateTable();
                lamRong();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }



    private void xuLySuKienXoaKhachHang() {
        String maKH = textFields[0].getText();
        if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng này không?", "Cảnh báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            KhachHang_DAO.xoaKhachHang(maKH);
            dskh.removeIf(kh -> kh.getMaKH().equals(maKH));
            updateTable();
            lamRong();
        }
    }

    private void xuLySuKienSuaKhachHang() {
        String maKH = textFields[0].getText();
        String tenKH = textFields[1].getText();
        String soDienThoai = textFields[2].getText();
        int diemTL = Integer.parseInt(textFields[3].getText());
        KhachHang newKH = new KhachHang(maKH, tenKH, soDienThoai, diemTL);
        if (newKH.getLoaiKhachHang() == null) {
            JOptionPane.showMessageDialog(this, "Không tìm được loại khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
       boolean ok = KhachHang_DAO.suaKhachHang(newKH);
       if(ok) {
    	   JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
    	   updateTable();
    	   lamRong();
       }
       else {
    	   JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
       }
        
        

        
        updateTable();
    }

    private void xuLySuKienTimKiemKhachHang() {
        String soDienThoai = txtTimKiem.getText();
        KhachHang kh = KhachHang_DAO.timKhachHangTheoSDT(soDienThoai);
        if (kh != null) {
            dskh = new ArrayList<>();
            dskh.add(kh);
        } else {
            dskh = new ArrayList<>();
        }
        loadDataToTable();
    }
    private void xuLySuKienLamMoi() {
    	dskh = KhachHang_DAO.getAllKhachHang();
    	loadDataToTable();
    	txtTimKiem.setText("");
    	lamRong();
    }
    
    private void lamRong(){
    	textFields[0].setText(""); 
        textFields[1].setText(""); 
        textFields[2].setText("");
        textFields[3].setText("");
        textFields[4].setText("");
    }
}
