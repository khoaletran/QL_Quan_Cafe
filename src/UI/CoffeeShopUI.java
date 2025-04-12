package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import Model.ChiTietHoaDon;
import Model.DanhSach_HoaDon;
import Model.DanhSach_KhachHang;
import Model.DanhSach_MaGiamGia;
import Model.HangHoa;
import Model.HoaDonBanHang;
import Model.KhachHang;
import Model.MaGiamGia;

public class CoffeeShopUI extends JFrame {
    
    private Map<String, HangHoa> products = new HashMap<>();
    private List<ChiTietHoaDon> currentOrder = new ArrayList<>();
    private DanhSach_HoaDon orderHistory;
    private DanhSach_KhachHang customerList;
    private DanhSach_MaGiamGia discountCodeList;
    private JPanel productGridPanel;
    private JTextField searchField;
    private JTable orderTable;
    private DefaultTableModel orderTableModel;
    private JTextField phoneField; // TextField để nhập số điện thoại khách hàng
    private JTextField discountCodeField; // TextField để nhập mã giảm giá
    
    public CoffeeShopUI() {
        setTitle("Quản Lý Quán Cafe - Phiên Bản Hoàn Thiện");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        orderHistory = new DanhSach_HoaDon();
        customerList = new DanhSach_KhachHang();
        discountCodeList = new DanhSach_MaGiamGia();
        // Khởi tạo một số mã giảm giá mẫu
        discountCodeList.them(new MaGiamGia("DISCOUNT5000", 5)); // Giảm 5%
        discountCodeList.them(new MaGiamGia("DISCOUNT10000", 10)); // Giảm 10%
        
        initializeProducts();
        createResponsiveUI();
        
        setVisible(true);
    }
    
    private void initializeProducts() {
        products.put("H0001", new HangHoa("H0001", "Cafe Đen", "resources/images/OIP.jpg", 25000, 0));
        products.put("H0002", new HangHoa("H0002", "Cafe Sữa", "resources/images/cafe_sua.jpg", 30000, 0));
        products.put("H0003", new HangHoa("H0003", "Trà Sen", "resources/images/tra_sen.jpg", 35000, 0));
        products.put("H0004", new HangHoa("H0004", "Trà Đào", "resources/images/tra_dao.jpg", 40000, 0));
        products.put("H0005", new HangHoa("H0005", "Sinh Tố Dâu", "resources/images/sinh_to_dau.jpg", 45000, 0));
        products.put("H0006", new HangHoa("H0006", "Sinh Tố Xoài", "resources/images/sinh_to_xoai.jpg", 45000, 0));
    }
    
    private void createResponsiveUI() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JPanel leftMenuPanel = createLeftMenuPanel();
        mainPanel.add(leftMenuPanel, BorderLayout.WEST);
        
        JPanel productListPanel = createProductListPanel();
        mainPanel.add(productListPanel, BorderLayout.CENTER);
        
