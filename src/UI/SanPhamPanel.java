package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import ConnectDB.ConnectDB;
import Dao.HangHoa_DAO;
import Dao.KhachHang_DAO;
import Dao.LoaiHangHoa_DAO;
import Model.HangHoa;
import Model.LoaiHangHoa;

public class SanPhamPanel extends JPanel {
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField txtMaHH, txtTenHH, txtGiaSP, txtTimKiem;
	private JLabel lblHinhAnh;
	private JComboBox<String> cbLoaiHH;
	private JButton btnThem, btnSua, btnXoa, btnTimKiem;
	private HangHoa_DAO hangHoaDAO;
	private LoaiHangHoa_DAO loaiHangHoaDAO;
	private ArrayList<LoaiHangHoa> dsLoaiHH;
	private JButton btnXoaTrang;
	private List<HangHoa> dsHH;
	private JTextField txtHinhAnhPath;

	public SanPhamPanel() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		hangHoaDAO = new HangHoa_DAO();
		loaiHangHoaDAO = new LoaiHangHoa_DAO();

		initUI();
		loadData();
	}

	private void initUI() {
		setLayout(new BorderLayout());

		// Phần CENTER: JPanel chứa tiêu đề và JTable
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setBackground(new Color(240, 242, 245)); // Xám nhạt
		// Tiêu đề "Quản lý Sản phẩm"
		JLabel lblTitle = new JLabel("Quản lý Sản phẩm", JLabel.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
		tablePanel.add(lblTitle, BorderLayout.NORTH);

		// Bảng hiển thị hàng hóa
		String[] columns = { "Mã HH", "Tên HH", "Hình ảnh", "Giá SP", "Loại HH" };
		tableModel = new DefaultTableModel(columns, 0);
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(80);

		// Tăng kích thước chữ trong bảng
		Font tableFont = new Font("Arial", Font.PLAIN, 14); // Kích thước chữ lớn hơn
		table.setFont(tableFont);

		// Làm đậm chữ trong header
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Arial", Font.BOLD, 14)); // Header in đậm

		// Renderer tùy chỉnh cho các cột văn bản để áp dụng font
		DefaultTableCellRenderer textRenderer = new DefaultTableCellRenderer();
		textRenderer.setFont(tableFont);
		textRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa nội dung
		table.getColumnModel().getColumn(0).setCellRenderer(textRenderer); // Mã HH
		table.getColumnModel().getColumn(1).setCellRenderer(textRenderer); // Tên HH
		table.getColumnModel().getColumn(3).setCellRenderer(textRenderer); // Giá SP
		table.getColumnModel().getColumn(4).setCellRenderer(textRenderer); // Loại HH

		// Renderer cho cột hình ảnh
		table.getColumnModel().getColumn(2).setCellRenderer(new ImageRenderer());

		// Set chiều rộng cho từng cột
		TableColumn column;
		column = table.getColumnModel().getColumn(0); // Mã HH
		column.setPreferredWidth(50);
		column = table.getColumnModel().getColumn(1); // Tên HH
		column.setPreferredWidth(200);
		column = table.getColumnModel().getColumn(2); // Hình ảnh
		column.setPreferredWidth(80);
		column = table.getColumnModel().getColumn(3); // Giá SP
		column.setPreferredWidth(100);
		column = table.getColumnModel().getColumn(4); // Loại HH
		column.setPreferredWidth(100);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row >= 0) {
					txtMaHH.setText(tableModel.getValueAt(row, 0).toString());
					txtMaHH.setEditable(false);
					txtTenHH.setText(tableModel.getValueAt(row, 1).toString());
					Object hinhAnh = tableModel.getValueAt(row, 2);
					if (hinhAnh instanceof ImageIcon) {
						ImageIcon icon = (ImageIcon) hinhAnh;
						Image scaledImage = icon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
						lblHinhAnh.setIcon(new ImageIcon(scaledImage));
						lblHinhAnh.setText("");
					} else {
						lblHinhAnh.setIcon(null);
						lblHinhAnh.setText("No Image");
					}

					txtHinhAnhPath.setText(hangHoaDAO.getHinhAnhByMa(tableModel.getValueAt(row, 0).toString()));
					txtGiaSP.setText(tableModel.getValueAt(row, 3).toString());
					String maLH = tableModel.getValueAt(row, 4).toString();
					cbLoaiHH.setSelectedItem(maLH);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		add(tablePanel, BorderLayout.CENTER);

		// Phần EAST: JPanel chứa tiêu đề và form nhập liệu
		JPanel inputPanel = new JPanel(new BorderLayout());
		inputPanel.setPreferredSize(new java.awt.Dimension(300, 600));
		inputPanel.setBackground(new Color(230, 230, 230)); 
		inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5)); // Padding left và right 5px
		// Tiêu đề "Thông tin chi tiết"
		JLabel lblDetailTitle = new JLabel("Thông tin chi tiết", JLabel.CENTER);
		lblDetailTitle.setFont(new Font("Arial", Font.BOLD, 18));
		lblDetailTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		inputPanel.add(lblDetailTitle, BorderLayout.NORTH);

		// Form nhập liệu
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		formPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;

		// Định nghĩa font cho các thành phần trong formPanel
		Font formFont = new Font("Arial", Font.PLAIN, 12); // Font kích thước 

		// Mã HH
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel lblMaHH = new JLabel("Mã HH:");
		lblMaHH.setFont(formFont); // Áp dụng font
		formPanel.add(lblMaHH, gbc);
		txtMaHH = new JTextField(10); // Giảm từ 15 xuống 10 cột
		txtMaHH.setFont(formFont); // Áp dụng font
		gbc.gridx = 1;
		formPanel.add(txtMaHH, gbc);

		// Tên HH
		gbc.gridx = 0;
		gbc.gridy = 1;
		JLabel lblTenHH = new JLabel("Tên HH:");
		lblTenHH.setFont(formFont);
		formPanel.add(lblTenHH, gbc);
		txtTenHH = new JTextField(10); // Giảm từ 15 xuống 10 cột
		txtTenHH.setFont(formFont);
		gbc.gridx = 1;
		formPanel.add(txtTenHH, gbc);

		// Hình ảnh
		gbc.gridx = 0;
		gbc.gridy = 2;
		JLabel lblHinhAnhLabel = new JLabel("Hình ảnh:");
		lblHinhAnhLabel.setFont(formFont);
		formPanel.add(lblHinhAnhLabel, gbc);
		lblHinhAnh = new JLabel("No Image", JLabel.CENTER);
		lblHinhAnh.setFont(formFont);
		lblHinhAnh.setBorder(BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY));
		lblHinhAnh.setPreferredSize(new java.awt.Dimension(100, 100));
		gbc.gridx = 1;
		formPanel.add(lblHinhAnh, gbc);

		// Đường dẫn hình ảnh
		gbc.gridx = 0;
		gbc.gridy = 3;
		JLabel lblDuongDan = new JLabel("Đường dẫn:");
		lblDuongDan.setFont(formFont);
		formPanel.add(lblDuongDan, gbc);
		txtHinhAnhPath = new JTextField(10); // Giảm từ 15 xuống 10 cột
		txtHinhAnhPath.setFont(formFont);
		gbc.gridx = 1;
		formPanel.add(txtHinhAnhPath, gbc);

		// Giá SP
		gbc.gridx = 0;
		gbc.gridy = 4;
		JLabel lblGiaSP = new JLabel("Giá SP:");
		lblGiaSP.setFont(formFont);
		formPanel.add(lblGiaSP, gbc);
		txtGiaSP = new JTextField(10); // Giảm từ 15 xuống 10 cột
		txtGiaSP.setFont(formFont);
		gbc.gridx = 1;
		formPanel.add(txtGiaSP, gbc);

		// Loại HH
		gbc.gridx = 0;
		gbc.gridy = 5;
		JLabel lblLoaiHH = new JLabel("Loại HH:");
		lblLoaiHH.setFont(formFont);
		formPanel.add(lblLoaiHH, gbc);
		cbLoaiHH = new JComboBox<>();
		for (LoaiHangHoa lhh : loaiHangHoaDAO.getAllLoaiHangHoa()) {
		    cbLoaiHH.addItem(lhh.getTenLoaiHang());
		}
		cbLoaiHH.setFont(formFont);
		gbc.gridx = 1;
		formPanel.add(cbLoaiHH, gbc);

		// Panel tìm kiếm
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		JLabel lblTimKiem = new JLabel("Tìm kiếm:");
		lblTimKiem.setFont(formFont);
		searchPanel.add(lblTimKiem);
		txtTimKiem = new JTextField(15); // Giảm từ 10 xuống 8 cột
		txtTimKiem.setFont(formFont);
		searchPanel.add(txtTimKiem);

		// Panel nút
		JPanel buttonPanel = new JPanel(new FlowLayout());
		btnThem = new JButton("Thêm");
		btnSua = new JButton("Sửa");
		btnXoa = new JButton("Xóa");
		btnXoaTrang = new JButton("Xóa Trắng");
		
		buttonPanel.add(btnThem);
		buttonPanel.add(btnSua);
		buttonPanel.add(btnXoa);
		buttonPanel.add(btnXoaTrang);

		// Thêm các panel vào formPanel
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		formPanel.add(searchPanel, gbc);
		gbc.gridy = 7;
		formPanel.add(buttonPanel, gbc);

		inputPanel.add(formPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.EAST);

		// Xử lý sự kiện
		btnThem.addActionListener(e -> themHangHoa());
		btnXoa.addActionListener(e -> xoaHangHoa());
		btnSua.addActionListener(e -> suaHangHoa());
		btnXoaTrang.addActionListener(e -> clearForm());
		txtTimKiem.getDocument().addDocumentListener(timKiemDong());
	}

	private void loadData() {
		tableModel.setRowCount(0);
		dsHH = hangHoaDAO.getAllHangHoaForSanPhamPanel();
		for (HangHoa hh : dsHH) {
			ImageIcon imageIcon = createImageIcon(hh.getHinhAnh(), 150, 100);
			tableModel
					.addRow(new Object[] { hh.getMaHH(), hh.getTenHH(), imageIcon != null ? imageIcon : hh.getHinhAnh(),
							hh.getGiaSP(), hh.getLoaiHangHoa().getTenLoaiHang() });
		}
	}
	
	private void reloadData() {
		tableModel.setRowCount(0);
		for (HangHoa hh : dsHH) {
			ImageIcon imageIcon = createImageIcon(hh.getHinhAnh(), 150, 100);
			tableModel
					.addRow(new Object[] { hh.getMaHH(), hh.getTenHH(), imageIcon != null ? imageIcon : hh.getHinhAnh(),
							hh.getGiaSP(), hh.getLoaiHangHoa().getTenLoaiHang() });
		}
	}

	public void themHangHoa() {
		String tenHH = txtTenHH.getText();
		if (tenHH.isEmpty()) {
		    JOptionPane.showMessageDialog(this, "Tên hàng hóa không được để trống");
		    txtTenHH.requestFocus();
		    return;
		}
		
		String hinhAnhPath = txtHinhAnhPath.getText();
		if (hinhAnhPath.isEmpty()) {
		    JOptionPane.showMessageDialog(this, "Đường dẫn hình ảnh không được để trống");
		    txtHinhAnhPath.requestFocus();
		    return;
		}
		double giaSP = 0;
		try {
			if(txtGiaSP.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Giá sản phẩm không được để trống");
			    
			    return;
			}
			giaSP = Double.parseDouble(txtGiaSP.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Nhập sai định dạng giá sản phẩm");
			txtGiaSP.requestFocus();
			return;
		}

		String tenLoaiHH = cbLoaiHH.getSelectedItem().toString();
		String maLoaiHH = loaiHangHoaDAO.getMaLoaiHangByTen(tenLoaiHH);

		if (maLoaiHH == null) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy mã loại hàng hóa!");
			return;
		}
		LoaiHangHoa lhh = new LoaiHangHoa(maLoaiHH);
		HangHoa hh = new HangHoa(tenHH, hinhAnhPath, giaSP, lhh);

		if (hangHoaDAO.themHangHoa(hh)) {
			JOptionPane.showMessageDialog(this, "Thêm hàng hóa thành công!");
			loadData();
		} else {
			JOptionPane.showMessageDialog(this, "Thêm hàng hóa thất bại!");
		}
	}

	private void xoaHangHoa() {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng hóa để xóa!");
			return;
		}
		if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa hàng hóa này?") == JOptionPane.YES_OPTION) {
			String maHH = tableModel.getValueAt(row, 0).toString();
			try {
				if (hangHoaDAO.xoaHangHoa(maHH)) {
					JOptionPane.showMessageDialog(this, "Xóa hàng hóa thành công!");
					loadData();
					clearForm();
				} else {
					JOptionPane.showMessageDialog(this, "Xóa hàng hóa thất bại!");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Lỗi khi xóa hàng hóa: " + ex.getMessage(), "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void suaHangHoa() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng hóa để sửa!");
			return;
		}

		String maHH = tableModel.getValueAt(selectedRow, 0).toString();
		String tenHH = txtTenHH.getText();
		String hinhAnhPath = txtHinhAnhPath.getText();
		double giaSP = 0;
		try {
			giaSP = Double.parseDouble(txtGiaSP.getText());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Nhập sai định dạng giá sản phẩm");
			return;
		}

		String tenLoaiHH = cbLoaiHH.getSelectedItem().toString();
		String maLoaiHH = loaiHangHoaDAO.getMaLoaiHangByTen(tenLoaiHH);

		if (maLoaiHH == null) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy mã loại hàng hóa!");
			return;
		}

		LoaiHangHoa lhh = new LoaiHangHoa(maLoaiHH);
		HangHoa hh = new HangHoa(maHH, tenHH, hinhAnhPath, giaSP, lhh);

		if (hangHoaDAO.capNhatHangHoa(hh)) {
			JOptionPane.showMessageDialog(this, "Cập nhật hàng hóa thành công!");
			loadData();
		} else {
			JOptionPane.showMessageDialog(this, "Cập nhật hàng hóa thất bại!");
		}
	}

