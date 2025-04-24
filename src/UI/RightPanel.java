package UI;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    public RightPanel(OrderPanel orderPanel) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(250, 250, 250));
        setPreferredSize(new Dimension(300, 0));
        createUI(orderPanel);
    }

    private void createUI(OrderPanel orderPanel) {
        add(orderPanel, BorderLayout.CENTER);
    }
}