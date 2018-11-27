package wixplus.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.arnx.jsonic.JSON;
import cache.data.Cache;
import core.data.Constant;
import core.data.Entry;
import core.util.LogUtil;

public class WIXPlusServiceImpl {
	
	private Cache cache;

//	private static final Logger logger = Logger.getLogger(WIXPlusServiceImpl.class);
	private static LogUtil logger;
	
	public WIXPlusServiceImpl() {
		this.cache = new Cache();
		logger = new LogUtil(WIXPlusServiceImpl.class);
	}
	
	public String getRecommendEntriesInWixFile(String wid, String eid, String type) {
		StringBuffer json = new StringBuffer("{\"WIXFILE\":"); // return
		
		String key = wid + "_" + eid;
		String originTarget = cache.getWixplusCache(key)[1]; // target index = 1
		if ( "WIXPLUS_COUNT".equals(type) ) {
			json.append(newCountRankingJson(key, originTarget, wid)).append(",");
		} else if ( "WIXPLUS_PAIR".equals(type) ) {
			json.append(newPairRankingJson(key, originTarget, wid)).append(",");
		} else if ( "WIXPLUS_JACCARD".equals(type) ) {
			json.append(newJaccardRankingJson(key, originTarget, wid)).append(",");
		} else {
			logger.error("Incorrect type.");
		}
		
		return json.toString();
	}

	public String getRecommendEntriesInBookmark(String wid, String eid, String bookmarkedWIX, String type) {
		StringBuffer json = new StringBuffer("\"BOOKMARK\":");
		
		String key = wid + "_" + eid;
		String originTarget = cache.getWixplusCache(key)[1]; // target index = 1
		
		String[] bookmarkArr = bookmarkedWIX.split("-");
		int[] bookmark = new int[bookmarkArr.length];
		
		for ( int i = 0; i < bookmarkArr.length; i++ ) {
			bookmark[i] = Integer.parseInt(bookmarkArr[i]);
		}
		
		// Arrays.binarySearch()するために必須
		Arrays.sort(bookmark);
		
		if ( "WIXPLUS_COUNT".equals(type) ) {
			json.append(newCountRankingJson(key, originTarget, bookmark)).append("}");
		} else if ( "WIXPLUS_PAIR".equals(type) ) {
			json.append(newPairRankingJson(key, originTarget, bookmark)).append("}");
		} else if ( "WIXPLUS_JACCARD".equals(type) ) {
			json.append(newJaccardRankingJson(key, originTarget, bookmark)).append("}");
		} else {
			logger.error("Incorrect type.");
		}
		
		return json.toString();
	}
	
	private String newCountRankingJson(String key, String originTarget, String _wid) {
		int wid = Integer.parseInt(_wid);
		
		List<Entry> countRankingEntries = cache.getCountRankingCache(wid);
		List<Entry> result = new ArrayList<Entry>();
		List<String> targets = new ArrayList<String>();
		targets.add(originTarget);
		
		int entryCount = 0,
			max = Constant.WIXPLUS_MAX.getInt();
		
		if ( countRankingEntries != null ) {
			for ( Entry elem : countRankingEntries ) {
				if ( entryCount >= max ) {
					break;
				}
				
				String target = elem.getTarget();
				
				if ( !targets.contains(target) ) {
					targets.add(target);
					result.add(elem);
					entryCount++;
				}
			}
		} else {
			logger.warn("Count ranking cache is null :"
				+ " wid = " + wid
			);
		}
		
		return JSON.encode(result, true);
	}
	
	private String newCountRankingJson(String key, String originTarget, int[] bookmark) {
		List<Entry> result = new ArrayList<Entry>();
		List<String> targets = new ArrayList<String>();
		
		targets.add(originTarget);
		
		for ( int i = 0; i < bookmark.length; i++ ) {
			List<Entry> countRankingEntries = cache.getCountRankingCache(bookmark[i]);
			
			if ( countRankingEntries != null ) {
				for ( Entry elem : countRankingEntries ) {
					String target = elem.getTarget();
					
					if ( !targets.contains(target) ) {
						targets.add(target);
						result.add(elem);// TODO : この時点でorder byでsortされた状態なのか要確認。それによって後のcomparatorが必要かどうか決める。
					}
				}
			}
		}
		
		result = limitSort(result, 0, Constant.WIXPLUS_MAX.getInt());
		
		return JSON.encode(result, true);
	}
	
	private String newPairRankingJson(String key, String originTarget, String _wid) {
		int wid = Integer.parseInt(_wid);
		List<int[]> pairRankingCache = cache.getPairRankingCache(key);
		List<Entry> result = new ArrayList<Entry>();
		List<String> targets = new ArrayList<String>();
		
		targets.add(originTarget);
		
		int entryCount = 0,
				max = Constant.WIXPLUS_MAX.getInt();
		
		if ( pairRankingCache != null ) {
			for ( int[] elem : pairRankingCache ) {
				if ( entryCount >= max ) {
					break;
				}
				
				if ( elem[0] == wid ) {
					String[] wixplusCache = cache.getWixplusCache(elem[0] + "_" + elem[1]);// keyword index = 0, target index = 1
					
					if ( !targets.contains(wixplusCache[1]) ) {
						targets.add(wixplusCache[1]);
						result.add(new Entry(elem[0], 
								elem[1], 
								wixplusCache[0], 
								wixplusCache[1])
						);
						
						entryCount++;
					}
				}
			}
		}
		
		// pair rankingでもcount ranking必要なの？？
		List<Entry> countRankingCache = cache.getCountRankingCache(wid);
		
		for ( Entry elem : countRankingCache ) {
			if ( entryCount >= max ) {
				break;
			}
			
			String target = elem.getTarget();
			
			if ( !targets.contains(target) ) {
				targets.add(target);
				result.add(elem);
				entryCount++;
			}
		}
		
		return JSON.encode(result, true);
	}
	
