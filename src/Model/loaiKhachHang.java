package Model;

public class loaiKhachHang {
	private String maLKH ; 
	private String tenLKH;
	private int giamGia;
	
	public loaiKhachHang() {}
	
	/**
     * Constructor để tạo một loại khách hàng.
     * 
     * @param maLKH    Mã loại khách hàng
     * @param tenLKH   Tên loại khách hàng
     * @param giamGia  Phần trăm giảm giá áp dụng cho khách hàng
     */
	public loaiKhachHang(String maLKH,String tenLKH,int giamGia) {
			setMaLKH(maLKH);
			setTenLKH(tenLKH);
			setGiamGia(giamGia);
	}
	
	

	public String getMaLKH() {
		return maLKH;
	}

	public void setMaLKH(String maLKH) {
		this.maLKH = maLKH;
	}

	public String getTenLKH() {
		return tenLKH;
	}

	public void setTenLKH(String tenLKH) {
		this.tenLKH = tenLKH;
	}

	public int getGiamGia() {
		return giamGia;
	}

	public void setGiamGia(int giamGia) {
		this.giamGia = giamGia;
	}

	@Override
	public String toString() {
		return "loaiKhachHang [maLKH=" + maLKH + ", tenLKH=" + tenLKH + ", giamGia=" + giamGia + "]";
	}
	
}
