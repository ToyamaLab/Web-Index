package dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.DatabaseDao;
import dao.PartEntryMapper;
import data.PartEntry;

@Repository
public class PartEntryDaoImpl implements DatabaseDao {
	
	@Autowired
	private PartEntryMapper partEntryMapper;

	@Override
	public void insert(Object insertData) {
		PartEntry partEntry = (PartEntry) insertData;
		partEntryMapper.insert(partEntry);
	}

	@Override
	public void delete(Object deleteData) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void update(Object updateData) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
