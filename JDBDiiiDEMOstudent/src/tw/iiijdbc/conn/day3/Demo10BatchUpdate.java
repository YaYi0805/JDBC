package tw.iiijdbc.conn.day3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import tw.iiijdbc.util.DBConnUtil;

public class Demo10BatchUpdate {
	
	public void passwordGenerateBatch() throws SQLException {
		ArrayList<String> users = new ArrayList<String>();
		users.add("Tom");
		users.add("Gina");
		users.add("Mary");
		users.add("Mike");
		
		DBConnUtil util = new DBConnUtil();
		Connection conn = util.createSQLServerConn();
		
		String sqlStr = "Insert into Users(username, userPwd) Values(?,?)";
		PreparedStatement preState = conn.prepareStatement(sqlStr);
		
		for(String aUser : users) {
			preState.setString(1, aUser);
			preState.setString(2, "abcd" + (int)(Math.random()*10000000) + 1 + "efgh");
			preState.addBatch(); //隱含的List
		}
		
		int[] rows = preState.executeBatch(); //executeBatch()方法會回傳一個整數陣列->代表每一筆SQL語法更動的筆數
		System.out.println("SQL counts: " + rows.length); //執行幾次SQL
		preState.close();
		util.closeConn();
	}	

	public static void main(String[] args) {
		
		Demo10BatchUpdate demo10 = new Demo10BatchUpdate();
		
		try {
			demo10.passwordGenerateBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("e.getMessage(): " + e.getMessage());
			System.out.println("e.getErrorCode(): " + e.getErrorCode());
		}

	}

}
