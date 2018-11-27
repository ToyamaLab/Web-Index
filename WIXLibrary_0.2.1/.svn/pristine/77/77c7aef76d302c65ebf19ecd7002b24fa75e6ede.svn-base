package service;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import data.MetaInfo;
import data.Version;

public interface UploadService {
	
	void newWixFile(MetaInfo metaInfo) throws Exception;
	
	void insertCategoryWix(List<Integer> cidList, int wid);
	
	void updateWixFile(String diffStr, Version version) throws MalformedURLException, RemoteException, NotBoundException, SQLException, ClassNotFoundException;
	
}
