package tw.iiijdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
	
	private Connection conn;
	
	//開連線,換丟出錯誤的寫法
	public Connection createSQLServerConn() throws SQLException {
		
		String urlStr = "jdbc:sqlserver://localhost:1433;databaseName=JDBCDemoDB;user=sa;password=404381408";
		
		conn = DriverManager.getConnection(urlStr);
		return conn;
	}
	
	
	//關連線,換丟出錯誤的寫法
	public void closeConn() throws SQLException {
		if(conn != null) {
			conn.close();
		}
	}
	
	
}