	private String newPairRankingJson(String key, String originTarget, int[] bookmark) {
		List<int[]> pairRankingCache = cache.getPairRankingCache(key);
		List<Entry> result = new ArrayList<Entry>();
		List<String> targets = new ArrayList<String>();
		
		int entryCount = 0,
				max = Constant.WIXPLUS_MAX.getInt();
		
		if ( pairRankingCache != null ) {
			for ( int[] elem : pairRankingCache ) {
				if ( entryCount >= max ) {
					break;
				}
				
				if ( Arrays.binarySearch(bookmark, elem[0]) >= 0 ) {
					String[] wixplusCache = cache.getWixplusCache(elem[0] + "_" + elem[1]);// keyword index = 1, target index = 1
					
					if ( !targets.contains(wixplusCache[1]) ) {
						targets.add(wixplusCache[1]);
						result.add(new Entry(elem[0], 
								elem[1], 
								wixplusCache[0], 
								wixplusCache[1])
						);
						
						entryCount ++;
					}
				}
			}
		}
		
		List<Entry> _result = new ArrayList<Entry>();// TODO: "_"つかいたくない...
		
		if ( entryCount < max ) { // breakで抜けてない場合
			for ( int i = 0; i < bookmark.length; i++ ) {
				List<Entry> countRankingCache = cache.getCountRankingCache(bookmark[i]);
				
				if ( countRankingCache != null ) {
					for ( Entry elem : countRankingCache ) {
						String target = elem.getTarget();
						if ( !targets.contains(target) ) {
							targets.add(target);
							_result.add(elem);
						}
					}
				}
			}
		}
		
		_result = limitSort(_result, entryCount, max);
		result.addAll(_result);
		
		return JSON.encode(result, true);
	}
	
	private String newJaccardRankingJson(String key, String originTarget, String _wid) {
		int wid = Integer.parseInt(_wid);
		
		List<int[]> jaccardRankingCache = cache.getJaccardRankingCache(key);
		List<Entry> result = new ArrayList<Entry>();
		List<String> targets = new ArrayList<String>();
		
		targets.add(originTarget);
		
		int entryCount = 0,
				max = Constant.WIXPLUS_MAX.getInt();
		
		if ( jaccardRankingCache != null ) {
			for ( int[] elem : jaccardRankingCache ) {
				if ( entryCount >= max) {
					break;
				}
				
				if ( elem[0] == wid ) {
					String[] wixplusCache = cache.getWixplusCache(elem[0] + "_" + elem[1]);
					
					if ( !targets.contains(wixplusCache[1]) ) {
						targets.add(wixplusCache[1]);
						result.add(new Entry(elem[0], 
								elem[1], 
								wixplusCache[0], 
								wixplusCache[1]));
						
						entryCount++;
					}
				}
			}
		}
		
		List<Entry> countRankingCache = cache.getCountRankingCache(wid);
		
		if ( countRankingCache != null ) {
			for ( Entry elem : countRankingCache ) {
				if ( entryCount >= max ) {
					break;
				}
				
				String target = elem.getTarget();
				
				if ( !targets.contains(target) ) {
					targets.add(target);
					result.add(elem);
					
					entryCount++;
				}
			}
		}
		
		return JSON.encode(result, true);
	}
	
	private String newJaccardRankingJson(String key, String originTarget, int[] bookmark) {
		List<int[]> jaccardRankingCache = cache.getJaccardRankingCache(key);
		List<Entry> result = new ArrayList<Entry>();
		List<String> targets = new ArrayList<String>();
		
		targets.add(originTarget);
		
		int entryCount = 0,
				max = Constant.WIXPLUS_MAX.getInt();
		
		if ( jaccardRankingCache != null ) {
			for ( int[] elem : jaccardRankingCache ) {
				if ( entryCount >= max ) {
					break;
				}
				
				if ( Arrays.binarySearch(bookmark, elem[0]) >= 0 ) {
					String[] wixplusCache = cache.getWixplusCache(elem[0] + "_" + elem[1]);
					
					if ( !targets.contains(wixplusCache[1]) ) {
						targets.add(wixplusCache[1]);
						result.add(new Entry(elem[0], 
								elem[1], 
								wixplusCache[0], 
								wixplusCache[1]));
						
						entryCount++;
					}
				}
			}
		}
		
		List<Entry> _result = new ArrayList<Entry>();
		
		// breakで抜けてない場合
		if ( entryCount < max ) {
			for ( int i = 0; i < bookmark.length; i++ ) {
				List<Entry> countRankingCache = cache.getCountRankingCache(bookmark[i]);
				
				if ( countRankingCache != null ) {
					for ( Entry elem : countRankingCache ) {
						String target = elem.getTarget();
						
						if ( !targets.contains(target) ) {
							targets.add(target);
							_result.add(elem);
						}
					}
				}
			}
		}
		
		_result = limitSort(_result, entryCount, max);
		result.addAll(_result);
		
		return JSON.encode(result, true);
	}
	
	// startを指定することで降順に並べたstartからmaxまでのlistを返す
	private List<Entry> limitSort(List<Entry> target, int start, int max) {
		List<Entry> sortedList = new ArrayList<Entry>();
		
		Entry[] oa = (Entry[]) target.toArray(new Entry[0]);
		Arrays.sort(oa, new WixplusDataComparatorImpl());
		
		target.clear();
		
		for ( int i = 0; i < oa.length && start < max; i++ ) {
			sortedList.add(oa[i]);
			start++;
		}
		
		return sortedList;
	}
	
}
