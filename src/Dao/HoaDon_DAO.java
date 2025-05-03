package Dao;

import ConnectDB.ConnectDB;
import Model.HoaDonBanHang;
import Model.KhachHang;
import Model.MaGiamGia;
import Model.ChiTietHoaDon;
import Model.HangHoa;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.time.LocalDate;

public class HoaDon_DAO {
	
	public ArrayList<HoaDonBanHang> getAllHoaDon() {
        ArrayList<HoaDonBanHang> dsHoaDon = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            ConnectDB.getInstance().connect();
            con = ConnectDB.getConnection();
            if (con == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu");
                return dsHoaDon;
            }
            String sql = "select MAHDBH,MANV,MAKH,NGAYHDBH,TONGTIEN,DIEMTL,GIAMGIA,HINHTHUCTHANHTOAN FROM HOADONBANHANG";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
            	String maHDBH = rs.getString("MAHDBH");
            	String maNV = rs.getString("MANV");
            	String maKH = rs.getString("MAKH");
                LocalDate ngayLap = rs.getDate("NGAYHDBH").toLocalDate();
                double tongTien = rs.getDouble("TONGTIEN");
                int diemTL = rs.getInt("DIEMTL");
                int giamGia = rs.getInt("GIAMGIA");
                boolean hinhThucTT = rs.getBoolean("HINHTHUCTHANHTOAN");
                HoaDonBanHang hoaDon = new HoaDonBanHang(maHDBH, ngayLap, diemTL, hinhThucTT, maKH, maNV, giamGia, tongTien);
                dsHoaDon.add(hoaDon);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn hóa đơn: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsHoaDon;
    }
	
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
        String sql = "INSERT INTO HOADONBANHANG (MANV, MAKH, NGAYHDBH, TONGTIEN, DIEMTL, MAGIAM, GIAMGIA, HINHTHUCTHANHTOAN) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
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
            stmt.setDouble(4, hoaDon.tinhTongThanhToan());
            stmt.setInt(5, hoaDon.getdiemTL_THD());
            if (hoaDon.getGiamGia() == null) {
                stmt.setNull(6, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(6, hoaDon.getGiamGia().getMaGiam());
            }
            stmt.setInt(7, (int) hoaDon.getTongGiamGia());
            stmt.setBoolean(8, hoaDon.isHinhThucThanhToan());
            
            System.out.println(hoaDon.toString());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Tạo hóa đơn thất bại, không có hàng nào được thêm");
            }
            
            return getMaHoaDonVuaThem(conn, maNhanVien, java.sql.Date.valueOf(hoaDon.getNgayLapHDBH()));
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
    
    public HoaDonBanHang getHoaDonTheoMa(String maHDBH) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        HoaDonBanHang hoaDon = null;

        try {
            ConnectDB.getInstance().connect();
            conn = ConnectDB.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu");
                return null;
            }

            String sql = "SELECT MAHDBH, MANV, MAKH, NGAYHDBH, TONGTIEN, DIEMTL, GIAMGIA, HINHTHUCTHANHTOAN " +
                         "FROM HOADONBANHANG WHERE MAHDBH = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, maHDBH);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String maHD = rs.getString("MAHDBH");
                String maNV = rs.getString("MANV");
                String maKH = rs.getString("MAKH");
                LocalDate ngayLap = rs.getDate("NGAYHDBH").toLocalDate();
                double tongTien = rs.getDouble("TONGTIEN");
                int diemTL = rs.getInt("DIEMTL");
                int giamGia = rs.getInt("GIAMGIA");
                boolean hinhThucTT = rs.getBoolean("HINHTHUCTHANHTOAN");

