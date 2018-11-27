package core.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;
import core.dao.FindIndexDao;
import core.data.Constant;
import core.data.Product;
import core.service.Rewriter;
import core.util.LogUtil;

@WebServlet(name = "objectAttach", urlPatterns = "/objattach")
public class ObjectAttach extends HttpServlet{
	
	/** デフォルトシリアルバージョンID */
	private static final long serialVersionUID = 1L;
	
	// singleton pattern
	private static LogUtil logger;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger = new LogUtil(ObjectAttach.class);
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding(Constant.ENCODING.getString());
		PrintWriter out = response.getWriter();
		
		String uid = request.getParameter("uid");
		String bookmarkedWIX = request.getParameter("bookmarkedWIX");
		String body = request.getParameter("body");
		Integer minLength = Integer.valueOf(request.getParameter("minLength"));
		
		// null check
		if ( bookmarkedWIX == null || body == null || minLength == null ) {
			out.println("[]");
			
			logger.fatal("Object attach request parameter is null : "
				+ " uid = " + uid
				+ " bookmarkedWIX = " + bookmarkedWIX
				+ " body = " + body
				+ " minLength = " + minLength
			);
		} else {
			// pre-find
			String[] ppbody = JSON.decode(body, String[].class);
			
			// find and select
			FindIndexDao findIndexDao = new FindIndexDao();
			List<List<Product>> resultOfSelect = new ArrayList<List<Product>>();
			
			try {
				resultOfSelect = findIndexDao.find(ppbody, bookmarkedWIX, minLength);
			} catch (NotBoundException e) {
				logger.error(e.getMessage());
			}
			
			//rewrite
			Rewriter rewriter = new Rewriter(bookmarkedWIX);
			String newBody = JSON.encode(rewriter.rewrite(resultOfSelect, ppbody));
			
			out.println(newBody);
			
			logger.info("Object attach request :"
				+ " uid = " + uid
				+ " bookmarked wix file = " + bookmarkedWIX
				+ " min length = " + minLength
				+ " body size = " + body.getBytes().length + " byte"
				+ " new body size = " + newBody.getBytes().length + " byte"
			);
		}
	}
}
