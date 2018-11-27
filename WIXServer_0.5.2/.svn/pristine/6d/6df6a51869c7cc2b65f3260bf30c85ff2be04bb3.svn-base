package core.data;

import java.io.Serializable;

/**
 * entry object
 * @author kosuda
 */
public class Entry implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int wid;
	
	private int eid;
		
	private String keyword;
	
	private String target;
	
	private int clickCount;
	
	/**
	 * wix-plusのcount ranking作成時におけるvalue listの要素オブジェクト
	 */
	public Entry(int wid, int eid, String keyword, String target, int clickCount) {
		this.wid = wid;
		this.eid = eid;
		this.keyword = keyword;
		this.target = target;
		this.clickCount = clickCount;
	}
	
	/**
	 * wix-plusのpair ranking出力時におけるvalue listの要素オブジェクト
	 */
	public Entry(int wid, int eid, String keyword, String target) {
		this.wid = wid;
		this.eid = eid;
		this.keyword = keyword;
		this.target = target;
	}
	
	public int getWid() {
		return this.wid;
	}

	public int getEid() {
		return this.eid;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

}
