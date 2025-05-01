package Model;

/**
 * Lớp ChiTietHoaDon đại diện cho một dòng sản phẩm trong hóa đơn.
 * Chứa thông tin số lượng và tham chiếu đến đối tượng Hàng hóa.
 */
public class ChiTietHoaDon {
    private int soLuong;
    private HangHoa hangHoa;

    /**
     * Khởi tạo một chi tiết hóa đơn với số lượng và hàng hóa.
     *
     * @param soLuong Số lượng sản phẩm
     * @param hangHoa Sản phẩm tương ứng
     */
    public ChiTietHoaDon(int soLuong, HangHoa hangHoa) {
        setSoLuong(soLuong);
        this.hangHoa = hangHoa;
    }
    // Getter và Setter
    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        if(soLuong <= 0) {
        	throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        this.soLuong = soLuong;
    }

    public HangHoa getHangHoa() {
        return hangHoa;
    }

    public void setHangHoa(HangHoa hangHoa) {
        this.hangHoa = hangHoa;
    }

    /**
     * Tính thành tiền cho dòng sản phẩm.
     * @return soLuong * đơn giá sản phẩm
     */
    public double getThanhTien() {
        return soLuong * hangHoa.getGiaSP();
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon [hangHoa=" + hangHoa +
                ", soLuong=" + soLuong +
                ", thanhTien=" + getThanhTien() + "]";
    }
}

//test chitiethaoadon mới
//package Model;
//
///**
// * Lớp ChiTietHoaDon đại diện cho một dòng sản phẩm trong hóa đơn.
// * Chứa thông tin số lượng, thành tiền, mã hàng hóa và mã hóa đơn.
// */
//public class ChiTietHoaDon {
//    private int maCTHD; // Mã chi tiết hóa đơn
//    private String maHDBH; // Mã hóa đơn
//    private String maHH; // Mã hàng hóa
//    private HangHoa hangHoa; // Hàng hóa tương ứng
//    private int soLuong; // Số lượng sản phẩm
//    private double thanhTien; // Thành tiền của sản phẩm
//
//    /**
//     * Khởi tạo một chi tiết hóa đơn với số lượng và hàng hóa.
//     *
//     * @param soLuong Số lượng sản phẩm
//     * @param hangHoa Hàng hóa tương ứng
//     */
//    public ChiTietHoaDon(int soLuong, HangHoa hangHoa) {
//        setSoLuong(soLuong);
//        this.hangHoa = hangHoa;
//        this.maHDBH = null; // Đây là mã hóa đơn, sẽ được gán khi lưu vào DB
//        this.maHH = hangHoa.getMaHH(); // Mã hàng hóa từ đối tượng HangHoa
//        this.thanhTien = soLuong * hangHoa.getGiaSP(); // Tính thành tiền từ số lượng và giá sản phẩm
//    }
//
//    /**
//     * Khởi tạo một chi tiết hóa đơn với mã hóa đơn, mã hàng hóa, số lượng và thành tiền.
//     *
//     * @param maHDBH Mã hóa đơn
//     * @param maHH Mã hàng hóa
//     * @param soLuong Số lượng sản phẩm
//     * @param thanhTien Thành tiền của sản phẩm
//     */
//    public ChiTietHoaDon(String maHDBH, String maHH, int soLuong, double thanhTien) {
//        this.maHDBH = maHDBH;
//        this.maHH = maHH;
//        setSoLuong(soLuong);
//        this.thanhTien = thanhTien;
//    }
//
//    // Getter và Setter
//    public int getMaCTHD() {
//        return maCTHD;
//    }
//
//    public void setMaCTHD(int maCTHD) {
//        this.maCTHD = maCTHD;
//    }
//
//    public String getMaHDBH() {
//        return maHDBH;
//    }
//
//    public void setMaHDBH(String maHDBH) {
//        this.maHDBH = maHDBH;
//    }
//
//    public String getMaHH() {
//        return maHH;
//    }
//
//    public void setMaHH(String maHH) {
//        this.maHH = maHH;
//    }
//
//    public HangHoa getHangHoa() {
//        return hangHoa;
//    }
//
//    public void setHangHoa(HangHoa hangHoa) {
//        this.hangHoa = hangHoa;
//    }
//
//    public int getSoLuong() {
//        return soLuong;
//    }
//
//    public void setSoLuong(int soLuong) {
//        if (soLuong <= 0) {
//            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
//        }
//        this.soLuong = soLuong;
//    }
//
//    public double getThanhTien() {
//        return thanhTien;
//    }
//
//    public void setThanhTien(double thanhTien) {
//        this.thanhTien = thanhTien;
//    }
//
//    @Override
//    public String toString() {
//        return "ChiTietHoaDon [maCTHD=" + maCTHD +
//                ", maHDBH=" + maHDBH +
//                ", maHH=" + maHH +
//                ", hangHoa=" + hangHoa +
//                ", soLuong=" + soLuong +
//                ", thanhTien=" + thanhTien + "]";
//    }
//}

