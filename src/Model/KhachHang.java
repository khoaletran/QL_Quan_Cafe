package Model;

public class KhachHang {

	private String maKH;
	private String tenKH;
	private String diaChi;
	private String soDienThoai;
	private int diemTL;
	private LoaiKhachHang loaiKhachHang;
	
	public KhachHang() {}
	
	
	/**
     * Constructor để tạo một khách hàng với đầy đủ thông tin.
     * 
     * @param maKH          Mã khách hàng
     * @param tenKH         Tên khách hàng
     * @param diaChi        Địa chỉ khách hàng
     * @param soDienThoai   Số điện thoại khách hàng
     * @param diemTL        Điểm tích lũy của khách hàng
     * @param loaiKhachHang Loại khách hàng (VIP, thường,...)
     */
	public KhachHang(String maKH,String tenKH,String diaChi,String soDienThoai,int diemTL,LoaiKhachHang loaiKhachHang) {
		setMaKH(maKH);
		setTenKH(tenKH);
		setDiaChi(diaChi);
		setSoDienThoai(soDienThoai);
		setDiemTL(diemTL);
		setLoaiKhachHang(loaiKhachHang);
	}
	
	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public int getDiemTL() {
		return diemTL;
	}
	public void setDiemTL(int diemTL) {
		this.diemTL = diemTL;
	}
	public LoaiKhachHang getLoaiKhachHang() {
		return loaiKhachHang;
	}
	public void setLoaiKhachHang(LoaiKhachHang loaiKhachHang) {
		this.loaiKhachHang = loaiKhachHang;
	}
	@Override
	public String toString() {
		return "khachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", diaChi=" + diaChi + ", soDienThoai=" + soDienThoai
				+ ", diemTL=" + diemTL + ", loaiKhachHang=" + loaiKhachHang + "]";
	}
	
	
}
