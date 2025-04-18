package Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Model.NhanVien;

public class NhanVien_DAO {
    public static ArrayList<NhanVien> getAllNhanVien() {
        ArrayList<NhanVien> dsNhanVien = new ArrayList<>();

        try {
            // Gọi ConnectDB để thiết lập kết nối
            ConnectDB.getInstance().connect();  // Thiết lập kết nối

            // Lấy kết nối
            Connection con = ConnectDB.getConnection();
            
            // Truy vấn dữ liệu
            String sql = "SELECT * FROM NHANVIEN";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maNV = rs.getString("MANV");
                String tenNV = rs.getString("TENNV");
                String diaChi = rs.getString("DIACHI");
                Date ngayVaoLam = rs.getDate("NGAYVAOLAM");
                boolean gioiTinh = rs.getBoolean("GIOITINH");
                String sdt = rs.getString("SDT");
                String matKhau = rs.getString("MATKHAU");

                // Tạo đối tượng nhân viên và thêm vào danh sách
                NhanVien nv = new NhanVien(maNV, tenNV, diaChi, ngayVaoLam.toLocalDate(), gioiTinh, sdt, matKhau);
                dsNhanVien.add(nv);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return dsNhanVien;
    }
}
