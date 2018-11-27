package controller;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import data.JsonModel;

public interface FindIndexController {
	
	JsonModel update(int wid) throws MalformedURLException, RemoteException, NotBoundException, SQLException, ClassNotFoundException;
	
}
