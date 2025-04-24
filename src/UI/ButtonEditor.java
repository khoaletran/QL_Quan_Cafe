package UI;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;

public class ButtonEditor extends DefaultCellEditor {
    private CustomButton button;
    private String label;
    private boolean isPushed;
    private JTable table;
    private int row;
    private OrderPanel orderPanel; // Tham chiếu đến OrderPanel để gọi updateTotal()

    public ButtonEditor(JCheckBox checkBox, OrderPanel orderPanel) {
        super(checkBox);
        this.orderPanel = orderPanel;
        button = new CustomButton("Xóa", new Color(200, 50, 50), Color.WHITE, 5); // Tùy chỉnh giao diện nút
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        this.row = row;
        label = (value == null) ? "Xóa" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // Xóa hàng tại chỉ số row
            ((DefaultTableModel) table.getModel()).removeRow(row);
            // Cập nhật lại tổng tiền
            orderPanel.updateTotal(); // Gọi updateTotal() thông qua tham chiếu OrderPanel
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}