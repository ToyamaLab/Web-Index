package service;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import data.Entry;

public interface FindIndexService {

	void setSecurityManager();
	
	boolean findIndexUpdate(List<Entry> addTargets, List<Entry> eraseTargets) throws MalformedURLException, RemoteException, NotBoundException, SQLException, ClassNotFoundException;
	
}
