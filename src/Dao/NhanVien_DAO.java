package Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

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
    //Thêm nhân viên
    public boolean themNhanVien(NhanVien nv) {
        try {
            // Thiết lập kết nối
            Connection con = ConnectDB.getConnection();

            // SQL câu lệnh insert
            String sql = "INSERT INTO NHANVIEN (TENNV, DIACHI, NGAYVAOLAM, GIOITINH, SDT, MATKHAU) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            // Tạo Statement và truyền tham số vào câu lệnh
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nv.getTenNV());
            ps.setString(2, nv.getDiaChi());
            ps.setDate(3, Date.valueOf(nv.getNgayVaoLam()));
            ps.setBoolean(4, nv.isGioiTinh());
            ps.setString(5, nv.getSdt());
            ps.setString(6, nv.getMatKhau());

            // Thực hiện câu lệnh insert
            int rowsAffected = ps.executeUpdate();
            
            // Nếu có dòng bị thay đổi (thêm thành công)
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //Xóa nhân viên
    public boolean xoaNhanVien(String maNV) {
        try {
            // Thiết lập kết nối
            Connection con = ConnectDB.getConnection();

            // SQL câu lệnh delete
            String sql = "DELETE FROM NHANVIEN WHERE MANV = ?";

            // Tạo Statement và truyền tham số vào câu lệnh
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maNV);

            // Thực hiện câu lệnh delete
            int rowsAffected = ps.executeUpdate();

            // Nếu có dòng bị thay đổi (xóa thành công)
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //Sửa nhân viên
    public boolean suaNhanVien(NhanVien nv) {
        try {
            // Thiết lập kết nối
            Connection con = ConnectDB.getConnection();

            // SQL câu lệnh update
            String sql = "UPDATE NHANVIEN SET TENNV = ?, DIACHI = ?, NGAYVAOLAM = ?, GIOITINH = ?, SDT = ?, MATKHAU = ? "
                    + "WHERE MANV = ?";

            // Tạo Statement và truyền tham số vào câu lệnh
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nv.getTenNV());
            ps.setString(2, nv.getDiaChi());
            ps.setDate(3, Date.valueOf(nv.getNgayVaoLam()));
            ps.setBoolean(4, nv.isGioiTinh());
            ps.setString(5, nv.getSdt());
            ps.setString(6, nv.getMatKhau());
            ps.setString(7, nv.getMaNV());

            // Thực hiện câu lệnh update
            int rowsAffected = ps.executeUpdate();

            // Nếu có dòng bị thay đổi (cập nhật thành công)
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Tìm nhân viên theo mã nhân viên
    public NhanVien timNhanVienTheoMaNV(String maNV) {
        NhanVien nv = null;

        try {
            // Thiết lập kết nối
            Connection con = ConnectDB.getConnection();

            // SQL câu lệnh select
            String sql = "SELECT * FROM NHANVIEN WHERE MANV = ?";

            // Tạo Statement và truyền tham số vào câu lệnh
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maNV);

            // Thực thi câu lệnh truy vấn
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Lấy thông tin từ ResultSet
                String tenNV = rs.getString("TENNV");
                String diaChi = rs.getString("DIACHI");
                Date ngayVaoLam = rs.getDate("NGAYVAOLAM");
                boolean gioiTinh = rs.getBoolean("GIOITINH");
                String sdt = rs.getString("SDT");
                String matKhau = rs.getString("MATKHAU");

                // Tạo đối tượng nhân viên từ dữ liệu
                nv = new NhanVien(maNV, tenNV, diaChi, ngayVaoLam.toLocalDate(), gioiTinh, sdt, matKhau);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nv;
    }



}
