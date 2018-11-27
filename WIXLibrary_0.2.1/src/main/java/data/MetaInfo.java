package data;

import java.io.Serializable;

public class MetaInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int wid;
	
	private String name;
	
	private String author;
	
	private String comment;
	
	private String description;
	
	private String language;
	
	private int type;
	
	private MetaInfo child;
	
	private MetaInfo parent;
	
	public int getWid() {
		return wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public MetaInfo getChild() {
		return child;
	}

	public void setChild(MetaInfo child) {
		// create metainfo queue.
//		if ( this.child == null ) {
//			this.child = child;
//		} else {
//			child.child = this.child;
//			this.child = child;
//		}
		if ( this.child == null ) {
			this.child = child;
		} else {
			child.child = this.child;
			this.child = child;
		}
	}

	public MetaInfo getParent() {
		return parent;
	}

	public void setParent(MetaInfo parent) {
		if ( this.parent == null ) {
			this.parent = parent;
		} else {
			parent.parent = this.parent;
			this.parent = parent;
		}
	}

}
