package UI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import Model.HangHoa;
import ConnectDB.ConnectDB;
import Dao.MaGiamGia_DAO;
import Model.MaGiamGia;

public class OrderPanel extends JPanel {
    private DefaultTableModel orderTableModel;
    private JLabel totalLabel;
    private JLabel totalAfterDiscountLabel;
    private JLabel discountAmountLabel;
    private JTextField discountCodeField;
    private JTextField phoneField;
    private JCheckBox bankTransferCheckBox; // Thêm để truy cập checkbox
    private double discountPercentage = 0.0; // Phần trăm chiết khấu
    private DecimalFormat df = new DecimalFormat("#,##0đ");
    private JLabel discountStatusLabel;

    public OrderPanel() {
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
                return column == 4; // Chỉ cột "Xóa" có thể chỉnh sửa
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 1: // Số lượng
                        return Integer.class;
                    case 2: // Giá
                    case 3: // Tổng
                        return Double.class;
                    default:
                        return String.class;
                }
            }
        };
        JTable orderTable = new JTable(orderTableModel);
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        orderTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        orderTable.getColumnModel().getColumn(4).setPreferredWidth(50);
        
     // Thêm renderer và editor cho cột "Xóa"
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

        // Phương thức thanh toán
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
        
        discountPanel.add(discountCodeLabel);
        discountPanel.add(discountCodeField);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void addOrderItem(HangHoa hh, int quantity) {
        double price = hh.getGiaSP();
        double total = price * quantity;

        // Kiểm tra xem sản phẩm đã có trong bảng chưa
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            if (orderTableModel.getValueAt(i, 0).equals(hh.getTenHH())) {
                int existingQuantity = (Integer) orderTableModel.getValueAt(i, 1);
                int newQuantity = existingQuantity + quantity;
                orderTableModel.setValueAt(newQuantity, i, 1);
                orderTableModel.setValueAt(newQuantity * price, i, 3);
                updateTotal();
                return;
            }
        }

        // Nếu sản phẩm chưa có, thêm dòng mới
        Object[] row = {
            hh.getTenHH(),
            quantity,
            price, // Lưu Double
            total, // Lưu Double
            "Xóa"
        };
        orderTableModel.addRow(row);
        updateTotal();
    }

    public void updateTotal() {
        double total = 0.0;
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            Double rowTotal = (Double) orderTableModel.getValueAt(i, 3);
            total += rowTotal;
        }
        
        double discountAmount = total * (discountPercentage / 100);
        double finalTotal = total - discountAmount;

        totalLabel.setText("Tổng: " + df.format(total));
        discountAmountLabel.setText("Chiết khấu: " + df.format(discountAmount));
        totalAfterDiscountLabel.setText("Thành tiền: " + df.format(finalTotal));
    }
  
    public DefaultTableModel getOrderTableModel() {
        return orderTableModel;
    }
    
    private void checkDiscountCode() {
        String discountCode = discountCodeField.getText().trim();
        
        // Nếu mã trống, reset discount về 0
        if (discountCode.isEmpty()) {
            discountPercentage = 0.0;
            discountStatusLabel.setText(" ");
            discountStatusLabel.setForeground(Color.BLUE);
            updateTotal();
            return;
        }
        
        // Kiểm tra mã giảm giá
        MaGiamGia_DAO discountDAO = new MaGiamGia_DAO();
        MaGiamGia discount = discountDAO.timMaGiamGia(discountCode);
        
        if (discount != null) {
            // Áp dụng giảm giá nếu mã hợp lệ
            discountPercentage = discount.getGiamGia();
            discountStatusLabel.setText("Áp dụng thành công: Giảm " + (int)discountPercentage + "%");
            discountStatusLabel.setForeground(new Color(0, 100, 0)); // Màu xanh lá đậm
        } else {
            // Reset về 0 nếu mã không hợp lệ
            discountPercentage = 0.0;
            discountStatusLabel.setText("Mã giảm giá không hợp lệ");
            discountStatusLabel.setForeground(Color.RED);
        }
        
        updateTotal();
        
        // Tự động ẩn thông báo sau 3 giây
        Timer timer = new Timer(3000, e -> {
            discountStatusLabel.setText(" ");
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void clearOrder() {
        orderTableModel.setRowCount(0);
        discountPercentage = 0.0;
        discountCodeField.setText("");
        phoneField.setText("");
        totalLabel.setText("Tổng: 0đ");
        discountAmountLabel.setText("Chiết khấu: 0đ");
        totalAfterDiscountLabel.setText("Thành tiền: 0đ");
    }
}