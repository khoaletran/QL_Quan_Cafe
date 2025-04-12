package Model;

import java.util.ArrayList;

public class DanhSach_LoaiKhachHang {
	ArrayList<LoaiKhachHang> list;
	
	public DanhSach_LoaiKhachHang() {
		list = new ArrayList<LoaiKhachHang>();
	}
	
	public LoaiKhachHang getLoaiKH(String maLKH) {
		for(LoaiKhachHang l : list) {
			if(l.getMaLKH().equals(maLKH)) {
				return l;
			}
		}
		return null;
	}
}
