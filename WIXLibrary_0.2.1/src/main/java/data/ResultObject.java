package data;

import java.io.Serializable;
/**
 * wid, eid, keyword, target, start, end, next(ResultObject)を持つシリアライズされたオブジェクト
 * 
 * TODO このオブジェクトからkeyword, targetを消す
 * 
 * @author kosuda
 */
public class ResultObject implements Serializable {

	private static final long serialVersionUID = -5844279551000749637L;
	private int wid;
	private int eid;
	private String keyword;
	private int start;
	private int end;
	private String markup;
	private ResultObject next;
	private int length;
	
	/**
	 * FindIndexのアウトプット用コンストラクタ
	 * 
	 * @param wid
	 * @param eid
	 * @param keyword.length()
	 */
	public ResultObject(int wid, int eid, int length) {
		this.wid = wid;
		this.eid = eid;
		this.length = length;
		this.next = null;
	}
	
	/**
	 * FindIndex内でResultOfFindに追加するときのコンストラクタ
	 * 
	 * @param resultObj
	 * @param start　文書中の出現位置における開始位置
	 * @param end　文書中の出現位置における開始位置
	 */
	public ResultObject(ResultObject resultObj, int start, int end) {
		this.wid = resultObj.wid;
		this.eid = resultObj.eid;
		//this.keyword = resultObj.keyword;
		//this.target = resultObj.target;
		this.length = resultObj.length;
		this.start = start;
		this.end = end;
		this.next = null;
	}
	/**
	 * PreFind時のDoNotRewriteList用のコンストラクタ
	 * 
	 * @param markup　抽出したタグからタグまでのString
	 * @param start　文書中の出現位置における開始位置
	 */
	public ResultObject(String markup, int start) {
		this.markup = markup;
		this.start = start;
	}
	
	public int getWid() {
		return this.wid;
	}

	public int getStart() {
		return this.start;
	}

	public int getEnd() {
		return this.end;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public int getEid() {
		return this.eid;
	}

	public String getMarkup() {
		return this.markup;
	}

	public int getKeywordLength() {
		return this.length;
	}
	
	public void setNext(ResultObject obj) {
		if(this.next == null) {
			this.next = obj;
			return;
		} else {
			obj.next = this.next;
			this.next = obj;
		}
	}

	public ResultObject getNext() {
		return this.next;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
