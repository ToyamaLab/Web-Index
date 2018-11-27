package core.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.configuration.Configuration;

import core.dao.PropertyFileDao;
import core.data.Constant;
import core.util.LogUtil;
import core.util.WixUtilities;

/**
 * コンテキスト初期化及びコンテキスト停止をトリガーとした処理の実装クラス
 * @author kosuda
 */
@WebListener()
public class InitializationListener implements ServletContextListener {
	
	// singleton pattern
	private static LogUtil logger;
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//TODO : Context停止時の処理。ここでJDBCドライバ系を解放してあげるといいかも。
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger = new LogUtil(InitializationListener.class);
		//Context開始時の処理。プロパティセット
		PropertyFileDao propDao = new PropertyFileDao();
		String filePath = event.getServletContext().getRealPath("WEB-INF/classes/wix.properties");
		
		Constant.PROP_PATH.setString(filePath);
		Configuration prop = null;
		try {
			prop = propDao.read();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		// ENVIRONMENT = developmentの場合のみ有効
		// ENVIRONMENT = productの場合は固定値を使用し設定ミスを防ぐ
		if ( "development".equals(Constant.ENVIRONMENT.getString()) ) {
			if ( prop.getBoolean("CHANGE_CONSTANT") ) {
				Constant.UPDATE_TYPE.setString(prop.getString("UPDATE_TYPE"));
				Constant.RMI_PORT.setString(prop.getString("RMI_PORT"));
				Constant.RMI_URL.setString("rmi://localhost:" + Constant.RMI_PORT.getString() + "/FindIndex");
				
				logger.debug("Read property file :"
					+ " PROP_PATH = " + Constant.PROP_PATH.getString()
					+ " UPDATE_TYPE = " + Constant.UPDATE_TYPE.getString()
					+ " RMI_PORT = " + Constant.RMI_PORT.getString()
					+ " RMI_URL = " + Constant.RMI_URL.getString()
				);
			}
		} else if ( "product".equals(Constant.ENVIRONMENT.getString()) ) {
			Constant.CACHE_HOST_NAME.setString("noah.db.ics.keio.ac.jp");
			Constant.CONTEXT_PATH.setString("demo");
			Constant.UNICAST_REMOTE_OBJECT_PORT.setString("60001");
			Constant.RMI_PORT.setString("1099");
			Constant.RMI_URL.setString("rmi://localhost:" + Constant.RMI_PORT.getString() + "/FindIndex");
			Constant.POSTGRES_URL.setString("jdbc:postgresql://localhost:5432/wix_prd");
			Constant.MAKE_CACHE.setBool(true);
			Constant.MAKE_WORDTANK_CACHE.setBool(true);
			Constant.WIXPLUS_ENABLED.setBool(false);
			Constant.MAKE_CACHE_URL.setString("http://" + Constant.CACHE_HOST_NAME.getString() + "/" + Constant.CONTEXT_PATH.getString() + "/cache/redirector");
			Constant.MAKE_WIXPLUS_CACHE_URL.setString("http://" + Constant.CACHE_HOST_NAME.getString() + "/" + Constant.CONTEXT_PATH.getString() + "/cache/wixplus");
			Constant.MAKE_WIXPLUSCOUNT_CACHE_URL.setString("http://" + Constant.CACHE_HOST_NAME.getString() + "/" + Constant.CONTEXT_PATH.getString() + "/cache/count_ranking");
			Constant.MAKE_WIXPLUSPAIR_CACHE_URL.setString("http://" + Constant.CACHE_HOST_NAME.getString() + "/" + Constant.CONTEXT_PATH.getString() + "/cache/pair_ranking");
			Constant.MAKE_WIXPLUSJACCARD_CACHE_URL.setString("http://" + Constant.CACHE_HOST_NAME.getString() + "/" + Constant.CONTEXT_PATH.getString() + "/cache/jaccard_ranking");
			Constant.MAKE_WORDTANK_CACHE_URL.setString("http://" + Constant.CACHE_HOST_NAME.getString() + "/" + Constant.CONTEXT_PATH.getString() + "/cache/wordtank");
		}
		
		logger.info("WIX CONFIGURATION :" + System.getProperty("line.separator")
			+ " [System url] = " + WixUtilities.getHostName() + "/" + Constant.CONTEXT_PATH.getString() + System.getProperty("line.separator")
			+ " [Environment] = " + Constant.ENVIRONMENT.getString() + System.getProperty("line.separator")
			+ " [Find index url] = " + Constant.RMI_URL.getString() + System.getProperty("line.separator")
			+ " [Unicast remote object port] = " + Constant.UNICAST_REMOTE_OBJECT_PORT.getString() + System.getProperty("line.separator")
			+ " [Find index Update type] = " + Constant.UPDATE_TYPE.getString() + System.getProperty("line.separator")
			+ " [Enabled make expect trie] = " + Constant.UPDATE_ENABLED.getBool() + System.getProperty("line.separator")
			+ " [Enabled make cache] = " + Constant.MAKE_CACHE.getBool() + System.getProperty("line.separator")
			+ " [Enabled wixplus] = " + Constant.WIXPLUS_ENABLED.getBool() + System.getProperty("line.separator")
			+ " [Enabled wordtank] = " + Constant.MAKE_WORDTANK_CACHE.getBool() + System.getProperty("line.separator")
			+ " [Redirector url] = " + Constant.CACHE_HOST_NAME.getString() + "/redirector" + System.getProperty("line.separator")
			+ " [Make cache url] = " + Constant.MAKE_CACHE_URL.getString() + System.getProperty("line.separator")
			+ " [Make wixplus cache url] = " + Constant.MAKE_WIXPLUS_CACHE_URL.getString() + System.getProperty("line.separator")
			+ " [Make wixplus count cache url] = " + Constant.MAKE_WIXPLUSCOUNT_CACHE_URL.getString() + System.getProperty("line.separator")
			+ " [Make wixplus pair cache url] = " + Constant.MAKE_WIXPLUSPAIR_CACHE_URL.getString() + System.getProperty("line.separator")
			+ " [Make wixplus jaccard cache url] = " + Constant.MAKE_WIXPLUSJACCARD_CACHE_URL.getString() + System.getProperty("line.separator")
			+ " [Make wordtank cache url] = " + Constant.MAKE_WORDTANK_CACHE_URL.getString() + System.getProperty("line.separator")
			+ " [Postgres url] = " + Constant.POSTGRES_URL.getString() + System.getProperty("line.separator")
			+ " [SQLite url] = " + Constant.SQLITE_URL.getString() + System.getProperty("line.separator")
			+ " [Extension new version] = " + Constant.EXTENSION_NEW_VERSION.getString() + System.getProperty("line.separator")
		);
	}
	
}
