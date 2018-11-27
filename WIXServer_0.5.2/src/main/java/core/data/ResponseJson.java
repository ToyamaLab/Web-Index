package core.data;

import java.io.Serializable;

public class ResponseJson implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int index;
	
	private String newBody;
	
	public ResponseJson() {
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getNewBody() {
		return newBody;
	}

	public void setNewBody(String newBody) {
		this.newBody = newBody;
	}

}
