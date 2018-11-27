package core.dao;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import core.data.Constant;
import core.util.LogUtil;

/**
 * PropertyFileDaoの実装
 * @author kosuda
 */
public class PropertyFileDao {
	
	private static LogUtil logger;
	
	public PropertyFileDao() {
		logger = new LogUtil(PropertyFileDao.class);
	}
	
	public Configuration read() throws FileNotFoundException, IOException {
		try {
			Configuration prop = new PropertiesConfiguration(Constant.PROP_PATH.getString());
			logger.debug("Prop read :"
					+ " PATH = " + Constant.PROP_PATH.getString()
			);
				
			return prop;
		} catch (ConfigurationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}
	
}