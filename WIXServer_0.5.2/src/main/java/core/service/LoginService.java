package core.service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.arnx.jsonic.JSON;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import core.dao.LoginDao;
import core.dao.UserDao;
import core.util.LogUtil;
import core.util.WixUtilities;

public class LoginService {
	
	private LoginDao loginDao;
	
	private UserDao userDao;

	// singleton pattern
	private static LogUtil logger;
	
	private final static String GET_USER = 
		"SELECT id"
		+ " FROM usr"
		+ " WHERE name=? AND pass=md5(?)"
	;
	
	private final static String GET_BOOKMARK = 
		"SELECT b.id, b.name, bw.wid"
		+ " FROM bookmark AS b"
		+ " INNER JOIN bookmark_wix AS bw"
		+ " ON b.id=bw.bid"
		+ " WHERE b.uid=? ORDER BY b.id"
	;
		
	private final static String GET_LOGINHISTORY = 
		"SELECT *"
		+ " FROM LoginManager"
	;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public LoginService() {
		this.loginDao = new LoginDao();
		this.userDao = new UserDao();
		logger = new LogUtil(LoginService.class);
	}
	
	public List<List<String>> getUser(String userName, String password) throws SQLException, ClassNotFoundException {
		List<List<String>> users = new ArrayList<List<String>>();
		
		String[] params = { 
			userName,
			password
		};
				
		ResultSet rset = userDao.read(GET_USER, params);
		
		ResultSetMetaData rsmd = rset.getMetaData();
		int i = 0;
		
		while ( rset.next() ) {
			List<String> user = new ArrayList<String>();
			users.add(user);
			
			for ( int j = 1; j <= rsmd.getColumnCount(); j++ ) {
				users.get(i).add(rset.getString(j));
			}
			
			i++;
		}
		
		rset.close();
		loginDao.close();
		
		return users;
	}

	private List<List<String>> getBookmark(int userId) throws SQLException, ClassNotFoundException {
		List<List<String>> bookmarks = new ArrayList<List<String>>();
		String[] params = {
			String.valueOf(userId)
		};
		
		ResultSet rset = userDao.read(GET_BOOKMARK, params);
		
		ResultSetMetaData rsmd = rset.getMetaData();
		int n = 0;
		
		while ( rset.next() ) {
			List<String> bookmark = new ArrayList<String>();
			bookmarks.add(bookmark);
			
			for ( int j = 1; j <= rsmd.getColumnCount(); j++ ) {
				bookmarks.get(n).add(rset.getString(j));
			}
			
			n++;
		}
		
		rset.close();
		loginDao.close();
		
		return bookmarks;
	}

	// TODO : 引数多い, Objectで渡す
	public void insertLoginInfo(String WIXLoginID, int userID, String userAgent, String clientIP, boolean autoLogin) 
			throws ClassNotFoundException, SQLException {
		
		// Get an UserAgentStringParser and analyze the requesting client
		UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
		ReadableUserAgent agent = parser.parse(userAgent);
		
		String query =
			"INSERT INTO LoginManager(LoginID, LoginTime, BrwsName, BrwsVer, uid, IPAddress, Status, Auto)"
			+ "VALUES ('" + WIXLoginID + "'" 
				+ ", '" + WixUtilities.getNowTimestamp() + "'"
				+ ", '" + agent.getName() + "'"
				+ ", '" + agent.getVersionNumber().toVersionString() + "'"
				+ ", " + userID + ""
				+ ", '" + clientIP + "'"
				+ ", 1"
				+ ", '" + String.valueOf(autoLogin) + "'"
			+ ")"
		;
		
		loginDao.insert(query);
		
		loginDao.close();
	}

	public int getUser(String WIXLoginID) throws ClassNotFoundException, SQLException {
		int uid = 0;
		
		String query =
			"UPDATE LoginManager"
			+ " SET Status = 1, LogoutTime = null "
			+ " WHERE LoginID = '" + WIXLoginID + "'"
		;

		loginDao.insert(query);
		
		query = 
			"SELECT uid"
			+ " FROM LoginManager "
			+ " WHERE LoginID = '" + WIXLoginID + "'"
		;
		
		ResultSet rset = loginDao.read(query);
		
		int rowCount = 0;
		
		while ( rset.next() ) {
			uid = rset.getInt("uid");
			rowCount++;
		}
		
		if ( rowCount > 1 ) {
			logger.fatal("WIXLoginID duplication : WIXLoginID = " + WIXLoginID);
		}
		
		rset.close();
		loginDao.close();
		
		return uid;
	}
	
	public List<List<String>> getLoginHistory() throws ClassNotFoundException, SQLException {
		List<List<String>> loginHistory = new ArrayList<List<String>>();
		
		ResultSet rset = loginDao.read(GET_LOGINHISTORY);
		
		ResultSetMetaData rsmd = rset.getMetaData();
		int i = 0;
		
		while ( rset.next() ) {
			List<String> _loginHistory = new ArrayList<String>();
			loginHistory.add(_loginHistory);
			
			for ( int j = 1; j <= rsmd.getColumnCount(); j++ ) {
				loginHistory.get(i).add(rset.getString(j));
			}
			
			i++;
		}
		
		rset.close();
		loginDao.close();
		
		return loginHistory;
	}
	
	public String createLoginJson(int uid) throws SQLException, ClassNotFoundException{
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		
		List<List<String>> bookmarkIdAndWixId = new ArrayList<List<String>>();
		bookmarkIdAndWixId = getBookmark(uid);
		
		if ( bookmarkIdAndWixId.size() > 0 ) {
			int bookmarkID = Integer.parseInt(bookmarkIdAndWixId.get(0).get(0));
			String bookmarkName = bookmarkIdAndWixId.get(0).get(1);
			String bookmarkedWIX = bookmarkIdAndWixId.get(0).get(2);
			
			for ( int i = 1; i < bookmarkIdAndWixId.size(); i++ ) {
				if ( bookmarkID == Integer.parseInt(bookmarkIdAndWixId.get(i).get(0)) ) {
					bookmarkedWIX = bookmarkedWIX + "-" + bookmarkIdAndWixId.get(i).get(2);
				} else {
					HashMap<String, String> bWIXMap = new HashMap<String, String>();
					bWIXMap.put("bookmarkedWIX", bookmarkedWIX);
					bWIXMap.put("name", bookmarkName);
					jsonList.add(bWIXMap);
					
					bookmarkID = Integer.parseInt(bookmarkIdAndWixId.get(i).get(0));
					bookmarkName = bookmarkIdAndWixId.get(i).get(1);
					bookmarkedWIX = bookmarkIdAndWixId.get(i).get(2);
				}
			}
			HashMap<String, String> bWIXMap = new HashMap<String, String>();
			bWIXMap.put("bookmarkedWIX", bookmarkedWIX);
			bWIXMap.put("name", bookmarkName);
			jsonList.add(bWIXMap);
		}
		
		logger.info("Created Login JSON : uid = " + uid);
		
		return JSON.encode(jsonList);
	}
}
