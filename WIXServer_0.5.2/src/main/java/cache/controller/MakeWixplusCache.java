package cache.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cache.service.CacheServiceImpl;
import core.util.LogUtil;
import core.util.WixUtilities;

/**
 * wix-plusのHash mapの構築
 * @author haseshun
 */
@WebServlet(name = "makeWixplusCache", urlPatterns = "/cache/wixplus")
public class MakeWixplusCache extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static LogUtil logger;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger = new LogUtil(MakeWixplusCache.class);
		logger.info("Make wixplus hashmap process has started.");
		
		response.setContentType("text/html; charset=UTF-8");
		String filePath = getServletContext().getRealPath("WEB-INF/classes/wix.properties");
		
		CacheServiceImpl cacheService = new CacheServiceImpl();
		
		try {
			cacheService.newCache(filePath, "WIXPLUS");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		logger.info("Make wix plus count hashmap process has completed successfully.");
		WixUtilities.printMem();
	}
	
}