package UI;

import javax.swing.*;
import java.awt.*;

public class ProductDetailPanel extends JPanel {
    public ProductDetailPanel() {
        setLayout(new BorderLayout());
        createUI();
    }

    private void createUI() {
        JLabel titleLabel = new JLabel("CHI TIẾT SẢN PHẨM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(Color.WHITE);

        JLabel productImage = new JLabel("No Image", SwingConstants.CENTER);
        productImage.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        productImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        productImage.setPreferredSize(new Dimension(200, 200));

        JLabel nameLabel = new JLabel("Tên sản phẩm", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel priceLabel = new JLabel("0đ", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(Color.RED);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel categoryLabel = new JLabel("Danh mục: N/A", SwingConstants.CENTER);
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea descriptionArea = new JTextArea("Không có mô tả");
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        descriptionArea.setMaximumSize(new Dimension(260, 100));

        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBackground(Color.WHITE);
        descriptionPanel.add(new JLabel("Mô tả:"), BorderLayout.NORTH);
        descriptionPanel.add(descriptionArea, BorderLayout.CENTER);

        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        quantityPanel.setBackground(Color.WHITE);
        quantityPanel.add(new JLabel("Số lượng:"));
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantityPanel.add(quantitySpinner);

        CustomButton addButton = new CustomButton("Thêm vào đơn", new Color(70, 130, 180), Color.WHITE, 10);

        contentPanel.add(productImage);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(nameLabel);
        contentPanel.add(priceLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(categoryLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(descriptionPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(quantityPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(addButton);

        add(contentPanel, BorderLayout.CENTER);
    }
}