package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import Dao.NhanVien_DAO;
import Model.NhanVien;

public class NhanVienPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<NhanVien> dsnv = NhanVien_DAO.getAllNhanVien();
	private JTextField txtMaNV, txtTenNV, txtNgayVaoLam, txtSdt, txtDiaChi, txtMatKhau;
	private JRadioButton radNam, radNu;
	private ButtonGroup gioiTinhGroup;
	private DefaultTableModel tableModel;
	private JTable table;
	private JCheckBox chkQuanLy;
	private JCheckBox chkLocNam;
	private JCheckBox chkLocNu;

	public NhanVienPanel() {
		setLayout(new BorderLayout());
		setBackground(new Color(245, 245, 245));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		createUI();
	}

	private void createUI() {
		JLabel titleLabel = new JLabel("Quản Lý Nhân Viên", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
		add(titleLabel, BorderLayout.NORTH);

		JPanel inputPanel = new JPanel(new GridLayout(8, 2, 8, 35));
		inputPanel.setBackground(new Color(245, 245, 245));
		inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		Font labelFont = new Font("Arial", Font.PLAIN, 14);
		Font textFieldFont = new Font("Arial", Font.PLAIN, 14);

		// Mã Nhân Viên
		JLabel lblMaNV = new JLabel("Mã Nhân Viên:");
		lblMaNV.setFont(labelFont);
		inputPanel.add(lblMaNV);
		txtMaNV = new JTextField();
		txtMaNV.setFont(textFieldFont);
		txtMaNV.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		txtMaNV.setPreferredSize(new Dimension(120, 10));
		txtMaNV.setEnabled(false);
		inputPanel.add(txtMaNV);

		// Tên Nhân Viên
		JLabel lblTenNV = new JLabel("Tên Nhân Viên:");
		lblTenNV.setFont(labelFont);
		inputPanel.add(lblTenNV);
		txtTenNV = new JTextField();
		txtTenNV.setFont(textFieldFont);
		txtTenNV.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		txtTenNV.setPreferredSize(new Dimension(120, 10));
		inputPanel.add(txtTenNV);

		// Ngày Vào Làm
		JLabel lblNgayVaoLam = new JLabel("Ngày Vào Làm:");
		lblNgayVaoLam.setFont(labelFont);
		inputPanel.add(lblNgayVaoLam);
		txtNgayVaoLam = new JTextField();
		txtNgayVaoLam.setFont(textFieldFont);
		txtNgayVaoLam.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		txtNgayVaoLam.setPreferredSize(new Dimension(120, 10));
		inputPanel.add(txtNgayVaoLam);

		// Số Điện Thoại
		JLabel lblSdt = new JLabel("Số Điện Thoại:");
		lblSdt.setFont(labelFont);
		inputPanel.add(lblSdt);
		txtSdt = new JTextField();
		txtSdt.setFont(textFieldFont);
		txtSdt.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		txtSdt.setPreferredSize(new Dimension(120, 10));
		inputPanel.add(txtSdt);

		// Địa Chỉ
		JLabel lblDiaChi = new JLabel("Địa Chỉ:");
		lblDiaChi.setFont(labelFont);
		inputPanel.add(lblDiaChi);
		txtDiaChi = new JTextField();
		txtDiaChi.setFont(textFieldFont);
		txtDiaChi.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		txtDiaChi.setPreferredSize(new Dimension(120, 10));
		inputPanel.add(txtDiaChi);

		// Mật Khẩu
		JLabel lblMatKhau = new JLabel("Mật Khẩu:");
		lblMatKhau.setFont(labelFont);
		inputPanel.add(lblMatKhau);
		txtMatKhau = new JTextField();
		txtMatKhau.setFont(textFieldFont);
		txtMatKhau.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		txtMatKhau.setPreferredSize(new Dimension(120, 10));
		inputPanel.add(txtMatKhau);

		// Giới Tính
		JLabel lbGioiTinh = new JLabel("Giới Tính:");
		lbGioiTinh.setFont(labelFont);
		radNam = new JRadioButton("Nam");
		radNu = new JRadioButton("Nữ");
		radNam.setFont(labelFont);
		radNu.setFont(labelFont);
		gioiTinhGroup = new ButtonGroup();
		gioiTinhGroup.add(radNam);
		gioiTinhGroup.add(radNu);
		inputPanel.add(lbGioiTinh);
		JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		genderPanel.setBackground(new Color(245, 245, 245));
		genderPanel.add(radNam);
		genderPanel.add(radNu);
		inputPanel.add(genderPanel);

		// Quản Lý
		JLabel lblQuanLy = new JLabel("Quản Lý:");
		lblQuanLy.setFont(labelFont);
		chkQuanLy = new JCheckBox();
		chkQuanLy.setBackground(new Color(245, 245, 245));
		inputPanel.add(lblQuanLy);
		inputPanel.add(chkQuanLy);

		// Bọc inputPanel trong panel có tiêu đề
		JPanel inputWrapperBorder = new JPanel(new BorderLayout());
		inputWrapperBorder.setBackground(new Color(245, 245, 245));
		inputWrapperBorder.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200)), "Thông Tin Nhân Viên", TitledBorder.LEFT,
				TitledBorder.TOP, new Font("Arial", Font.BOLD, 12), Color.DARK_GRAY));
		inputWrapperBorder.add(inputPanel, BorderLayout.CENTER);

		JPanel inputWrapper = new JPanel(new BorderLayout());
		inputWrapper.setBackground(new Color(245, 245, 245));
		inputWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		inputWrapper.add(inputWrapperBorder, BorderLayout.CENTER);

		String[] columnNames = { "Mã Nhân Viên", "Tên Nhân Viên", "Ngày Vào Làm", "Giới Tính", "Số Điện Thoại",
				"Địa Chỉ", "Mật Khẩu", "Quản Lý" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(tableModel);
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		table.setRowHeight(30);

		table.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow >= 0) {
		            txtMaNV.setText((String) table.getValueAt(selectedRow, 0));
		            txtTenNV.setText((String) table.getValueAt(selectedRow, 1));
		            txtNgayVaoLam.setText((String) table.getValueAt(selectedRow, 2));

		            String gt = (String) table.getValueAt(selectedRow, 3);
		            if (gt.equalsIgnoreCase("Nam"))
		                radNam.setSelected(true);
		            else
		                radNu.setSelected(true);

		            txtSdt.setText((String) table.getValueAt(selectedRow, 4));
		            txtDiaChi.setText((String) table.getValueAt(selectedRow, 5));
		            txtMatKhau.setText((String) table.getValueAt(selectedRow, 6));

		            String ql = (String) table.getValueAt(selectedRow, 7);
		            chkQuanLy.setSelected(ql.equalsIgnoreCase("Có"));
		        }
		    }
		});


		TableColumn maNVColumn = table.getColumnModel().getColumn(0);
		maNVColumn.setPreferredWidth(40);
		TableColumn gioiTinhColumn = table.getColumnModel().getColumn(3);
		gioiTinhColumn.setPreferredWidth(30);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		// Bọc scrollPane trong panel có tiêu đề
		JPanel tableWrapper = new JPanel(new BorderLayout());
		tableWrapper.setBackground(new Color(245, 245, 245));
		tableWrapper.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200)), "Danh Sách Nhân Viên", TitledBorder.LEFT,
				TitledBorder.TOP, new Font("Arial", Font.BOLD, 12), Color.DARK_GRAY));
		tableWrapper.add(scrollPane, BorderLayout.CENTER);

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(new Color(245, 245, 245));
		centerPanel.add(inputWrapper, BorderLayout.EAST);
		centerPanel.add(tableWrapper, BorderLayout.CENTER);
		add(centerPanel, BorderLayout.CENTER);

		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		leftPanel.setBackground(new Color(245, 245, 245));
		leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
				"Chức năng chính", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12),
				Color.DARK_GRAY));
		Font buttonFont = new Font("Arial", Font.BOLD, 14);

		JButton btnThem = new JButton("Thêm");
		btnThem.setFont(buttonFont);
		btnThem.setBackground(new Color(220, 220, 220));
		btnThem.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
		btnThem.setPreferredSize(new Dimension(100, 40));
		btnThem.addActionListener(e -> themNhanVien());
		leftPanel.add(btnThem);

		JButton btnXoa = new JButton("Xóa");
		btnXoa.setFont(buttonFont);
		btnXoa.setBackground(new Color(220, 220, 220));
		btnXoa.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
		btnXoa.setPreferredSize(new Dimension(100, 40));
		btnXoa.addActionListener(e -> xoaNhanVien());
		leftPanel.add(btnXoa);

		JButton btnSua = new JButton("Sửa");
		btnSua.setFont(buttonFont);
		btnSua.setBackground(new Color(220, 220, 220));
		btnSua.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
		btnSua.setPreferredSize(new Dimension(100, 40));
		btnSua.addActionListener(e -> suaNhanVien());
		leftPanel.add(btnSua);

		JButton btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setFont(buttonFont);
		btnXoaTrang.setBackground(new Color(220, 220, 220));
		btnXoaTrang.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
		btnXoaTrang.setPreferredSize(new Dimension(100, 40));
		btnXoaTrang.addActionListener(e -> xoaTrang());
		leftPanel.add(btnXoaTrang);

		JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
		rightPanel.setBackground(new Color(245, 245, 245));
		rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
				"Lọc theo giới tính", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12),
				Color.DARK_GRAY));

		chkLocNam = new JCheckBox("Nam");
		chkLocNam.setFont(new Font("Arial", Font.PLAIN, 13));
		chkLocNam.setBackground(new Color(245, 245, 245));
		chkLocNam.addItemListener(e -> {
			if (chkLocNam.isSelected())
				chkLocNu.setSelected(false);
			locTheoGioiTinh();
		});
		rightPanel.add(chkLocNam);

		chkLocNu = new JCheckBox("Nữ");
		chkLocNu.setFont(new Font("Arial", Font.PLAIN, 13));
		chkLocNu.setBackground(new Color(245, 245, 245));
		chkLocNu.addItemListener(e -> {
			if (chkLocNu.isSelected())
				chkLocNam.setSelected(false);
			locTheoGioiTinh();
		});
		rightPanel.add(chkLocNu);

		JSplitPane buttonSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		buttonSplitPane.setDividerLocation(850);
		buttonSplitPane.setBorder(null);

		JPanel bottomWrapper = new JPanel(new BorderLayout());
		bottomWrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		bottomWrapper.add(buttonSplitPane, BorderLayout.CENTER);
		add(bottomWrapper, BorderLayout.SOUTH);

		reloadTable();
	}

	private void reloadTable() {
		tableModel.setRowCount(0);
		dsnv = NhanVien_DAO.getAllNhanVien();
		for (NhanVien nv : dsnv) {
			tableModel.addRow(new Object[] { nv.getMaNV(), nv.getTenNV(),
					nv.getNgayVaoLam().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
					nv.isGioiTinh() ? "Nữ" : "Nam", nv.getSdt(), nv.getDiaChi(), nv.getMatKhau(),
					nv.isQuanly() ? "Có" : "Không" });
		}
	}

	private void locTheoGioiTinh() {
		tableModel.setRowCount(0);
		dsnv = NhanVien_DAO.getAllNhanVien();

		for (NhanVien nv : dsnv) {
			boolean shouldAdd = true;

			if (chkLocNam.isSelected()) {
				shouldAdd = !nv.isGioiTinh(); // Nam = false
			} else if (chkLocNu.isSelected()) {
				shouldAdd = nv.isGioiTinh(); // Nữ = true
			}

			if (shouldAdd) {
				tableModel.addRow(new Object[] { nv.getMaNV(), nv.getTenNV(),
						nv.getNgayVaoLam().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
						nv.isGioiTinh() ? "Nữ" : "Nam", nv.getSdt(), nv.getDiaChi(), nv.getMatKhau(),
						nv.isQuanly() ? "Có" : "Không" });
			}
		}
	}

	private void xoaTrang() {
		txtMaNV.setText("");
		txtTenNV.setText("");
		txtNgayVaoLam.setText("");
		txtSdt.setText("");
		txtDiaChi.setText("");
		txtMatKhau.setText("");
		gioiTinhGroup.clearSelection();
		chkQuanLy.setSelected(false);
		table.clearSelection();
	}

	private void themNhanVien() {
		if (!validata()) {
			return;
		}

		String ten = txtTenNV.getText();
		String ngay = txtNgayVaoLam.getText();
		String sdt = txtSdt.getText();
		String diaChi = txtDiaChi.getText();
		String matKhau = txtMatKhau.getText();
		boolean gt = radNu.isSelected();
		LocalDate nvl = LocalDate.parse(ngay, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		boolean ql = chkQuanLy.isSelected();

		NhanVien nv = new NhanVien(ten, diaChi, nvl, gt, sdt, matKhau, ql);
		boolean ok = new NhanVien_DAO().themNhanVien(nv);

		if (ok) {
			JOptionPane.showMessageDialog(this, "Đã thêm nhân viên!");
			reloadTable();
			xoaTrang();
		} else {
			JOptionPane.showMessageDialog(this, "Thêm thất bại.");
		}
	}

	private void xoaNhanVien() {
		int row = table.getSelectedRow();
		if (row >= 0) {
			String ma = (String) table.getValueAt(row, 0);
			int confirm = JOptionPane.showConfirmDialog(this, "Xóa nhân viên " + ma + "?", "Xác nhận",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				boolean ok = new NhanVien_DAO().xoaNhanVien(ma);
				if (ok) {
					JOptionPane.showMessageDialog(this, "Đã xóa!");
					xoaTrang();
					reloadTable();
				} else {
					JOptionPane.showMessageDialog(this, "Xóa thất bại.");
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Chọn nhân viên cần xóa.");
			return;
		}
	}

	private void suaNhanVien() {

		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Chọn nhân viên cần sửa.");
			return;
		}

		// Gọi validata trước
		if (!validata()) {
			return; // Dừng nếu dữ liệu không hợp lệ
		}

		// Lấy dữ liệu
		String ma = txtMaNV.getText();
		String ten = txtTenNV.getText();
		String ngay = txtNgayVaoLam.getText();
		String sdt = txtSdt.getText();
		String diaChi = txtDiaChi.getText();
		String matKhau = txtMatKhau.getText();
		boolean gt = radNu.isSelected();
		LocalDate nvl = LocalDate.parse(ngay, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		boolean ql = chkQuanLy.isSelected();

		// Tạo đối tượng và gọi DAO
		NhanVien nv = new NhanVien(ma, ten, diaChi, nvl, gt, sdt, matKhau, ql);
		boolean ok = new NhanVien_DAO().suaNhanVien(nv);
		if (ok) {
			JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
			xoaTrang();
			reloadTable();
		} else {
			JOptionPane.showMessageDialog(this, "Sửa thất bại.");
		}
	}

	private boolean validata() {
		String ten = txtTenNV.getText();
		if (ten == null || ten.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Lỗi: Tên nhân viên không được để trống");
			txtTenNV.requestFocus();
			return false;
		}
		String ngay = txtNgayVaoLam.getText();
		LocalDate nvl = null;
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			nvl = LocalDate.parse(ngay, dtf);
			if (nvl.isAfter(LocalDate.now())) {
				JOptionPane.showMessageDialog(this, "Lỗi: Ngày vào làm không được lớn hơn ngày hiện tại.");
				txtNgayVaoLam.requestFocus();
				return false;
			}
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(this, "Lỗi: Ngày vào làm không hợp lệ (định dạng dd/MM/yyyy)");
			txtNgayVaoLam.requestFocus();
			return false;
		}

		String sdt = txtSdt.getText();
		if (!sdt.isEmpty()) {
			if (!sdt.matches("^0[2-9]\\d{8}$")) {
				JOptionPane.showMessageDialog(this, "Lỗi: Số điện thoại phải bắt đầu từ 02-09 và đủ 10 chữ số.");
				txtSdt.requestFocus();
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Lỗi: Số điện thoại nhân viên không được để trống");
			txtSdt.requestFocus();
			return false;
		}
		String diaChi = txtDiaChi.getText();
		if (diaChi == null || diaChi.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Lỗi: Địa chỉ nhân viên không được để trống");
			txtDiaChi.requestFocus();
			return false;
		}
		String matKhau = txtMatKhau.getText();
		if (matKhau == null || matKhau.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Lỗi: Mật khẩu không được để trống");
			txtMatKhau.requestFocus();
			return false;
		}
		if (!radNam.isSelected() && !radNu.isSelected()) {
			JOptionPane.showMessageDialog(this, "Lỗi: Phải chọn giới tính");
			return false;
		}

		return true;
	}
}