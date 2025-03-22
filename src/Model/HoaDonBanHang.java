package Model;

import java.time.LocalDate;

/**
 * Lớp HoaDonBanHang đại diện cho một hóa đơn bán hàng trong hệ thống.
 * Lớp này chứa các thông tin cơ bản của hóa đơn như mã hóa đơn, ngày lập, giảm giá, điểm tích lũy và chi phí khách.
 */
public class HoaDonBanHang {
    // Thuộc tính
    private String maHDBH;
    private LocalDate ngayLapHDBH;
    private int giamGia;
    private int diemTL;
    private double chiPhiKhac;

    /**
     * Khởi tạo một đối tượng HoaDonBanHang với các thông tin được cung cấp.
     *
     * @param maHDBH      Mã hóa đơn bán hàng
     * @param ngayLapHDBH Ngày lập hóa đơn
     * @param giamGia     Giảm giá áp dụng cho hóa đơn
     * @param diemTL      Điểm tích lũy của hóa đơn
     * @param chiPhiKhac  Chi phí khác liên quan đến hóa đơn
     */
    public HoaDonBanHang(String maHDBH, LocalDate ngayLapHDBH, int giamGia, int diemTL, double chiPhiKhac) {
        this.maHDBH = maHDBH;
        this.ngayLapHDBH = ngayLapHDBH;
        this.giamGia = giamGia;
        this.diemTL = diemTL;
        this.chiPhiKhac = chiPhiKhac;
    }

    // Getter và Setter cho maHDBH
    public String getMaHDBH() {
        return maHDBH;
    }

    public void setMaHDBH(String maHDBH) {
        this.maHDBH = maHDBH;
    }

    // Getter và Setter cho ngayLapHDBH
    public LocalDate getNgayLapHDBH() {
        return ngayLapHDBH;
    }

    public void setNgayLapHDBH(LocalDate ngayLapHDBH) {
        this.ngayLapHDBH = ngayLapHDBH;
    }

    // Getter và Setter cho giamGia
    public int getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }

    // Getter và Setter cho diemTL
    public int getDiemTL() {
        return diemTL;
    }

    public void setDiemTL(int diemTL) {
        this.diemTL = diemTL;
    }

    // Getter và Setter cho chiPhiKhac
    public double getChiPhiKhac() {
        return chiPhiKhac;
    }

    public void setChiPhiKhac(double chiPhiKhac) {
        this.chiPhiKhac = chiPhiKhac;
    }

    /**
     * Trả về chuỗi biểu diễn thông tin của đối tượng HoaDonBanHang.
     *
     * @return Chuỗi chứa thông tin của hóa đơn bán hàng
     */
    @Override
    public String toString() {
        return "HoaDonBanHang [maHDBH=" + maHDBH + ", ngayLapHDBH=" + ngayLapHDBH + ", giamGia=" + giamGia
                + ", diemTL=" + diemTL + ", chiPhiKhac=" + chiPhiKhac + "]";
    }
}