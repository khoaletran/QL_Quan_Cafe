package Model;

public class KhachHang {

    private String maKH; // SQL tự tăng – không set từ Java
    private String tenKH;
    private String soDienThoai;
    private int diemTL;
    private LoaiKhachHang loaiKhachHang;

    public KhachHang() {
    }

    /**
     * Constructor không bao gồm maKH (vì SQL tự tăng).
     */
    public KhachHang(String tenKH, String soDienThoai, int diemTL, LoaiKhachHang loaiKhachHang) {
    	 /**
         * Constructor để tạo một khách hàng khi nhân viên cần tạo mới.
         *
         * @param tenKH         Tên khách hàng
         * @param soDienThoai   Số điện thoại khách hàng
         * @param diemTL        Điểm tích lũy của khách hàng
         * @param loaiKhachHang Loại khách hàng (VIP, thường,...)
         */
        setTenKH(tenKH);
        setSoDienThoai(soDienThoai);
        setDiemTL(diemTL);
        setLoaiKhachHang(loaiKhachHang);
    }
    
    public KhachHang(String maKH,String tenKH, String soDienThoai, int diemTL, LoaiKhachHang loaiKhachHang) {
   	 /**
        * Constructor để tạo một khách hàng với đầy đủ thông tin lấy dữ liệu từ SQL
        *
        * @param maKH          Mã khách hàng
        * @param tenKH         Tên khách hàng
        * @param soDienThoai   Số điện thoại khách hàng
        * @param diemTL        Điểm tích lũy của khách hàng
        * @param loaiKhachHang Loại khách hàng (VIP, thường,...)
        */
    	setMaKH(maKH);
    	setTenKH(tenKH);
    	setSoDienThoai(soDienThoai);
    	setDiemTL(diemTL);
    	setLoaiKhachHang(loaiKhachHang);
    }
    
    public KhachHang(String tenKH ,String soDienThoai,int diemTL) {
      	 /**
           * Constructor này tạo khách hành mới với tên và số điện thoại khi nhập ở hóa đơn
           *
           * @param tenKH         Tên khách hàng
           * @param soDienThoai   Số điện thoại khách hàng
           * @param diemTL        Điểm tích lũy của khách hàng
           * @param loaiKhachHang Loại khách hàng (VIP, thường,...)
           */
       	setTenKH(tenKH);
       	setSoDienThoai(soDienThoai);
       	setDiemTL(diemTL);
       	setLoaiKhachHang(loaiKhachHang);
      }

    public String getMaKH() {
        return maKH;
    }

    // Không có setter cho maKH – SQL sẽ tự tăng
    // Nếu thật sự cần, có thể thêm setter nhưng để package-private hoặc protected 
    protected void setMaKH(String maKH) {
    	if(maKH == null || !maKH.matches("^KH\\d{6}$")) {
    		throw new IllegalArgumentException("Ma khách hàng sai định dạng.");
    	}
    	this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        if (tenKH == null || tenKH.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống.");
        }
        this.tenKH = tenKH.trim();
    }



    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        if (soDienThoai == null || !soDienThoai.matches("^0[2-9]\\d{8}$")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ (phải bắt đầu từ 02–09 và đủ 10 số).");
        }
        this.soDienThoai = soDienThoai;
    }

    public int getDiemTL() {
        return diemTL;
    }

    public void setDiemTL(int diemTL) {
        if (diemTL < 0) {
            throw new IllegalArgumentException("Điểm tích lũy phải lớn hơn hoặc bằng 0.");
        }
        this.diemTL = diemTL;
    }

    public LoaiKhachHang getLoaiKhachHang() {
        return loaiKhachHang;
    }

    public void setLoaiKhachHang(LoaiKhachHang loaiKhachHang) {
        if (loaiKhachHang == null) {
            throw new IllegalArgumentException("Loại khách hàng không được null.");
        }
        this.loaiKhachHang = loaiKhachHang;
    }

    @Override
    public String toString() {
        return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", soDienThoai=" + soDienThoai
                + ", diemTL=" + diemTL + ", loaiKhachHang=" + loaiKhachHang + "]";
    }
}
