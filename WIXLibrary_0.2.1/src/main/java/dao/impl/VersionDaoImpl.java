package dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.DatabaseDao;
import dao.VersionMapper;
import data.Version;

@Repository
public class VersionDaoImpl implements DatabaseDao {
	
	@Autowired
	private VersionMapper versionMapper;
	
	@Override
	public void insert(Object insertData) {
		Version version = (Version) insertData;
		versionMapper.insert(version);
	}

	@Override
	public void delete(Object deleteData) {
	}

	@Override
	public void update(Object updateData) {
	}

	public int getLatestVersion(Object[] args) {
//		String sql = "SELECT max(version) FROM wix_file_version WHERE wid=?";
//		int version = jdbcTemplate.queryForObject(sql, args, Integer.class);
//		return version;
		return 0;
	}
	
	public int findOldHash(Version version) {
		return versionMapper.findOldHash(version);
	}
	
	public int findLatestVersion(int wid) {
		return versionMapper.findLatestVersion(wid);
	}
}
