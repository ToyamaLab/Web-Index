package core.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import core.data.Constant;
import core.data.Product;
import core.service.RmiFunction;
import core.util.LogUtil;
/**
 * FindIndexDaoの実装
 * 
 * @author kosuda
 */
public class FindIndexDao {
	
	// singleton pattern
	private static LogUtil logger;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public FindIndexDao() {
		logger = new LogUtil(FindIndexDao.class);
	}
	
	public List<Product> find(String ppBody, String bookmarkedWIX, int minLength) throws NotBoundException, FileNotFoundException, UnsupportedEncodingException, IOException {
		List<Product> resultOfSelect = new ArrayList<Product>();
		
		RmiFunction finder = (RmiFunction) Naming.lookup(Constant.RMI_URL.getString());
		
		resultOfSelect = (List<Product>) finder.find(ppBody, bookmarkedWIX, minLength);
		
		logger.debug("Find proccess :"
			+ " bookmarkedWIX = " + bookmarkedWIX
			+ " minLength = " + minLength
			+ " resultOfSelect size = " + resultOfSelect.size()
		);
		
		return resultOfSelect;
	}
	
	public List<List<Product>> find(String[] ppBody, String bookmarkedWIX, int minLength) throws NotBoundException, FileNotFoundException, UnsupportedEncodingException, IOException {
		List<List<Product>> resultOfSelect = new ArrayList<List<Product>>();
		
		RmiFunction finder = (RmiFunction) Naming.lookup(Constant.RMI_URL.getString());
		
		resultOfSelect = (List<List<Product>>) finder.find(ppBody, bookmarkedWIX, minLength);
		
		logger.debug("Find proccess :"
			+ " bookmarkedWIX = " + bookmarkedWIX
			+ " minLength = " + minLength
			+ " resultOfSelect size = " + resultOfSelect.size()
		);
		
		return resultOfSelect;
	}
	
	public void rmiServer(Remote o) throws RemoteException, MalformedURLException {
		Remote r = UnicastRemoteObject.exportObject(o, Integer.parseInt(Constant.UNICAST_REMOTE_OBJECT_PORT.getString()));
		Naming.rebind(Constant.RMI_URL.getString(), r);
	}

	public boolean update() throws Exception {
		RmiFunction func = (RmiFunction) Naming.lookup(Constant.RMI_URL.getString());
		String updateType = Constant.UPDATE_TYPE.getString();
		
		if ( "RELOAD".equals(updateType) ) {
			if ( func.reload() == 1 ) {
				return true;
			}
		} else if ( "BUFFERING".equals(updateType) ) {
			if ( func.bufferingUpdate() == 1 ) {
				return true;
			}
		}
		
		logger.warn("Find index update was failed.");
		return false;
	}
	
}