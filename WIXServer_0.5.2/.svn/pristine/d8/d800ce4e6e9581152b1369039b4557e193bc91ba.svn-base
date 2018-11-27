package core.data;

/**
 * constant enumeration
 * @author kosuda
 */
public enum Constant {
	//ここから
	/**
	 * 環境 ( product, development )
	 * product指定の場合固定値を使用、development指定の場合各自で設定した値を使用
	 */
	// TODO : localも作りたい...
	ENVIRONMENT("development"),

	/** ツールバーの最新バージョン */
	EXTENSION_NEW_VERSION("0.7.0"),

	/** webapps directoryまでの絶対パス */
	TO_WEBAPPS_PATH("/srv/tomcat/webapps"),

	/** コンテキストパス　(format : [開発者名]_WIXServer_[version])　*/
	CONTEXT_PATH("shinzato_WIXServer_0.5.3"),

	/** cacheの構築URLのホスト名 */
	CACHE_HOST_NAME("trezia.db.ics.keio.ac.jp"),

	/** cache 構築するかしないか */
	MAKE_CACHE(true),

	/** word tank cache構築 (MAKE_CACHE = falseの場合無効) */
	MAKE_WORDTANK_CACHE(true),

	/** wixplusを有効化するかどうか (MAKE_CACHE = falseの場合無効) */
	WIXPLUS_ENABLED(false),

	/** unicastRemoteObject.exporptObject()の引数で指定するポート番号 (default:random) */
	UNICAST_REMOTE_OBJECT_PORT("60184"),

	/** rmiregistryの起動ポート（ default : 1099ポート ）*/
	RMI_PORT("1184"),

	/** findindexのアップデートタイプ */
	UPDATE_TYPE("RELOAD"),

	/** findindexをRMI起動するかどうか */
	RMI_ENABLED(true),

	/** findindexのexcpect treeを構築するかどうか */
	UPDATE_ENABLED(false),
	//ここまでの値はそれぞれで変更してね

	/** RMIのURL */
	RMI_URL("rmi://localhost:" + RMI_PORT.getString() + "/FindIndex"),

	/** postgresqlドライバ */
	POSTGRES_DRIVER("org.postgresql.Driver"),

	/** sqliteドライバ */
	SQLITE_DRIVER("org.sqlite.JDBC"),

	/** postgresqlのURL */
	POSTGRES_URL("jdbc:postgresql://localhost:5432/wix"),

	/** sqliteのURL */
	SQLITE_URL("jdbc:sqlite:" + TO_WEBAPPS_PATH.getString() + "/" + CONTEXT_PATH.getString() + "/WEB-INF/logs/Login.db"),

	/** postgresqlのユーザ名 */
	POSTGRES_USER("wixadm"),

	/** postgresqlのパスワード */
	POSTGRES_PASSWORD(""),

	/** プロパティファイルのパス */
	PROP_PATH("./wix.properties"),

	/** ハッシュマップの構築URL */
	MAKE_CACHE_URL("http://" + CACHE_HOST_NAME.getString() + "/" + CONTEXT_PATH.getString() + "/cache/redirector"),
	MAKE_WIXPLUS_CACHE_URL("http://" + CACHE_HOST_NAME.getString() + "/" + CONTEXT_PATH.getString() + "/cache/wixplus"),
	MAKE_WIXPLUSCOUNT_CACHE_URL("http://" + CACHE_HOST_NAME.getString() + "/" + CONTEXT_PATH.getString() + "/cache/count_ranking"),
	MAKE_WIXPLUSPAIR_CACHE_URL("http://" + CACHE_HOST_NAME.getString() + "/" + CONTEXT_PATH.getString() + "/cache/pair_ranking"),
	MAKE_WIXPLUSJACCARD_CACHE_URL("http://" + CACHE_HOST_NAME.getString() + "/" + CONTEXT_PATH.getString() + "/cache/jaccard_ranking"),
	MAKE_WORDTANK_CACHE_URL("http://" + CACHE_HOST_NAME.getString() + "/" + CONTEXT_PATH.getString() + "/cache/wordtank"),

	/** プロパティ(XML)ファイルのパス(eclipse上でtest時はWEB-INF/conf/log4j.properties, command lineから実行時は../conf/log4j.properties) */
	LOGXML_PATH("WEB-INF/classes/log4j.xml"),

	/**リダイレクターからのログ取得の際のカテゴリー名*/
	LOGCATEGORY_REDIRECT("redirector"),

	/**WIXPLUSからのログ取得の際のカテゴリー名*/
	LOGCATEGORY_WIXPLUS("wixplus"),

	/**WIXPLUSにおける最大エントリ数*/
	WIXPLUS_MAX(100),

	/**WIXPLUSCOUNTHASHMAPの1WIDあたりの最大エントリ数*/
	WIXPLUS_COUNT_MAX(101),

	/** エントリーテーブルのwidのカラム番号 */
	ARRAY_WID(1),

	/** エントリーテーブルのeidのカラム番号 */
	ARRAY_EID(2),

	/** エントリーテーブルのkeywordのカラム番号 */
	ARRAY_WORD(3),

	/** エントリーテーブルのTARGETのカラム番号 */
	ARRAY_TARGET(4),

	/** エントリーテーブルのcountのカラム番号 */
	ARRAY_COUNT(5),

	/** jaccard, logpairテーブルのoriginal-widのカラム番号 */
	ARRAY_PAIR_ORIWID(1),

	/** jaccard, logpairテーブルのoriginal-eidのカラム番号 */
	ARRAY_PAIR_ORIEID(2),

	/** jaccard, logpairテーブルのwidのカラム番号 */
	ARRAY_PAIR_WID(3),

	/** jaccard, logpairテーブルのeidのカラム番号 */
	ARRAY_PAIR_EID(4),

	/** wix_wordtank_metainfoのidのカラム番号 */
	ARRAY_WORDTANK_ID(1),

	/** wix_wordtankのpathのカラム番号 */
	ARRAY_WORDTANK_PATH(2),

	/** 文字エンコーディング */
	ENCODING("UTF-8"),
	;

	private String strVal;

	private int intVal;

	private boolean boolVal;

	private Constant(String value) {
		this.strVal = value;
	}

	private Constant(int value) {
		this.intVal = value;
	}

	private Constant(boolean boolVal) {
		this.boolVal = boolVal;
	}

	public String getString() {
		return strVal;
	}

	public void setString(String value) {
		this.strVal = value;
	}

	public int getInt() {
		return intVal;
	}

	public void setInt(int intVal) {
		this.intVal = intVal;
	}

	public boolean getBool() {
		return boolVal;
	}

	public void setBool(boolean boolVal) {
		this.boolVal = boolVal;
	}

}
