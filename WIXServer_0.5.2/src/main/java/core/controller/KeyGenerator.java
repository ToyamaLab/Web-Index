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
import core.util.WixUtilities;

/**
 * 30文字のランダム文字列IDをクライアントに出力
 * @author kosuda
 */
@WebServlet(name = "keyGenerator", urlPatterns = "/keygen")
public class KeyGenerator extends HttpServlet{

	/** デフォルトシリアルバージョンID */
	private static final long serialVersionUID = 1L;
	
	private static LogUtil logger;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger = new LogUtil(KeyGenerator.class);
		response.setContentType("text/plain; charset=UTF-8");
		request.setCharacterEncoding(Constant.ENCODING.getString());
		PrintWriter out = response.getWriter();
		
		String id = WixUtilities.getRandomStr(30);
		
		String json = "{\"id\":\"" + id + "\"}";
		out.println(json);
		
		logger.info("Generate rangom sting : "
			+ " ID = " + id
		);
	}

}
