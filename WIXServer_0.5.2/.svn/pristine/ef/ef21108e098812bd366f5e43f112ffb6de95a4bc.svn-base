package core.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import core.data.Entry;
import core.data.Product;

/**
 * Find処理
 * 
 * @author ishizaki
 */
public interface RmiFunction extends Remote{
	
	/**
	 * find process
	 * このメソッドはリモートメソッドとしてrmiregistryに登録する。
	 * @param ppBody タグ抽出後のHTML文書
	 * @param bookmarkedWIX ブックマーク情報
	 * @param minLength アタッチするキーワードの最短文字数
	 * @return
	 * @throws RemoteException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	List<Product> find(String ppBody, String bookmarkedWIX, int minLength) throws RemoteException, FileNotFoundException, UnsupportedEncodingException, IOException;
	
	List<List<Product>> find(String[] ppBody, String boolmarkedWIX, int minLength) throws RemoteException, UnsupportedEncodingException;
	
	/**
	 * buffering update
	 * @throws RemoteException
	 * @throws ParserConfigurationException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @return
	 * @throws Exception 
	 */
	int bufferingUpdate( ) throws RemoteException, ParserConfigurationException,SQLException, ClassNotFoundException, FileNotFoundException, IOException, Exception;
	
	/**
	 * reload
	 * @throws RemoteException
	 * @throws ParserConfigurationException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @return
	 * @throws Exception 
	 */
	int reload( ) throws RemoteException, ParserConfigurationException, SQLException, ClassNotFoundException, FileNotFoundException, IOException, Exception;
	
	/**
	 * 差分更新
	 * @param addTarget
	 * @param eraseTarget
	 * @throws RemoteException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return
	 */
	boolean incrementUpdate(List<Entry> addTarget, List<Entry> eraseTarget) throws RemoteException, SQLException, ClassNotFoundException;
}
