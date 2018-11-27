package dao;

import org.apache.ibatis.annotations.Insert;

import data.Category;

@MyMapper
public interface CategoryMapper {

//	static final String INSERT_CATEGORY = "INSERT INTO category (name, type) VALUES (#{name}, #{type})";
	
//	static final String INSERT_CATEGORY_TYPE = "INSERT INTO category_type (type) VALUES (#{type})";
	
	static final String INSERT_CATEGORY_WIX = "INSERT INTO category_wix (cid, wid) VALUES (#{cid}, #{wid})";
	
//	static final String DELETE_CATEGORY = "DELETE FROM category WHERE oid = #{oid}";
//	
//	static final String DELETE_CATEGORY_TYPE = "DELETE FROM category_type WHERE tid = #{tid}";
//	
//	static final String SELECT_BY_ID = "SELECT o.cid, o.name, t.name as tName FROM category o, category_type t WHERE o.cid = #{cid} AND o.type = t.tid";
//	
//	static final String SELECT_ALL = "SELECT o.cid, o.name, t.name as tName FROM category o, category_type t WHERE o.type = t.tid";
	
//	@Insert(INSERT_CATEGORY)
//	void insert(CATEGORY category);
	
//	@Insert(INSERT_CATEGORY_TYPE)
//	void insertType(@Param("type") String type);
	
	@Insert(INSERT_CATEGORY_WIX)
	void insert(Category category);
	
//	@Delete(DELETE_CATEGORY)
//	void delete(@Param("cid") int cid);
//	
//	@Delete(DELETE_CATEGORY_TYPE)
//	void deleteType(@Param("tid") int tid);
//	
//	@Select(SELECT_BY_ID)
//	Category findOne(@Param("cid") int cid);
//	
//	@Select(SELECT_ALL)
//	List<Category> findAll();
}
