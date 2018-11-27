package findindex.service.impl;

import org.apache.commons.configuration.Configuration;

import core.data.Constant;
import core.util.LogUtil;
import core.util.WixUtilities;
import findindex.service.InitData;
/**
 * FindIndexに使うデータ初期化の実装
 * @author ishizaki
 */
public class InitDataImpl implements InitData {

	private static LogUtil logger;
	/**
	 * デフォルトコンストラクタ
	 */
	public InitDataImpl() {
		logger = new LogUtil(InitDataImpl.class);
	}
	
	public void init(Configuration prop) {
		// ENVIRONMENT = developmentの場合のみ有効
		// ENVIRONMENT = productの場合は固定値を使用し設定ミスを防ぐ
		if ( "development".equals(Constant.ENVIRONMENT.getString()) ) {
			if ( prop.getBoolean("CHANGE_DB_CONF") ) {
				Constant.POSTGRES_DRIVER.setString(prop.getString("DB_DRIVER"));
				Constant.POSTGRES_URL.setString(prop.getString("DB_URL"));
				Constant.POSTGRES_USER.setString(prop.getString("DB_USER"));
				Constant.POSTGRES_PASSWORD.setString(prop.getString("DB_PASSWORD"));
			}
			
			if ( prop.getBoolean("CHANGE_FINDINDEX_CONF") ) {
				Constant.RMI_ENABLED.setBool(prop.getBoolean("RMI_ENABLED"));
				Constant.UPDATE_ENABLED.setBool(prop.getBoolean("UPDATE_ENABLED"));
			}
			
			if ( prop.getBoolean("CHANGE_CONSTANT") ) {
				Constant.MAKE_CACHE.setBool(prop.getBoolean("MAKE_CACHE"));
				Constant.WIXPLUS_ENABLED.setBool(prop.getBoolean("WIXPLUS_ENABLED"));
				Constant.MAKE_WORDTANK_CACHE.setBool(prop.getBoolean("MAKE_WORDTANK_CACHE"));
				Constant.UNICAST_REMOTE_OBJECT_PORT.setString(prop.getString("UNICAST_REMOTE_OBJECT_PORT"));
				Constant.RMI_PORT.setString(prop.getString("RMI_PORT"));
				Constant.CONTEXT_PATH.setString(prop.getString("CONTEXT_PATH"));
				Constant.CACHE_HOST_NAME.setString(prop.getString("CACHE_HOST_NAME"));
				Constant.MAKE_CACHE_URL.setString("http://" + Constant.CACHE_HOST_NAME.getString() + "/" + Constant.CONTEXT_PATH.getString() + "/cache/redirector");
				Constant.MAKE_WIXPLUS_CACHE_URL.setString("http://" + Constant.CACHE_HOST_NAME.getString() + "/" + Constant.CONTEXT_PATH.getString() + "/cache/wixplus");
				Constant.MAKE_WIXPLUSCOUNT_CACHE_URL.setString("http://" + Constant.CACHE_HOST_NAME.getString() + "/" + Constant.CONTEXT_PATH.getString() + "/cache/count_ranking");
				Constant.MAKE_WIXPLUSPAIR_CACHE_URL.setString("http://" + Constant.CACHE_HOST_NAME.getString() + "/" + Constant.CONTEXT_PATH.getString() + "/cache/pair_ranking");
				Constant.MAKE_WIXPLUSJACCARD_CACHE_URL.setString("http://" + Constant.CACHE_HOST_NAME.getString() + "/" + Constant.CONTEXT_PATH.getString() + "/cache/jaccard_ranking");
				Constant.MAKE_WORDTANK_CACHE_URL.setString("http://" + Constant.CACHE_HOST_NAME.getString() + "/" + Constant.CONTEXT_PATH.getString() + "/cache/wordtank");
			}
		} else if ( "product".equals(Constant.ENVIRONMENT.getString()) ) {
			Constant.CACHE_HOST_NAME.setString("wixdemo.db.ics.keio.ac.jp");
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
			+ " [Make wordtank cache url] = " + Constant.MAKE_WORDTANK_CACHE.getString() + System.getProperty("line.separator")
			+ " [Postgres url] = " + Constant.POSTGRES_URL.getString() + System.getProperty("line.separator")
			+ " [SQLite url] = " + Constant.SQLITE_URL.getString() + System.getProperty("line.separator")
			+ " [Extension new version] = " + Constant.EXTENSION_NEW_VERSION.getString() + System.getProperty("line.separator")
		);
	}
	
}
