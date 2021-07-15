package tw.iiijdbc.conn.day1;


import java.sql.Connection;
import java.sql.DriverManager;

public class Demo1TestJdbcConnEx1 {

	public static void main(String[] args) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //透過Class.forName載入JDBC Driver驅動程式
			String urlString = "jdbc:sqlserver://localhost:1433;databaseName=JDBCDemoDB;user=sa;password=404381408";
			//String url2 = "jdbc:sqlserver://LAPTOP-PAB784LD\\SQLEXPRESS:1433;databaseName=JDBCDemoDB;user=sa;password=404381408";
			
			Connection conn = DriverManager.getConnection(urlString);
			//方法DriverManager.getConnection()會回傳一個連線物件Connection幫忙傳遞資料庫所使用的SQL語句
			
			boolean status = !conn.isClosed(); //檢查是否有該連線物件, 加!(非)表示打開是true
			System.out.println("Connection Open status: " + status);
			conn.close(); //使用完時立即將連線關閉,因資料庫連線較佔記憶體
			
			
		} catch (Exception e) {
			System.out.println("=== Something Wrong!!! ===");
			e.printStackTrace();
		}

	}

}
