package core.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.data.Constant;
import core.service.LogoutService;
import core.util.LogUtil;

/**
 * logout process
 * @author kosuda
 */
@WebServlet(name = "logout", urlPatterns = "/logout")
public class Logout extends HttpServlet {
	
	/** デフォルトシリアルバージョンID */
	private static final long serialVersionUID = 1L;
	
	private static LogUtil logger;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger = new LogUtil(Logout.class);
		String encoding = Constant.ENCODING.getString();
		response.setContentType("text/html; charset=" + encoding);
		response.setHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding(encoding);
				
		String status = request.getParameter("message");
		Cookie[] cookie = request.getCookies();
		String WIXLoginID = "";
				
		// auto login userなので'status = logout'の時のみクッキーを削除
		if ( cookie != null ) {
			for ( int i = 0; i < cookie.length; i++ ) {
				if ( "WIXLoginID".equals(cookie[i].getName()) ) {
					WIXLoginID = cookie[i].getValue();
					logger.debug("Get the ID from a cookie : WIXLoginID = " + WIXLoginID);
					if ( "logout".equals(status) ) {
						cookie[i].setMaxAge(0);
						response.addCookie(cookie[i]);
						logger.info("Cookie has removed from client browser. Session of this user has expired.");
					}
					break;
				}
			}

			// auto login userではないのでbrwsCloseでもセッション破棄の処理を実行
			if ( WIXLoginID == "" ) {
				HttpSession hs = request.getSession(false);
				if ( hs != null ) {
					WIXLoginID = (String) hs.getAttribute("_WIXLoginID");
					if ( status.equals("logout") || status.equals("browserClose") ) {
						hs.invalidate();
						logger.info("Session has removed from server. Session of this user has expired.");
					}
				} else {
					logger.warn("Session of tomcat is empty.");
				}
			}
		} else {
			logger.warn("It may have disabled cookies this user's browser.");
		}
		
		LogoutService logout = new LogoutService();
		
		try {
			logout.statusManager(WIXLoginID, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Logout process :"
			+ " status = " + status
		);
		
		// no response
	}
}
