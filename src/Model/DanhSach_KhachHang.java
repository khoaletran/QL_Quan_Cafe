package Model;

import java.util.ArrayList;

public class DanhSach_KhachHang {
	private ArrayList<KhachHang> list;
	
	public DanhSach_KhachHang() {
		list = new ArrayList<KhachHang>();
	}
	
	public boolean themKH(KhachHang KH) {
		for(KhachHang kh : list) {
			if(KH.equals(kh.getMaKH())) {
				return false;
			}
		}
		list.add(KH);
		return true;
	}
	
	public KhachHang timKHbangMa(String sdt) {
		for(KhachHang kh : list) {
			if(sdt.equals(kh.getSoDienThoai())) {
				return kh;
			}
		}
		return null;
	}
	
}
