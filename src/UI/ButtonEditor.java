package UI;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private OrderPanel orderPanel;
    private int row;

    public ButtonEditor(JCheckBox checkBox, OrderPanel orderPanel) {
        super(checkBox);
        this.orderPanel = orderPanel;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> {
            fireEditingStopped();
            // Kiểm tra xem hàng có còn tồn tại không
            if (row >= 0 && row < orderPanel.getOrderTableModel().getRowCount()) {
                orderPanel.getOrderTableModel().removeRow(row);
                orderPanel.updateTotal();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, 
            boolean isSelected, int row, int column) {
        this.row = row;
        button.setText((value == null) ? "" : value.toString());
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}