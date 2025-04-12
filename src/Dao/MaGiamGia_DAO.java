package Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Model.KhachHang;
import Model.MaGiamGia;

public class MaGiamGia_DAO {
	public ArrayList<MaGiamGia> getAllMaGiamGia(){
		ArrayList<MaGiamGia> dsMaGiamGia = new ArrayList<MaGiamGia>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			
			String sql = "SELECT * FROM LOAIKHACHHANG";
			Statement statement = con.createStatement();
			//Thực thi câu lệnh SQL trả về đối tượng ResultSet.
			ResultSet rs = statement.executeQuery(sql);
			//Duyệt trên kết quả trả về
			while(rs.next()) {
				String maLKH = rs.getString("MALKH");
				String tenKH = rs.getString("TENLKH");
				int giamGia = rs.getInt("GIAMGIA");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsMaGiamGia;
		
	}
}
