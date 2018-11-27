package dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.DatabaseDao;
import dao.TreeMapper;
import data.Tree;

@Repository
public class TreeDaoImpl implements DatabaseDao {

	@Autowired
	private TreeMapper treeMapper;
	
	@Override
	public void insert(Object insertData) {
		Tree tree = (Tree) insertData;
		treeMapper.insert(tree);
	}

	@Override
	public void delete(Object deleteData) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void update(Object updateData) {
		// TODO 自動生成されたメソッド・スタブ

	}
	
	public void batchInsert(List<Tree> trees) {
		for ( Tree tree : trees ) {
			treeMapper.insert(tree);
		}
	}

}
