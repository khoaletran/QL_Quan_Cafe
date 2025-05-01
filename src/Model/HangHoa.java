package Model;

public class HangHoa {
    private String maHH;
    private String tenHH;
    private String hinhAnh;
    private double giaSP;
    private LoaiHangHoa loaiHangHoa;

    public HangHoa() {
    	 setMaHH(maHH);
         setTenHH(tenHH);
         setHinhAnh(hinhAnh);
         setGiaSP(giaSP);
         setLoaiHangHoa(loaiHangHoa);
	}
    
   

	public HangHoa(String maHH, String tenHH, String hinhAnh, double giaSP) {
        setMaHH(maHH);
        setTenHH(tenHH);
        setHinhAnh(hinhAnh);
        setGiaSP(giaSP);
//        this.loaiHangHoa = null; // Gán null, cần kiểm tra khi sử dụng
    }

    public HangHoa(String tenHH, String hinhAnh, double giaSP, LoaiHangHoa loaiHangHoa) {
        setMaHH("HH0000"); // Mã tạm thời, trigger sẽ tạo MAHH thực
        setTenHH(tenHH);
        setHinhAnh(hinhAnh);
        setGiaSP(giaSP);
        setLoaiHangHoa(loaiHangHoa);
    }

    public HangHoa(String maHH, String tenHH, String hinhAnh, double giaSP, LoaiHangHoa loaiHangHoa) {
        setMaHH(maHH);
        setTenHH(tenHH);
        setHinhAnh(hinhAnh);
        setGiaSP(giaSP);
        setLoaiHangHoa(loaiHangHoa);
    }

    public String getMaHH() {
        return maHH;
    }

    public void setMaHH(String maHH) {
        if (maHH == null || !maHH.matches("^HH\\d{4}$")) {
            throw new IllegalArgumentException("Mã hàng hóa không hợp lệ. Phải bắt đầu bằng 'HH' và theo sau là 4 chữ số");
        }
        this.maHH = maHH;
    }

    public String getTenHH() {
        return tenHH;
    }

    public void setTenHH(String tenHH) {
        if (tenHH == null) {
            throw new IllegalArgumentException("Tên hàng hóa không được để trống");
        }
        this.tenHH = tenHH;
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
        if (giaSP < 0) {
            throw new IllegalArgumentException("Giá sản phẩm không hợp lệ (lớn hơn 0)");
        }
        this.giaSP = giaSP;
    }

    public LoaiHangHoa getLoaiHangHoa() {
        return loaiHangHoa;
    }

    public void setLoaiHangHoa(LoaiHangHoa loaiHangHoa) {
        if (loaiHangHoa == null) {
            throw new IllegalArgumentException("Loại hàng hóa không được để trống");
        }
        this.loaiHangHoa = loaiHangHoa;
    }

    public String getLoaiHH() {
        return loaiHangHoa != null ? loaiHangHoa.getTenLoaiHang() : "N/A";
    }

    public String getMoTa() {
        return loaiHangHoa != null ? loaiHangHoa.getMota() : "Không có mô tả";
    }

    @Override
    public String toString() {
        return "HangHoa [maHH=" + maHH + ", tenHH=" + tenHH + ", hinhAnh=" + hinhAnh + ", giaSP=" + giaSP
                + ", loaiHangHoa=" + loaiHangHoa + "]";
    }
}