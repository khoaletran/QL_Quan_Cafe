package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import ConnectDB.ConnectDB;

public class ThongKe_DAO {

    public static Map<String, Integer> getSoLuongSanPhamBanRa(Connection conn) throws Exception {
    	ConnectDB.getInstance().connect();
        Connection con = ConnectDB.getConnection();
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
}
