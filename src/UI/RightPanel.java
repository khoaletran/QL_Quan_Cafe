package UI;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    public RightPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(250, 250, 250));
        setPreferredSize(new Dimension(300, 0));
        createUI();
    }

    private void createUI() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Chi Tiết Sản Phẩm", new ProductDetailPanel());
        tabbedPane.addTab("Đơn Hàng", new OrderPanel());
        add(tabbedPane, BorderLayout.CENTER);
    }
}