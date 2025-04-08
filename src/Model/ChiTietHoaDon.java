package Model;

/**
 * Lớp ChiTietHoaDon đại diện cho một dòng sản phẩm trong hóa đơn.
 * Chứa thông tin số lượng và tham chiếu đến đối tượng Hàng hóa.
 */
public class ChiTietHoaDon {
    private int soLuong;
    private HangHoa hangHoa; // Tham chiếu đến đối tượng HangHoa

    /**
     * Khởi tạo một chi tiết hóa đơn với số lượng và hàng hóa.
     *
     * @param soLuong Số lượng sản phẩm
     * @param hangHoa Sản phẩm tương ứng
     */
    public ChiTietHoaDon(int soLuong, HangHoa hangHoa) {
        this.soLuong = soLuong;
        this.hangHoa = hangHoa;
    }

    // Getter và Setter
    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
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
