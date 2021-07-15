package tw.iiijdbc.conn.day2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import tw.iiijdbc.util.DBConnUtil;

public class Demo8MetaData {

	private DBConnUtil dbUtil = new DBConnUtil();
	
	private Connection conn;
	
	public void getConn() {
		
		try {
			conn = dbUtil.createSQLServerConn();
			System.out.println("Connection Success!!");
			
		}catch(SQLException e) {
			System.out.println("=== Connection Fail ===");
			e.printStackTrace();
		}
	}	
	
	public void closeConn() {
		try {
			dbUtil.closeConn();
			System.out.println("Close Connect Finished!");
		} catch (SQLException e) {
			System.out.println("=== Close Connection Fail ===");
			e.printStackTrace();
		}
	}
	
	//DatabaseMetaDatay 取得資料庫系統資訊
	public void getDbMetaData() throws SQLException {
		DatabaseMetaData metaData = conn.getMetaData();
		
		StringBuilder sb  = new StringBuilder();
		
		sb.append("資料庫名稱: " + metaData.getDatabaseProductName());
		sb.append("\n資料庫版本: " + metaData.getDatabaseProductVersion());
		sb.append("\n驅動程式名稱: " + metaData.getDriverName());
		sb.append("\n驅動版本: " + metaData.getDriverVersion());
		sb.append("\nDBMS 的 URL: " + metaData.getURL()); //DataBase Management System
		sb.append("\n使用者名稱: " + metaData.getUserName());
		
		
		System.out.println(sb.toString());
	}
	
	//ResultSetMetaData 資料表
	public void getTableMetaData() throws SQLException {
		String sqlStr = "Select * From Product";
		PreparedStatement preState = conn.prepareStatement(sqlStr);
		
		ResultSet rs = preState.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData(); //用ResultSet中的getMetaData方法取得ResultSetMetaData物件
		
		StringBuilder sb = new StringBuilder();
		sb.append("ColumnCount: " + metaData.getColumnCount()); //欄位總數int
		sb.append("\nCloumnName(1): " + metaData.getColumnName(1)); //欄位名稱String, SQL Server若是別名會回傳別名
		sb.append("\nCloumnLabel(1): " + metaData.getColumnLabel(1)); //欄位別名String, 若無別名會回傳欄位名稱
		sb.append("\nCloumnName(2): " + metaData.getColumnName(2)); 
		sb.append("\nCloumnType(1): " + metaData.getColumnType(1)); 
		sb.append("\nCloumnTypeName(1): " + metaData.getColumnTypeName(1)); //欄位資料型別String
		sb.append("\nCloumnDisplaySize(1): " + metaData.getColumnDisplaySize(1)); //欄位有效長度int
		
		System.out.println(sb.toString());
		
		rs.close();
		preState.close();
	}
	

	public static void main(String[] args) throws SQLException {

		Demo8MetaData demo8 = new Demo8MetaData();
		
		demo8.getConn();
		//demo8.getDbMetaData();
		demo8.getTableMetaData();
		
		demo8.closeConn();
	}


}