//	private void timKiemHangHoa() {
//	    String keyword = txtTimKiem.getText().trim();
//	    tableModel.setRowCount(0);
//	    ArrayList<HangHoa> dsHH = hangHoaDAO.getAllHangHoaForSanPhamPanel();
//	    for (HangHoa hh : dsHH) {
//	        if (hh.getMaHH().toLowerCase().contains(keyword.toLowerCase())
//	                || hh.getTenHH().toLowerCase().contains(keyword.toLowerCase())
//	                || hh.getLoaiHangHoa().getTenLoaiHang().toLowerCase().contains(keyword.toLowerCase())) {
//	            ImageIcon imageIcon = createImageIcon(hh.getHinhAnh(), 80, 80);
//	            tableModel.addRow(new Object[] { 
//	                hh.getMaHH(), 
//	                hh.getTenHH(), 
//	                imageIcon != null ? imageIcon : hh.getHinhAnh(),
//	                hh.getGiaSP(), 
//	                hh.getLoaiHangHoa().getTenLoaiHang()
//	            });
//	        }
//	    }
//	}
	
    private DocumentListener timKiemDong() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
				thucHienTimKiem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
				thucHienTimKiem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            	thucHienTimKiem();
            }

            private void thucHienTimKiem() {
            	String tuKhoa = txtTimKiem.getText().trim();
				dsHH = hangHoaDAO.timKiemHangHoa(tuKhoa);
				reloadData();
            }
        };
    }
    


	private void clearForm() {
		loadData();
		table.clearSelection();
		txtMaHH.setText("");
		txtTenHH.setText("");
		lblHinhAnh.setIcon(null);
		lblHinhAnh.setText("No Image");
		txtHinhAnhPath.setText("");
		txtGiaSP.setText("");
		if (cbLoaiHH.getItemCount() > 0) {
			cbLoaiHH.setSelectedIndex(0);
		}
	}

	private ImageIcon createImageIcon(String path, int width, int height) {
		try {
			if (path != null && !path.isEmpty()) {
				ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));
				if (icon.getImage() != null) {
					Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
					return new ImageIcon(scaledImage);
				} else {
					System.out.println("Hình ảnh không tồn tại: " + path);
				}
			}
		} catch (Exception e) {
			System.out.println("Không load được ảnh: " + path);
			e.printStackTrace();
		}
		return null;
	}

	class ImageRenderer extends JLabel implements TableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setIcon(null);
			setText("");

			if (value instanceof ImageIcon) {
				setIcon((ImageIcon) value);
			} else {
				setText(value != null ? "No Image" : "");
			}

			setHorizontalAlignment(JLabel.CENTER);
			setVerticalAlignment(JLabel.CENTER);

			if (isSelected) {
				setBackground(table.getSelectionBackground());
				setOpaque(true);
			} else {
				setBackground(table.getBackground());
				setOpaque(false);
			}

			return this;
		}
	}

}