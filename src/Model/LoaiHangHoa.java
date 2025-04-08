package Model;

public class LoaiHangHoa {
	String maLoaiHang;
	String tenLoaiHang;
	String mota;
	   
	public LoaiHangHoa(String maLoaiHang, String tenLoaiHang, String mota) {
		/**
	     * Constructor để tạo một loại hàng hóa
	     * @param maLoaiHang   Mã Loại Hàng Hóa
	     * @param tenLoaiHang   Tên Loại Hàng Hóa
	     * @param moTa   Mô tả cho sản phẩm(màu sắc,kích cỡ,...)
	     */
		
		setMaLoaiHang(maLoaiHang);
		setTenLoaiHang(tenLoaiHang);
		setMota(mota);
		
	}

	public String getMaLoaiHang() {
		return maLoaiHang;
	}
	public void setMaLoaiHang(String maLoaiHang) {
		if (maLoaiHang == null || !maLoaiHang.matches("^LHH\\d{1,4}$")) {
            throw new IllegalArgumentException("Mã loại hàng không hợp lệ.Phải bắt đầu bằng 'LHH' và theo sau là tối đa 4 chữ số");
        }this.maLoaiHang = maLoaiHang;
	}
	public String getTenLoaiHang() {
		return tenLoaiHang;
	}
	public void setTenLoaiHang(String tenLoaiHang) {
		 if (tenLoaiHang == null ) {
	            throw new IllegalArgumentException("Tên loại hàng không được để trống");
	        }this.tenLoaiHang = tenLoaiHang;
	}
	public String getMota() {
		return mota;
	}
	public void setMota(String mota) {
		 if (mota == null ) {
	            throw new IllegalArgumentException("Mô tả không được để trống");
	        }this.mota = mota;
	}



   
   
}
