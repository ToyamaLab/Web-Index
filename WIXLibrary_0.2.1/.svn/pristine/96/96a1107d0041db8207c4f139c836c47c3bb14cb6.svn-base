package dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import data.MetaInfo;

@MyMapper
public interface MetaInfoMapper {
	
	static final String INSERT_META_INFO = "INSERT INTO wix_file_meta_info (wid, name, author, comment, description, language, type) " +
			"VALUES (#{wid}, #{name}, #{author}, #{comment}, #{description}, #{language}, #{type})";
	
	static final String SELECT_KEY = "SELECT NEXTVAL('wix_file_meta_info_wid_seq')";
	
	static final String SELECT_BY_ID = "SELECT * FROM wix_file_meta_info WHERE wid = #{wid}";
	
	static final String SELECT_ALL = "SELECT * FROM wix_file_meta_info";
	
	static final String SELECT_LATEST_WID = "SELECT DISTINCT(wid) FROM wix_file_meta_info ORDER BY wid DESC LIMIT 1 OFFSET 0";
	
	@Insert(INSERT_META_INFO)
	// serial insert やーめた
//	@SelectKey(statement = SELECT_KEY, keyProperty = "wid", before = true, resultType = int.class)
	void insert(MetaInfo metaInfo);
	
	@Select(SELECT_BY_ID)
	MetaInfo findOne(int wid);
	
	@Select(SELECT_ALL)
	List<MetaInfo> findAll();
	
	@Select(SELECT_LATEST_WID)
	Integer findLatestWid();
}
