package findindex.controller.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import core.dao.EntryDao;
import core.dao.PropertyFileDao;
import core.dao.SynonymDao;
import core.data.Constant;
import core.data.Entry;
import core.util.LogUtil;
import findindex.controller.FindIndexController;
import findindex.data.BlockData;
import findindex.data.DebugData;
import findindex.data.EntryData;
import findindex.data.ExpectTrieData;
import findindex.data.GotoFunctionNode;
import findindex.data.TmpGotoFunctionData;
import findindex.service.impl.CreateExpectTrieImpl;
import findindex.service.impl.CreateFailureImpl;
import findindex.service.impl.CreateTrieImpl;
import findindex.service.impl.EntryComparator;
import findindex.service.impl.InitDataImpl;


/**
 * FindIndex構築におけるワークフロー制御の実装
 * @author ishizaki
 */
public class FindIndexControllerImpl implements FindIndexController {
	
	private InitDataImpl initData;
	
	private PropertyFileDao propertyFileDao;
	
	private EntryDao entryDao;
	
	private SynonymDao synDao;
	
	private TmpGotoFunctionData node;
	
	private DebugData debug;
	
	public BlockData block;
	
	public EntryData entry;
	
	public GotoFunctionNode darts;
	
	public ExpectTrieData expect;
	
	public CreateTrieImpl createTrie;
	
	public CreateFailureImpl createFailure;
	
	public CreateExpectTrieImpl createExpect;
	
	private static LogUtil logger;
	
	public FindIndexControllerImpl(){
		this.initData = new InitDataImpl();
		this.propertyFileDao = new PropertyFileDao();
		this.entryDao = new EntryDao();
		this.debug = new DebugData();
		this.synDao = new SynonymDao();
		logger = new LogUtil(FindIndexControllerImpl.class);
	}

	@Override
	public void createFindIndex() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		// init process and db access
		long startMs = System.currentTimeMillis();
		
		Configuration prop = propertyFileDao.read();
		initData.init(prop);
		
		ResultSet rset = entryDao.read(prop.getString("QUERY"));
		
		ResultSet synonym = synDao.read(prop.getString("QUERY_SYNONYM"));
		
		long stopMs = System.currentTimeMillis();
		
		logger.debug("Init process and DB access done :"
				+ " time = " + (stopMs - startMs) + " ms"
			);
		
		List<Entry> list = new ArrayList<Entry>();
		
		while( rset.next() ){
			int wid = rset.getInt(Constant.ARRAY_WID.getInt());
			int eid = rset.getInt(Constant.ARRAY_EID.getInt());
			String keyword = rset.getString(Constant.ARRAY_WORD.getInt());
			String target = rset.getString(Constant.ARRAY_TARGET.getInt());
			
			Entry entry = new Entry(wid, eid, keyword, target);
			list.add(entry);
		}
		
		while( synonym.next() ){ 
			int wid = synonym.getInt(Constant.ARRAY_WID.getInt());
			int eid = synonym.getInt(Constant.ARRAY_EID.getInt());
			String keyword = synonym.getString(Constant.ARRAY_WORD.getInt());
			String target = synonym.getString(Constant.ARRAY_TARGET.getInt());
			
			Entry entry = new Entry(wid, eid, keyword, target);
			list.add(entry);

		}
		
		Collections.sort(list, new EntryComparator()); //全体でソート
		int entryNum = list.size();
		debug.setNumOfEntry(entryNum);
		
		logger.debug("numOfEntry = " + entryNum);
		
		// set up memory
		startMs = System.currentTimeMillis();
		setMemory(entryNum);
		stopMs = System.currentTimeMillis();
		
		logger.debug("Set up memory done :"
			+ " time = " + (stopMs - startMs) + " ms"
		);
		
		// create trie
		startMs = System.currentTimeMillis();
		createTrie = new CreateTrieImpl(node, block, entry, darts, debug);
		createTrie.createTrie(list);
		stopMs = System.currentTimeMillis();
		
		logger.debug("Create trie done :"
			+ " time = " + (stopMs - startMs) + " ms"
		);
		
		// create failure
		startMs = System.currentTimeMillis();
		createFailure = new CreateFailureImpl(block, darts, entry);
		createFailure.createFailure();
		stopMs = System.currentTimeMillis();
		
		logger.debug("Create failure done :"
			+ " time = " + (stopMs - startMs) + " ms"
		);
		
//		darts.printDartsArray();
		
		if ( Constant.UPDATE_ENABLED.getBool() ) {
			startMs = System.currentTimeMillis();
			createExpect = new CreateExpectTrieImpl(block, darts, expect, debug);
			createExpect.createExpectTrie(rset);
//			expect.printExpect();
			stopMs = System.currentTimeMillis();
			
			logger.debug("Create expect tree done :"
				+ " time = " + (stopMs - startMs) + " ms"
			);
		}
		
		rset.close();
		synonym.close();
		entryDao.close();
		synDao.close();
	}
	
	private void setMemory(int entryNum) {
		node = new TmpGotoFunctionData();
		block = new BlockData(entryNum);
		entry = new EntryData(entryNum);
		darts = new GotoFunctionNode(entryNum);
		
		if ( Constant.UPDATE_ENABLED.getBool() ) {
			expect = new ExpectTrieData(entryNum);
		}
	}
	
}	
