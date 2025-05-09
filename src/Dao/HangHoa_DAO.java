package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.ConnectDB;
import Model.HangHoa;
import Model.LoaiHangHoa;

public class HangHoa_DAO {

	// Lấy toàn bộ danh sách hàng hóa
	public static ArrayList<HangHoa> getAllHangHoa() {
		ArrayList<HangHoa> dshh = new ArrayList<HangHoa>();
		ConnectDB.getInstance();

		try (Connection con = ConnectDB.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT MAHH, TENHH, HINHANH, GIASP FROM HANGHOA")) {

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

	public ArrayList<HangHoa> getAllHangHoaForSanPhamPanel() {
		ArrayList<HangHoa> dshh = new ArrayList<HangHoa>();
		ConnectDB.getInstance();
		Statement statement = null;
		ResultSet rs = null;
		try {
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT HH.MAHH, HH.TENHH, HH.HINHANH, HH.GIASP, " + "LH.MALH, LH.TENLH, LH.MOTA "
					+ "FROM HANGHOA HH JOIN LOAIHANGHOA LH ON HH.MALH = LH.MALH";

			statement = con.createStatement();
			rs = statement.executeQuery(sql);

			while (rs.next()) {
				String maHH = rs.getString("MAHH");
				String tenHH = rs.getString("TENHH");
				String hinhAnh = rs.getString("HINHANH");
				double giaSP = rs.getDouble("GIASP");

				String maLH = rs.getString("MALH");
				String tenLH = rs.getString("TENLH");
				String moTa = rs.getString("MOTA");

				LoaiHangHoa loaiHH = new LoaiHangHoa(maLH, tenLH, moTa);
				HangHoa hh = new HangHoa(maHH, tenHH, hinhAnh, giaSP, loaiHH);

				dshh.add(hh);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dshh;
	}

	public String getHinhAnhByMa(String maHH) {
		ConnectDB.getInstance();
		String hinhanh = null; // Khai báo ở đây
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Connection con = ConnectDB.getConnection();
			String sql = "select HINHANH from HANGHOA where MAHH = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maHH);
			rs = stmt.executeQuery();
			while (rs.next()) {
				hinhanh = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return hinhanh;
	}


	// Thêm hàng hóa mới
	public boolean themHangHoa(HangHoa hh) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement statement = null;
	    int n = 0;
	    try {
	        String sql = "INSERT INTO HANGHOA (MALH, TENHH, HINHANH, GIASP) VALUES (?, ?, ?, ?)";
	        statement = con.prepareStatement(sql);
	        statement.setString(1, hh.getLoaiHangHoa().getMaLoaiHang());
	        statement.setString(2, hh.getTenHH());
	        statement.setString(3, hh.getHinhAnh().trim());
	        statement.setDouble(4, hh.getGiaSP());

	        n = statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
			try {
				if(statement!=null) statement.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	    return n > 0;
	}


	// Xóa hàng hóa theo mã
	public boolean xoaHangHoa(String maHH) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		String sql = "DELETE FROM HANGHOA WHERE MAHH = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maHH);

			int rowsDeleted = stmt.executeUpdate();
			return rowsDeleted > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt!=null) stmt.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return false;
	}

	// Cập nhật thông tin hàng hóa
	public boolean capNhatHangHoa(HangHoa hh) {
	    ConnectDB.getInstance();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    int n = 0;
	    try {
	        String sql = "UPDATE HANGHOA SET TENHH = ?, HINHANH = ?, GIASP = ?, MALH = ? WHERE MAHH = ?";
	        stmt = con.prepareStatement(sql);
	        stmt.setString(1, hh.getTenHH());
	        stmt.setString(2, hh.getHinhAnh());
	        stmt.setDouble(3, hh.getGiaSP());
	        stmt.setString(4, hh.getLoaiHangHoa().getMaLoaiHang());
	        stmt.setString(5, hh.getMaHH());

	        n = stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
			try {
				if(stmt!=null) stmt.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	    return n > 0;
	}
	
	public static HangHoa timHangHoaTheoTen(String ten){

			HangHoa hh = null;
		    try {
		        ConnectDB.getInstance().connect();
		        Connection con = ConnectDB.getConnection();

		        String sql = "SELECT HH.MAHH, HH.MALH, HH.TENHH, HH.HINHANH, HH.GIASP, " +
		                     "LH.TENLH, LH.MOTA " +
		                     "FROM HANGHOA HH JOIN LOAIHANGHOA LH ON HH.MALH = LH.MALH " +
		                     "WHERE HH.TENHH = ?";

		        PreparedStatement ps = con.prepareStatement(sql);
		        ps.setString(1,ten);

		        ResultSet rs = ps.executeQuery();

		        while (rs.next()) {
		            String maHH = rs.getString("MAHH");
		            String tenHH = rs.getString("TENHH");
		            String hinhAnh = rs.getString("HINHANH");
		            double giaSP = rs.getDouble("GIASP");

		            String maLH = rs.getString("MALH");
		            String tenLH = rs.getString("TENLH");
		            String moTa = rs.getString("MOTA");

		            LoaiHangHoa loai = new LoaiHangHoa(maLH, tenLH, moTa);
		            hh = new HangHoa(maHH, tenHH, hinhAnh, giaSP, loai);
		        }

		    } catch (Exception e) {
		        e.printStackTrace(); // Có thể thay bằng log nếu cần
		    }

		    return hh;
		}

	
	
	public List<HangHoa> timKiemHangHoa(String tuKhoa) {
	    List<HangHoa> list = new ArrayList<>();

	    try {
	        ConnectDB.getInstance().connect();
	        Connection con = ConnectDB.getConnection();

	        String sql = "SELECT HH.MAHH, HH.MALH, HH.TENHH, HH.HINHANH, HH.GIASP, " +
	                     "LH.TENLH, LH.MOTA " +
	                     "FROM HANGHOA HH JOIN LOAIHANGHOA LH ON HH.MALH = LH.MALH " +
	                     "WHERE HH.TENHH COLLATE Vietnamese_CI_AI LIKE ?";

	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, "%" + tuKhoa + "%");

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            String maHH = rs.getString("MAHH");
	            String tenHH = rs.getString("TENHH");
	            String hinhAnh = rs.getString("HINHANH");
	            double giaSP = rs.getDouble("GIASP");

	            String maLH = rs.getString("MALH");
	            String tenLH = rs.getString("TENLH");
	            String moTa = rs.getString("MOTA");

	            LoaiHangHoa loai = new LoaiHangHoa(maLH, tenLH, moTa);
	            HangHoa hh = new HangHoa(maHH, tenHH, hinhAnh, giaSP, loai);
	            list.add(hh);
	        }

	    } catch (Exception e) {
	        e.printStackTrace(); // Có thể thay bằng log nếu cần
	    }

	    return list;
	}


}
