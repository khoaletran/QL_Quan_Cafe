package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Model.KhachHang;
import Model.LoaiKhachHang;

public class KhachHang_DAO {

    // Lấy tất cả khách hàng
    public ArrayList<KhachHang> getAllKhachHang() {
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();

        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT KH.MAKH, KH.TENKH, KH.SDT, KH.DIEMTL, " +
                         "LKH.MALKH, LKH.TENLKH, LKH.GIAMGIA " +
                         "FROM KHACHHANG KH JOIN LOAIKHACHHANG LKH ON KH.MALKH = LKH.MALKH";

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maKH = rs.getString("MAKH");
                String tenKH = rs.getString("TENKH");
                String sdt = rs.getString("SDT");
                int diemTL = rs.getInt("DIEMTL");

                String maLKH = rs.getString("MALKH");
                String tenLKH = rs.getString("TENLKH");
                int giamGia = rs.getInt("GIAMGIA");

                LoaiKhachHang loaiKH = new LoaiKhachHang(maLKH, tenLKH, giamGia);
                KhachHang kh = new KhachHang(maKH, tenKH, sdt, diemTL, loaiKH);

                dsKhachHang.add(kh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dsKhachHang;
    }
    //Tìm khách hàng theo số điện thoại

    public KhachHang timKhachHangTheoSDT(String soDienThoai) {
        KhachHang kh = null;

        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT KH.MAKH, KH.TENKH, KH.SDT, KH.DIEMTL, " +
                         "LKH.MALKH, LKH.TENLKH, LKH.GIAMGIA " +
                         "FROM KHACHHANG KH JOIN LOAIKHACHHANG LKH ON KH.MALKH = LKH.MALKH " +
                         "WHERE KH.SDT = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, soDienThoai);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String maKH = rs.getString("MAKH");
                String tenKH = rs.getString("TENKH");
                String sdt = rs.getString("SDT");
                int diemTL = rs.getInt("DIEMTL");

                String maLKH = rs.getString("MALKH");
                String tenLKH = rs.getString("TENLKH");
                int giamGia = rs.getInt("GIAMGIA");

                LoaiKhachHang loaiKH = new LoaiKhachHang(maLKH, tenLKH, giamGia);
                kh = new KhachHang(maKH, tenKH, sdt, diemTL, loaiKH);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return kh;
    }

    // Thêm khách hàng (MAKH được sinh tự động bởi TRIGGER trong SQL)
    public boolean themKhachHang(KhachHang kh) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "INSERT INTO KHACHHANG(TENKH, SDT, DIEMTL, MALKH) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSoDienThoai());
            ps.setInt(3, kh.getDiemTL());
            ps.setString(4, kh.getLoaiKhachHang().getMaLKH());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Xóa khách hàng theo mã
    public boolean xoaKhachHang(String maKH) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "DELETE FROM KHACHHANG WHERE MAKH = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, maKH);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Sửa thông tin khách hàng
    public boolean suaKhachHang(KhachHang kh) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "UPDATE KHACHHANG SET TENKH = ?, SDT = ?, DIEMTL = ?, MALKH = ? WHERE MAKH = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSoDienThoai());
            ps.setInt(3, kh.getDiemTL());
            ps.setString(4, kh.getLoaiKhachHang().getMaLKH());
            ps.setString(5, kh.getMaKH());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
