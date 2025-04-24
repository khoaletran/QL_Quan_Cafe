package UI;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    public RightPanel(ProductDetailPanel productDetailPanel, OrderPanel orderPanel) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(250, 250, 250));
        setPreferredSize(new Dimension(300, 0));
        createUI(productDetailPanel, orderPanel);
    }

    private void createUI(ProductDetailPanel productDetailPanel, OrderPanel orderPanel) {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Chi Tiết Sản Phẩm", productDetailPanel);
        tabbedPane.addTab("Đơn Hàng", orderPanel);
        add(tabbedPane, BorderLayout.CENTER);
    }
}