package dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.DatabaseDao;
import dao.MetaInfoMapper;
import data.MetaInfo;

@Repository
public class MetaInfoDaoImpl implements DatabaseDao {
	
	@Autowired
	private MetaInfoMapper metaInfoMapper;
	
	@Override
	public void insert(Object insertData) {
		MetaInfo metaInfo = (MetaInfo) insertData;
		metaInfoMapper.insert(metaInfo);
	}

	@Override
	public void delete(Object deleteData) {
	}

	@Override
	public void update(Object updateData) {
	}
	
	public MetaInfo loadById(int wid) {
		return metaInfoMapper.findOne(wid);
	}
	
	public List<MetaInfo> loadBulk() {
		return metaInfoMapper.findAll();
	}
	
	public Integer loadLatestWid() {
		return metaInfoMapper.findLatestWid();
	}
}