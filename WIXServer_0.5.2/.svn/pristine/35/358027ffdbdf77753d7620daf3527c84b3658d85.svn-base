package core.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.service.LoginService;

/**
 * ログイン履歴管理クラス
 * @author kosuda
 */
@WebServlet(name = "adminLoginHistory", urlPatterns = "/loginhistory")
public class AdminLoginHistory extends HttpServlet {
	
	/** デフォルトシリアルバージョンID */
	private static final long serialVersionUID = 1L;
	
	private LoginService login;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head>");
		
		List<List<String>> loginHistory = new ArrayList<List<String>>();
		login = new LoginService();
		
		try {
			loginHistory = login.getLoginHistory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		out.println("過去のログイン数 : " + loginHistory.size() + "件<br>");
		
		out.println(
			"<table border=4 align=center>" +
				"<tr bgcolor=\"#cccccc\">" +
					"<th>WIXLoginID</th>" + 
					"<th>LoginTime</th>" +
					"<th>LogoutTime</th>" +
					"<th>BrwsName</th>" +
					"<th>BrwsVersion</th>" +
					"<th>uid</th>" +
					"<th>ClientIP</th>" +
					"<th>status</th>" +
					"<th>Omit</th>" +
				"</tr>"
		);
		
		for ( int i = 0; i < loginHistory.size(); i++ ) {
			out.println("<tr align=center>");
			
			for ( int j = 0; j < loginHistory.get(i).size(); j++ ) {
				out.println("<td>" + loginHistory.get(i).get(j) + "</td>");
			}
			
			out.println("</tr>");
		}
		
		out.println("</table>");
	    out.println("<body>");
	    out.println("</body></html>");
	}
}
