package Dao;

import ConnectDB.ConnectDB;
import Model.HoaDonBanHang;
import Model.ChiTietHoaDon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;

public class HoaDon_DAO {
	
    public void saveOrderWithDetails(HoaDonBanHang hoaDon, String maNhanVien) throws SQLException {
        // Kiểm tra tham số đầu vào
        if (hoaDon == null) {
            throw new IllegalArgumentException("Hóa đơn không được null");
        }
        if (maNhanVien == null || maNhanVien.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được trống");
        }

        Connection conn = null;
        try {
            conn = ConnectDB.getInstance().getConnection();
            conn.setAutoCommit(false);

            // 1. Lưu hóa đơn chính và lấy mã mới
            String maHDBH = insertHoaDon(conn, hoaDon, maNhanVien);
            
            // 2. Cập nhật mã hóa đơn vào đối tượng
            hoaDon.setMaHDBH(maHDBH);
            
            // 3. Lưu chi tiết hóa đơn nếu có
            List<ChiTietHoaDon> chiTietList = hoaDon.getChiTietHoaDonList();
            if (chiTietList != null && !chiTietList.isEmpty()) {
                insertChiTietHoaDon(conn, maHDBH, chiTietList);
            }
            
            // 4. Cập nhật điểm tích lũy nếu không phải khách lẻ
            if (!"KH0000".equals(hoaDon.getKhachHang().getMaKH())) {
                updateDiemTichLuy(conn, hoaDon);
            }
            
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String insertHoaDon(Connection conn, HoaDonBanHang hoaDon, String maNhanVien) throws SQLException {
        String sql = "INSERT INTO HOADONBANHANG (MANV, MAKH, NGAYHDBH, TONGTIEN, DIEMTL, GIAMGIA, HINHTHUCTHANHTOAN) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, maNhanVien);
            if(hoaDon.getKhachHang() == null)
            {
            	stmt.setNull(2, java.sql.Types.VARCHAR);
            }
            else {
            	stmt.setString(2, hoaDon.getKhachHang().getMaKH());
            }
            
            stmt.setDate(3, java.sql.Date.valueOf(hoaDon.getNgayLapHDBH()));
            stmt.setDouble(4, hoaDon.tinhTongThanhToan()); // Lưu tổng tiền sau giảm giá
            stmt.setInt(5, hoaDon.getdiemTL_THD());
            stmt.setInt(6, (int) hoaDon.getTongGiamGia()); // Lưu tổng giảm giá (%)
            stmt.setBoolean(7, hoaDon.isHinhThucThanhToan());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Tạo hóa đơn thất bại, không có hàng nào được thêm");
            }
            
            return getMaHoaDonVuaThem(conn, maNhanVien, java.sql.Date.valueOf(hoaDon.getNgayLapHDBH()));
        }
    }
    
    private String getMaHoaDonVuaThem(Connection conn, String maNV, java.sql.Date ngayHDBH) throws SQLException {
        String sql = "SELECT TOP 1 maHDBH FROM HOADONBANHANG WHERE MANV = ? AND NGAYHDBH = ? ORDER BY maHDBH DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            stmt.setDate(2, ngayHDBH);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("maHDBH");
                }
                return null;
            }
        }
    }


    private void insertChiTietHoaDon(Connection conn, String maHDBH, List<ChiTietHoaDon> chiTietList) 
            throws SQLException {
        String sql = "INSERT INTO CHITIETHOADON (MAHDBH, MAHH, SOLUONG, THANHTIEN) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (ChiTietHoaDon cthd : chiTietList) {
                if (cthd == null || cthd.getHangHoa() == null) {
                    continue;
                }
                
                stmt.setString(1, maHDBH);
                stmt.setString(2, cthd.getHangHoa().getMaHH());
                stmt.setInt(3, cthd.getSoLuong());
                stmt.setDouble(4, cthd.getThanhTien());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private void updateDiemTichLuy(Connection conn, HoaDonBanHang hoaDon) throws SQLException {
        String sql = "UPDATE KHACHHANG SET DIEMTL = DIEMTL + ? WHERE MAKH = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hoaDon.getdiemTL());
            stmt.setString(2, hoaDon.getKhachHang().getMaKH());
            stmt.executeUpdate();
        }
    }
    
    public String getLatestMaHDBH() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String latestMaHDBH = null;

        try {
            conn = ConnectDB.getConnection();
            if (conn == null) {
                throw new SQLException("Không thể kết nối tới database.");
            }

            // Sửa truy vấn: dùng TOP 1 thay vì LIMIT 1
            String sql = "SELECT TOP 1 maHDBH FROM HOADONBANHANG ORDER BY maHDBH DESC";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                latestMaHDBH = rs.getString("maHDBH");
            }

            return latestMaHDBH;

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}