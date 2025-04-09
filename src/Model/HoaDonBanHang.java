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
    private MaGiamGia giamGia;			// >= 0
    private int diemTL;                  // >= 0
    private KhachHang khachHang;

    private ArrayList<ChiTietHoaDon> chiTietHoaDonList;

    // constructor nhap
    // Mốt làm hàm main phải khai báo dsmagiamgia với dskhachhang để thêm vào đây kho dữ liệu 
    public HoaDonBanHang(DanhSach_MaGiamGia DSMGG,DanhSach_KhachHang DSKH,String giamGia,String sdt,String mgg) {
        setGiamGia(DSMGG.LayMa(mgg));
        setKhachHang(DSKH.timKHbangMa(sdt));
        DSKH.timKHbangMa(sdt).setDiemTL(DSKH.timKHbangMa(sdt).getDiemTL() + diemTL);
        this.chiTietHoaDonList = new ArrayList<ChiTietHoaDon>();
    }


	// constructor day du lay du lieu tu sql
    public HoaDonBanHang(String maHDBH, LocalDate ngayLapHDBH, int giamGia, int diemTL, ArrayList<ChiTietHoaDon> chiTietHoaDonList) {
        setMaHDBH(maHDBH);
        setNgayLapHDBH(ngayLapHDBH);
        setDiemTL(diemTL);
        setChiTietHoaDonList(chiTietHoaDonList != null ? chiTietHoaDonList : new ArrayList<ChiTietHoaDon>());
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

    public void setDiemTL(int diemTL) {
        if (diemTL < 0) {
            throw new IllegalArgumentException("Điểm tích lũy không được âm");
        }
        this.diemTL = diemTL;
    }

    public void setChiTietHoaDonList(ArrayList<ChiTietHoaDon> chiTietHoaDonList) {
        this.chiTietHoaDonList = chiTietHoaDonList;
    }

    // ===== Getter =====
    public String getMaHDBH() {
        return maHDBH;
    }

    public LocalDate getNgayLapHDBH() {
        return ngayLapHDBH;
    }

    public int getDiemTL() {
        return diemTL;
    }
    

    public MaGiamGia getGiamGia() {
		return giamGia;
	}

	public void setGiamGia(MaGiamGia giamGia) {
		this.giamGia = giamGia;
	}
	

    public KhachHang getKhachHang() {
		return khachHang;
	}


	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
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
        double giam = tong * ( giamGia.getGiamGia() / 100.0);
        return tong - giam;
    }
    
    public int getdiemTL() {
    	return (int) (tinhTong() * 0.1);
    }

    @Override
    public String toString() {
        return "HoaDonBanHang [" +
                "maHDBH=" + maHDBH +
                ", ngayLap=" + ngayLapHDBH +
                ", giamGia=" + giamGia.getGiamGia() +
                ", diemTL=" + diemTL +
                ", tongTienHang=" + tinhTong() +
                ", thanhToan=" + tinhTongThanhToan() +
                ", soMatHang=" + chiTietHoaDonList.size() +
                "]";
    }
}
    