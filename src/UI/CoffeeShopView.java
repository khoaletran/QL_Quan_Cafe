package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CoffeeShopView extends JFrame {
    public CoffeeShopView() {
        setTitle("Quản Lý Quán Cafe - Phiên Bản Hoàn Thiện");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
        setVisible(true);
    }

    private void createUI() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new LeftMenuPanel(), BorderLayout.WEST);
        mainPanel.add(new ProductListPanel(), BorderLayout.CENTER);
        mainPanel.add(new RightPanel(), BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);

        JLabel footerLabel = new JLabel("Hệ Thống Quản Lý Quán Cafe - © 2025", SwingConstants.CENTER);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(footerLabel, BorderLayout.SOUTH);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                mainPanel.getComponent(0).setPreferredSize(new Dimension(width / 4, 0));
                mainPanel.getComponent(1).setPreferredSize(new Dimension(width / 2, 0));
                mainPanel.getComponent(2).setPreferredSize(new Dimension(width / 4, 0));
                revalidate();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CoffeeShopView();
        });
    }
}