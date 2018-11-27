package findindex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;

import core.dao.FindIndexDao;
import core.data.Product;
import core.util.LogUtil;


public class KeepAlive {
	
	// singleton pattern
	private static LogUtil logger;

	public static void main(String args[]) {
		logger = new LogUtil(KeepAlive.class);
		logger.info("keep alive task has started");
		
		FindIndexDao findIndexDao = new FindIndexDao();
		List<Product> resultOfSelect = new ArrayList<Product>();
		
		try {
			resultOfSelect = findIndexDao.find("ももいろクローバーZ 玉井詩織", "3", 1);
		} catch (NotBoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		logger.debug("resultOfSelect.size()" + resultOfSelect.size());
		logger.info("keep alive task has finished");
	}
	
}
