package Model;

import java.util.ArrayList;

public class DanhSach_HoaDon {
    ArrayList<HoaDonBanHang> listHD;
    
    public DanhSach_HoaDon() {
        this.listHD = new ArrayList<HoaDonBanHang>();
    }
    
    // Phương thức để lấy kích thước danh sách hóa đơn
    public int size() {
        return listHD.size();
    }
    
    // Phương thức để thêm hóa đơn vào danh sách
    public void add(HoaDonBanHang hd) {
        listHD.add(hd);
    }
    
    // Phương thức để lấy danh sách hóa đơn (nếu cần)
    public ArrayList<HoaDonBanHang> getListHD() {
        return new ArrayList<>(listHD); // Trả về một bản sao để bảo vệ dữ liệu gốc
    }
    
    public HoaDonBanHang getHoaDonBanHang(String maHD) {
        for (HoaDonBanHang hd : listHD) {
            if (hd.getMaHDBH().equals(maHD)) {
                return hd;
            }
        }
        return null;
    }
    
    public double tongDoanhThuTatCa() {
        double sum = 0;
        for (HoaDonBanHang hd : listHD) {
            sum += hd.tinhTongThanhToan();
        }
        return sum;
    }
    
    public double tongDoanhThuTheoNgayThang(int ngay, int thang) {
        double sum = 0;
        for (HoaDonBanHang hd : listHD) {
            if (hd.getNgayLapHDBH().getDayOfMonth() == ngay && hd.getNgayLapHDBH().getMonthValue() == thang) {
                sum += hd.tinhTongThanhToan();
            }
        }
        return sum;
    }
}