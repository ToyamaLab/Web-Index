package dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.CategoryMapper;
import dao.DatabaseDao;
import data.Category;

@Repository
public class CategoryDaoImpl implements DatabaseDao {
	
	@Autowired
	private CategoryMapper categoryMapper;
	
//	private Logger logger;

	@Override
	public void insert(Object insertData) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void delete(Object deleteData) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void update(Object updateData) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	public void bachInsert(List<Category> cidList, int wid) {
		for ( Category category : cidList ) {
			categoryMapper.insert(category);
			// コメントアウト外すと1回しか挿入されない
//			logger.info("Now inserting category_wix : wid = " + category.getWid() + " , cid = " + category.getCid());
		}
	}

}