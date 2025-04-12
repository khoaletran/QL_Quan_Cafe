package Model;

import java.util.ArrayList;

public class DanhSach_HoaDon {
	ArrayList<HoaDonBanHang> listHD;
	
	public DanhSach_HoaDon() {
		this.listHD = new ArrayList<HoaDonBanHang>();
	}
	
	public HoaDonBanHang getHoaDonBanHang(String maHD) {
		for (HoaDonBanHang hd : listHD) {
			if(hd.getMaHDBH().equals(maHD)) {
				return hd;
			}
		}
		return null;
	}
	public double tongDoanhThuTatCa() {
		double sum =0;
		for (HoaDonBanHang hd : listHD) {
			sum += hd.tinhTongThanhToan();
		}
		return sum;
	}
	public double tongDoanhThuTheoNgayThang(int ngay, int thang) {
		double sum =0;
		for (HoaDonBanHang hd : listHD) {
			if(hd.getNgayLapHDBH().getDayOfMonth() == ngay && hd.getNgayLapHDBH().getMonthValue()==thang) {
				sum += hd.tinhTongThanhToan();
			}
		}
		return sum;
	}
}
