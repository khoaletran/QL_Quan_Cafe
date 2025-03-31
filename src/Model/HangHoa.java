package Model;

public class HangHoa {
   private String maHH;
   private String tenHH;
   private int soLuong;
   private String hinhAnh;
   private double giaSP;
   
public HangHoa(String maHH, String tenHH, int soLuong, String hinhAnh, double giaSP) {
	/**
     * Constructor để tạo một  hàng hóa
     * 
     * @param maHH    Mã Hàng Hóa
     * @param tenHH   Tên Hàng Hóa
     * @param hinhAnh  đường link cho hình ảnh
     * @param giaSP   Giá sản phảm
     */
	
	this.maHH = maHH;
	this.tenHH = tenHH;
	this.soLuong = soLuong;
	this.hinhAnh = hinhAnh;
	this.giaSP = giaSP;
}
public HangHoa() {
	this.maHH = "";
	this.tenHH = "";
	this.soLuong = 0;
	this.hinhAnh = "";
	this.giaSP = 0.0;
}
public String getMaHH() {
	return maHH;
}
public void setMaHH(String maHH) {
	this.maHH = maHH;
}
public String getTenHH() {
	return tenHH;
}
public void setTenHH(String tenHH) {
	this.tenHH = tenHH;
}
public int getSoLuong() {
	return soLuong;
}
public void setSoLuong(int soLuong) {
	this.soLuong = soLuong;
}
public String getHinhAnh() {
	return hinhAnh;
}
public void setHinhAnh(String hinhAnh) {
	this.hinhAnh = hinhAnh;
}
public double getGiaSP() {
	return giaSP;
}
public void setGiaSP(double giaSP) {
	this.giaSP = giaSP;
}
@Override
public String toString() {
	return "HangHoa [maHH=" + maHH + ", tenHH=" + tenHH + ", soLuong=" + soLuong + ", hinhAnh=" + hinhAnh + ", giaSP="
			+ giaSP + "]";
}


}
