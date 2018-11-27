package core.service;

import java.sql.SQLException;

import core.dao.LoginDao;
import core.util.WixUtilities;

public class LogoutService {
	
	private LoginDao loginDao;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public LogoutService() {
		this.loginDao = new LoginDao();
	}

	public void statusManager(String WIXLoginID, String status) throws ClassNotFoundException, SQLException {
		String query = "";
		
		if ( status.equals("logout") || status.equals("browserClose") ) {
			query = "update LoginManager set LogoutTime = '" + WixUtilities.getNowTimestamp() + "', Status = 4 where LoginID = '" + WIXLoginID + "'";
		} else if ( status.equals("active") ) {
			query = "update LoginManager set LogoutTime = null, status = 2 where LoginID = '" + WIXLoginID + "'";
		} else if ( status.equals("away") ) {
			query = "update LoginManager set LogoutTime = '" + WixUtilities.getNowTimestamp() + "', Status = 3 where LoginID = '" + WIXLoginID + "'";
		}
		
		loginDao.insert(query);
		
		loginDao.close();
	}
	
}
