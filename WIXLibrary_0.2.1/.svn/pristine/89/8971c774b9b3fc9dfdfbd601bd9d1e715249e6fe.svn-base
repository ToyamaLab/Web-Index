package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import data.ResultObject;

public interface RmiFunction extends Remote{
	
	boolean incrementUpdate(List<ResultObject> addTarget, List<ResultObject> eraseTarget) throws RemoteException, SQLException, ClassNotFoundException;
	
}
