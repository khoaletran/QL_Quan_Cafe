package Model;

/**
 * Lớp ChiTietHoaDon đại diện cho chi tiết của một hóa đơn trong hệ thống.
 * Lớp này chứa các thông tin như mã chi tiết hóa đơn, số lượng và tham chiếu đến hàng hóa.
 */
public class ChiTietHoaDon {
    // Thuộc tính
	
    private int soLuong;
    private HangHoa hangHoa; // Tham chiếu đến đối tượng HangHoa thay vì lưu donGia

    /**
     * Khởi tạo một đối tượng ChiTietHoaDon với các thông tin được cung cấp.
     *
     * @param maCTHD  Mã chi tiết hóa đơn
     * @param soLuong Số lượng sản phẩm trong chi tiết hóa đơn
     * @param hangHoa Hàng hóa liên quan đến chi tiết hóa đơn
     */
    public ChiTietHoaDon(String maCTHD, int soLuong, HangHoa hangHoa) {
        this.soLuong = soLuong;
        this.hangHoa = hangHoa;
    }


    // Getter và Setter cho soLuong
    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    // Getter và Setter cho hangHoa
    public HangHoa getHangHoa() {
        return hangHoa;
    }

    public void setHangHoa(HangHoa hangHoa) {
        this.hangHoa = hangHoa;
    }

    /**
     * Tính thành tiền của chi tiết hóa đơn dựa trên số lượng và đơn giá của hàng hóa.
     *
     * @return Thành tiền (số lượng * đơn giá)
     */
    public double getThanhTien() {
        return soLuong * hangHoa.getGiaSP();
    }

    /**
     * Trả về chuỗi biểu diễn thông tin của đối tượng ChiTietHoaDon.
     *
     * @return Chuỗi chứa thông tin của chi tiết hóa đơn
     */
    @Override
    public String toString() {
        return "ChiTietHoaDon [soLuong=" + soLuong + ", hangHoa=" + hangHoa + ", thanhTien=" + getThanhTien() + "]";
    }
}