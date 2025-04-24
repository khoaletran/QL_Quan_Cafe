package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import Model.HangHoa;
import ConnectDB.ConnectDB;

public class OrderPanel extends JPanel {
    private DefaultTableModel orderTableModel;
    private JLabel totalLabel;
    private JLabel totalAfterDiscountLabel;
    private JLabel discountAmountLabel;
    private JTextField discountCodeField;
    private double discountPercentage = 0.0; // Phần trăm chiết khấu
    private DecimalFormat df = new DecimalFormat("#,##0đ");

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
        JTextField phoneField = new JTextField(10);
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

        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Tổng: 0đ");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(totalLabel);
        summaryPanel.add(totalPanel);

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

        bottomPanel.add(summaryPanel, BorderLayout.NORTH);

        CustomButton checkoutButton = new CustomButton("Thanh Toán", new Color(34, 139, 34), Color.WHITE, 10);
        checkoutButton.addActionListener(e -> applyDiscountAndCheckout());
        bottomPanel.add(checkoutButton, BorderLayout.CENTER);

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

    private void updateTotal() {
        double total = 0.0;
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            Double rowTotal = (Double) orderTableModel.getValueAt(i, 3); // Cột Tổng
            total += rowTotal;
        }
        double discountAmount = total * (discountPercentage / 100);
        double finalTotal = total - discountAmount;

        totalLabel.setText("Tổng: " + df.format(total));
        discountAmountLabel.setText("Chiết khấu: " + df.format(discountAmount));
        totalAfterDiscountLabel.setText("Thành tiền: " + df.format(finalTotal));
    }

    private void applyDiscountAndCheckout() {
        String discountCode = discountCodeField.getText().trim();
        if (!discountCode.isEmpty()) {
            String sql = "SELECT GIAMGIA FROM MAGIAMGIA WHERE MAGIAM = ?";
            try (Connection con = ConnectDB.getConnection();
                 PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, discountCode);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    discountPercentage = rs.getInt("GIAMGIA");
                    JOptionPane.showMessageDialog(this, "Áp dụng mã giảm giá thành công! Giảm " + (int) discountPercentage + "%");
                } else {
                    discountPercentage = 0.0;
                    JOptionPane.showMessageDialog(this, "Mã giảm giá không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi kiểm tra mã giảm giá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateTotal();
        // TODO: Thêm logic thanh toán (lưu vào HOADONBANHANG và CHITIETHOADON)
    }

    public void clearOrder() {
        orderTableModel.setRowCount(0);
        discountPercentage = 0.0;
        discountCodeField.setText("");
        totalLabel.setText("Tổng: 0đ");
        discountAmountLabel.setText("Chiết khấu: 0đ");
        totalAfterDiscountLabel.setText("Thành tiền: 0đ");
    }
}