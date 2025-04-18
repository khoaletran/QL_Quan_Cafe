package Model;

public class HangHoa {
   private String maHH;
   private String tenHH;
   private String hinhAnh;
   private double giaSP;
   
	public HangHoa(String maHH, String tenHH, String hinhAnh, double giaSP) {
		/**
	     * Constructor để tạo một  hàng hóa
	     * @param maHH    Mã Hàng Hóa
	     * @param tenHH   Tên Hàng Hóa
	     * @param hinhAnh  đường link cho hình ảnh
	     * @param giaSP   Giá sản phảm
	     */
		
		 setMaHH(maHH);	 
		 setTenHH(tenHH);	
		 setHinhAnh(hinhAnh);
		 setGiaSP(giaSP);
	}
	
	public HangHoa( String tenHH, String hinhAnh, double giaSP) {
		/**
	     * Constructor để tạo một  hàng hóa
	     * 
	     * @param maHH    Mã Hàng Hóa
	     * @param tenHH   Tên Hàng Hóa
	     * @param hinhAnh  đường link cho hình ảnh
	     * @param giaSP   Giá sản phảm
	     */
		 
		 
		setMaHH(maHH);
		setTenHH(tenHH);
		setHinhAnh(hinhAnh);
		setGiaSP(giaSP);
	}
	
	
	public String getMaHH() {
		return maHH;
	}
	public void setMaHH(String maHH) {
		 if (maHH == null || !maHH.matches("^HH\\d{4}$")) {
			 throw new IllegalArgumentException("Mã hàng hóa không hợp lệ. Phải bắt đầu bằng 'HH' và theo sau là tối đa 4 chữ số");
		 }this.maHH = maHH;
	}
	public String getTenHH() {
		return tenHH;
	}
	public void setTenHH(String tenHH) {
		 if (tenHH == null ) {
			 throw new IllegalArgumentException("Tên hàng hóa không được để trống");
		 }this.tenHH = tenHH;
	}
	
	public String getHinhAnh() {
		return hinhAnh;
	}
	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
	public double getGiaSP() {
		return giaSP;
	}
	public void setGiaSP(double giaSP) {
		if(giaSP < 0 ) {
			throw new IllegalArgumentException("Giá sản phẩm không hợp lệ(lớn hơn 0)");
		}this.giaSP = giaSP;
	}

	
	@Override
	public String toString() {
		return "HangHoa [maHH=" + maHH + ", tenHH=" + tenHH + ", hinhAnh=" + hinhAnh + ", giaSP=" + giaSP +"]";
	}
	
	



}
