package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.impl.EntryDaoImpl;
import data.Entry;
import service.EntryService;

@Service
public class EntryServiceImpl implements EntryService {

	@Autowired
	private EntryDaoImpl entryDao;
	
	@Override
	@Transactional(readOnly = true)
	public Entry loadById(int wid, int eid) {
		return entryDao.findOne(wid, eid);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Entry> bulkLoad() {
		return entryDao.findAll();
	}
	
	public List<Entry> loadByWid(int wid) {
		return entryDao.findByWid(wid);
	}

}
