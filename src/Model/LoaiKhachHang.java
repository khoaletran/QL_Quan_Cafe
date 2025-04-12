package Model;

public class LoaiKhachHang {
    private String maLKH;          // SQL tự tăng – không set từ bên ngoài
    private String tenLKH;
    private int giamGia;           // Phần trăm giảm giá (1% – 99%)
    private int mucDiem;

    public LoaiKhachHang() {
    }

    /**
     * Constructor không bao gồm maLKH vì để SQL tự tăng.
     */
    public LoaiKhachHang(String tenLKH, int giamGia) {
		/**
		 * Constructor để tạo một loại khách hàng.
		 * 
		 * @param maLKH    Mã loại khách hàng
		 * @param tenLKH   Tên loại khách hàng
		 * @param giamGia  Phần trăm giảm giá áp dụng cho khách hàng
		 */
        setTenLKH(tenLKH);
        setGiamGia(giamGia);
    }

    
    public LoaiKhachHang(String maLKH,String tenLKH, int giamGia,int mucdiem) {
		/**
		 * Constructor để tạo một loại khách hàng.
		 * 
		 * @param maLKH    Mã loại khách hàng
		 * @param tenLKH   Tên loại khách hàng
		 * @param giamGia  Phần trăm giảm giá áp dụng cho khách hàng
		 */
    	setMaLKH(maLKH);
        setTenLKH(tenLKH);
        setGiamGia(giamGia);
        setMucDiem(mucdiem);
    }
    
    public String getMaLKH() {
        return maLKH;
    }

    // Có thể set nếu cần gán từ DB ra object a
    public void setMaLKH(String maLKH) {
    	if(maLKH == null || !maLKH.matches("^LKH\\d{6}$")) {
    		throw new IllegalArgumentException("Mã loại khách hàng sai định dạng");
    	}
    	this.maLKH = maLKH;
    }

    public String getTenLKH() {
        return tenLKH;
    }

    public void setTenLKH(String tenLKH) {
        if (tenLKH == null || tenLKH.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên loại khách hàng không được để trống.");
        }
        this.tenLKH = tenLKH.trim();
    }

    public int getGiamGia() {
        return giamGia;
    }


    public void setGiamGia(int giamGia) {
        if (giamGia <= 0 || giamGia >= 100) {
            throw new IllegalArgumentException("Giảm giá phải lớn hơn 0% và nhỏ hơn 100%.");
        }
        this.giamGia = giamGia;
    }
    

    public int getMucDiem() {
		return mucDiem;
	}

	public void setMucDiem(int mucDiem) {
		this.mucDiem = mucDiem;
	}

	@Override
    public String toString() {
        return "LoaiKhachHang [maLKH=" + maLKH + ", tenLKH=" + tenLKH + ", giamGia=" + giamGia + "%]";
    }
}
