package Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConnectDB.ConnectDB;
import Model.ChiTietHoaDon;
import Model.HoaDonBanHang;

public class HoaDon_DAO {
    public boolean saveOrderWithDetails(HoaDonBanHang hoaDon, String maNhanVien) throws SQLException {
        Connection connection = ConnectDB.getInstance().getConnection();

        // Câu lệnh SQL cho bảng HOADONBANHANG
        String sqlInsertHoaDon = "INSERT INTO HOADONBANHANG (MANV, MAKH, NGAYHDBH, TONGTIEN, DIEMTL, GIAMGIA) VALUES (?, ?, ?, ?, ?, ?)";
        
        // Câu lệnh SQL cho bảng CHITIETHOADON
        String sqlInsertChiTiet = "INSERT INTO CHITIETHOADON (MAHDBH, MAHH, SOLUONG, THANHTIEN) VALUES (?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            // Insert vào bảng HOADONBANHANG mà không cần mã hóa đơn (trigger sẽ tự sinh)
            String maHoaDon = null;
            try (PreparedStatement stmtHoaDon = connection.prepareStatement(sqlInsertHoaDon, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtHoaDon.setString(1, maNhanVien);
                stmtHoaDon.setString(2, hoaDon.getKhachHang().getMaKH());
                stmtHoaDon.setDate(3, Date.valueOf(hoaDon.getNgayLapHDBH()));
                stmtHoaDon.setDouble(4, hoaDon.tinhTongThanhToan());
                stmtHoaDon.setInt(5, hoaDon.getdiemTL());
                stmtHoaDon.setInt(6, hoaDon.getGiamGia().getGiamGia());
                stmtHoaDon.executeUpdate();

                // Lấy mã hóa đơn tự sinh từ SQL
                ResultSet rs = stmtHoaDon.getGeneratedKeys();
                if (rs.next()) {
                    maHoaDon = rs.getString(1);
                }
            }

            // Nếu không lấy được mã hóa đơn, rollback
            if (maHoaDon == null) {
                connection.rollback();
                throw new SQLException("Không lấy được mã hóa đơn từ trigger.");
            }

            // Insert vào bảng CHITIETHOADON
            try (PreparedStatement stmtChiTiet = connection.prepareStatement(sqlInsertChiTiet)) {
                for (ChiTietHoaDon chiTiet : hoaDon.getChiTietHoaDonList()) {
                    stmtChiTiet.setString(1, maHoaDon);
                    stmtChiTiet.setString(2, chiTiet.getHangHoa().getMaHH());
                    stmtChiTiet.setInt(3, chiTiet.getSoLuong());
                    stmtChiTiet.setDouble(4, chiTiet.getThanhTien());
                    stmtChiTiet.addBatch();
                }
                stmtChiTiet.executeBatch();
            }

            // Commit giao dịch
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
