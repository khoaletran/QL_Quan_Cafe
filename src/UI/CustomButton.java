package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {
    private static final Color DEFAULT_BG = new Color(230, 230, 230);
    private static final Color DEFAULT_FG = Color.BLACK;
    private static final int DEFAULT_PADDING = 10;
    private final Color originalBackground; // Lưu màu nền gốc
    private final Color hoverBackground;   // Màu nền khi hover

    public CustomButton(String text, Color backgroundColor, Color foregroundColor, int padding) {
        super(text);
        // Đảm bảo màu nền và màu chữ không null
        this.originalBackground = backgroundColor != null ? backgroundColor : DEFAULT_BG;
        this.hoverBackground = backgroundColor != null ? backgroundColor.brighter() : DEFAULT_BG.brighter(); // Màu sáng hơn khi hover
        setBackground(this.originalBackground);
        setForeground(foregroundColor != null ? foregroundColor : DEFAULT_FG);
        setOpaque(true);
        setBorderPainted(false);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        // setFont(new Font("Arial", Font.PLAIN, 14)); // Uncomment nếu muốn dùng font

        // Thêm hiệu ứng hover
        addHoverEffect();
    }

    public CustomButton(String text) {
        this(text, DEFAULT_BG, DEFAULT_FG, DEFAULT_PADDING);
        setHorizontalAlignment(SwingConstants.LEFT);
    }

    private void addHoverEffect() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBackground); // Đổi sang màu hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(originalBackground); // Khôi phục màu gốc
            }
        });
    }
}