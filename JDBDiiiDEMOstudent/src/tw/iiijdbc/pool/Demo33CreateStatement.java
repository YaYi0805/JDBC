package tw.iiijdbc.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariDataSource;

public class Demo33CreateStatement {
	
	private Connection conn; //conn 提高為全域變數 
	
	private HikariUtil connPool = new HikariUtil();
	
	//開連線
	public void createConn() throws Exception {
		
		//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//String urlString = "jdbc:sqlserver://localhost:1433;databaseName=JDBCDemoDB;user=sa;password=404381408";
		//String url2 = "jdbc:sqlserver://LAPTOP-PAB784LD\\SQLEXPRESS:1433;databaseName=JDBCDemoDB;user=sa;password=404381408";
		
		//conn = DriverManager.getConnection(urlString);
		
		HikariDataSource ds = connPool.openDataSource();
		conn = ds.getConnection();
		
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
			connPool.closeDataSource(); //關閉連線池
			System.out.println("Close HikariDataSource!");
		}
	}	
	
	//查找有無資料
	public void queryDB1() throws SQLException {
		String sqlStr = "Select * from Product"; //sql指令字串
		Statement stamt = conn.createStatement(); //透過連線物件Connection的createStatement()方法產生Statement物件
		ResultSet rs = stamt.executeQuery(sqlStr); //執行sql語句, executeQuery()方法使用在查詢時,會回傳一個結果集ResultSet
		System.out.println("Data Result: " + rs.next());
		rs.close();
		stamt.close();
	}
	
	//查找全部資料
	public void queryDB2() throws SQLException {
		String sqlStr = "Select * from Product";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlStr);
		
		while(rs.next()) { //ResultSet物件裝有查詢結果,透過next()方法資料列指標cursor
			System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));
			System.out.println("===================================");
		}
		rs.close();
		stmt.close();		
	}
	
	//更新資料
	public void updateData() throws SQLException {
		String sqlStr = " Update Product Set productprice = 60 where productname = 'mask' ";
		Statement state = conn.createStatement();
		int row = state.executeUpdate(sqlStr); //executeUpdate()方法使用在刪除,新增,修改,DDL(建立刪除表格一類),會回傳int整數型態值表示已成功幾筆
		System.out.println("Success Update"+row+"row");
		state.close();
	}
	
	public void updateDataHW() throws SQLException {
		String sqlStr = " Update Product Set productprice = 15 where productid = 1001 ";
		Statement state = conn.createStatement();
		int row = state.executeUpdate(sqlStr);
		System.out.println("Update Product Success :"+row+"row");
		state.close();
	}
		
	//新增一筆資料
	public void insertData() throws SQLException {
		String sqlStr = "Insert into Product(productid, productname, productprice, productdate, remark)"+
	                    "Values(1010, 'water', 50, '2021-05-31', '一瓶')";
		Statement state = conn.createStatement();
		boolean boo = state.execute(sqlStr);
		System.out.println("Insert Product Success ");
		state.close();
	}
	
	public void insertDataHW() throws SQLException {
		String sqlStr = "Insert into Product(productid, productname, productprice, productdate, remark)"+
				"Values(1006, 'Mac mini', 20000, '2021-05-31', '一台')";
		Statement state = conn.createStatement();
		boolean boo = state.execute(sqlStr);
		System.out.println("Insert Product Success ");
		state.close();
	}
	
	//刪除一筆資料
	public void deleteOneData() throws SQLException {
		String sqlStr = "Delete Product Where productid = 1001";
		Statement state = conn.createStatement();
		int row = state.executeUpdate(sqlStr);
		System.out.println("Success Delete "+row+" Data Finsh");
		state.close();
	}
	
	public void deleteOneDataHW() throws SQLException {
		String sqlStr = "Delete Product Where productname = 'mask'";
		Statement state = conn.createStatement();
		int row = state.executeUpdate(sqlStr);
		System.out.println("Success Product Delete");
		state.close();
	}

	
	
    //主程式
	public static void main(String[] args) {
	    Demo33CreateStatement demo3 = new Demo33CreateStatement();
	    
	    try {
	    	demo3.createConn();
	    	//demo3.queryDB1();
	    	demo3.queryDB2();
	    	//demo3.updateData();
	    	//demo3.insertDataHW();
	    	//demo3.deleteOneData();
	    	//demo3.updateDataHW();
	    	//demo3.deleteOneDataHW();
	    	
	    }catch(Exception e) {
	    	System.out.println("Something Wrong!");
	    	e.printStackTrace();
	    }finally {
	    	try {
		    	demo3.closeConn();
		    }catch(Exception e) {
		    	e.printStackTrace();
		    }
		
	    }
	}

}
