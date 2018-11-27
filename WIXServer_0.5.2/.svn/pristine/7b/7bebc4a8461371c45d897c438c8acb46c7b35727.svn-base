package core.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.data.Constant;
import core.util.LogUtil;

/**
 * extension version manager
 * @author haseshun
 */
@WebServlet(name = "version", urlPatterns = "/version")
public class Version extends HttpServlet{
	
	/** デフォルトシリアルバージョンID */
	private static final long serialVersionUID = 1L;
	
	private static LogUtil logger;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger = new LogUtil(Version.class);
		response.setContentType("text/plain; charset=UTF-8");
		request.setCharacterEncoding(Constant.ENCODING.getString());
		PrintWriter out = response.getWriter();
		
		String version = request.getParameter("version");
		
		out.println(Constant.EXTENSION_NEW_VERSION.getString());
		
		logger.debug("Version check process :"
			+ " client extension version = " + version
			+ " latest extension version = " + Constant.EXTENSION_NEW_VERSION.getString()
		);
	}
	
}
