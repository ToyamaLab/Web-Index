package data;

import java.io.Serializable;

public class Category implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int wid;
	
	private int cid;
	
	public Category() {
	}

	/**
	 * @return wid
	 */
	public int getWid() {
		return wid;
	}

	/**
	 * @param wid セットする wid
	 */
	public void setWid(int wid) {
		this.wid = wid;
	}

	/**
	 * @return cid
	 */
	public int getCid() {
		return cid;
	}

	/**
	 * @param cid セットする cid
	 */
	public void setCid(int cid) {
		this.cid = cid;
	}
	
}
