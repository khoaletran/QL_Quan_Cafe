package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Model.HangHoa;

public class HangHoa_DAO {

    // Lấy toàn bộ danh sách hàng hóa
    public static ArrayList<HangHoa> getAllHangHoa() {
        ArrayList<HangHoa> dshh = new ArrayList<HangHoa>();
        ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT MAHH, TENHH, HINHANH, GIASP FROM HANGHOA";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String maHH = rs.getString("MAHH");
                String tenHH = rs.getString("TENHH");
                String hinhAnh = rs.getString("HINHANH");
                double giaSP = rs.getDouble("GIASP");

                HangHoa hh = new HangHoa(maHH, tenHH, hinhAnh, giaSP);
                dshh.add(hh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dshh;
    }

    // Thêm hàng hóa mới
    public boolean themHangHoa(HangHoa hh) {
        ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
        String sql = "INSERT INTO HANGHOA (MAHH, TENHH, HINHANH, GIASP) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, hh.getMaHH());
            stmt.setString(2, hh.getTenHH());
            stmt.setString(3, hh.getHinhAnh());
            stmt.setDouble(4, hh.getGiaSP());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa hàng hóa theo mã
    public boolean xoaHangHoa(String maHH) {
        ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
        String sql = "DELETE FROM HANGHOA WHERE MAHH = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maHH);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin hàng hóa
    public boolean suaHangHoa(HangHoa hh) {
        ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
        String sql = "UPDATE HANGHOA SET TENHH = ?, HINHANH = ?, GIASP = ? WHERE MAHH = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, hh.getTenHH());
            stmt.setString(2, hh.getHinhAnh());
            stmt.setDouble(3, hh.getGiaSP());
            stmt.setString(5, hh.getMaHH());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
