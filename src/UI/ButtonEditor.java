package UI;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
            DefaultTableModel model = orderPanel.getOrderTableModel();
            
            // Lấy số lượng hiện tại
            int quantity = (Integer) model.getValueAt(row, 1);
            
            if (quantity > 1) {
                // Giảm số lượng nếu > 1
                model.setValueAt(quantity - 1, row, 1);
                // Cập nhật tổng
                double price = (Double) model.getValueAt(row, 2);
                model.setValueAt((quantity - 1) * price, row, 3);
            } else {
                // Hỏi xác nhận nếu số lượng = 1
                int confirm = JOptionPane.showConfirmDialog(
                    button, 
                    "Bạn có chắc muốn xóa sản phẩm này khỏi đơn hàng?", 
                    "Xác nhận xóa", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(row);
                }
            }
            
            orderPanel.updateTotal();
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