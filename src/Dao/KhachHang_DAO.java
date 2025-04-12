package Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import Model.KhachHang;
import Model.LoaiKhachHang;

public class KhachHang_DAO {
//	public ArrayList<KhachHang> getAllKhachHang(){
//		ArrayList<KhachHang> dsKhachHang = new ArrayList<KhachHang>();
//		try {
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			
//			String sql = "SELECT * FROM KHACHHANG";
//			Statement statement = con.createStatement();
//			//Thực thi câu lệnh SQL trả về đối tượng ResultSet.
//			ResultSet rs = statement.executeQuery(sql);
//			//Duyệt trên kết quả trả về
//			while(rs.next()) {
//				String maKH = rs.getString(0);
//				String tenKH = rs.getString(1);
//				String soDienThoai = rs.getString(2);
//				int diemTL = rs.getInt(3);
//
//			}
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//	}
}
