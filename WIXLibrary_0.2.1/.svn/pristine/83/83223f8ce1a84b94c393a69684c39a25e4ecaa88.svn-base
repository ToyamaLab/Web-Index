package controller.impl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.FindIndexController;

import service.impl.EntryServiceImpl;
import service.impl.FindIndexServiceImpl;
import service.impl.UploadServiceImpl;

import data.Entry;
import data.JsonModel;

public class FindIndexControllerImpl implements FindIndexController{
	
	@Autowired
	EntryServiceImpl entryService;
	
	@Autowired
	UploadServiceImpl uploadService;
	
	@Autowired
	FindIndexServiceImpl findIndexService;
	
	@Override
	@RequestMapping(value = "/findindex/update/{wid}", method = RequestMethod.PUT, headers = { "Accept=application/json" })
	public @ResponseBody JsonModel update(
			@PathVariable("wid") int wid ) throws MalformedURLException, RemoteException, NotBoundException, SQLException, ClassNotFoundException {
		List<Entry> addTargets = entryService.loadByWid(wid);
		List<Entry> eraseTargets = new ArrayList<Entry>();
		
		// find index incremental update
		findIndexService.setSecurityManager();
		findIndexService.findIndexUpdate(addTargets, eraseTargets);
		
		JsonModel res = new JsonModel();
		res.setSuccess(true);
		res.setWid(wid);
		res.setFileName("");
		res.setMessage("Findindex update is comleted successfully.");
		return res;
	}
	
}
