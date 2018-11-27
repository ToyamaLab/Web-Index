package cache.data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import core.data.Entry;


/**
 * cache data
 * @author haseshun
 */
public class Cache {
	
	/** リダイレクター用キャッシュ */
	private static Map<String, String> redirectorCache;
	
	/** WIXPLUSリダイレクター&KEYWORD,TARGET取得用のキャッシュ */
	private static Map<String, String[]> wixplusCache;
	
	/** word tank 用リダイレクタキャッシュ */
	private static Map<String, String> wordTankCache;
	
	/** WIXPLUS(COUNT)用のHashmap **/
	private static Map<Integer, List<Entry>> countRankingCache;
	
	/** WIXPLUS(PAIR)用のHashmap **/
	private static Map<String, List<int[]>> pairRankingCache;
	
	/** WIXPLUS(JACCARD)用のHashmap **/
	private static Map<String, List<int[]>> jaccardRankingCache;

	/**
	 *  リダイレクター用Hashmapに関するメソッド
	 * 　構築 
	 */
	public void newRedirectorCache() {
		redirectorCache = new ConcurrentHashMap<String, String>();
	}
	
	/** Hashmapの取得 **/
	public Map<String, String> getRedirectorCache() {
		return redirectorCache;
	}
	
	/** 値をセット **/
	public void setRedirectorCache(String key, String value) {
		redirectorCache.put(key, value);
	}
	
	/** TARGETの取得 **/
	public String getRedirectorTarget(String key) {
		return redirectorCache.get(key);
	}
	
	/**
	 *  WIXPLUSリダイレクター&KEYWORD,TARGET取得用のHashmapに関するメソッド
	 * 　構築 
	 */
	public void newWixplusCache() {
		wixplusCache = new ConcurrentHashMap<String, String[]>();
	}
	
	/** Hashmapの取得 **/
	public String[] getWixplusCache(String key) {
		return wixplusCache.get(key);
	}
	
	/** 値をセット **/
	public void setWixplusCache(String key, String value[]) {
		wixplusCache.put(key, value);
	}
	
	/** KEYWORDの取得 **/
	public String getWixplusCacheKeyword(String key) {
		return wixplusCache.get(key)[0];
	}
	
	/** TARGETの取得 **/
	public String getWixplusHashmapTarget(String key) {
		return wixplusCache.get(key)[1];
	}
	
	/**
	 * word tank用キャッシュ構築メソッド
	 */
	public void newWordTankCache() {
		wordTankCache = new ConcurrentHashMap<String, String>();
	}
	
	public String getWordTankCache(String key) {
		return wordTankCache.get(key);
	}
	
	public void setWordTankCache(String key, String value) {
		wordTankCache.put(key, value);
	}
	
	/**
	 *  WIXPLUS(COUNT)用Hashmapに関するメソッド
	 * 　構築 
	 */
	public void newCountRankingCache() {
		countRankingCache = new ConcurrentHashMap<Integer, List<Entry>>();
	}
	
	/** Hashmapの取得 **/
	public List<Entry> getCountRankingCache(int key) {
		return countRankingCache.get(key);
	}
	
	/** 値をセット **/
	public void setCountRankingCache(int key, List<Entry> value) {
		countRankingCache.put(key, value);
	}
	
	/** ENTRYSETの取得 **/
	public List<Entry> getWixplusCountHashmapEntrySet(int key) {
		return countRankingCache.get(key);
	}
	
	/**
	 *  WIXPLUS(PAIR)用Hashmapに関するメソッド
	 * 　構築 
	 */
	public void newPairRankingCache() {
		pairRankingCache = new ConcurrentHashMap<String, List<int[]>>();
	}
	
	/** Hashmapの取得 **/
	public List<int[]> getPairRankingCache(String key) {
		return pairRankingCache.get(key);
	}
	
	/** 値をセット **/
	public void setPairRankingCache(String key, List<int[]> value) {
		pairRankingCache.put(key, value);
	}
	
	/** ENTRYSETの(WID,EID)取得 **/
	public List<int[]> getWixplusPairHashmapEntrySet(String key) {
		return pairRankingCache.get(key);
	}
	
	/**
	 *  WIXPLUS(JACCARD)用Hashmapに関するメソッド
	 * 　構築 
	 */
	public void newJaccardRankingCache() {
		jaccardRankingCache = new ConcurrentHashMap<String, List<int[]>>();
	}
	
	/** Hashmapの取得 **/
	public List<int[]> getJaccardRankingCache(String key) {
		return pairRankingCache.get(key);
	}
	
	/** 値をセット **/
	public void setJaccardRankingCache(String key, List<int[]> value) {
		jaccardRankingCache.put(key, value);
	}
	
	/** ENTRYSETの(WID,EID)取得 **/
	public List<int[]> getWixplusJaccardHashmapEntrySet(String key) {
		return jaccardRankingCache.get(key);
	}
	
}
