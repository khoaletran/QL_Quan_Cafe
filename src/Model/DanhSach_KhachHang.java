package Model;

import java.util.ArrayList;

public class DanhSach_KhachHang {
	private ArrayList<KhachHang> listKH;
	
	public DanhSach_KhachHang() {
		listKH = new ArrayList<KhachHang>();
	}
	
	public boolean themKH(KhachHang KH) {
		for(KhachHang kh : listKH) {
			if(KH.equals(kh.getMaKH())) {
				return false;
			}
		}
		listKH.add(KH);
		return true;
	}
	
	public KhachHang timKHbangMa(String sdt) {
		for(KhachHang kh : listKH) {
			if(sdt.equals(kh.getSoDienThoai())) {
				return kh;
			}
		}
		return null;
	}
	
	public void capNhatDiemTichLuy(String sdt, int diemTL) {
		for (KhachHang kh : listKH) {
			if(kh.getSoDienThoai().equals(sdt)) {
				kh.setDiemTL(kh.getDiemTL() + diemTL);
//				kh.setLoaiKhachHang(kh.getDiemTL());
			}
		}
	}
	  public KhachHang getElementAt(int index) {
	    	if(index < 0 || index >= listKH.size()) {
	    		throw new IllegalArgumentException("Không tìm thấy");
	    	}
	    	return listKH.get(index);
	    }
	
	
}
