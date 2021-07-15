package tw.iiijdbc.conn.day1;

import java.sql.Connection;
import java.sql.DriverManager;

public class Demo2TryWithResource {

	public static void main(String[] args) {
		
        String urlString = "jdbc:sqlserver://localhost:1433;databaseName=JDBCDemoDB;user=sa;password=404381408";
		
		try(Connection conn = DriverManager.getConnection(urlString)) {
			System.out.println("Connection OK!");
		} catch (Exception e) {
			System.out.println("Connection Fail!");
			e.printStackTrace();
		}

	}

}
