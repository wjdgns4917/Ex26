package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBcon {
	
	static Connection con;
	
	private DBcon() throws Exception {
		// 1. 드라이버로딩
				Class.forName("oracle.jdbc.driver.OracleDriver");
				String url = "jdbc:oracle:thin:@localhost:1521:orcl";
				String user = "video";
				String pass = "123456";

				// 2. Connection 연결객체 얻어오기
				con = DriverManager.getConnection(url, user, pass);
				System.out.println("성공");

	}
	
	public static Connection getConnection() throws Exception{
		if (con==null) {
			new DBcon();
		}
		return con;
	}
}
