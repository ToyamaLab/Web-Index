package findindex.service;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ExpectTrie関数の構築
 * 
 * @author ishizaki
 */
public interface CreateExpectTrie {
	/**
	 * ExpectTrie関数の構築メソッド
	 * @throws SQLException
	 * @throws UnsupportedEncodingException 
	 */
	public void createExpectTrie(ResultSet rset)throws SQLException, UnsupportedEncodingException;
}
