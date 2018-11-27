package core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import core.data.Constant;
import core.util.LogUtil;

public class LoginDao {
	
	private static Connection conn;
		
	private static Statement stmt;
	
	private static ResultSet rset;
	
	private static LogUtil logger;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public LoginDao() {
		conn = null;
		rset = null;
		stmt = null;
		logger = new LogUtil(LoginDao.class);
	}
	
	public ResultSet read(String query) throws ClassNotFoundException, SQLException {
		Class.forName(Constant.SQLITE_DRIVER.getString());
		
		conn = DriverManager.getConnection(
			Constant.SQLITE_URL.getString()
		);
		
		rset = stmt.executeQuery(query);
		
		logger.debug("[SQLite] Load LOGIN info :"
			+ " driver = " + Constant.SQLITE_DRIVER.getString()
			+ " url = " + Constant.SQLITE_URL.getString()
			+ " query = " + query
		);
		
		return rset;
	}
	
	public void insert(String query) throws ClassNotFoundException, SQLException {
		Class.forName(Constant.SQLITE_DRIVER.getString());
		
		conn = DriverManager.getConnection(Constant.SQLITE_URL.getString());
		stmt = conn.createStatement();
		
		stmt.execute(query);
		
		logger.debug("[SQLite] Upsert LOGIN info :"
			+ " driver = " + Constant.SQLITE_DRIVER.getString()
			+ " url = " + Constant.SQLITE_URL.getString()
			+ " query = " + query
		);
	}
	
	public void close() throws SQLException {
		if ( rset != null ) {
			rset.close();
		}
		
		if ( stmt != null ) {
			stmt.close();
		}
		
		if ( conn != null) {
			conn.close();
		}
		
		logger.debug("[SQLite] Opened the resource has done.");
	}
	
}
