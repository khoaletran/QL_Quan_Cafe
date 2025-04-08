package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp HoaDonBanHang đại diện cho một hóa đơn bán hàng trong hệ thống.
 */
public class HoaDonBanHang {
    private String maHDBH;               // định dạng HDXXXX
    private LocalDate ngayLapHDBH;       // ngày hiện tại
    private int giamGia;                 // >= 0
    private int diemTL;                  // >= 0

    private List<ChiTietHoaDon> chiTietHoaDonList;

    // constructor nhap
    public HoaDonBanHang(int giamGia, int diemTL) {
        setGiamGia(giamGia);
        setDiemTL(diemTL);
        this.chiTietHoaDonList = new ArrayList<>();
    }

    // constructor day du lay du lieu tu sql
    public HoaDonBanHang(String maHDBH, LocalDate ngayLapHDBH, int giamGia, int diemTL, List<ChiTietHoaDon> chiTietHoaDonList) {
        setMaHDBH(maHDBH);
        setNgayLapHDBH(ngayLapHDBH);
        setGiamGia(giamGia);
        setDiemTL(diemTL);
        setChiTietHoaDonList(chiTietHoaDonList != null ? chiTietHoaDonList : new ArrayList<>());
    }

    // ===== Ràng buộc =====
    public void setMaHDBH(String maHDBH) {
        if (!maHDBH.matches("^HD\\d{4}$")) {
            throw new IllegalArgumentException("Mã HĐ phải theo định dạng HDXXXX (ví dụ: HD0001)");
        }
        this.maHDBH = maHDBH;
    }
    public void setNgayLapHDBH(LocalDate ngayLapHDBH) {
        if (ngayLapHDBH == null) {
            throw new IllegalArgumentException("Ngày lập không được null");
        }
        this.ngayLapHDBH = ngayLapHDBH;
    }

    public void setGiamGia(int giamGia) {
        if (giamGia < 0) {
            throw new IllegalArgumentException("Giảm giá không được âm");
        }
        this.giamGia = giamGia;
    }

    public void setDiemTL(int diemTL) {
        if (diemTL < 0) {
            throw new IllegalArgumentException("Điểm tích lũy không được âm");
        }
        this.diemTL = diemTL;
    }

    public void setChiTietHoaDonList(List<ChiTietHoaDon> chiTietHoaDonList) {
        this.chiTietHoaDonList = chiTietHoaDonList;
    }

    // ===== Getter =====
    public String getMaHDBH() {
        return maHDBH;
    }

    public LocalDate getNgayLapHDBH() {
        return ngayLapHDBH;
    }

    public int getGiamGia() {
        return giamGia;
    }

    public int getDiemTL() {
        return diemTL;
    }

    public List<ChiTietHoaDon> getChiTietHoaDonList() {
        return chiTietHoaDonList;
    }

    // ===== Thêm chi tiết hóa đơn =====
    public void themChiTiet(ChiTietHoaDon cthd) {
        chiTietHoaDonList.add(cthd);
    }

    // ===== Tính tổng tiền hàng =====
    public double tinhTong() {
        double tong = 0;
        for (ChiTietHoaDon ct : chiTietHoaDonList) {
            tong += ct.getThanhTien();
        }
        return tong;
    }

    // ===== Tính tổng thanh toán sau giảm giá =====
    public double tinhTongThanhToan() {
    	double tong = tinhTong();
        double giam = tong * (giamGia / 100.0);
        return tong - giam;
    }

    @Override
    public String toString() {
        return "HoaDonBanHang [" +
                "maHDBH=" + maHDBH +
                ", ngayLap=" + ngayLapHDBH +
                ", giamGia=" + giamGia +
                ", diemTL=" + diemTL +
                ", tongTienHang=" + tinhTong() +
                ", thanhToan=" + tinhTongThanhToan() +
                ", soMatHang=" + chiTietHoaDonList.size() +
                "]";
    }
}
    