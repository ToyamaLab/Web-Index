package cache.controller;

import java.io.IOException;
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
 * リダイレクタ
 * @author haseshun
 */
@WebServlet(name = "redirector", urlPatterns = "/redirector")
public class Redirector extends HttpServlet{
	
	/** デフォルトシリアルバージョンID */
	private static final long serialVersionUID = 1L;
	
	// singleton pattern
	private static LogUtil logger;
	
	private static final String[] ESCAPE_STRS = { "%" };
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger = new LogUtil(Redirector.class);
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding(Constant.ENCODING.getString());
		
		String wid = request.getParameter("wid");
		String eid = request.getParameter("eid");
		
		String wtid = request.getParameter("wtid");
		String keyword = request.getParameter("keyword");
		
		String target = null;
		
		// null check
		if ( wid != null && eid != null ) {
			Cache cache = new Cache();
			target = cache.getRedirectorTarget(wid + "_" + eid);
			
			logger.info("wid = " + wid + " eid = " + eid);
			
			logger.debug("Redirect process :"
				+ " wid = " + wid
				+ " eid = " + eid
			);
			
			// TODO : client側文言
			if ( target != null ) {
				String url = "";
				try {
					url = targetEscape(target);
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				
				if ( !"".equals(url) ) {
					response.sendRedirect(url);
				}
			}
		} else if ( wtid != null && keyword != null ) {
			Cache cache = new Cache();
						
			target = cache.getWordTankCache(wtid);
			
			// get parameterの文字化け対策
			keyword = new String(keyword.getBytes(Constant.ENCODING.getString()), Constant.ENCODING.getString());
			
			logger.debug("Redirect process :"
				+ " wtid = " + wtid
				+ " keyword = " + keyword
				+ " get target from cache = " + target
			);
			
			// TODO : client側文言
			if ( target != null ) {
				String url = "";
				try {
					url = targetEscape(target + keyword);
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				
				if ( !"".equals(url) ) {
					response.sendRedirect(url);
				}
			}
		} else {
			logger.fatal("request parameter is null :"
				+ " wid = " + wid
				+ " eid = " + eid
				+ " wtid = " + wtid
				+ " keyword = " + keyword
			);
		}
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