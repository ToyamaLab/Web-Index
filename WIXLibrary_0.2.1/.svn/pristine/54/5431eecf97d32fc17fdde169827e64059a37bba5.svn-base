package data;

public enum Constant {
	
	/** wix fileのupdate時に使用するtemporary branch */
	UPDATE_BRANCH("for_update_branch"),
	
	/** master branch name */
	MASTER_BRANCH("master"),
	
	/** wix fileをfile systemで管理するディレクトリ */
	WIX_FILE_MANAGEMENT_DIR("/tmp"),
	
	/** canonical formの拡張子 */
	CANONICAL_FORM_EXTENSION(".csv"),
	
	/** update成功時のmessage */
	UPDATE_SUCCESS_MESSAGE("The update was completed successfully."),
	
	/** upload成功時のmessage */
	UPLOAD_SUCCESS_MESSAGE("The upload was completed successfully."),
	
	/** update時に変更点がない場合のmessage */
	NO_CHANGE_MASSAGE("Updates can not be found in the file you uploaded."),
	
	/** /metainfo/{wid}にアクセス時にwidが空の場合のerror message */
	WID_NULL_MESSAGE("Wid is null."),
	
	/** /entry/{wid}/{eid}にアクセス時にwidもしくはeidがnullの場合のerror message */
	WIX_OR_EID_NULL_MESSAGE("Wid or eid is null."),
	
	/** body tag name */
	BODY_TAG_NAME("body"),
	
	/** entry tag name */
	ENTRY_TAG_NAME("entry"),
	
	/** keyword tag name */
	KEYWORD_TAG_NAME("keyword"),
	
	/** target tag name */
	TARGET_TAG_NAME("target"),
	
	/** comment tag name */
	COMMENT_TAG_NAME("comment"),
	
	/** description tag name */
	DESCRIPTION_TAG_NAME("description"),
	
	/** language tag name */
	LANGUAGE_TAG_NAME("language"),
	
	/** part tag name */
	PART_TAG_NAME("part"),
	
	/** child tag name */
	CHILD_TAG_NAME("child"),
	
	/** csv delimiter */
	CSV_DELIMITER(","),
	
	/** add prefix */
	ADD_PREFIX("+"),
	
	/** delete prefix */
	DELETE_PREFIX("-"),
	
	/** newline character */
	NEWLINE_CHARACTER("\n"),
	
	/** findindex起動してるhost */
	RMI_HOST("localhost"),
	
	/** rmiregistry起動ポート */
	RMI_PORT("1099"),
	
	/** RMIのURL */
	RMI_URL("rmi://" + RMI_HOST.getValue() + ":" + RMI_PORT.getValue() + "/FindIndex"),
	;
	
	private String value;
	
	private Constant(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
