package cache.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import cache.data.Cache;
import core.dao.EntryDao;
import core.dao.PropertyFileDao;
import core.data.Constant;
import core.data.Entry;
import core.util.LogUtil;

public class CacheServiceImpl {
	
	private PropertyFileDao propDao;
	
	private EntryDao entryDao;
	
	private Cache cache;
	
	private static LogUtil logger;
	
	public CacheServiceImpl() {
		this.propDao = new PropertyFileDao();
		this.entryDao = new EntryDao();
		this.cache = new Cache();
		logger = new LogUtil(CacheServiceImpl.class);
	}

	public void newCache(String filePath, String type) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		Constant.PROP_PATH.setString(filePath);
		
		Configuration prop = propDao.read();
		ResultSet rset = null;

		String queryType = "QUERY";
		if ( !"".equals(type) ) {
			queryType += "_" + type;
		}
		
		if ( prop != null ) {
			rset = entryDao.read(prop.getString(queryType));
		}
		
		if ( rset != null ) {
			if ( "".equals(type) ) {
				newRedirectorCache(rset);
			} else if ( "WIXPLUS".equals(type) ) {
				newWixplusCache(rset);
			} else if ( "WORDTANK".equals(type) ) {
				newWordTankCache(rset);
			} else if ( "WIXPLUS_COUNT".equals(type) ) {
				newCountRankingCache(rset);
			} else if ( "WIXPLUS_JACCARD".equals(type) ) {
				newJaccardPairRankingCache(rset, type, true, false);
			} else if ( "WIXPLUS_PAIR".equals(type) ) {
				newJaccardPairRankingCache(rset, type, false, true);
			} else {
				logger.error("Maybe you misstake properties file query key");
			}
		}
		
		rset.close();
		entryDao.close();
	}
	
	private void newRedirectorCache(ResultSet rset) throws SQLException {
		cache.newRedirectorCache();
		
		rset.last();
		int entryNum = rset.getRow();
		logger.debug(""+entryNum);
		rset.first();
		rset.previous();
		
	 	NumberFormat format = NumberFormat.getInstance();
	 	format.setMaximumFractionDigits(1);
	 	format.setMinimumFractionDigits(1);
		
		int i = 1;
		while ( rset.next() ) {
			String key = getWid(rset) + "_" + getEid(rset);
			String value = getTarget(rset);
			
			cache.setRedirectorCache(key, value);
			if ( i % 1000000 == 0) {
				logger.debug( format.format(100 * ((double) i / entryNum)) + "% = " + i + " / " + entryNum );
			}
			i++;
		}
	}
	
	private void newWordTankCache(ResultSet rset) throws SQLException {
		cache.newWordTankCache();
		
		while ( rset.next() ) {
			String key = getWordTankId(rset);
			String value = getWordTankKeyword(rset);
			
			cache.setWordTankCache(key, value);
		}
	}
	
	private void newCountRankingCache(ResultSet rset) throws SQLException {
		cache.newCountRankingCache();
		
		int wid = -1, 
			preWid = -1, 
			max = Constant.WIXPLUS_MAX.getInt(),
			entryCount = 0
		;
		
		List<Entry> value = new ArrayList<Entry>();
		
		//TODO : resultset を取得する時にwid毎でmax length制限かけてあげる.<-rsetをfullに回したくない
		while ( rset.next() ) {
			wid = rset.getInt(Constant.ARRAY_WID.getInt());
			
			if ( wid != preWid || entryCount < max ) {
				if ( wid != preWid && preWid != -1) {
					cache.setCountRankingCache(wid, value);
					value = new ArrayList<Entry>();
					entryCount = 0;
				}
				
				preWid = wid;
				
				// TODO : getWid, getEidにint型返すメソッド用意する
				value.add(new Entry(Integer.parseInt(getWid(rset)), Integer.parseInt(getEid(rset)), getKeyword(rset), getTarget(rset), getClickCount(rset)));
				entryCount++;
			}
		}
		
		cache.setCountRankingCache(preWid, value);
	}
	
	// TODO : もちょっと良い名前ないかな... 急募！w
	private void newJaccardPairRankingCache(ResultSet rset, String type, boolean isJaccard, boolean isPair) throws SQLException {
		if ( isJaccard ) {
			cache.newJaccardRankingCache();
		} else if ( isPair ) {
			cache.newPairRankingCache();
		}
		
		String preKey = null;
		List<int[]> values = new ArrayList<int[]>();
		
		while ( rset.next() ) {
			String key = getOriginalWid(rset) + "_" + getOriginalEid(rset);
			
			if ( preKey != null || !key.equals(preKey) ) {
				if ( isJaccard ) {
					cache.setJaccardRankingCache(key, values);
				} else if ( isPair ) {
					cache.setPairRankingCache(key, values);
				}
				
				values = new ArrayList<int[]>();
			}
			
			preKey = key;
			
			int[] value = {_getWid(rset), _getEid(rset)};
			values.add(value);
		}
		if ( isJaccard ) {
			cache.setJaccardRankingCache(preKey, values);
		} else if ( isPair ) {
			cache.setPairRankingCache(preKey, values);
		}
	}
	
	private void newWixplusCache(ResultSet rset) throws SQLException {
		cache.newWixplusCache();
		
		while ( rset.next() ) {
			String key = getWid(rset) + "_" + getEid(rset);
			String value[] = { getKeyword(rset), getTarget(rset) };
			
			cache.setWixplusCache(key, value);
		}
		
	}
	
	private String getWid(ResultSet rset) throws SQLException {
		return rset.getString(Constant.ARRAY_WID.getInt());
	}
	
	private String getEid(ResultSet rset) throws SQLException {
		return rset.getString(Constant.ARRAY_EID.getInt());
	}
	
	private String getKeyword(ResultSet rset) throws SQLException {
		return rset.getString(Constant.ARRAY_WORD.getInt());
	}
	
	private String getTarget(ResultSet rset) throws SQLException {
		return rset.getString(Constant.ARRAY_TARGET.getInt());
	}
	
	private int getClickCount(ResultSet rset) throws SQLException {
		return rset.getInt(Constant.ARRAY_COUNT.getInt());
	}
	
	private String getOriginalWid(ResultSet rset) throws SQLException {
		return rset.getString(Constant.ARRAY_PAIR_ORIWID.getInt());
	}
	
	private String getOriginalEid(ResultSet rset) throws SQLException {
		return rset.getString(Constant.ARRAY_PAIR_ORIEID.getInt());
	}
	
	// TODO: '_'あんま使いたくないから後で考える
	private int _getWid(ResultSet rset) throws SQLException {
		return rset.getInt(Constant.ARRAY_PAIR_WID.getInt());
	}
	
	private int _getEid(ResultSet rset) throws SQLException {
		return rset.getInt(Constant.ARRAY_PAIR_EID.getInt());
	}
	
	private String getWordTankId(ResultSet rset) throws SQLException {
		return rset.getString(Constant.ARRAY_WORDTANK_ID.getInt());
	}
	
	private String getWordTankKeyword(ResultSet rset) throws SQLException {
		return rset.getString(Constant.ARRAY_WORDTANK_PATH.getInt());
	}
	
}
