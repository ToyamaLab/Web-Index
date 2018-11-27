package core.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.data.Constant;
import core.service.LoginService;
import core.util.LogUtil;

/**
 * auto login process
 * @author kosuda
 */
@WebServlet(name = "autoLogin", urlPatterns = "/autologin")
public class AutoLogin extends HttpServlet {
	
	/** デフォルトシリアルバージョンID */
	private static final long serialVersionUID = 1L;
	
	private static LogUtil logger;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger = new LogUtil(AutoLogin.class);
		String encoding = Constant.ENCODING.getString();
		response.setContentType("text/json; charset=" + encoding);
		response.setHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding(encoding);
		
		String WIXLoginID = "";
		Cookie cookie[] = request.getCookies();
		
		PrintWriter out = response.getWriter();
		
		if ( cookie != null ) {
			for ( int i = 0; i < cookie.length; i++ ) {
				if ( "WIXLoginID".equals(cookie[i].getName()) ) {
					WIXLoginID = cookie[i].getValue();
					break;
				}
			}
			
			LoginService login = new LoginService();
			String bookmarkJson = "";
			
			if ( "".equals(WIXLoginID) ) {
				logger.warn("WIXLoginID is empty.");
				bookmarkJson = "[error]";
			} else {
				int uid = 0;
				
				try {
					uid = login.getUser(WIXLoginID);
				} catch (ClassNotFoundException e) {
					logger.error(e.getMessage());
				} catch (SQLException e) {
					logger.error(e.getMessage());
				}
				
				if ( uid != 0 ) {
					try {
						bookmarkJson = login.createLoginJson(uid);
					} catch (SQLException e) {
						logger.error(e.getMessage());
					} catch (ClassNotFoundException e) {
						logger.error(e.getMessage());
					}
				} else {
					logger.error("WIXLoginID is not empty, but uid is 0.");
				}
			}
			
			logger.debug("Auto login process has completed successfully.");
			
			out.println(bookmarkJson);
		} else {
			logger.warn("This login process does not keep the previous session.");
			
			out.println("[error]");
		}
	}
	
}
