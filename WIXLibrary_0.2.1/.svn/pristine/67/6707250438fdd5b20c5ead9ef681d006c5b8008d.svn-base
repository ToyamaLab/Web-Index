package dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import data.Entry;

@MyMapper
public interface EntryMapper {

	static final String INSERT_ENTRY = "INSERT INTO wix_file_entry (wid, eid, keyword, target, click_count) " +
			"VALUES (#{wid}, #{eid}, #{keyword}, #{target}, #{clickCount})";
	
	static final String DELETE_ENTRY = "DELETE FROM wix_file_entry WHERE wid = #{wid} AND eid = #{eid}";
	
	static final String SELECT_BY_ID = "SELECT * FROM wix_file_entry WHERE wid = #{wid} AND eid = #{eid}";
	
	static final String SELECT_ALL = "SELECT * FROM wix_file_entry";
	
	static final String SELECT_BY_WID = "SELECT * FROM wix_file_entry WHERE wid = #{wid}";
	
	@Insert(INSERT_ENTRY)
	void insert(Entry entry);
	
	@Delete(DELETE_ENTRY)
	void delete(Entry entry);
	
	@Select(SELECT_BY_ID)
	Entry findOne(@Param("wid") int wid, @Param("eid") int eid);
	
	@Select(SELECT_ALL)
	List<Entry> findAll();
	
	@Select(SELECT_BY_WID)
	List<Entry> findByWid(int wid);
}