        JPanel rightPanel = createRightPanel();
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JLabel footerLabel = new JLabel("Hệ Thống Quản Lý Quán Cafe - © 2025", SwingConstants.CENTER);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(footerLabel, BorderLayout.SOUTH);
        
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                leftMenuPanel.setPreferredSize(new Dimension(width / 4, 0));
                productListPanel.setPreferredSize(new Dimension(width / 2, 0));
                rightPanel.setPreferredSize(new Dimension(width / 4, 0));
                revalidate();
            }
        });
    }
    
    private JPanel createLeftMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(245, 245, 245));
        
        JLabel logoLabel = new JLabel("QUÁN CAFE", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        panel.add(logoLabel, BorderLayout.NORTH);
        
        JPanel menuButtonsPanel = new JPanel(new GridLayout(0, 1, 0, 5));
        
        String[] menuItems = {
            "🏠 Bảng Điều Khiển",
            "📝 Đơn Hàng Mới",
            "🛒 Quản Lý Đơn",
            "☕ Sản Phẩm",
            "👥 Khách Hàng",
            "👨‍💼 Nhân Viên",
            "📊 Thống Kê",
            "⚙️ Cài Đặt"
        };
        
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBackground(new Color(230, 230, 230));
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            button.setFocusPainted(false);
            button.addActionListener(e -> handleMenuAction(item));
            menuButtonsPanel.add(button);
        }
        
        JScrollPane scrollPane = new JScrollPane(menuButtonsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void handleMenuAction(String menuItem) {
    switch (menuItem) {
        case "📝 Đơn Hàng Mới":
            currentOrder.clear();
            phoneField.setText(""); // Reset số điện thoại khi tạo đơn mới
            discountCodeField.setText(""); // Reset mã giảm giá
            updateOrderTable();
            JOptionPane.showMessageDialog(this, "Đã tạo đơn hàng mới!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            break;
        case "🛒 Quản Lý Đơn":
            StringBuilder orderList = new StringBuilder("Danh sách hóa đơn:\n");
            for (HoaDonBanHang hd : orderHistory.getListHD()) {
                orderList.append("Mã HĐ: ").append(hd.getMaHDBH())
                         .append(", Tổng: ").append(hd.tinhTongThanhToan())
                         .append("đ\n");
            }
            JOptionPane.showMessageDialog(this, orderList.toString(), "Danh sách đơn hàng", JOptionPane.INFORMATION_MESSAGE);
            break;
        default:
            JOptionPane.showMessageDialog(this, "Chức năng " + menuItem + " đang được phát triển!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}
    
    private JPanel createProductListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("DANH SÁCH SẢN PHẨM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchField = new JTextField();
        searchPanel.add(searchField, BorderLayout.CENTER);
        JButton searchButton = new JButton("🔍");
        searchPanel.add(searchButton, BorderLayout.EAST);
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);
        panel.add(headerPanel, BorderLayout.NORTH);
        
        productGridPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        productGridPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        updateProductGrid("");
        
        JScrollPane scrollPane = new JScrollPane(productGridPanel);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { updateSearch(); }
            public void removeUpdate(DocumentEvent e) { updateSearch(); }
            public void insertUpdate(DocumentEvent e) { updateSearch(); }
            
            private void updateSearch() {
                updateProductGrid(searchField.getText().toLowerCase());
            }
        });
        
        searchField.setActionCommand("search");
        searchField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "search");
        searchField.getActionMap().put("search", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                updateProductGrid(searchField.getText().toLowerCase());
            }
        });
        
        return panel;
    }
    
    private void updateProductGrid(String query) {
        productGridPanel.removeAll();
        for (HangHoa product : products.values()) {
            if (query.isEmpty() || 
                product.getTenHH().toLowerCase().contains(query)) {
                productGridPanel.add(createProductCard(product));
            }
        }
        productGridPanel.revalidate();
        productGridPanel.repaint();
    }
    
    private JPanel createProductCard(HangHoa product) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);
        
        JLabel imageLabel = createProductImage(product.getHinhAnh());
        imageLabel.setPreferredSize(new Dimension(150, 150));
        
        JLabel nameLabel = new JLabel(product.getTenHH(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        JLabel priceLabel = new JLabel(formatter.format(product.getGiaSP()) + "đ", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setForeground(Color.RED);
        
        JButton selectButton = new JButton("Chọn");
        selectButton.addActionListener(e -> showProductDetail(product.getMaHH()));
        
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        
        card.add(imageLabel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(selectButton, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JLabel createProductImage(String imagePath) {
        JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
        try {
            java.net.URL imageURL = getClass().getResource("/" + imagePath);
            if (imageURL != null) {
                ImageIcon icon = new ImageIcon(imageURL);
                Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                throw new Exception("Image not found");
            }
        } catch (Exception e) {
            imageLabel.setText("No Image");
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }
        return imageLabel;
    }
    
    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(250, 250, 250));
        
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(280, 0));
        
        JPanel productDetailPanel = createProductDetailPanel();
        tabbedPane.addTab("Chi Tiết Sản Phẩm", productDetailPanel);
        
        JPanel orderPanel = createOrderPanel();
        tabbedPane.addTab("Đơn Hàng", orderPanel);
        
        panel.add(tabbedPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createProductDetailPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("CHI TIẾT SẢN PHẨM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setMaximumSize(new Dimension(280, Integer.MAX_VALUE));
        
        JLabel productImage = new JLabel("No Image", SwingConstants.CENTER);
        productImage.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        productImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        productImage.setPreferredSize(new Dimension(200, 200));
        productImage.setMaximumSize(new Dimension(200, 200));
        
        JLabel nameLabel = new JLabel("", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel priceLabel = new JLabel("", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(Color.RED);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel categoryLabel = new JLabel("Danh mục: ", SwingConstants.CENTER);
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        descriptionArea.setMaximumSize(new Dimension(260, 100));
        
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBackground(Color.WHITE);
        descriptionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionPanel.add(new JLabel("Mô tả:"), BorderLayout.NORTH);
        descriptionPanel.add(descriptionArea, BorderLayout.CENTER);
        
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        quantityPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantityPanel.setBackground(Color.WHITE);
        quantityPanel.add(new JLabel("Số lượng:"));
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantityPanel.add(quantitySpinner);
        
        JButton addButton = new JButton("Thêm vào đơn");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setOpaque(true);
        addButton.setBorderPainted(false);
        addButton.setContentAreaFilled(true);
        addButton.addActionListener(e -> {
            HangHoa product = products.get((String) panel.getClientProperty("currentProductId"));
            if (product != null) {
                int quantity = (int) quantitySpinner.getValue();
                addToOrder(product, quantity);
            }
        });
        
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
        
        panel.putClientProperty("productImage", productImage);
        panel.putClientProperty("nameLabel", nameLabel);
        panel.putClientProperty("priceLabel", priceLabel);
        panel.putClientProperty("categoryLabel", categoryLabel);
        panel.putClientProperty("descriptionArea", descriptionArea);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createOrderPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Tiêu đề và TextField số điện thoại
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

    panel.add(headerPanel, BorderLayout.NORTH);

    // Bảng đơn hàng
    String[] columns = {"Sản phẩm", "Số lượng", "Giá", "Tổng", "Xóa"};
    orderTableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4;
        }
    };
    orderTable = new JTable(orderTableModel);
    orderTable.getColumn("Xóa").setCellRenderer(new ButtonRenderer());
    orderTable.getColumn("Xóa").setCellEditor(new ButtonEditor(new JCheckBox()));

    orderTable.getColumnModel().getColumn(0).setPreferredWidth(100);
    orderTable.getColumnModel().getColumn(1).setPreferredWidth(60);
    orderTable.getColumnModel().getColumn(2).setPreferredWidth(60);
    orderTable.getColumnModel().getColumn(3).setPreferredWidth(60);
    orderTable.getColumnModel().getColumn(4).setPreferredWidth(50);

    JScrollPane tableScrollPane = new JScrollPane(orderTable);
    panel.add(tableScrollPane, BorderLayout.CENTER);

    // Phần tổng kết
    JPanel bottomPanel = new JPanel(new BorderLayout());

    // Panel chứa tổng, mã giảm giá, chiết khấu, thành tiền
    JPanel summaryPanel = new JPanel();
    summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));

    // Tổng
    JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JLabel totalLabel = new JLabel("Tổng: 0đ");
    totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
    totalPanel.add(totalLabel);
    summaryPanel.add(totalPanel);

    // Mã giảm giá
    JPanel discountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JLabel discountCodeLabel = new JLabel("Mã giảm giá:");
    discountCodeLabel.setFont(new Font("Arial", Font.BOLD, 14));
    discountCodeLabel.setForeground(Color.BLUE);
    discountCodeField = new JTextField(10);
    discountCodeField.setPreferredSize(new Dimension(100, 25));
    discountCodeField.getDocument().addDocumentListener(new DocumentListener() {
        public void changedUpdate(DocumentEvent e) { updateDiscount(); }
        public void removeUpdate(DocumentEvent e) { updateDiscount(); }
        public void insertUpdate(DocumentEvent e) { updateDiscount(); }

        private void updateDiscount() {
            updateOrderTable();
        }
    });
    discountPanel.add(discountCodeLabel);
    discountPanel.add(discountCodeField);
    summaryPanel.add(discountPanel);

    // Chiết khấu
    JPanel discountAmountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JLabel discountAmountLabel = new JLabel("Chiết khấu: 0đ");
    discountAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
    discountAmountLabel.setForeground(Color.BLUE);
    discountAmountPanel.add(discountAmountLabel);
    summaryPanel.add(discountAmountPanel);

    // Thành tiền
    JPanel totalAfterDiscountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JLabel totalAfterDiscountLabel = new JLabel("Thành tiền: 0đ");
    totalAfterDiscountLabel.setFont(new Font("Arial", Font.BOLD, 16));
    totalAfterDiscountLabel.setForeground(Color.RED);
    totalAfterDiscountPanel.add(totalAfterDiscountLabel);
    summaryPanel.add(totalAfterDiscountPanel);

    bottomPanel.add(summaryPanel, BorderLayout.NORTH);

    // Nút Thanh Toán
    JButton checkoutButton = new JButton("Thanh Toán");
    checkoutButton.setBackground(new Color(34, 139, 34));
    checkoutButton.setForeground(Color.WHITE);
    checkoutButton.setOpaque(true);
    checkoutButton.setBorderPainted(false);
    checkoutButton.setContentAreaFilled(true);
    checkoutButton.addActionListener(e -> checkout());
    bottomPanel.add(checkoutButton, BorderLayout.CENTER);

    panel.add(bottomPanel, BorderLayout.SOUTH);

    // Lưu các nhãn vào panel để sử dụng sau
    panel.putClientProperty("totalLabel", totalLabel);
    panel.putClientProperty("discountAmountLabel", discountAmountLabel);
    panel.putClientProperty("totalAfterDiscountLabel", totalAfterDiscountLabel);

    return panel;
}
    
    private void showProductDetail(String productId) {
        HangHoa product = products.get(productId);
        if (product == null) {
            System.out.println("Product not found for ID: " + productId);
            return;
        }

        Container contentPane = getContentPane();
        Component mainComponent = contentPane.getComponent(0);
        if (!(mainComponent instanceof JPanel)) return;

        JPanel mainPanel = (JPanel) mainComponent;
        Component[] mainComponents = mainPanel.getComponents();
        JPanel rightPanel = null;
        for (Component comp : mainComponents) {
            Object constraint = ((BorderLayout) mainPanel.getLayout()).getConstraints(comp);
            if (BorderLayout.EAST.equals(constraint)) {
                rightPanel = (JPanel) comp;
                break;
            }
        }

        if (rightPanel != null) {
            JTabbedPane tabbedPane = (JTabbedPane) rightPanel.getComponent(0);
            JPanel productDetailPanel = (JPanel) tabbedPane.getComponent(0);
            
            JLabel nameLabel = (JLabel) productDetailPanel.getClientProperty("nameLabel");
            JLabel priceLabel = (JLabel) productDetailPanel.getClientProperty("priceLabel");
            JLabel categoryLabel = (JLabel) productDetailPanel.getClientProperty("categoryLabel");
            JTextArea descriptionArea = (JTextArea) productDetailPanel.getClientProperty("descriptionArea");
            JLabel productImage = (JLabel) productDetailPanel.getClientProperty("productImage");
            
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            nameLabel.setText(product.getTenHH());
            priceLabel.setText(formatter.format(product.getGiaSP()) + "đ");
            categoryLabel.setText("Danh mục: N/A"); // HangHoa không có danh mục, có thể thêm nếu cần
            descriptionArea.setText("Không có mô tả"); // HangHoa không có mô tả, có thể thêm nếu cần
            
            productImage.setIcon(null);
            productImage.setText("No Image");
            try {
                String imagePath = product.getHinhAnh();
                System.out.println("Loading image from path: " + imagePath);
                java.net.URL imageURL = getClass().getResource("/" + imagePath);
                if (imageURL != null) {
                    System.out.println("Image found: " + imageURL);
                    ImageIcon icon = new ImageIcon(imageURL);
                    Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    productImage.setIcon(new ImageIcon(scaledImage));
                    productImage.setText("");
                } else {
                    System.out.println("Image not found at path: " + imagePath);
                    throw new Exception("Image not found");
                }
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
                productImage.setText("No Image");
            }
            
            productDetailPanel.putClientProperty("currentProductId", productId);
        }
    }
    
    private void addToOrder(HangHoa product, int quantity) {
        for (ChiTietHoaDon item : currentOrder) {
            if (item.getHangHoa().getMaHH().equals(product.getMaHH())) {
                item.setSoLuong(item.getSoLuong() + quantity);
                updateOrderTable();
                JOptionPane.showMessageDialog(this, "Đã cập nhật số lượng " + product.getTenHH() + "!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        currentOrder.add(new ChiTietHoaDon(quantity, product));
        updateOrderTable();
        JOptionPane.showMessageDialog(this, "Đã thêm " + quantity + " " + product.getTenHH() + " vào đơn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private double calculateDiscount(double total) {
        String code = discountCodeField.getText().trim();
        if (code.isEmpty()) {
            return 0; // Không có mã giảm giá
        }
        MaGiamGia discountCode = discountCodeList.LayMa(code);
        if (discountCode != null) {
            return total * (discountCode.getGiamGia() / 100.0); // Tính phần trăm giảm giá
        }
        return 0; // Mã không hợp lệ
    }
    
    private void updateOrderTable() {
        orderTableModel.setRowCount(0);
        
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        double total = 0;
        
        for (ChiTietHoaDon item : currentOrder) {
            total += item.getThanhTien();
            Object[] row = {
                item.getHangHoa().getTenHH(),
                item.getSoLuong(),
                formatter.format(item.getHangHoa().getGiaSP()) + "đ",
                formatter.format(item.getThanhTien()) + "đ",
                "Xóa"
            };
            orderTableModel.addRow(row);
        }
        
        double discount = calculateDiscount(total);
        double totalAfterDiscount = total - discount;
        if (totalAfterDiscount < 0) totalAfterDiscount = 0;
        
        Container contentPane = getContentPane();
        Component mainComponent = contentPane.getComponent(0);
        if (!(mainComponent instanceof JPanel)) return;
        
        JPanel mainPanel = (JPanel) mainComponent;
        Component[] mainComponents = mainPanel.getComponents();
        JPanel rightPanel = null;
        for (Component comp : mainComponents) {
            Object constraint = ((BorderLayout) mainPanel.getLayout()).getConstraints(comp);
            if (BorderLayout.EAST.equals(constraint)) {
                rightPanel = (JPanel) comp;
                break;
            }
        }
        
        if (rightPanel != null) {
            JTabbedPane tabbedPane = (JTabbedPane) rightPanel.getComponent(0);
            JPanel orderPanel = (JPanel) tabbedPane.getComponent(1);
            
            JLabel totalLabel = (JLabel) orderPanel.getClientProperty("totalLabel");
            JLabel discountAmountLabel = (JLabel) orderPanel.getClientProperty("discountAmountLabel");
            JLabel totalAfterDiscountLabel = (JLabel) orderPanel.getClientProperty("totalAfterDiscountLabel");
            
            totalLabel.setText("Tổng: " + formatter.format(total) + "đ");
            discountAmountLabel.setText("Chiết khấu: " + formatter.format(discount) + "đ");
            totalAfterDiscountLabel.setText("Tổng tiền sau giảm giá: " + formatter.format(totalAfterDiscount) + "đ");
        }
    }
    
    private void checkout() {
    if (currentOrder.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Đơn hàng trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Kiểm tra số điện thoại
    String phoneNumber = phoneField.getText().trim();
    if (!phoneNumber.matches("^0[2-9]\\d{8}$")) {
        JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! Vui lòng nhập đúng định dạng (bắt đầu từ 02-09 và đủ 10 số).", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Tìm hoặc tạo khách hàng
    KhachHang customer = customerList.timKHbangMa(phoneNumber);
    if (customer == null) {
        customer = new KhachHang("Khách hàng mới", phoneNumber, 0); // Tạo khách hàng mới
        customerList.themKH(customer);
    }
    
    // Tạo mã hóa đơn (giả lập)
    String maHDBH = "HD" + String.format("%04d", orderHistory.size() + 1);
    
    // Tạo hóa đơn bán hàng
    HoaDonBanHang order = new HoaDonBanHang(discountCodeList, customerList, "", phoneNumber, discountCodeField.getText().trim());
    order.setMaHDBH(maHDBH);
    order.setNgayLapHDBH(LocalDate.now());
    for (ChiTietHoaDon item : currentOrder) {
        order.themChiTiet(item);
    }
    
    NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
    double total = order.tinhTong();
    double discount = calculateDiscount(total);
    double totalAfterDiscount = total - discount;
    if (totalAfterDiscount < 0) totalAfterDiscount = 0;
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Số điện thoại khách hàng: " + phoneNumber + "\n" +
        "Tổng tiền: " + formatter.format(total) + "đ\n" +
        "Chiết khấu: " + formatter.format(discount) + "đ\n" +
        "Tổng tiền sau giảm giá: " + formatter.format(totalAfterDiscount) + "đ\n" +
        "Xác nhận thanh toán?", 
        "Thanh Toán", JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        orderHistory.add(order);
        
        // Cập nhật điểm tích lũy cho khách hàng
        int pointsEarned = order.getdiemTL();
        customer.setDiemTL(customer.getDiemTL() + pointsEarned);
        
        printReceipt(order);
        
        currentOrder.clear();
        phoneField.setText("");
        discountCodeField.setText("");
        updateOrderTable();
        JOptionPane.showMessageDialog(this, "Thanh toán thành công! Hóa đơn đã được xuất.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}
    
    private void printReceipt(HoaDonBanHang order) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        double total = order.tinhTong();
        double discount = calculateDiscount(total);
        double totalAfterDiscount = total - discount;
        if (totalAfterDiscount < 0) totalAfterDiscount = 0;
        
        StringBuilder receipt = new StringBuilder();
        receipt.append("==================================\n");
        receipt.append("         HÓA ĐƠN THANH TOÁN       \n");
        receipt.append("==================================\n");
        receipt.append("HỆ THỐNG QUÁN CAFE\n");
        receipt.append("Thời gian: ").append(dateFormat.format(new Date())).append("\n");
        receipt.append("Mã hóa đơn: ").append(order.getMaHDBH()).append("\n");
        receipt.append("Số điện thoại khách hàng: ").append(phoneField.getText()).append("\n");
        receipt.append("----------------------------------\n");
        receipt.append(String.format("%-15s %-10s %-10s %-10s\n", "Sản phẩm", "Số lượng", "Giá", "Tổng"));
        receipt.append("----------------------------------\n");
        
        for (ChiTietHoaDon item : order.getChiTietHoaDonList()) {
            receipt.append(String.format("%-15s %-10d %-10s %-10s\n",
                item.getHangHoa().getTenHH(),
                item.getSoLuong(),
                formatter.format(item.getHangHoa().getGiaSP()) + "đ",
                formatter.format(item.getThanhTien()) + "đ"));
        }
        
        //
        
        receipt.append("----------------------------------\n");
        receipt.append(String.format("%-25s %10s\n", "Tổng tiền:", formatter.format(total) + "đ"));
        receipt.append(String.format("%-25s %10s\n", "Chiết khấu:", formatter.format(discount) + "đ"));
        receipt.append(String.format("%-25s %10s\n", "Tổng tiền sau giảm giá:", formatter.format(totalAfterDiscount) + "đ"));
        receipt.append("Điểm tích lũy: ").append(order.getdiemTL()).append("\n");
        receipt.append("==================================\n");
        receipt.append("Cảm ơn quý khách!\n");
        
        JFrame receiptFrame = new JFrame("Hóa Đơn Thanh Toán");
        receiptFrame.setSize(400, 500);
        receiptFrame.setLocationRelativeTo(this);
        receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JTextArea receiptArea = new JTextArea(receipt.toString());
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        receiptFrame.add(new JScrollPane(receiptArea));
        
        receiptFrame.setVisible(true);
        
        saveReceiptToFile(receipt.toString(), System.currentTimeMillis());
    }
    
    private void saveReceiptToFile(String receipt, long timestamp) {
        SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = "hoadon_" + fileNameFormat.format(new Date(timestamp)) + ".txt";
        
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(receipt);
            System.out.println("Hóa đơn đã được lưu vào file: " + fileName);
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu hóa đơn vào file: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Không thể lưu hóa đơn vào file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CoffeeShopUI();
        });
    }
    
    
    
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Xóa");
            setBackground(Color.RED);
            setForeground(Color.WHITE);
            setBorderPainted(false);
            setContentAreaFilled(true);
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
            button.setContentAreaFilled(true);
            button.addActionListener(e -> fireEditingStopped());
        }
        
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }
        
        public Object getCellEditorValue() {
            if (isPushed) {
                int row = orderTable.getSelectedRow();
                if (row >= 0 && row < currentOrder.size()) {
                    currentOrder.remove(row);
                    updateOrderTable();
                    JOptionPane.showMessageDialog(CoffeeShopUI.this, "Đã xóa sản phẩm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            isPushed = false;
            return label;
        }
        
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
        
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}