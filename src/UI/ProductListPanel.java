package UI;

import javax.swing.*;

import Dao.HangHoa_DAO;
import Model.HangHoa;

import java.awt.*;
import java.util.ArrayList;

public class ProductListPanel extends JPanel {
	
	ArrayList<HangHoa> dsHH = HangHoa_DAO.getAllHangHoa();
	
    public ProductListPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
        searchPanel.add(searchButton, BorderLayout.EAST);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        JPanel productGridPanel = new JPanel(new GridLayout(0, 4, 15, 15));
        productGridPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        addProduct(productGridPanel);
        
        JScrollPane scrollPane = new JScrollPane(productGridPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void addProduct(JPanel productGridPanel) {
    	for(HangHoa hh : dsHH) {
    		productGridPanel.add(createProductCard(hh.getTenHH(), hh.getHinhAnh(), hh.getGiaSP()));
    	}
    }

    private JPanel createProductCard(String name, String imagePath, double price) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);

        JLabel imageLabel = new JLabel("No Image", SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        imageLabel.setPreferredSize(new Dimension(150, 150));
        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
            Image scaledImage = icon.getImage().getScaledInstance(180, 150, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
            imageLabel.setText("");
        } catch (Exception e) {
            System.out.println("Không load được ảnh: " + imagePath);
        }


        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel priceLabel = new JLabel(String.format("%,.0fđ", price), SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setForeground(Color.RED);

        CustomButton selectButton = new CustomButton("Chọn", new Color(70, 130, 180), Color.WHITE, 10);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);

        card.add(imageLabel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(selectButton, BorderLayout.SOUTH);
        return card;
    }
}