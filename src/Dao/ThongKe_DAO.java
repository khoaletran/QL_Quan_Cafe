package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ThongKe_DAO {

    public static Map<String, Integer> getSoLuongSanPhamBanRa(Connection conn) throws Exception {
        String sql = "SELECT HH.TENHH, SUM(CTHD.SOLUONG) AS SOLUONGBAN " +
                     "FROM HOADONBANHANG HDBH " +
                     "JOIN CHITIETHOADON CTHD ON HDBH.MAHDBH = CTHD.MAHDBH " +
                     "JOIN HANGHOA HH ON CTHD.MAHH = HH.MAHH " +
                     "WHERE HDBH.NGAYHDBH BETWEEN '2020-01-01' AND '2025-12-31' " +
                     "GROUP BY HH.TENHH";

        Map<String, Integer> result = new LinkedHashMap<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("TENHH"), rs.getInt("SOLUONGBAN"));
            }
        }
        return result;
    }

    public static Map<String, Map<String, Integer>> getSoLuongTungLoaiSanPhamTheoNgay(Connection conn, String thang, int nam) throws SQLException {
        Map<String, Map<String, Integer>> result = new HashMap<>();
        String sql = "SELECT DAY(hd.NGAYHDBH) AS Ngay, sp.TENHH, SUM(ct.SoLuong) AS SoLuong " +
                     "FROM HOADONBANHANG hd " +
                     "JOIN CHITIETHOADON ct ON hd.MaHDBH = ct.MaHDBH " +
                     "JOIN HANGHOA sp ON sp.MaHH = ct.MaHH " +
                     "WHERE MONTH(hd.NGAYHDBH) = ? AND YEAR(hd.NGAYHDBH) = ? " +
                     "GROUP BY DAY(hd.NGAYHDBH), sp.TENHH " +
                     "ORDER BY Ngay";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, Integer.parseInt(thang));
        stmt.setInt(2, nam);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int ngay = rs.getInt("Ngay");
            String tenSP = rs.getString("TENHH");
            int soLuong = rs.getInt("SoLuong");

            result.putIfAbsent(tenSP, new HashMap<>());
            result.get(tenSP).put(String.format("%02d", ngay), soLuong);
        }
        return result;
    }

    public static List<String> getDanhSachTenSanPham(Connection conn) throws SQLException {
        List<String> danhSach = new ArrayList<>();
        String sql = "SELECT TENHH FROM HANGHOA";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                danhSach.add(rs.getString("TENHH"));
            }
        }
        return danhSach;
    }

    public static Map<String, Integer> getSoLuongSanPhamTheoThang(Connection conn, int thang, int nam) throws SQLException {
        String sql = "SELECT HH.TENHH, SUM(CTHD.SOLUONG) AS SOLUONGBAN " +
                     "FROM HOADONBANHANG HDBH " +
                     "JOIN CHITIETHOADON CTHD ON HDBH.MAHDBH = CTHD.MAHDBH " +
                     "JOIN HANGHOA HH ON CTHD.MAHH = HH.MAHH " +
                     "WHERE MONTH(HDBH.NGAYHDBH) = ? AND YEAR(HDBH.NGAYHDBH) = ? " +
                     "GROUP BY HH.TENHH";

        Map<String, Integer> result = new LinkedHashMap<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, thang);
            stmt.setInt(2, nam);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("TENHH"), rs.getInt("SOLUONGBAN"));
            }
        }
        return result;
    }

    public static Map<String, Double> getDoanhThuTheoThang(Connection conn, int nam) throws Exception {
        String sql = "SELECT MONTH(NGAYHDBH) AS THANG, SUM(TONGTIEN) AS DOANHTHU " +
                     "FROM HOADONBANHANG " +
                     "WHERE YEAR(NGAYHDBH) = ? " +
                     "GROUP BY MONTH(NGAYHDBH) " +
                     "ORDER BY THANG";

        Map<String, Double> result = new LinkedHashMap<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nam);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.put("Tháng " + rs.getInt("THANG"), rs.getDouble("DOANHTHU"));
            }
        }
        return result;
    }
}