package Model;

import java.util.ArrayList;

public class DanhSach_MaGiamGia {
	ArrayList<MaGiamGia> list;
	
	public DanhSach_MaGiamGia() {
		list = new ArrayList<MaGiamGia>();
	}
	
	public boolean them(MaGiamGia mgg) {
		for(MaGiamGia a : list) {
			if(mgg.equals(a.getMaGiam())) {
				return false;
			}
		}
		list.add(mgg);
		return true;
	}
	
	public MaGiamGia LayMa(String mgg) {
		for(MaGiamGia a : list) {
			if(mgg.equals(a.getMaGiam())) {
				return a;
			}
		}
		return null;
	}
}
