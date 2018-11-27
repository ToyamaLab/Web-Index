package wixplus.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cache.data.Cache;
import core.data.Constant;
import core.util.LogUtil;

/**
 * redirectore for wix-plus
 * @author haseshun
 */
@WebServlet(name = "wixplusRedirector", urlPatterns = "/wixplus/redirector")
public class WixplusRedirector extends HttpServlet{
	
	/** デフォルトシリアルバージョンID */
	private static final long serialVersionUID = 1L;
	
	private static LogUtil logger;
	
	private static final String[] ESCAPE_STRS = { "%" };
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger = new LogUtil(WixplusRedirector.class);
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding(Constant.ENCODING.getString());
		PrintWriter out = response.getWriter();
		out.println("Now, redirecting...<br>");
		
		String wid = request.getParameter("wid");
		String eid = request.getParameter("eid");
		String originalWid = request.getParameter("originalwid");
		String originalEid = request.getParameter("originaleid");
		
		String key = wid + "_" + eid;
		
		logger.info("originalWid = " + originalWid + " originalEid = " + originalEid + " wid = " + wid + " eid = " + eid);
		
		Cache cache = new Cache();
		String target = cache.getWixplusHashmapTarget(key);
		
		//TODO : 文言 client側
		if ( target != null ) {
			String url = "";
			try {
				url = targetEscape(target);
			} catch (URISyntaxException e) {
				logger.error(e.getMessage());
			}
			
			if ( !"".equals(url) ) {
				response.sendRedirect(url);
			}
		}
		
		logger.info("Wixplus redirector :"
			+ " wid = " + wid
			+ " eid = " + eid
			+ " original wid = " + originalWid
			+ " original eid = " + originalEid
		);
	}
	
	private String targetEscape(String target) throws URISyntaxException, UnsupportedEncodingException {
		for (int i = 0; i < ESCAPE_STRS.length; i++) {
			String escapeStr = ESCAPE_STRS[i];
			if (target.contains(escapeStr)) {
				String urlEncodeStr = URLEncoder.encode(escapeStr, "UTF-8");
				target = target.replaceAll(escapeStr, urlEncodeStr);
			}
		}
		
		for (int i = 0; i < target.length(); i++) {
			String check = String.valueOf(target.charAt(i));
			if (check.getBytes().length >= 2) {
				target = target.replace(String.valueOf(target.charAt(i)), new URI(check).toASCIIString());
			}
		}
		return target;
	}
	
}
