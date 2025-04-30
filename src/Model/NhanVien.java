package Model;

import java.time.LocalDate;

/**
 * Lớp NhanVien đại diện cho một nhân viên trong hệ thống.
 */
public class NhanVien {
    private String maNV;        // NVXXXX (do SQL tự tăng)
    private String tenNV;       // != null
    private String diaChi;      // != null
    private LocalDate ngayVaoLam; // <= hiện tại
    private boolean gioiTinh;   // false: Nam, true: Nữ
    private String sdt;         // Regex: 0[2-9]\d{8}
    private String matKhau;     // ≥ 8 ký tự, gồm: 1 số, 1 chữ thường, 1 chữ hoa, 1 ký tự đặc biệt
    private boolean quanly;

    /**
     * Constructor KHÔNG có maNV (dùng khi tạo mới, mã tự tăng trong DB)
     */
    public NhanVien(String tenNV, String diaChi, LocalDate ngayVaoLam, boolean gioiTinh, String sdt, String matKhau, boolean quanly) {
        setTenNV(tenNV);
        setDiaChi(diaChi);
        setNgayVaoLam(ngayVaoLam);
        setGioiTinh(gioiTinh);
        setSdt(sdt);
        setMatKhau(matKhau);
        setQuanly(quanly);
    }

    /**
     * Constructor CÓ maNV (dùng khi lấy từ DB)
     */
    public NhanVien(String maNV, String tenNV, String diaChi, LocalDate ngayVaoLam, boolean gioiTinh, String sdt, String matKhau,boolean quanly) {
        setMaNV(maNV);
        setTenNV(tenNV);
        setDiaChi(diaChi);
        setNgayVaoLam(ngayVaoLam);
        setGioiTinh(gioiTinh);
        setSdt(sdt);
        setMatKhau(matKhau);
        setQuanly(quanly);
    }

    public String getMaNV() {
        return maNV;
    }

    /**
     * Thiết lập mã nhân viên.
     * Mã phải theo định dạng: NVXXXX (X là chữ số).
     *
     * @param maNV Mã nhân viên cần thiết lập
     * @throws IllegalArgumentException nếu mã không đúng định dạng
     */
    public void setMaNV(String maNV) {
        if (!maNV.matches("^NV\\d{4}$")) {
            throw new IllegalArgumentException("Mã NV phải theo định dạng NVXXXX.");
        }
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    /**
     * Thiết lập tên nhân viên.
     *
     * @param tenNV Tên nhân viên
     * @throws IllegalArgumentException nếu tên rỗng hoặc null
     */
    public void setTenNV(String tenNV) {
        if (tenNV == null || tenNV.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên nhân viên không được để trống.");
        }
        this.tenNV = tenNV;
    }

    public String getDiaChi() {
        return diaChi;
    }

    /**
     * Thiết lập địa chỉ nhân viên.
     *
     * @param diaChi Địa chỉ nhân viên
     * @throws IllegalArgumentException nếu địa chỉ rỗng hoặc null
     */
    public void setDiaChi(String diaChi) {
        if (diaChi == null || diaChi.trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được để trống.");
        }
        this.diaChi = diaChi;
    }

    public LocalDate getNgayVaoLam() {
        return ngayVaoLam;
    }

    /**
     * Thiết lập ngày vào làm.
     *
     * @param ngayVaoLam Ngày vào làm
     * @throws IllegalArgumentException nếu ngày lớn hơn ngày hiện tại
     */
    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        if (ngayVaoLam.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày vào làm không được lớn hơn ngày hiện tại.");
        }
        this.ngayVaoLam = ngayVaoLam;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    /**
     * Thiết lập giới tính.
     *
     * @param gioiTinh false = Nam, true = Nữ
     */
    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSdt() {
        return sdt;
    }

    /**
     * Thiết lập số điện thoại.
     * Số điện thoại phải có định dạng: bắt đầu từ 0[2-9] và có đúng 10 chữ số.
     *
     * @param sdt Số điện thoại cần thiết lập
     * @throws IllegalArgumentException nếu định dạng không hợp lệ
     */
    public void setSdt(String sdt) {
        if (!sdt.matches("^0[2-9]\\d{8}$")) {
            throw new IllegalArgumentException("Số điện thoại phải bắt đầu từ 02-09 và đủ 10 chữ số.");
        }
        this.sdt = sdt;
    }
    
    

    public boolean isQuanly() {
		return quanly;
	}

	public void setQuanly(boolean quanly) {
		this.quanly = quanly;
	}

	public String getMatKhau() {
        return matKhau;
    }

    /**
     * Thiết lập mật khẩu.
     * Mật khẩu phải dài ít nhất 8 ký tự và chứa ít nhất:
     * 1 chữ thường, 1 chữ hoa, 1 số và 1 ký tự đặc biệt.
     *
     * @param matKhau Mật khẩu cần thiết lập
     * @throws IllegalArgumentException nếu không thỏa điều kiện
     */
    public void setMatKhau(String matKhau) {
//        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$";
//        if (!matKhau.matches(pattern)) {
//            throw new IllegalArgumentException("Mật khẩu phải có ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt.");
//        }
        this.matKhau = matKhau;
    }

	
    /**
     * Trả về chuỗi biểu diễn thông tin nhân viên.
     */
    @Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", diaChi=" + diaChi + ", ngayVaoLam=" + ngayVaoLam
				+ ", gioiTinh=" + gioiTinh + ", sdt=" + sdt + ", matKhau=" + matKhau + ", quanly=" + quanly + "]";
	}

}
