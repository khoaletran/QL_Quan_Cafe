package Model;

import java.util.ArrayList;

import Dao.LoaiKhachHang_DAO;

public class KhachHang {

    private String maKH; // SQL tự tăng – không set từ Java
    private String tenKH;
    private String soDienThoai;
    private int diemTL = 0;
    private LoaiKhachHang loaiKhachHang;

    public KhachHang() {
    }

    /**
     * Constructor không bao gồm maKH (vì SQL tự tăng).
     */
    public KhachHang(String tenKH, String soDienThoai, int diemTL, LoaiKhachHang loaiKhachHang) {
    	 /**
         * Constructor để tạo một khách hàng khi nhân viên cần tạo mới.
         *
         * @param tenKH         Tên khách hàng
         * @param soDienThoai   Số điện thoại khách hàng
         * @param diemTL        Điểm tích lũy của khách hàng
         * @param loaiKhachHang Loại khách hàng (VIP, thường,...)
         */
        setTenKH(tenKH);
        setSoDienThoai(soDienThoai);
        setDiemTL(diemTL);
        setLoaiKhachHang(loaiKhachHang);
    }
    
    public KhachHang(String maKH,String tenKH, String soDienThoai, int diemTL, LoaiKhachHang loaiKhachHang) {
   	 /**
        * Constructor để tạo một khách hàng với đầy đủ thông tin lấy dữ liệu từ SQL
        *
        * @param maKH          Mã khách hàng
        * @param tenKH         Tên khách hàng
        * @param soDienThoai   Số điện thoại khách hàng
        * @param diemTL        Điểm tích lũy của khách hàng
        * @param loaiKhachHang Loại khách hàng (VIP, thường,...)
        */
    	setMaKH(maKH);
    	setTenKH(tenKH);
    	setSoDienThoai(soDienThoai);
    	setDiemTL(diemTL);
    	setLoaiKhachHang(loaiKhachHang);
    }
    
    public KhachHang(String maKH,String tenKH, String soDienThoai, int diemTL) {
      	// chạy phương thức sửa khách hàng trong dao
       	setMaKH(maKH);
       	setTenKH(tenKH);
       	setSoDienThoai(soDienThoai);
       	setDiemTL(diemTL);
       }
    public KhachHang(String tenKH ,String soDienThoai,int diemTL) {
      	 /**
           * Constructor này tạo khách hành mới với tên và số điện thoại khi nhập ở hóa đơn
           *
           * @param tenKH         Tên khách hàng
           * @param soDienThoai   Số điện thoại khách hàng
           * @param diemTL        Điểm tích lũy của khách hàng
           * @param loaiKhachHang Loại khách hàng (VIP, thường,...)
           */
       	setTenKH(tenKH);
       	setSoDienThoai(soDienThoai);
       	setDiemTL(diemTL);
       	
      }

    public String getMaKH() {
        return maKH;
    }

    // Không có setter cho maKH – SQL sẽ tự tăng
    // Nếu thật sự cần, có thể thêm setter nhưng để package-private hoặc protected 
    public void setMaKH(String maKH) {
    	if(maKH == null || !maKH.matches("^KH\\d{4}$")) {
    		throw new IllegalArgumentException("Ma khách hàng sai định dạng.");
    	}
    	this.maKH = maKH;
    }

    
    
    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        if (tenKH == null || tenKH.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống.");
        }
        this.tenKH = tenKH.trim();
    }



    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        if (soDienThoai == null || !soDienThoai.matches("^0[2-9]\\d{8}$")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ (phải bắt đầu từ 02–09 và đủ 10 số).");
        }
        this.soDienThoai = soDienThoai;
    }

    public int getDiemTL() {
        return diemTL;
    }

    public void setDiemTL(int diemTL) {
        if (diemTL < 0) {
            throw new IllegalArgumentException("Điểm tích lũy phải lớn hơn hoặc bằng 0.");
        }
        this.diemTL += diemTL ;
    }

    public LoaiKhachHang getLoaiKhachHang() {
        return loaiKhachHang;
    }

    
    
//    public void setLoaiKhachHang(int diemtl) {
//    	ArrayList<LoaiKhachHang> listLKHD = new LoaiKhachHang_DAO().getAllLoaiKhachHang();
//    	DanhSach_LoaiKhachHang list = new DanhSach_LoaiKhachHang(listLKHD);
//    	
//        if(diemtl >= list.getElementAt(0).getMucDiem()){
//        	loaiKhachHang = list.getElementAt(0); 
//        }
//        else if(diemtl >= list.getElementAt(1).getMucDiem()) {
//        	loaiKhachHang = list.getElementAt(1);
//        }
//        else if(diemtl >= list.getElementAt(2).getMucDiem()) {
//        	loaiKhachHang = list.getElementAt(2);
//        }
//        else if(diemtl >= list.getElementAt(3).getMucDiem()) {
//        	loaiKhachHang = list.getElementAt(3);
//        }
//        else if(diemtl >= list.getElementAt(4).getMucDiem()) {
//        	loaiKhachHang = list.getElementAt(4);
//        }
//    }
//    Loai khach hang chuan
//    public void setLoaiKhachHang(int diemTL) {
//	        if (diemTL < 100) {
//	             this.loaiKhachHang = new LoaiKhachHang("LKH0001", "Thường", 0);
//	       } else if (diemTL < 200) {
//	        	this.loaiKhachHang = new LoaiKhachHang("LKH0002", "Thân thiết", 5);
//	       } else if (diemTL < 300) {
//	        	this.loaiKhachHang = new LoaiKhachHang("LKH0003", "Bạc", 10);
//	       } else if (diemTL < 400) {
//	    	   this.loaiKhachHang = new LoaiKhachHang("LKH0004", "Vàng", 15);
//	       } else{
//	    	   this.loaiKhachHang = new LoaiKhachHang("LKH0005", "Kim Cương", 20);
//	    } 
//   }

    
    public void setLoaiKhachHang(LoaiKhachHang loaikhanhhang) {
    	this.loaiKhachHang = loaikhanhhang;
    }

    @Override
    public String toString() {
        return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", soDienThoai=" + soDienThoai
                + ", diemTL=" + diemTL + ", loaiKhachHang=" + loaiKhachHang + "]";
    }
}
