package dao;

import org.apache.ibatis.annotations.Insert;

import data.Tree;

@MyMapper
public interface TreeMapper {
	
	static final String INSERT_TREE = "INSERT INTO wix_file_tree(parent, child) VALUES(#{parent}, #{child})";
	
	@Insert(INSERT_TREE)
	void insert(Tree tree);
	
}
