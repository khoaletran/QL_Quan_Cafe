package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp HoaDonBanHang đại diện cho một hóa đơn bán hàng trong hệ thống.
 * Quản lý danh sách chi tiết hóa đơn, tính tổng tiền, áp dụng giảm giá và chi phí khác.
 */
public class HoaDonBanHang {
    // Thuộc tính cơ bản
    private String maHDBH;
    private LocalDate ngayLapHDBH;
    private int giamGia;       // phần trăm (0 - 100)
    private int diemTL;
    private double chiPhiKhac;

    // Danh sách chi tiết hóa đơn
    private List<ChiTietHoaDon> chiTietHoaDonList;

    // Constructor
    public HoaDonBanHang(String maHDBH, LocalDate ngayLapHDBH, int giamGia, int diemTL, double chiPhiKhac) {
        this.maHDBH = maHDBH;
        this.ngayLapHDBH = ngayLapHDBH;
        this.giamGia = giamGia;
        this.diemTL = diemTL;
        this.chiPhiKhac = chiPhiKhac;
        this.chiTietHoaDonList = new ArrayList<>();
    }

    // Thêm chi tiết hóa đơn
    public void themChiTiet(ChiTietHoaDon cthd) {
        chiTietHoaDonList.add(cthd);
    }

    // Tính tổng tiền hàng (trước khi giảm giá)
    public double tinhTongTienHang() {
        double tong = 0;
        for (ChiTietHoaDon cthd : chiTietHoaDonList) {
            tong += cthd.getThanhTien();
        }
        return tong;
    }

    // Tính tổng tiền thanh toán cuối cùng
    public double tinhTongThanhToan() {
        double tongHang = tinhTongTienHang();
        double tienSauGiam = tongHang * (1 - giamGia / 100.0);
        return tienSauGiam + chiPhiKhac;
    }

    // Getter và Setter
    public String getMaHDBH() {
        return maHDBH;
    }

    public void setMaHDBH(String maHDBH) {
        this.maHDBH = maHDBH;
    }

    public LocalDate getNgayLapHDBH() {
        return ngayLapHDBH;
    }

    public void setNgayLapHDBH(LocalDate ngayLapHDBH) {
        this.ngayLapHDBH = ngayLapHDBH;
    }

    public int getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }

    public int getDiemTL() {
        return diemTL;
    }

    public void setDiemTL(int diemTL) {
        this.diemTL = diemTL;
    }

    public double getChiPhiKhac() {
        return chiPhiKhac;
    }

    public void setChiPhiKhac(double chiPhiKhac) {
        this.chiPhiKhac = chiPhiKhac;
    }

    public List<ChiTietHoaDon> getChiTietHoaDonList() {
        return chiTietHoaDonList;
    }

    public void setChiTietHoaDonList(List<ChiTietHoaDon> chiTietHoaDonList) {
        this.chiTietHoaDonList = chiTietHoaDonList;
    }
    // In thông tin hóa đơn
    @Override
    public String toString() {
        return "HoaDonBanHang [" +
                "maHDBH=" + maHDBH +
                ", ngayLap=" + ngayLapHDBH +
                ", giamGia=" + giamGia + "%" +
                ", diemTL=" + diemTL +
                ", chiPhiKhac=" + chiPhiKhac +
                ", tongTienHang=" + tinhTongTienHang() +
                ", thanhToan=" + tinhTongThanhToan() +
                ", soMatHang=" + chiTietHoaDonList.size() +
                "]";
    }
}
