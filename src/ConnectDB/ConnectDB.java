package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	private static Connection con;
	private static ConnectDB instance = new ConnectDB();
	

	public static ConnectDB getInstance() {
		return instance;
	}

	public void connect() throws SQLException {
		if (con == null || con.isClosed()) {
			String url = "jdbc:sqlserver://localhost:1433;databasename=QL_QuanCafe;encrypt=true;trustServerCertificate=true";
			String user = "QLQuanCafe";
			String password = "123";
			con = DriverManager.getConnection(url, user, password);
			System.out.println("✅ Kết nối thành công");
		}
	}


	public void disconnect() {
		if (con != null) {
			try {
				con.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Connection getConnection() {
		return con;
	}
}