                hoaDon = new HoaDonBanHang(maHD, ngayLap, diemTL, hinhThucTT, maKH, maNV, giamGia, tongTien);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn hóa đơn theo mã: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return hoaDon;
    }
    
    public static List<HoaDonBanHang> getDSHoaDonTheoMa(String maHDBH) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        HoaDonBanHang hoaDon = null;
        
        List<HoaDonBanHang> list = new ArrayList<HoaDonBanHang>();
        try {
            ConnectDB.getInstance().connect();
            conn = ConnectDB.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu");
                return null;
            }

            String sql = "SELECT MAHDBH, MANV, MAKH, NGAYHDBH, TONGTIEN, DIEMTL, GIAMGIA, HINHTHUCTHANHTOAN " +
                         "FROM HOADONBANHANG WHERE MAHDBH LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + maHDBH + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String maHD = rs.getString("MAHDBH");
                String maNV = rs.getString("MANV");
                String maKH = rs.getString("MAKH");
                LocalDate ngayLap = rs.getDate("NGAYHDBH").toLocalDate();
                double tongTien = rs.getDouble("TONGTIEN");
                int diemTL = rs.getInt("DIEMTL");
                int giamGia = rs.getInt("GIAMGIA");
                boolean hinhThucTT = rs.getBoolean("HINHTHUCTHANHTOAN");

                hoaDon = new HoaDonBanHang(maHD, ngayLap, diemTL, hinhThucTT, maKH, maNV, giamGia, tongTien);
                list.add(hoaDon);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn hóa đơn theo mã: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
    public static List<HoaDonBanHang> getDSHoaDonTheoSDT(String sdt) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<HoaDonBanHang> list = new ArrayList<>();

        try {
            ConnectDB.getInstance().connect();
            conn = ConnectDB.getConnection();
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu");
                return null;
            }

            String sql = "SELECT hd.MAHDBH, hd.MANV, hd.MAKH, hd.NGAYHDBH, hd.TONGTIEN, hd.DIEMTL, hd.GIAMGIA, hd.HINHTHUCTHANHTOAN " +
                         "FROM HOADONBANHANG hd JOIN KHACHHANG kh ON hd.MAKH = kh.MAKH " +
                         "WHERE kh.SDT LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + sdt + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String maHD = rs.getString("MAHDBH");
                String maNV = rs.getString("MANV");
                String maKH = rs.getString("MAKH");
                LocalDate ngayLap = rs.getDate("NGAYHDBH").toLocalDate();
                double tongTien = rs.getDouble("TONGTIEN");
                int diemTL = rs.getInt("DIEMTL");
                int giamGia = rs.getInt("GIAMGIA");
                boolean hinhThucTT = rs.getBoolean("HINHTHUCTHANHTOAN");

                HoaDonBanHang hoaDon = new HoaDonBanHang(maHD, ngayLap, diemTL, hinhThucTT, maKH, maNV, giamGia, tongTien);
                list.add(hoaDon);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn hóa đơn theo SDT: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    
    public List<ChiTietHoaDon> getChiTietSanPhamTheoMaHD(String maHDBH) {
        List<ChiTietHoaDon> chiTietList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            ConnectDB.getInstance().connect();
            conn = ConnectDB.getConnection();
            if (conn == null) {
                throw new SQLException("Không thể kết nối tới cơ sở dữ liệu");
            }

            String sql = "SELECT ct.SOLUONG, ct.THANHTIEN, hh.GIASP, hh.TENHH, hh.MAHH " +
                         "FROM CHITIETHOADON ct " +
                         "JOIN HANGHOA hh ON ct.MAHH = hh.MAHH " +
                         "WHERE ct.MAHDBH = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, maHDBH);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int soLuong = rs.getInt("SOLUONG");
                double thanhTien = rs.getDouble("THANHTIEN");
                double giaSanPham = rs.getDouble("GIASP");
                String tenHangHoa = rs.getString("TENHH");
                String maHangHoa = rs.getString("MAHH");

                HangHoa hangHoa = new HangHoa(maHangHoa, tenHangHoa, null, giaSanPham);
                ChiTietHoaDon chiTiet = new ChiTietHoaDon(soLuong, hangHoa);
                chiTietList.add(chiTiet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy chi tiết sản phẩm: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return chiTietList;
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