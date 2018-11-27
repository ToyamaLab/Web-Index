package core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.data.Constant;
import core.util.LogUtil;

public class UserDao {

	private static Connection conn;
		
	private static PreparedStatement pstmt;
	
	private static ResultSet rset;
	
	private static LogUtil logger;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public UserDao() {
		conn = null;
		rset = null;
		pstmt = null;
		logger = new LogUtil(UserDao.class);
	}
	
	public ResultSet read(String query, String[] params) throws ClassNotFoundException, SQLException {
		Class.forName(Constant.POSTGRES_DRIVER.getString());
		
		conn = DriverManager.getConnection(
			Constant.POSTGRES_URL.getString(), 
			Constant.POSTGRES_USER.getString(), 
			Constant.POSTGRES_PASSWORD.getString()
		);
		
		pstmt = conn.prepareStatement(query);
		
		// FIXME : ここハードコードだからスマートに
		switch (params.length ) {
		case 1:
			pstmt.setLong(1, Long.valueOf(params[0]));
			break;
		case 2:
			pstmt.setString(1, params[0]);
			pstmt.setString(2, params[1]);
			break;
		default:
			break;
		}
		
		rset = pstmt.executeQuery();
		
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
		
		if ( pstmt != null ) {
			pstmt.close();
		}
		if ( conn != null ) {
			conn.close();
		}
		
		logger.debug("[Postgres] Opend the resource has done");
	}
	
}
