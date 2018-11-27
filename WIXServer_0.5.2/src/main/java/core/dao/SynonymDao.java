package core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import core.data.Constant;
import core.util.LogUtil;

public class SynonymDao {

	private static Connection conn;
		
	private static Statement stmt;
	
	private static PreparedStatement pstmt;
	
	private static ResultSet rset;
	
	private static LogUtil logger;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public SynonymDao() {
		conn = null;
		rset = null;
		stmt = null;
		pstmt = null;
		logger = new LogUtil(SynonymDao.class);
	}
	
	public void connect() {
		try {
			Class.forName(Constant.POSTGRES_DRIVER.getString());
			conn = DriverManager.getConnection(
					Constant.POSTGRES_URL.getString(), 
					Constant.POSTGRES_USER.getString(), 
					Constant.POSTGRES_PASSWORD.getString()
			);
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	public ResultSet read(String query) throws ClassNotFoundException, SQLException {
		connect();
		
		stmt = conn.createStatement();
		rset = stmt.executeQuery(query);
			
		logger.debug("[Postgres] Load USER info :"
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
		
		if ( pstmt != null ) {
			pstmt.close();
		}

		if ( conn != null ) {
			conn.close();
		}
		
		logger.debug("[Postgres] Opend the resource has done");
	}
	
}
