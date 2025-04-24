package UI;

import javax.swing.*;

import Model.HangHoa;

import java.awt.*;

public class ProductDetailPanel extends JPanel {
    private JLabel productImage;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel categoryLabel;
    private JTextArea descriptionArea;
    private JSpinner quantitySpinner;
    private HangHoa currentProduct; // Lưu món hàng hiện tại
    private OrderPanel orderPanel; // Tham chiếu đến OrderPanel

    public ProductDetailPanel(OrderPanel orderPanel) {
        this.orderPanel = orderPanel;
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

        productImage = new JLabel("No Image", SwingConstants.CENTER);
        productImage.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        productImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        productImage.setPreferredSize(new Dimension(200, 200));

        nameLabel = new JLabel("Tên sản phẩm", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        priceLabel = new JLabel("0đ", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(Color.RED);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        categoryLabel = new JLabel("Danh mục: N/A", SwingConstants.CENTER);
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        descriptionArea = new JTextArea("Không có mô tả");
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
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantityPanel.add(quantitySpinner);

        CustomButton addButton = new CustomButton("Thêm vào đơn", new Color(70, 130, 180), Color.WHITE, 10);
        addButton.addActionListener(e -> addToOrder());

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

    public void updateProductDetails(HangHoa hh) {
        this.currentProduct = hh;
        nameLabel.setText(hh.getTenHH());
        priceLabel.setText(String.format("%,.0fđ", hh.getGiaSP()));
        categoryLabel.setText("Danh mục: " + hh.getLoaiHH()); // Sử dụng getLoaiHH()
        descriptionArea.setText(hh.getMoTa()); // Sử dụng getMoTa()

        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(hh.getHinhAnh()));
            Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            productImage.setIcon(new ImageIcon(scaledImage));
            productImage.setText("");
        } catch (Exception e) {
            productImage.setIcon(null);
            productImage.setText("No Image");
            System.out.println("Không load được ảnh: " + hh.getHinhAnh());
        }

        quantitySpinner.setValue(1); // Reset số lượng về 1
    }

    private void addToOrder() {
        if (currentProduct != null) {
            int quantity = (Integer) quantitySpinner.getValue();
            orderPanel.addOrderItem(currentProduct, quantity);
        }
    }
}