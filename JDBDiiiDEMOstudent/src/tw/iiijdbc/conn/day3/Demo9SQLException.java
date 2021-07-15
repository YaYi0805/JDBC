package tw.iiijdbc.conn.day3;

import java.sql.Connection;
import java.sql.SQLException;

import tw.iiijdbc.util.DBConnUtil;

public class Demo9SQLException {
	
	public static void main(String[] args) {
		DBConnUtil util = new DBConnUtil();
		try {
			Connection conn = util.createSQLServerConn();
			System.out.println("Connection Success!");
			conn.close();
		} catch (SQLException e) {
			System.out.println("e.getMessage()" + e.getMessage());
			System.out.println("e.getErrorCode()" + e.getErrorCode());
		}

	}

}
