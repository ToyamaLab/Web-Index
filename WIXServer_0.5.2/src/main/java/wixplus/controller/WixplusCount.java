package wixplus.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wixplus.service.WIXPlusServiceImpl;
import core.data.Constant;
import core.util.LogUtil;

/**
 * wix-plus (count ranking)
 * @author haseshun
 */
@WebServlet(name = "wixplusCount", urlPatterns = "/wixplus/count")
public class WixplusCount extends HttpServlet{
	
	/** デフォルトシリアルバージョンID */
	private static final long serialVersionUID = 1L;
	
	private static LogUtil logger;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger = new LogUtil(WixplusCount.class);
		
		response.setContentType("text/json; charset=UTF-8");
		request.setCharacterEncoding(Constant.ENCODING.getString());
		PrintWriter out = response.getWriter();
		
		String wid = request.getParameter("wid");
		String eid = request.getParameter("eid");
		String bookmarkedWIX = request.getParameter("bookmarkedWIX");
		
		WIXPlusServiceImpl wixplusService = new WIXPlusServiceImpl();

		StringBuffer json = new StringBuffer();
		out.println(
			json
			.append(wixplusService.getRecommendEntriesInWixFile(wid, eid, "WIXPLUS_COUNT"))
			.append(wixplusService.getRecommendEntriesInBookmark(wid, eid, bookmarkedWIX, "WIXPLUS_COUNT"))
			.toString()
		);
		
		logger.debug("Get /wixplus/count :"
			+ " wid = " + wid
			+ " eid = " + eid
		);
	}
}
