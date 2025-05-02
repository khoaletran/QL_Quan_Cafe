//package Model;
//
//import java.util.ArrayList;
//
//public class DanhSach_LoaiKhachHang {
//	ArrayList<LoaiKhachHang> listLKH;
//	
//	public DanhSach_LoaiKhachHang() {
//		listLKH = new ArrayList<LoaiKhachHang>();
//	}
//	
//	public DanhSach_LoaiKhachHang(ArrayList<LoaiKhachHang> listnew) {
//		listLKH = listnew;
//	}
//	
//	public LoaiKhachHang getLoaiKH(String maLKH) {
//		for(LoaiKhachHang l : listLKH) {
//			if(l.getMaLKH().equals(maLKH)) {
//				return l;
//			}
//		}
//		return null;
//	}
//	  public LoaiKhachHang getElementAt(int index) {
//	    	if(index < 0 || index >= listLKH.size()) {
//	    		throw new IllegalArgumentException("Không tìm thấy");
//	    	}
//	    	return listLKH.get(index);
//	    }
//}
