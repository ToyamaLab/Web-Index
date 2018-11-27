package dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.DatabaseDao;
import dao.EntryMapper;
import data.Entry;

@Repository
public class EntryDaoImpl implements DatabaseDao {
	
	@Autowired
	private EntryMapper entryMapper;
	
	private Logger logger;
	
	@Override
	public void insert(Object insertData) {
		Entry entry = (Entry) insertData;
		entryMapper.insert(entry);
	}

	@Override
	public void delete(Object deleteData) {
		Entry entry = (Entry) deleteData;
		entryMapper.delete(entry);
	}

	@Override
	public void update(Object updateData) {
	}
	
//	public void batchInsert(List<Entry> insertEntryList, int wid) {
	// wid作成済み
	public void batchInsert(List<Entry> insertEntryList) {
		int eid = 1;
		int wid = 1;
		
		boolean isDebugable = false;
		this.logger = Logger.getLogger(EntryDaoImpl.class);
		
		int total = insertEntryList.size();
		
		if ( total > 1000000 ) {
			isDebugable = true;
			logger.info("Insert large amounts of data has started.");
		}
		
		
		
		for ( Entry entry : insertEntryList ) {
			if ( entry.getWid() != wid ) {
				wid = entry.getWid();
				eid = 1;
			}
			entry.setEid(eid);
			entryMapper.insert(entry);
			eid++;
			
			if ( isDebugable && eid % 1000000 == 0 ) {
				logger.info("Now inserting entry : " + eid + " / " + total);
			}
		}
	}
	
	public void batchUpdate(List<Entry> insertEntryList, List<Entry> deleteEntryList) {
		for ( Entry entry : deleteEntryList ) {
			entryMapper.delete(entry);
		}
		for ( Entry entry : insertEntryList ) {
			entryMapper.insert(entry);
		}
	}
	
	public Entry findOne(int wid, int eid) {
		return entryMapper.findOne(wid, eid);
	}
	
	public List<Entry> findAll() {
		return entryMapper.findAll();
	}
	
	public List<Entry> findByWid(int wid) {
		return entryMapper.findByWid(wid);
	}
	
}