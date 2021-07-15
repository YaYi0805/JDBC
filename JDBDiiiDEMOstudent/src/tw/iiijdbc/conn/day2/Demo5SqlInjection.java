package tw.iiijdbc.conn.day2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo5SqlInjection {
	
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
	
	
	//用createStatement登入
	public boolean checkLogin1(String userName, String userPwd) throws SQLException {
		String sqlStr = "  Select * from Users Where username='" + userName + "' and userPwd='" + userPwd + "'  ";
		
		System.out.println("sqlStr = " + sqlStr);
		//漏洞:登入成功, sqlStr =   Select * from Users Where username='' or 1=1 --' and userPwd='111' 
		Statement creState = conn.createStatement();
		ResultSet rs = creState.executeQuery(sqlStr);
		boolean checkOK = rs.next();
		
		rs.close();
		creState.close();
		
		return checkOK;	
	}
	
	//用 prepareStatement 避免 checkLogin1 的情況
	public boolean checkLogin2(String userName2, String userPwd2) throws SQLException {
		String sqlStr2 = "Select * from Users Where username=? and userPwd=?";
		System.out.println("sqlStr = " + sqlStr2);
		PreparedStatement preState =  conn.prepareStatement(sqlStr2);
		preState.setString(1, userName2);
		preState.setString(2, userPwd2);
		ResultSet rs = preState.executeQuery();
		boolean checkOK2 = rs.next();
		
		rs.close();
		preState.close();
		
		return checkOK2;	
	}

	public static void main(String[] args) {
		Demo5SqlInjection demo5 = new Demo5SqlInjection();
		
		String loginUser = "' or 1=1 --";
		String loginPwd = "111";
		//登入成功: sqlStr =   Select * from Users Where username='' or 1=1 --' and userPwd='111' 
		
		String loginUser2 = "' or 1=1 --";
		String loginPwd2 = "qwert";
		//帳號密碼錯誤，登入失敗: sqlStr = Select * from Users Where username=? and userPwd=?
		
		try {
			
			demo5.createConn();
			//boolean result = demo5.checkLogin1(loginUser, loginPwd);
			boolean result = demo5.checkLogin2(loginUser2, loginPwd2);
			
			
			if (result) {
				System.out.println("登入成功");
			}else {
				System.out.println("帳號密碼錯誤，登入失敗");
			}
			
		} catch (Exception e) {
			System.out.println("Something Wrong!");
			e.printStackTrace();
		}finally {
			try {
				demo5.closeConn();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

	}

}
