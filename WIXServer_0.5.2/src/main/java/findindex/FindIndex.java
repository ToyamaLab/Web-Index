package findindex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.sql.SQLException;

import core.dao.FindIndexDao;
import core.data.Constant;
import core.service.RmiFunctionImpl;
import core.util.LogUtil;
import core.util.WixUtilities;
import findindex.controller.impl.FindIndexControllerImpl;

public class FindIndex {
	
	// main
	public static FindIndexControllerImpl ac_1;
	
	// to buffering update
	public static FindIndexControllerImpl ac_2;
	
	// singleton pattern
	private static LogUtil logger;

	public static void main(String args[]) throws RemoteException {
		logger = new LogUtil(FindIndex.class);
		logger.info("Create findindex process has started.");
		
		FindIndexDao findIndexDao = new FindIndexDao();
		ac_1 = new FindIndexControllerImpl();
		
		RmiFunctionImpl.nowAC = 1;
		RmiFunctionImpl.ansflg = true;
		RmiFunctionImpl.updflg = true;
		
		try {
			ac_1.createFindIndex();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		if(Constant.RMI_ENABLED.getBool() && System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		
		RmiFunctionImpl finder = new RmiFunctionImpl();
		
		if ( Constant.RMI_ENABLED.getBool() ) {
			try {
				findIndexDao.rmiServer(finder);
				logger.info("Find index has completed successfully.");
			} catch (MalformedURLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.debug("Test attach has started.");
			String ppbody = "ロゴマーク";
			String bookmarkedWIX = "128";
			int minLength = 1;
			try {
				finder.find(ppbody, bookmarkedWIX, minLength);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		
		// TODO : System.gc()は使う必要が本当にあるのかを検討
		WixUtilities.printMem();
		System.gc();
		logger.warn("System gc has completed.");
		WixUtilities.printMem();
		
		if ( Constant.MAKE_CACHE.getBool() ) {
			logger.info("Create cache process has started.");
			try {
				URL createRedirectorCacheUrl = new URL(Constant.MAKE_CACHE_URL.getString());
				createRedirectorCacheUrl.getContent();
				
				if ( Constant.MAKE_WORDTANK_CACHE.getBool() ) {
					URL createWordTankCacheUrl = new URL(Constant.MAKE_WORDTANK_CACHE_URL.getString());
					createWordTankCacheUrl.getContent();
				}
				
				if ( Constant.WIXPLUS_ENABLED.getBool() ) {
					URL createWixplusCacheUrl = new URL(Constant.MAKE_WIXPLUS_CACHE_URL.getString());
					createWixplusCacheUrl.getContent();
					
					URL createCountRankingCacheUrl = new URL(Constant.MAKE_WIXPLUSCOUNT_CACHE_URL.getString());
					createCountRankingCacheUrl.getContent();

					URL createPairRankingCacheUrl = new URL(Constant.MAKE_WIXPLUSPAIR_CACHE_URL.getString());
					createPairRankingCacheUrl.getContent();

					URL createJaccardRankingCacheUrl = new URL(Constant.MAKE_WIXPLUSJACCARD_CACHE_URL.getString());
					createJaccardRankingCacheUrl.getContent();
				}
			} catch (MalformedURLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			logger.info("Cache process has completed successfully.");
		}
	}

}