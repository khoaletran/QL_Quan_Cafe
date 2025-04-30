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
    private MaGiamGia giamGia;          // có thể null nếu không có mã giảm giá
    private int diemTL;                  // >= 0
    private KhachHang khachHang;
    private boolean hinhThucThanhToan;
    private ArrayList<ChiTietHoaDon> chiTietHoaDonList;
    private double tongGiamGia;

    // Constructor đầy đủ lấy dữ liệu từ SQL
    public HoaDonBanHang(String maHDBH, LocalDate ngayLapHDBH, int diemTL, ArrayList<ChiTietHoaDon> chiTietHoaDonList) {
        setMaHDBH(maHDBH);
        setNgayLapHDBH(ngayLapHDBH);
        setDiemTL(diemTL);
        setChiTietHoaDonList(chiTietHoaDonList != null ? chiTietHoaDonList : new ArrayList<ChiTietHoaDon>());
    }

 // Sửa constructor này
    public HoaDonBanHang(String maHDBH, LocalDate ngayLapHDBH, MaGiamGia giamGia, KhachHang khachHang, boolean hinhThucThanhToan) {
        setMaHDBH(maHDBH);  // Phương thức setMaHDBH đã được sửa để chấp nhận null
        setNgayLapHDBH(ngayLapHDBH);
        setGiamGia(giamGia);
        setKhachHang(khachHang);
        setHinhThucThanhToan(hinhThucThanhToan);
        setDiemTL(0);
        this.chiTietHoaDonList = new ArrayList<ChiTietHoaDon>();
    }
    
 // Constructor cho việc tạo hóa đơn mới (chưa có mã)
    public HoaDonBanHang(LocalDate ngayLapHDBH, MaGiamGia giamGia, KhachHang khachHang, boolean hinhThucThanhToan) {
        this.maHDBH = null; // Khởi tạo là null
        this.ngayLapHDBH = ngayLapHDBH;
        this.giamGia = giamGia;
        this.khachHang = khachHang;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.diemTL = 0;
        this.chiTietHoaDonList = new ArrayList<>();
    }
    
    public HoaDonBanHang(LocalDate ngayLapHDBH, MaGiamGia giamGia, KhachHang khachHang, 
            boolean hinhThucThanhToan, double tongGiamGia) {
       this.maHDBH = null;
       setNgayLapHDBH(ngayLapHDBH);
       setGiamGia(giamGia);
       setKhachHang(khachHang);
       setHinhThucThanhToan(hinhThucThanhToan);
       setTongGiamGia(tongGiamGia);
       setDiemTL(0);
       this.chiTietHoaDonList = new ArrayList<>();
    }

    // ===== Ràng buộc =====
    public void setMaHDBH(String maHDBH) {
        // Cho phép maHDBH là null (khi tạo hóa đơn mới chưa có mã)
        if (maHDBH != null && !maHDBH.matches("^HD\\d{4}$")) {
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

    public void setDiemTL(int diemTL) {
        if (diemTL < 0) {
            throw new IllegalArgumentException("Điểm tích lũy không được âm");
        }
        this.diemTL = diemTL;
    }

    public void setChiTietHoaDonList(ArrayList<ChiTietHoaDon> chiTietHoaDonList) {
        this.chiTietHoaDonList = chiTietHoaDonList;
    }

    public void setGiamGia(MaGiamGia giamGia) {
        this.giamGia = giamGia;
    }

    public void setKhachHang(KhachHang khachHang) {
        if (khachHang == null) {
            throw new IllegalArgumentException("Khách hàng không được null");
        }
        this.khachHang = khachHang;
    }

    public void setHinhThucThanhToan(boolean hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    // ===== Getter =====
    public String getMaHDBH() {
        return maHDBH;
    }

    public LocalDate getNgayLapHDBH() {
        return ngayLapHDBH;
    }

    public MaGiamGia getGiamGia() {
        return giamGia;
    }
    public int getdiemTL() {
        return diemTL;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public boolean isHinhThucThanhToan() {
        return hinhThucThanhToan;
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
        double giam = tong * (tongGiamGia / 100.0);
        return tong - giam;
    }

    public int getdiemTL_THD() {
        return (int) (tinhTong() * 0.0001);
    }
    
    public double getTongGiamGia() {
        return tongGiamGia;
    }

    public void setTongGiamGia(double tongGiamGia) {
        if (tongGiamGia < 0 || tongGiamGia > 100) {
            throw new IllegalArgumentException("Tổng giảm giá phải từ 0 đến 100%");
        }
        this.tongGiamGia = tongGiamGia;
    }

   

    @Override
    public String toString() {
        return "HoaDonBanHang [" +
                "maHDBH=" + maHDBH +
                ", ngayLap=" + ngayLapHDBH +
                ", giamGia=" + (giamGia != null ? giamGia.getGiamGia() : 0) +
                ", diemTL=" + diemTL +
                ", hinhThucThanhToan=" + (hinhThucThanhToan ? "Chuyển khoản" : "Tiền mặt") +
                ", tongTienHang=" + tinhTong() +
                ", thanhToan=" + tinhTongThanhToan() +
                ", soMatHang=" + chiTietHoaDonList.size() +
                "]";
    }
}
