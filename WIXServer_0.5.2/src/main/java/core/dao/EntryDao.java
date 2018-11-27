package core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import core.data.Constant;
import core.util.LogUtil;

public class EntryDao {
	
	private static Connection conn;
		
	private static Statement stmt;
	
	private static ResultSet rset;
	
	private static LogUtil logger;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public EntryDao() {
		conn = null;
		rset = null;
		stmt = null;
		logger = new LogUtil(EntryDao.class);
	}
	
	public ResultSet read(String query) throws ClassNotFoundException, SQLException {
		Class.forName(Constant.POSTGRES_DRIVER.getString());
		
		conn = DriverManager.getConnection(
			Constant.POSTGRES_URL.getString(), 
			Constant.POSTGRES_USER.getString(), 
			Constant.POSTGRES_PASSWORD.getString()
		);
		
		stmt = conn.createStatement(
			ResultSet.TYPE_SCROLL_SENSITIVE, 
			ResultSet.CONCUR_READ_ONLY
		);
		
		// postgresの仕様で件数の多いものは必ずsequential scanをしてしまうのでindex scanをさせるようにするため
		if ( "service.findindex.controller.impl.FindIndexControllerImpl"
				.equals(new Exception().getStackTrace()[1].getClassName()) ) {
			logger.debug("Set enable_seqscan only when reading from findindex.");
			stmt.executeUpdate("SET enable_seqscan = false");
		}
		
		rset = stmt.executeQuery(query);
		
		logger.debug("[Postgres] Load ENTRY info :"
			+ " driver = " + Constant.POSTGRES_DRIVER.getString()
			+ " url = " + Constant.POSTGRES_URL.getString()
			+ " user = " + Constant.POSTGRES_USER.getString()
			+ " query = " + query
		);
		
		return rset;
	}
	
	public void close() throws SQLException {
		if ( rset != null ) {
			rset.close();
		}
		
		if ( stmt != null ) {
			stmt.close();
		}
		
		conn.close();
		
		logger.debug("[Postgres] Opend the resource has done");
	}
	
}
