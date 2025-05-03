package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ConnectDB.ConnectDB;
import Dao.HangHoa_DAO;
import Dao.LoaiHangHoa_DAO;
import Model.HangHoa;
import Model.LoaiHangHoa;

public class ProductListPanel extends JPanel {
    private List<HangHoa> dsHH;
    private JPanel productGridPanel;
    private OrderPanel orderPanel;
    private JTextField searchField;
    private JComboBox<String> cbLocLoaiHH;
    private HangHoa_DAO hangHoaDAO;
    private LoaiHangHoa_DAO loaiHangHoaDAO;

    public ProductListPanel(OrderPanel orderPanel) {
        try {
            ConnectDB.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.orderPanel = orderPanel;
        setLayout(new BorderLayout());
        hangHoaDAO = new HangHoa_DAO();
        loaiHangHoaDAO = new LoaiHangHoa_DAO();
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);
        createUI();
    }

    private void createUI() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("DANH SÁCH SẢN PHẨM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        // Panel chứa tìm kiếm và lọc
        JPanel filterPanel = new JPanel(new BorderLayout(10, 5));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        filterPanel.setBackground(Color.WHITE);

        // Thanh tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setPreferredSize(new Dimension(200, 15));
        CustomButton searchButton = new CustomButton("🔍", Color.WHITE, Color.BLACK, 5);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Bộ lọc loại hàng hóa
        JPanel locLoaiHangCbo = new JPanel(new BorderLayout());
        locLoaiHangCbo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Lọc theo loại hàng"),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        
        cbLocLoaiHH = new JComboBox<>();
        cbLocLoaiHH.setFont(new Font("Arial", Font.PLAIN, 12));
        cbLocLoaiHH.addItem("Tất cả");
        try {
            List<LoaiHangHoa> dsLoaiHH = loaiHangHoaDAO.getAllLoaiHangHoa();
            for (LoaiHangHoa lhh : dsLoaiHH) {
                cbLocLoaiHH.addItem(lhh.getTenLoaiHang());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách loại hàng hóa!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        cbLocLoaiHH.addItemListener(e -> locLoaiHang());
        locLoaiHangCbo.add(cbLocLoaiHH, BorderLayout.CENTER);

        filterPanel.add(searchPanel, BorderLayout.CENTER);
        filterPanel.add(locLoaiHangCbo, BorderLayout.EAST);

        // Thêm DocumentListener cho tìm kiếm động
        searchField.getDocument().addDocumentListener(createDocumentListener());

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(filterPanel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        productGridPanel = new JPanel(new GridLayout(0, 4, 10, 30));
        productGridPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        productGridPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(productGridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        loadProducts(); // Tải sản phẩm sau khi tạo UI
    }

    private DocumentListener createDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                thucHienTimKiem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                thucHienTimKiem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                thucHienTimKiem();
            }

            private void thucHienTimKiem() {
                String keyword = searchField.getText().trim();
                try {
                    if (keyword.isEmpty()) {
                        dsHH = hangHoaDAO.getAllHangHoaForSanPhamPanel(); // Hiển thị tất cả nếu từ khóa rỗng
                    } else {
                        dsHH = hangHoaDAO.timKiemHangHoa(keyword); // Giả sử phương thức này tồn tại
                    }
                    addProducts(dsHH); // Cập nhật danh sách sản phẩm
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ProductListPanel.this, 
                        "Lỗi khi tìm kiếm sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    private void loadProducts() {
        try {
            dsHH = hangHoaDAO.getAllHangHoaForSanPhamPanel();
            addProducts(dsHH);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sản phẩm!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void locLoaiHang() {
        String loaiHang = cbLocLoaiHH.getSelectedItem().toString();
        try {
            List<HangHoa> filteredHH = new ArrayList<>();
            for (HangHoa hh : dsHH) {
                if (loaiHang.equals("Tất cả") || 
                    (hh.getLoaiHangHoa() != null && 
                     hh.getLoaiHangHoa().getTenLoaiHang().equalsIgnoreCase(loaiHang))) {
                    filteredHH.add(hh);
                }
            }
            addProducts(filteredHH); // Cập nhật danh sách sản phẩm
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc sản phẩm!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProducts(List<HangHoa> dsHH2) {
        productGridPanel.removeAll();
        for (HangHoa hh : dsHH2) {
            productGridPanel.add(createProductCard(hh));
        }
        productGridPanel.revalidate();
        productGridPanel.repaint();
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
                    Image scaledImage = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
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
        selectButton.addActionListener(e -> addToOrder(hh));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);

        card.add(imageLabel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(selectButton, BorderLayout.SOUTH);
        return card;
    }

    private void addToOrder(HangHoa hh) {
        if (hh != null) {
            orderPanel.addOrderItem(hh, 1);
        }
    }
}