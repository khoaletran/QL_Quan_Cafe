package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Model.MaGiamGia;

public class MaGiamGia_DAO {

    // Lấy toàn bộ mã giảm giá
    public static ArrayList<MaGiamGia> getAllMaGiamGia() {
        ArrayList<MaGiamGia> dsMaGiamGia = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM MAGIAMGIA";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maGiam = rs.getString("MAGIAM");
                int giamGia = rs.getInt("GIAMGIA");
                MaGiamGia mg = new MaGiamGia(maGiam, giamGia);
                dsMaGiamGia.add(mg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsMaGiamGia;
    }
    // Tìm mã giảm giá theo mã
    public MaGiamGia timMaGiamGia(String mg1) {
        MaGiamGia mg = null;

        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM MAGIAMGIA WHERE MAGIAM = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, mg1);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int giamGia = rs.getInt("GIAMGIA");
                mg = new MaGiamGia(mg1, giamGia);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mg;
    }
    // Thêm mã giảm giá
    public boolean themMaGiamGia(MaGiamGia mg2) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "INSERT INTO MAGIAMGIA (MAGIAM, GIAMGIA) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, mg2.getMaGiam());
            ps.setInt(2, mg2.getGiamGia());

            int rows = ps.executeUpdate();
            return rows > 0;
            // nếu có dòng nào được thêm thì trả về true, k có thì trả về false

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xoá mã giảm giá
    public boolean xoaMaGiamGia(String mg3) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "DELETE FROM MAGIAMGIA WHERE MAGIAM = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, mg3);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Sửa mã giảm giá
    public boolean suaMaGiamGia(MaGiamGia mg4) {
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();

            String sql = "UPDATE MAGIAMGIA SET GIAMGIA = ? WHERE MAGIAM = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, mg4.getGiamGia());
            ps.setString(2, mg4.getMaGiam());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

   
}
