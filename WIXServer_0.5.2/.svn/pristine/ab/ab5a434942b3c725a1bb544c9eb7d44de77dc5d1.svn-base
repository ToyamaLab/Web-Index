package core.data;

import java.io.Serializable;

/**
 * each process result object
 * @author kosuda
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int wid;
	
	private int eid;
		
	private int start;
	
	private int end;
	
	//FIXME : keyword, targetは必要かどうか検討
	private String keyword;
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	private String target;
	
	public String getTarget() {
		return target;
	}
	
	public String getTargetName(String target) {//targetの最後の文字を取り出す
		target = target.substring(target.lastIndexOf("/")+1);
		
		return target;
	}
	
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	private int keywordLength;
	
	private String markup;
	
	private Product next;
	
	/**
	 * FindIndexのアウトプット用コンストラクタ
	 * @param wid
	 * @param eid
	 * @param keyword.length()
	 */
	public Product(int wid, int eid, int length, String keyword, String target) {
		this.wid = wid;
		this.eid = eid;
		this.keywordLength = length;
		//FIXME : keyword, targetありの場合となしの場合でメモリ使用量調査
		this.keyword = keyword;
		this.target = target;
		next = null;
	}
	
	/**
	 * FindIndex内でResultOfFindに追加するときのコンストラクタ
	 * @param outputValue
	 * @param start　文書中の出現位置における開始位置
	 * @param end　文書中の出現位置における開始位置
	 */
	public Product(Product outputValue, int start, int end) {
		this.wid = outputValue.wid;
		this.eid = outputValue.eid;
		this.keywordLength = outputValue.keywordLength;
		//FIXME : keyword, targetありの場合となしの場合でメモリ使用量調査
		this.keyword = outputValue.keyword;
		this.target = outputValue.target;
		this.start = start;
		this.end = end;
		this.next = null;
	}
	
	/**
	 * PreFind時のDoNotRewriteList用のコンストラクタ
	 * @param markup　抽出したタグからタグまでのString
	 * @param start　文書中の出現位置における開始位置
	 */
	public Product(String markup, int start) {
		this.markup = markup;
		this.start = start;
	}
	
	public int getWid() {
		return wid;
	}
	
	public void setWid(int wid) {
		this.wid = wid;
	}
	
	public int getEid() {
		return eid;
	}
	
	public void setEid(int eid) {
		this.eid = eid;
	}
	
	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}

	public String getMarkup() {
		return markup;
	}
	
	public void setMarkup(String markup) {
		this.markup = markup;
	}

	public int getKeywordLength() {
		return keywordLength;
	}
	
	public void setKeywordLength(int keywordLength) {
		this.keywordLength = keywordLength;
	}
	
	public Product getNext() {
		return this.next;
	}
	
	public void setNext(Product obj) {
		if(this.next == null) {
			this.next = obj;
			return;
		} else {
			obj.next = this.next;
			this.next = obj;
		}
	}
	
	public boolean equalsSynonym(Product obj){
		if( this.eid == obj.eid && this.target == obj.target){
			return true;
		} else{
			return false;
		}
	}

}
