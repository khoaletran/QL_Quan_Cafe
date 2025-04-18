package UI;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    public CustomButton(String text, Color backgroundColor, Color foregroundColor, int padding) {
        super(text);
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setOpaque(true);
        setBorderPainted(false);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
//        setFont(new Font("Arial", Font.PLAIN, 14));
    }

    public CustomButton(String text) {
        this(text, new Color(230, 230, 230), Color.BLACK, 10);
        setHorizontalAlignment(SwingConstants.LEFT);
    }
}