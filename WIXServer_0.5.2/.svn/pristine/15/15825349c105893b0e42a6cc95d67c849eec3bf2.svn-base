package core.controller;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import core.dao.FindIndexDao;
import core.data.Constant;
import core.util.LogUtil;

/**
 * Find-Index reload, buffering update
 * @author kosuda
 */
@WebServlet(name = "findIndexUpdate", urlPatterns = "/doupdate")
public class FindIndexUpdate extends HttpServlet{
	
	/** デフォルトシリアルバージョンID */
	private static final long serialVersionUID = 1L;
	
	// singleton pattern
	private static LogUtil logger;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger = new LogUtil(FindIndexUpdate.class);
		response.setContentType("text/html; charset = utf-8");
		
		FindIndexDao findIndexDao = new FindIndexDao();
		
		boolean updateSuccess = false;
		try {
			updateSuccess = findIndexDao.update();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		if ( updateSuccess ) {
			logger.info("FindIndex update :"
					+ " UPDATE_TYPE = " + Constant.UPDATE_TYPE.getString());
		} else {
			logger.warn("FindIndex update has failed.");
		}
	}
}
