package findindex.service;

import org.apache.commons.configuration.Configuration;

/**
 * FindIndexに使うデータの初期化
 * 
 * @author ishizaki
 */
public interface InitData {
	/**
	 * FindIndex構築に使うデータ初期化メソッド
	 * 
	 * @param prop プロパティファイル（wix.properties）
	 */
	public void init(Configuration prop);
}
