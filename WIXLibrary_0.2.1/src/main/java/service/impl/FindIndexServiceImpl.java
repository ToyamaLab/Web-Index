package service.impl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import data.Constant;
import data.Entry;
import data.ResultObject;
import service.FindIndexService;
import service.RmiFunction;

@Service
public class FindIndexServiceImpl implements FindIndexService {

	@Override
	public boolean findIndexUpdate(List<Entry> addTargets,
			List<Entry> eraseTargets)
					throws MalformedURLException, RemoteException, NotBoundException, SQLException, ClassNotFoundException {
		RmiFunction func = (RmiFunction) Naming.lookup(Constant.RMI_URL.getValue());
		List<ResultObject> _addTargets = this.valueOf(addTargets);
		List<ResultObject> _eraseTargets = this.valueOf(eraseTargets);
		
		if ( func.incrementUpdate(_addTargets, _eraseTargets) ) {
			return true;
		}
		
		return false;
	}

	@Override
	public void setSecurityManager() {
		if ( System.getSecurityManager() == null ) {
			System.setSecurityManager(new RMISecurityManager());
		}
	}
	
	private List<ResultObject> valueOf(List<Entry> entryList) {
		List<ResultObject> resultObjList = new ArrayList<ResultObject>();
		
		for ( Entry entry : entryList ) {
			ResultObject resultObj = new ResultObject(entry.getWid(), entry.getEid(), entry.getKeyword().length());
			resultObjList.add(resultObj);
		}
		
		return resultObjList;
	}

}
