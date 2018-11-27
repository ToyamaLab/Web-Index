package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.impl.MetaInfoDaoImpl;
import data.MetaInfo;
import service.MetaInfoService;

@Service
public class MetaInfoServiceImpl implements MetaInfoService {

	@Autowired
	private MetaInfoDaoImpl metaInfoDao;
	
	@Override
	@Transactional(readOnly = true)
	public MetaInfo loadById(int wid) {
		return metaInfoDao.loadById(wid);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MetaInfo> bulkLoad() {
		return metaInfoDao.loadBulk();
	}
	
	public Integer loadLatestWid() {
		return metaInfoDao.loadLatestWid();
	}

}
