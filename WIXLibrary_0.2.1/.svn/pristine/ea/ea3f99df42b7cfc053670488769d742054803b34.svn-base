package dao;

import org.apache.ibatis.annotations.Insert;

import data.PartEntry;

@MyMapper
public interface PartEntryMapper {
	
	static final String INSERT_PART_ENTRY = "INSERT INTO wix_file_part_entry(virtual_wid, entity_wid, entity_eid) VALUES(#{virtualWid}, #{entityWid}, #{entityEid})";
	
	@Insert(INSERT_PART_ENTRY)
	void insert(PartEntry partEntry);
	
}
