package Model;

import java.time.LocalDate;

/**
 * Lớp NhanVien đại diện cho một nhân viên trong hệ thống.
 * Lớp này chứa các thông tin cơ bản của nhân viên như mã, tên, địa chỉ, ngày vào làm, giới tính, số điện thoại và mật khẩu.
 */
public class NhanVien {
    // Thuộc tính
    private String maNV;
    private String tenNV;
    private String diaChi;
    private LocalDate ngayVaoLam;
    private boolean gioiTinh;
    private String sdt;
    private String matKhau;

    /**
     * Khởi tạo một đối tượng NhanVien với các thông tin được cung cấp.
     *
     * @param maNV       Mã nhân viên
     * @param tenNV      Tên nhân viên
     * @param diaChi     Địa chỉ của nhân viên
     * @param ngayVaoLam Ngày vào làm của nhân viên
     * @param gioiTinh   Giới tính của nhân viên (true: nam, false: nữ)
     * @param sdt        Số điện thoại của nhân viên
     * @param matKhau    Mật khẩu của nhân viên
     */
    public NhanVien(String maNV, String tenNV, String diaChi, LocalDate ngayVaoLam, boolean gioiTinh, String sdt, String matKhau) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.diaChi = diaChi;
        this.ngayVaoLam = ngayVaoLam;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.matKhau = matKhau;
    }

    // Getter và Setter cho maNV
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    // Getter và Setter cho tenNV
    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    // Getter và Setter cho diaChi
    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    // Getter và Setter cho ngayVaoLam
    public LocalDate getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    // Getter và Setter cho gioiTinh
    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    // Getter và Setter cho sdt
    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    // Getter và Setter cho matKhau
    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    /**
     * Trả về chuỗi biểu diễn thông tin của đối tượng NhanVien.
     *
     * @return Chuỗi chứa thông tin của nhân viên
     */
    @Override
    public String toString() {
        return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", diaChi=" + diaChi + ", ngayVaoLam=" + ngayVaoLam
                + ", gioiTinh=" + gioiTinh + ", sdt=" + sdt + ", matKhau=" + matKhau + "]";
    }
}