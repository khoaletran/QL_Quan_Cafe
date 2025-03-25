package Model;

public class LoaiHangHoa {
   String maLoaiHang;
   String tenLoaiHang;
   String mota;
   
public LoaiHangHoa(String maLoaiHang, String tenLoaiHang, String mota) {
	/**
     * Constructor để tạo một loại hàng hóa
     * 
     * @param maLoaiHang   Mã Loại Hàng Hóa
     * @param tenLoaiHang   Tên Loại Hàng Hóa
     * @param moTa   Mô tả cho sản phẩm(màu sắc,kích cỡ,...)
     */
	
	this.maLoaiHang = maLoaiHang;
	this.tenLoaiHang = tenLoaiHang;
	this.mota = mota;
}
public LoaiHangHoa() {
	this.maLoaiHang = "";
	this.tenLoaiHang = "";
	this.mota = "";
}
public String getMaLoaiHang() {
	return maLoaiHang;
}
public void setMaLoaiHang(String maLoaiHang) {
	this.maLoaiHang = maLoaiHang;
}
public String getTenLoaiHang() {
	return tenLoaiHang;
}
public void setTenLoaiHang(String tenLoaiHang) {
	this.tenLoaiHang = tenLoaiHang;
}
public String getMota() {
	return mota;
}
public void setMota(String mota) {
	this.mota = mota;
}



   
   
}
