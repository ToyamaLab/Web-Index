package data;

import java.io.Serializable;

public class PartEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int virtualWid;
	
	private int entityWid;
	
	private int entityEid;

	public int getVirtualWid() {
		return virtualWid;
	}

	public void setVirtualWid(int virtualWid) {
		this.virtualWid = virtualWid;
	}

	public int getEntityWid() {
		return entityWid;
	}

	public void setEntityWid(int entityWid) {
		this.entityWid = entityWid;
	}

	public int getEntityEid() {
		return entityEid;
	}

	public void setEntityEid(int entityEid) {
		this.entityEid = entityEid;
	}
	
}
