package service.impl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import service.UploadService;
import dao.impl.CategoryDaoImpl;
import dao.impl.EntryDaoImpl;
import dao.impl.MetaInfoDaoImpl;
import dao.impl.PartEntryDaoImpl;
import dao.impl.TreeDaoImpl;
import dao.impl.VersionDaoImpl;
import data.Category;
import data.Constant;
import data.Entry;
import data.MetaInfo;
import data.Tree;
import data.Version;

@Service
public class UploadServiceImpl implements UploadService {

	private Logger logger;
	
	@Autowired
	private EntryDaoImpl entryDao;
	
	@Autowired
	private MetaInfoDaoImpl metaInfoDao;

	@Autowired
	private VersionDaoImpl versionDao;
	
	@Autowired
	private TreeDaoImpl treeDao;
	
	@Autowired
	private PartEntryDaoImpl partEntryDao;
	
	@Autowired
	private CategoryDaoImpl categoryDao;
	
	@Autowired
	private FindIndexServiceImpl findIndexService;
	
	private List<Entry> insertEntryList;
	
	private List<Entry> deleteEntryList;
	
	private List<Entry> entryList;
	
	private List<Tree> trees;
	
	public UploadServiceImpl() {
		this.logger = Logger.getLogger(UploadServiceImpl.class);
	}
	
	@Override
	@Transactional(
			propagation=Propagation.REQUIRED, 
			isolation=Isolation.READ_COMMITTED,
			readOnly=false,
			rollbackFor=RuntimeException.class)
	public void newWixFile(MetaInfo metaInfo) throws MalformedURLException, RemoteException, NotBoundException, SQLException, ClassNotFoundException {
		// queueがなくなるまでmetainfo insert
		while ( metaInfo != null ) {
			metaInfoDao.insert(metaInfo);
			metaInfo = metaInfo.getChild();
		}
		
		// tree insert
		treeDao.batchInsert(trees);
		
		// entry insert
		entryDao.batchInsert(entryList);
	}

	@Override
	@Transactional(
			propagation=Propagation.REQUIRED, 
			isolation=Isolation.READ_COMMITTED,
			readOnly=false,
			rollbackFor=RuntimeException.class)
	public void updateWixFile(String diffStr, Version veresion) 
			throws MalformedURLException, RemoteException, NotBoundException, SQLException, ClassNotFoundException {
		this.insertEntryList = new ArrayList<Entry>();
		this.deleteEntryList = new ArrayList<Entry>();
		
		this.newListForUpdate(diffStr);
		
		entryDao.batchUpdate(insertEntryList, deleteEntryList);
		
		//Findindex incremental update
		findIndexService.setSecurityManager();
		findIndexService.findIndexUpdate(insertEntryList, deleteEntryList);
		
		logger.debug(
			"UPDATE INFO : "
			+ " deleteNum = " + this.deleteEntryList.size()
			+ " insertNum = " + this.insertEntryList.size()
		);
		
		versionDao.insert(veresion);
	}

	public void insertVersion(Version version) {
		versionDao.insert(version);
	}
	
	private void newListForUpdate(String diffStr) {
		String[] parseNewLine = diffStr.split(Constant.NEWLINE_CHARACTER.getValue());
		for ( int i = 5; i < parseNewLine.length; i++ ) {//最初のmeta情報 (5行) は読まなくておｋ
			if ( parseNewLine[i].startsWith(Constant.ADD_PREFIX.getValue()) ) {
				myParser(parseNewLine[i], Constant.ADD_PREFIX.getValue());
			} else if ( parseNewLine[i].startsWith(Constant.DELETE_PREFIX.getValue()) ) {
				myParser(parseNewLine[i], Constant.DELETE_PREFIX.getValue());
			}
		}
	}

	private void myParser(String diffStrElem, String symbol) {
		String[] diffEntry = diffStrElem.substring(1).split(Constant.CSV_DELIMITER.getValue());
		Entry entry = new Entry();
		entry.setWid(Integer.parseInt(diffEntry[0]));
		entry.setEid(Integer.parseInt(diffEntry[1]));
		entry.setKeyword(diffEntry[2]);
		entry.setTarget(diffEntry[3]);
		entry.setClickCount(Integer.parseInt(diffEntry[4]));
		if ( "+".equals(symbol) ) {
			this.insertEntryList.add(entry);
		} else if ( "-".equals(symbol) ) {
			this.deleteEntryList.add(entry);
		}
	}

	public void setEntryList(List<Entry> entryList) {
		this.entryList = entryList;
	}

	public List<Tree> getTrees() {
		return trees;
	}

	public void setTrees(List<Tree> trees) {
		this.trees = trees;
	}
	
	@Override
	@Transactional(
			propagation=Propagation.REQUIRED, 
			isolation=Isolation.READ_COMMITTED,
			readOnly=false,
			rollbackFor=RuntimeException.class)
	public void insertCategoryWix(List<Integer> cidList, int wid) {
		List<Category> cList = new ArrayList<Category>();
		for ( Integer cid : cidList ) {
			Category category = new Category();
			category.setWid(wid);
			category.setCid(cid);
			cList.add(category);
		}
		categoryDao.bachInsert(cList, wid);
	}
}
