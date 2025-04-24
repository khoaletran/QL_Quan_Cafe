package UI;

import javax.swing.table.TableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;

public class ButtonRenderer extends CustomButton implements TableCellRenderer {
    public ButtonRenderer() {
        super("Xóa", new Color(200, 50, 50), Color.WHITE, 5); // Màu đỏ nhạt, chữ trắng, padding 5
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}