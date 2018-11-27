package dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import data.Version;

@MyMapper
public interface VersionMapper {
	
	static final String INSERT_VERSION = "INSERT INTO wix_file_version (wid, version, revision_num) " +
			"VALUES (#{wid}, #{version}, #{revisionNum})";
	
	static final String SELECT_REVISION_NUM = "SELECT revision_num FROM wix_file_veresion WHERE wid = #{wid} AND version = #{version}";
	
	static final String SELECT_LATEST_VERSION = "SELECT max(version) FROM wix_file_version WHERE wid = #{wid}";
	
	@Insert(INSERT_VERSION)
	void insert(Version version);
	
	@Select(SELECT_REVISION_NUM)
	int findOldHash(Version version);
	
	@Select(SELECT_LATEST_VERSION)
	int findLatestVersion(int wid);
	
}
