package tw.iiijdbc.conn.day2;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Demo7BlobImage {
	
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
	
	//使用BLOB把圖片存到資料庫
	public void storeImageFileToDB() throws Exception{
		File myfile = new File("C:/JDBCfile/temp/IMG_8062.JPG"); //File物件指定檔案或資料夾儲存的路徑
		FileInputStream fis1 = new FileInputStream(myfile); //FileInputStream讀取檔案
		
		String sqStr = "Insert into Gallery(imageName, imageFile) Values(?,?)";
		PreparedStatement preState = conn.prepareStatement(sqStr);
		
		preState.setString(1, "IMG_8062");
		preState.setBinaryStream(2, fis1); //使用.setBinaryStream()將開啟的圖片串流物件交給Statement物件
		
		preState.execute();
		preState.close();
		fis1.close();
		
		System.out.println("store file OK!");
	}

	//使用BLOB從資料庫取出圖片,並存到特定位置
	public void getImageFromDB() throws SQLException, IOException {
		String sqlStr = "Select * From Gallery Where id = ?";
		PreparedStatement preState = conn.prepareStatement(sqlStr);
		preState.setInt(1, 1);
		
		ResultSet rs = preState.executeQuery();
		rs.next();
		Blob blob = rs.getBlob(3); //第三個欄位
		System.out.println("blob.length() = " + blob.length() );
		
		
		//FileOutputStream fos1 = new FileOutputStream("C:/JDBCfile/temp/out"); //FileOutputStream寫檔案
		//BufferedOutputStream bos =  new BufferedOutputStream(fos1);
		BufferedOutputStream bos =  new BufferedOutputStream(new FileOutputStream("C:/JDBCfile/temp/out/IMG_8062.JPG")); //經過記憶體
		bos.write(blob.getBytes(1, (int)blob.length())); //getByte(開始位置, 要取得的資料長度) 開始位置從1開始
		bos.flush(); //強制寫出記憶體
		
		bos.close();
		rs.close();
		preState.close();
		
		System.out.println("Get Image Success!");
		
	}

	public static void main(String[] args) {
		Demo7BlobImage demo7 = new Demo7BlobImage();
		
		try {
			demo7.createConn();
			//demo7.storeImageFileToDB();
			demo7.getImageFromDB();
			
		} catch (Exception e) {
			System.out.println("Some Error: " + e);
		} finally {
			try {
				demo7.closeConn();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

	}

}
