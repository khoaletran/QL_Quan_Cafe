package UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Dao.HangHoa_DAO;
import Model.HangHoa;

public class ProductListPanel extends JPanel {
    private ArrayList<HangHoa> dsHH;
    private ProductDetailPanel productDetailPanel; // Tham chiếu đến ProductDetailPanel
    private JPanel productGridPanel; // Để cập nhật sản phẩm khi tìm kiếm

    public ProductListPanel(ProductDetailPanel productDetailPanel) {
        this.productDetailPanel = productDetailPanel;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);
        createUI();
    }

    private void createUI() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("DANH SÁCH SẢN PHẨM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        JTextField searchField = new JTextField();
        searchPanel.add(searchField, BorderLayout.CENTER);
        CustomButton searchButton = new CustomButton("🔍", Color.WHITE, Color.BLACK, 5);
        searchButton.addActionListener(e -> searchProducts(searchField.getText().trim()));
        searchPanel.add(searchButton, BorderLayout.EAST);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        productGridPanel = new JPanel(new GridLayout(0, 4, 15, 15));
        productGridPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        productGridPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(productGridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        loadProducts(); // Tải sản phẩm sau khi tạo UI
    }

    private void loadProducts() {
        try {
            dsHH = HangHoa_DAO.getAllHangHoa();
            addProducts(dsHH);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProducts(ArrayList<HangHoa> products) {
        productGridPanel.removeAll();
        for (HangHoa hh : products) {
            productGridPanel.add(createProductCard(hh));
        }
        productGridPanel.revalidate();
        productGridPanel.repaint();
    }

    private void searchProducts(String keyword) {
        if (keyword.isEmpty()) {
            addProducts(dsHH); // Hiển thị tất cả nếu không có từ khóa
            return;
        }
        ArrayList<HangHoa> filteredProducts = new ArrayList<>();
        for (HangHoa hh : dsHH) {
            if (hh.getTenHH().toLowerCase().contains(keyword.toLowerCase())) {
                filteredProducts.add(hh);
            }
        }
        addProducts(filteredProducts);
    }

    private JPanel createProductCard(HangHoa hh) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(200, 250));

        JLabel imageLabel = new JLabel("No Image", SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        imageLabel.setPreferredSize(new Dimension(150, 150));
        if (hh.getHinhAnh() != null && !hh.getHinhAnh().isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(hh.getHinhAnh()));
                if (icon.getImage() != null) {
                    Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                    imageLabel.setText("");
                } else {
                    System.out.println("Hình ảnh không tồn tại: " + hh.getHinhAnh());
                }
            } catch (Exception e) {
                System.out.println("Không load được ảnh: " + hh.getHinhAnh());
            }
        }

        JLabel nameLabel = new JLabel(hh.getTenHH(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel priceLabel = new JLabel(String.format("%,.0fđ", hh.getGiaSP()), SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setForeground(Color.RED);

        CustomButton selectButton = new CustomButton("Chọn", new Color(70, 130, 180), Color.WHITE, 10);
        selectButton.addActionListener(e -> productDetailPanel.updateProductDetails(hh));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);

        card.add(imageLabel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(selectButton, BorderLayout.SOUTH);
        return card;
    }
}