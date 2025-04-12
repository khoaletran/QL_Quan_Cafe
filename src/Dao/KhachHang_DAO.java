package Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Model.KhachHang;
import Model.LoaiKhachHang;

public class KhachHang_DAO {
    public ArrayList<KhachHang> getAllKhachHang() {
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();

        try {
            // Gọi singleton để đảm bảo đã kết nối
            ConnectDB.getInstance().connect(); // không có connect thì không chạy được,để tạo kết nối cơ sở dữ liệu là cần thiết trước khi sử dụng getConnection().
            Connection con = ConnectDB.getConnection();

            // Truy vấn lấy thông tin đầy đủ 2 bảng
            String sql = "SELECT KH.MAKH, KH.TENKH, KH.SDT, KH.DIEMTL, " +
                         "LKH.MALKH, LKH.TENLKH, LKH.GIAMGIA " +
                         "FROM KHACHHANG KH JOIN LOAIKHACHHANG LKH ON KH.MALKH = LKH.MALKH";

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                // Lấy thông tin KH
                String maKH = rs.getString("MAKH");
                String tenKH = rs.getString("TENKH");
                String sdt = rs.getString("SDT");
                int diemTL = rs.getInt("DIEMTL");

                // Lấy thông tin loại KH
                String maLKH = rs.getString("MALKH");
                String tenLKH = rs.getString("TENLKH");
                int giamGia = rs.getInt("GIAMGIA");

                // Tạo đối tượng loại khách hàng
                LoaiKhachHang loaiKH = new LoaiKhachHang();
                loaiKH.setMaLKH(maLKH);
                loaiKH.setTenLKH(tenLKH);
                loaiKH.setGiamGia(giamGia);

                // Tạo đối tượng khách hàng
                KhachHang kh = new KhachHang(maKH, tenKH, sdt, diemTL, loaiKH);

                // Thêm vào danh sách
                dsKhachHang.add(kh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dsKhachHang;
    }
}
