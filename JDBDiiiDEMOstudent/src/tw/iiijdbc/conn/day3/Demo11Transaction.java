package tw.iiijdbc.conn.day3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import tw.iiijdbc.util.DBConnUtil;

public class Demo11Transaction {

	public static void main(String[] args) throws SQLException {
		
		DBConnUtil util = new DBConnUtil();
		Connection conn = null; //提高Connection位置讓catch也可使用conn物件,設置一開始是空的
		
		try {
			conn = util.createSQLServerConn();
			conn.setAutoCommit(false); //Connection物件下的setAutoCommit()方法並設置為false來啟動隱含交易模式
			
			String sqlStr = "Update product Set remark = ? Where productid = ?";
			PreparedStatement preState = conn.prepareStatement(sqlStr);
			
			preState.setString(1, "因疫情,不在24H保障內" );
			preState.setInt(2, 1002);
			preState.execute();
			
			preState.setString(1, "因疫情,不在24H保障內" );
			preState.setInt(2, 1006);
			preState.execute();
			
			conn.commit();
			System.out.println("Commit OK!");
			conn.setAutoCommit(true); //轉回 Auto-commit 模式
			
			preState.close();
			
		} catch (SQLException e) {
			System.out.println("Something Worng and ROLLBACK!");
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
			System.out.println("Connection Close!");
		}

	}

}
