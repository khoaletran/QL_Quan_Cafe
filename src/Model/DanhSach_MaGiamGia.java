//package Model;
//
//import java.util.ArrayList;
//
//public class DanhSach_MaGiamGia {
//	ArrayList<MaGiamGia> listMGG;
//	
//	public DanhSach_MaGiamGia() {
//		listMGG = new ArrayList<MaGiamGia>();
//	}
//	
//	public boolean them(MaGiamGia mgg) {
//		for(MaGiamGia a : listMGG) {
//			if(mgg.equals(a.getMaGiam())) {
//				return false;
//			}
//		}
//		listMGG.add(mgg);
//		return true;
//	}
//	
//	public MaGiamGia LayMa(String mgg) {
//		for(MaGiamGia a : listMGG) {
//			if(mgg.equals(a.getMaGiam())) {
//				return a;
//			}
//		}
//		return null;
//	}
//	  public MaGiamGia getElementAt(int index) {
//	    	if(index < 0 || index >= listMGG.size()) {
//	    		throw new IllegalArgumentException("Không tìm thấy");
//	    	}
//	    	return listMGG.get(index);
//	    }
//}
