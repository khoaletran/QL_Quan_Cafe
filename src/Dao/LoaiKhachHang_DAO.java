package Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Model.LoaiKhachHang;
import Model.MaGiamGia;

public class LoaiKhachHang_DAO {
	public ArrayList<LoaiKhachHang> getAllMaGiamGia(){
		ArrayList<LoaiKhachHang> dsLoaiKhachHang = new ArrayList<LoaiKhachHang>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			
			String sql = "SELECT * FROM MAGIAMGIA";
			Statement statement = con.createStatement();
			//Thực thi câu lệnh SQL trả về đối tượng ResultSet.
			ResultSet rs = statement.executeQuery(sql);
			//Duyệt trên kết quả trả về
			while(rs.next()) {
				String maGiam = rs.getString("MAGIAM");
				int giamGia = rs.getInt("GIAMGIA");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsLoaiKhachHang;
		
	}
}
