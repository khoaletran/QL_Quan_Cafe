package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Model.LoaiHangHoa;
public class LoaiHangHoa_DAO {
	public LoaiHangHoa_DAO() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<LoaiHangHoa> getAllLoaiHangHoa(){
		ArrayList<LoaiHangHoa> dslhh = new ArrayList<LoaiHangHoa>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from LOAIHANGHOA";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				String maLH = rs.getString(1);
				if(maLH==null) continue;
				String tenLH = rs.getString(2);
				String moTa = rs.getString(3);
				
				LoaiHangHoa lhh = new LoaiHangHoa(maLH, tenLH, moTa);
				dslhh.add(lhh);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dslhh;
	}
	public String getMaLoaiHangByTen(String tenLoaiHang) {
		ConnectDB.getInstance();
		String maLH = null;
		try {
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT MALH FROM LOAIHANGHOA WHERE TENLH = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, tenLoaiHang);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				maLH = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maLH;
	}
	
}
