package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class OrderPanel extends JPanel {
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
        DefaultTableModel orderTableModel = new DefaultTableModel(columns, 0);
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
        JLabel totalLabel = new JLabel("Tổng: 0đ");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(totalLabel);
        summaryPanel.add(totalPanel);

        JPanel discountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel discountCodeLabel = new JLabel("Mã giảm giá:");
        discountCodeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        discountCodeLabel.setForeground(Color.BLUE);
        JTextField discountCodeField = new JTextField(10);
        discountCodeField.setPreferredSize(new Dimension(100, 25));
        discountPanel.add(discountCodeLabel);
        discountPanel.add(discountCodeField);
        summaryPanel.add(discountPanel);

        JPanel discountAmountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel discountAmountLabel = new JLabel("Chiết khấu: 0đ");
        discountAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        discountAmountLabel.setForeground(Color.BLUE);
        discountAmountPanel.add(discountAmountLabel);
        summaryPanel.add(discountAmountPanel);

        JPanel totalAfterDiscountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel totalAfterDiscountLabel = new JLabel("Thành tiền: 0đ");
        totalAfterDiscountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalAfterDiscountLabel.setForeground(Color.RED);
        totalAfterDiscountPanel.add(totalAfterDiscountLabel);
        summaryPanel.add(totalAfterDiscountPanel);

        bottomPanel.add(summaryPanel, BorderLayout.NORTH);

        CustomButton checkoutButton = new CustomButton("Thanh Toán", new Color(34, 139, 34), Color.WHITE, 10);
        bottomPanel.add(checkoutButton, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);
    }
}