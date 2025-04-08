package Model;

import java.util.ArrayList;

public class DanhSach_KhachHang {
	private ArrayList<KhachHang> list;
	
	public DanhSach_KhachHang() {
		list = new ArrayList<KhachHang>();
	}
	
	public boolean themKH(KhachHang KH) {
		for(KhachHang kh : list) {
			if(kh.equals(kh.getMaKH())) {
				return false;
			}
		}
		list.add(KH);
		return true;
	}
	
	public KhachHang timKHbangMa(String maKH) {
		for(KhachHang kh : list) {
			if(kh.equals(kh.getMaKH())) {
				return kh;
			}
		}
		return null;
	}
	
}
