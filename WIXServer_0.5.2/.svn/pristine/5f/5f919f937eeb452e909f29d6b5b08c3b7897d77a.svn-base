package core.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.data.Constant;
import core.service.LoginService;
import core.util.LogUtil;
import core.util.WixUtilities;

/**
 * login process
 * @author kosuda
 */
@WebServlet(name = "login", urlPatterns = "/login")
public class Login extends HttpServlet{
	
	/** デフォルトシリアルバージョンID */
	private static final long serialVersionUID = 1L;
	
	private static LogUtil logger;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger = new LogUtil(Login.class);
		String encoding = Constant.ENCODING.getString();
		response.setContentType("text/json; charset=" + encoding);
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding(encoding);
		
		String userName = request.getParameter("user_name");
		String password = request.getParameter("pass");
		boolean autoLogin = Boolean.valueOf(request.getParameter("login_omit"));
		String userAgent = request.getHeader("USER-AGENT");
		String ipAddress = request.getHeader("x-forwarded-for");
		
		
		logger.info("Login process has started :"
				+ " UserName = " + userName
				+ " AutoLogin = " + autoLogin
				+ " USER_AGENT = " + userAgent
				+ " IP Address = " + ipAddress);
		
		String json = "";
		
		LoginService login = new LoginService();
		String WIXLoginID = WixUtilities.getRandomStr(30);
		
		try {
			login = new LoginService();
			
			List<List<String>> usersList = new ArrayList<List<String>>();
			
			try {
				usersList = login.getUser(userName, password);
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage());
			}
			
			if ( usersList.size() == 1 ) {
				int uid = Integer.parseInt(usersList.get(0).get(0));
				
				try {
					json = login.createLoginJson(uid);
				} catch (ClassNotFoundException e) {
					logger.error(e.getMessage());
				}
				
				try {
					login.insertLoginInfo(WIXLoginID, 
							uid, 
							userAgent, 
							ipAddress, 
							autoLogin);
				}  catch (ClassNotFoundException e) {
					logger.error(e.getMessage());
				} catch (SQLException e) {
					logger.error(e.getMessage());
				}
			} else {
				logger.error("The size of user list is not one.");
				json = "[error]";
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			json = "[error]";
		}
		
		if ( autoLogin ) {
	    	Cookie loginCookie = new Cookie("WIXLoginID", WIXLoginID);
	    	loginCookie.setMaxAge(604800);
	    	response.addCookie(loginCookie);
	    } else {
	    	HttpSession hs = request.getSession(false);
	    	if ( hs == null ) {
	    		hs = request.getSession(true);
	    		hs.setAttribute("_WIXLoginID", WIXLoginID);
	    	} else {
	    		hs.invalidate();
	    		hs = request.getSession(true);
	    		hs.setAttribute("_WIXLoginID", WIXLoginID);
	    	}
	    }
		
		logger.info("Login process has completed successfully.");
		
		out.println(json);
	}
	
}
