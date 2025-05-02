package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ThongKe_DAO {

    public static Map<String, Integer> getSoLuongSanPhamBanRa(Connection conn) throws Exception {
        String sql = "SELECT HH.TENHH, SUM(CTHD.SOLUONG) AS SOLUONGBAN " +
                     "FROM HOADONBANHANG HDBH " +
                     "JOIN CHITIETHOADON CTHD ON HDBH.MAHDBH = CTHD.MAHDBH " +
                     "JOIN HANGHOA HH ON CTHD.MAHH = HH.MAHH " +
                     "WHERE HDBH.NGAYHDBH BETWEEN '2024-01-01' AND '2025-12-31' " +
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

    // [SỬA] thêm tham số lọc theo tháng
    public static Map<String, Map<String, Integer>> getSoLuongTungLoaiSanPhamTheoNgay(Connection conn, String thang) throws SQLException {
        Map<String, Map<String, Integer>> result = new HashMap<>();

        String sql = "SELECT DAY(hd.NGAYHDBH) AS Ngay, sp.TENHH, SUM(ct.SoLuong) AS SoLuong " +
                     "FROM HOADONBANHANG hd " +
                     "JOIN CHITIETHOADON ct ON hd.MaHDBH = ct.MaHDBH " +
                     "JOIN HANGHOA sp ON sp.MaHH = ct.MaHH " +
                     "WHERE MONTH(hd.NGAYHDBH) = ? AND YEAR(hd.NGAYHDBH) = YEAR(GETDATE()) " +
                     "GROUP BY DAY(hd.NGAYHDBH), sp.TENHH " +
                     "ORDER BY Ngay";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, Integer.parseInt(thang)); // [SỬA] dùng tháng truyền vào
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
    
 // [THÊM] Lấy danh sách tên sản phẩm từ bảng HANGHOA
    public static java.util.List<String> getDanhSachTenSanPham(Connection conn) throws SQLException {
        java.util.List<String> danhSach = new java.util.ArrayList<>();
        String sql = "SELECT TENHH FROM HANGHOA";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                danhSach.add(rs.getString("TENHH"));
            }
        }

        return danhSach;
    }
    
    public static Map<String, Integer> getSoLuongSanPhamTheoThang(Connection conn, int thang) throws SQLException {
        String sql = "SELECT HH.TENHH, SUM(CTHD.SOLUONG) AS SOLUONGBAN " +
                     "FROM HOADONBANHANG HDBH " +
                     "JOIN CHITIETHOADON CTHD ON HDBH.MAHDBH = CTHD.MAHDBH " +
                     "JOIN HANGHOA HH ON CTHD.MAHH = HH.MAHH " +
                     "WHERE MONTH(HDBH.NGAYHDBH) = ? AND YEAR(HDBH.NGAYHDBH) = YEAR(GETDATE()) " +
                     "GROUP BY HH.TENHH";

        Map<String, Integer> result = new LinkedHashMap<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, thang);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.put(rs.getString("TENHH"), rs.getInt("SOLUONGBAN"));
            }
        }
        return result;
    }

    
    public static Map<String, Double> getDoanhThuTheoThang(Connection conn) throws Exception {
        String sql = "SELECT MONTH(NGAYHDBH) AS THANG, SUM(TONGTIEN) AS DOANHTHU " +
                     "FROM HOADONBANHANG HDBH " +
                     "JOIN CHITIETHOADON CTHD ON HDBH.MAHDBH = CTHD.MAHDBH " +
                     "WHERE YEAR(HDBH.NGAYHDBH) = YEAR(GETDATE()) " +

                     "GROUP BY MONTH(NGAYHDBH), TONGTIEN " +
                     "ORDER BY THANG";

        Map<String, Double> result = new LinkedHashMap<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int thang = rs.getInt("THANG");
                double doanhThu = rs.getDouble("DOANHTHU");
                result.put("Tháng " + thang, doanhThu);
            }
        }

        return result;
    }
}
