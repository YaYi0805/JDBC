package tw.iiijdbc.conn.day2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Demo6CallableStatement {
	
    private Connection conn; //conn 提高為全域變數 
	
	//開連線
	public void createConn() throws Exception {
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String urlString = "jdbc:sqlserver://localhost:1433;databaseName=JDBCDemoDB;user=sa;password=404381408";
		//String url2 = "jdbc:sqlserver://LAPTOP-PAB784LD\\SQLEXPRESS:1433;databaseName=JDBCDemoDB;user=sa;password=404381408";
		
		conn = DriverManager.getConnection(urlString);
		boolean status = !conn.isClosed();
		if(status) {
			System.out.println("Connection Opens status: " + status);
		}
	}
	//關連線
	public void closeConn() throws Exception {
		if(conn != null) {
			conn.close();
			System.out.println("Close Connection!");
		}
	}
	
	//呼叫stored procedure
	public void callProcedure() throws SQLException {
		CallableStatement callState = conn.prepareCall("{call productProc(?,?)}"); //用prepareCall()方法取得指定的預存程序,回傳一個CallableStatement
		callState.setInt(1, 1002); //設定輸入參數IN
		callState.registerOutParameter(2, java.sql.Types.VARCHAR); //設定輸出參數OUT,registerOutParameter()方法註冊輸出參數的資料型別
		
		callState.execute();
		String productName = callState.getString(2);
		System.out.println("My productName: " + productName);
		
		callState.close();
	}

	public static void main(String[] args) {
		Demo6CallableStatement demo6 = new Demo6CallableStatement();
		
		try {
			demo6.createConn();
			demo6.callProcedure();			
			
		} catch (Exception e) {
			System.out.println("Something Wrong!");
		}finally {
			try {
				demo6.closeConn();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
