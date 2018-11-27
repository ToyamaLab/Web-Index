package core.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import core.data.Entry;
import core.data.Product;
import findindex.FindIndex;
import findindex.controller.impl.FindIndexControllerImpl;
import findindex.service.impl.FinderImpl;
import findindex.service.impl.UpdateImpl;

public class RmiFunctionImpl implements RmiFunction{

	public static int nowAC;
	
	public static boolean ansflg;
	
	public static boolean updflg;
	
	private FinderImpl find = new FinderImpl();
	
	private UpdateImpl updateImpl = new UpdateImpl();

	public RmiFunctionImpl() throws RemoteException {
		super();
	}

	@Override
	public List<Product> find(String ppbody, String bookmarkedWIX, int minLength) throws RemoteException, FileNotFoundException, UnsupportedEncodingException, IOException {
		// index構築中判定
		if ( !ansflg ){
			return null;
		}
		
		if ( nowAC == 1 ) {
			return find.answer(FindIndex.ac_1, ppbody, bookmarkedWIX, minLength);
		} else {
			return find.answer(FindIndex.ac_2, ppbody, bookmarkedWIX, minLength);
		}
	}


	@Override
	public int bufferingUpdate() throws Exception {
		// update中判定
		if ( !updflg ) {
			return 0;
		}
		
		updflg = false;
		
		if ( nowAC == 1 ) {
			FindIndex.ac_2 = new FindIndexControllerImpl();
			FindIndex.ac_2.createFindIndex();
			
			nowAC = 2;
			
			FindIndex.ac_1 = null;
		} else if ( nowAC == 2 ) {
			FindIndex.ac_1 = new FindIndexControllerImpl();
			FindIndex.ac_1.createFindIndex();
			
			nowAC = 1;
			FindIndex.ac_2 = null;
		}
		
		updflg = true;
		
		return 1;
	}


	@Override
	public int reload() throws Exception {
		ansflg = false;
		FindIndex.ac_1 = null;
		FindIndex.ac_2 = null;
		FindIndex.ac_1 = new FindIndexControllerImpl();
		
		FindIndex.ac_1.createFindIndex();

		nowAC = 1;
		ansflg = true;
		return 1;
	}

	@Override
	public boolean incrementUpdate(List<Entry> addTarget, List<Entry> eraseTarget) throws RemoteException, SQLException, ClassNotFoundException {
		if ( updflg ) {
			if ( nowAC == 1 ) {
				updateImpl.doUpdate(eraseTarget, addTarget, FindIndex.ac_1);
			} else if ( nowAC == 2 ) {
				updateImpl.doUpdate(eraseTarget, addTarget, FindIndex.ac_2);
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public List<List<Product>> find(String[] ppBody, String bookmarkedWIX,
			int minLength) throws RemoteException, UnsupportedEncodingException {
		// index構築中判定
		if ( !ansflg ){
			return null;
		}
		
		if ( nowAC == 1 ) {
			return find.answer_2(FindIndex.ac_1, ppBody, bookmarkedWIX, minLength);
		} else {
			return find.answer_2(FindIndex.ac_2, ppBody, bookmarkedWIX, minLength);
		}
	}

}
