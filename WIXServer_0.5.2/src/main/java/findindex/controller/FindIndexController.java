package findindex.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * FindIndexの構築ワークフローの制御
 * @author ishizaki
 */
public interface FindIndexController {
	/**
	 * FindIndexの構築メソッド
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void createFindIndex() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException;
}
