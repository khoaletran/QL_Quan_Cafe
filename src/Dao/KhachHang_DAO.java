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
    public static ArrayList<KhachHang> getAllKhachHang() {
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();

        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT KH.MAKH, KH.TENKH, KH.SDT, KH.DIEMTL, " +
                         "LKH.MALKH, LKH.TENLKH, LKH.GIAMGIA " +
                         "FROM KHACHHANG KH JOIN LOAIKHACHHANG LKH ON KH.MALKH = LKH.MALKH "
                         +"ORDER BY KH.MAKH ASC";

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

    public static ArrayList<KhachHang> timKhachHangTheoSDT(String soDienThoai) {
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();

        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT KH.MAKH, KH.TENKH, KH.SDT, KH.DIEMTL, " +
                         "LKH.MALKH, LKH.TENLKH, LKH.GIAMGIA " +
                         "FROM KHACHHANG KH JOIN LOAIKHACHHANG LKH ON KH.MALKH = LKH.MALKH " +
                         "WHERE KH.SDT LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + soDienThoai + "%");

            ResultSet rs = ps.executeQuery();

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
    
    public static KhachHang getKhachHangTheoMaKH(String maKH) {
        KhachHang khachHang = null;

        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT KH.MAKH, KH.TENKH, KH.SDT, KH.DIEMTL, " +
                         "LKH.MALKH, LKH.TENLKH, LKH.GIAMGIA " +
                         "FROM KHACHHANG KH JOIN LOAIKHACHHANG LKH ON KH.MALKH = LKH.MALKH " +
                         "WHERE KH.MAKH = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, maKH);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String maKHResult = rs.getString("MAKH");
                String tenKH = rs.getString("TENKH");
                String sdt = rs.getString("SDT");
                int diemTL = rs.getInt("DIEMTL");

                String maLKH = rs.getString("MALKH");
                String tenLKH = rs.getString("TENLKH");
                int giamGia = rs.getInt("GIAMGIA");

                LoaiKhachHang loaiKH = new LoaiKhachHang(maLKH, tenLKH, giamGia);
                khachHang = new KhachHang(maKHResult, tenKH, sdt, diemTL, loaiKH);
            }

            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return khachHang;
    }
    
    public static KhachHang timKhachHangTheoSDT_DT(String soDienThoai) {
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
    public static boolean themKhachHang(KhachHang kh) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "INSERT INTO KHACHHANG (TENKH, SDT, DIEMTL) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSoDienThoai());
            ps.setInt(3, kh.getDiemTL());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }




    // Xóa khách hàng theo mã
    public static boolean xoaKhachHang(String maKH) throws Exception {
        ConnectDB.getInstance().connect();
        Connection con = ConnectDB.getConnection();

        String sql = "DELETE FROM KHACHHANG WHERE MAKH = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, maKH);
        int rows = ps.executeUpdate();
        return rows > 0;
    }


    // Sửa thông tin khách hàng
    public static boolean suaKhachHang(KhachHang kh) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "UPDATE KHACHHANG SET TENKH = ?, SDT = ?, DIEMTL = ? WHERE MAKH = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSoDienThoai());
            ps.setInt(3, kh.getDiemTL());
//            ps.setString(4, kh.getLoaiKhachHang().getMaLKH());
            ps.setString(4, kh.getMaKH());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
