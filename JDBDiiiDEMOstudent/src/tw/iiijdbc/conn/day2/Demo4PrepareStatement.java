package tw.iiijdbc.conn.day2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Demo4PrepareStatement {
	
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
		
	//新增表格Create table
	public void createTable() {
		String sqlstr = "Create table profiles(id int primary key not null identity(101,1), name varchar(30) not null," 
	                     + "address varchar(50) not null, phone varchar(20))";
		try {
			//PreparedStatement繼承Statement的介面,除可處理靜態sql指令外,亦可處理"動態"sql指令
			PreparedStatement preState = conn.prepareStatement(sqlstr); 
			preState.execute(); ////PreparedStatement執行sql
			preState.close();
			System.out.println("Create table Success!");
		}catch(Exception e) {
			System.out.println("Create table Fail!");
			e.printStackTrace();
		}
	}
		
	//新增一筆資料,用PreparedStatement
	public void insertData() {
		String sqlStr = "Insert Into profiles(name, address, phone) Values(?,?,?)"; 
		//動態sql指令, id如上identity(101,1)自動產生,故此只需實作3個參數
		try {
			PreparedStatement preState = conn.prepareStatement(sqlStr);
			preState.setString(1, "Jason"); //設置第一個?
			preState.setString(2, "America"); //設置第二個?
			preState.setString(3, "000-999-123-222"); //設置第三個?
			
			preState.executeUpdate(); //PreparedStatement執行sql
			preState.close();
			System.out.println("Insert Data Success!");
			
		} catch (SQLException e) {
			System.out.println("Insert Data Fail!");
			e.printStackTrace();
		}
	}
	
	//修改資料,用PreparedStatement
	public void updateData() throws SQLException {
		String sqlStr = "Update profiles Set phone = ? where name=? and address=?";
		PreparedStatement preState =conn.prepareStatement(sqlStr);
		preState.setString(1, "555-555-555");
		preState.setString(2, "Apple");
		preState.setString(3, "Japan");
		
		int row = preState.executeUpdate();
		System.out.println("Update profile count: " + row );
		preState.close();	
	}
	
	//有條件尋找資料
	public void queryByAddress(String location) throws SQLException {
		String sqlStr = "Select * from profiles where address=?";
		PreparedStatement preState = conn.prepareStatement(sqlStr);
		preState.setString(1, location);
		boolean isQuery = preState.execute(); //execute()方法若是執行查詢語句SELECT語句會回傳true,否則回傳false
		ResultSet rs = preState.getResultSet();
		System.out.println("Query? " + isQuery);
		while(rs.next()) {
			System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
		}
		rs.close();
		preState.close();
	}
	
	//作業 Delete by id
	public void deleteData() throws SQLException {
		String sqlStr = "Delete from profiles where id=?";
		PreparedStatement preState = conn.prepareStatement(sqlStr);
		preState.setInt(1, 101);
		preState.execute();
		preState.close();
		
		System.out.println("Delete Finish!");
		
	}
	
	
    //主程式
	public static void main(String[] args) {
		
		Demo4PrepareStatement demo4 = new Demo4PrepareStatement();
		
	    try {
	    	demo4.createConn();
	    	//demo4.createTable();
	    	//demo4.insertData();
	    	//demo4.updateData();
	    	demo4.queryByAddress("Japan");
	    	//demo4.deleteData();

	    }catch(Exception e) {
	    	System.out.println("Something Wrong!");
	    	e.printStackTrace();
	    }finally {
	    	try {
		    	demo4.closeConn();
		    }catch(Exception e) {
		    	e.printStackTrace();
		    }
		
	    }
		

	}

}
