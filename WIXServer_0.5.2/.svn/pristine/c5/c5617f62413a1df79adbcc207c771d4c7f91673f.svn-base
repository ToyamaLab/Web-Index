package findindex.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import core.data.Product;
import findindex.controller.impl.FindIndexControllerImpl;

/**
 * Find本体
 *
 * @author ishizaki
 */
public interface Finder2 {
	/**
	 * Findメソッド
	 *
	 * @param ac	ACのそれ
	 * @param ppbody	入力テキスト
	 * @param bookmarkedWIX　ブックマークidの集合
	 *
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public List<Product> answer(FindIndexControllerImpl ac, String ppbody,String bookmarkedWIX, int minLength) throws FileNotFoundException, UnsupportedEncodingException,IOException;
}
