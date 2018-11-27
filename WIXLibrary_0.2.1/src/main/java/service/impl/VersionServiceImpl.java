package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.impl.VersionDaoImpl;

import service.VersionService;

@Service
public class VersionServiceImpl implements VersionService {

	@Autowired
	VersionDaoImpl versionDao;
	
	@Override
	@Transactional(readOnly = true)
	public int loadLatestVersion(int wid) {
		return versionDao.findLatestVersion(wid);
	}

}
