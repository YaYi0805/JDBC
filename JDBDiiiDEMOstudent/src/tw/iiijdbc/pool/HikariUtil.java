package tw.iiijdbc.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariUtil {
	
	private HikariDataSource ds;
	
	public HikariDataSource openDataSource() {
		
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:sqlserver://localhost:1433;databaseName=JDBCDemoDB");
		config.setUsername("sa");
		config.setPassword("404381408");
		config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		config.setMaximumPoolSize(10); //規定最多10個連線
		
		ds = new HikariDataSource(config); //透過HikariDataSource()拿到連線
		return ds;
	}
	
	public void closeDataSource() {
		if(ds != null) {
			ds.close();
		}
	}
	
	
	
	
	

}
