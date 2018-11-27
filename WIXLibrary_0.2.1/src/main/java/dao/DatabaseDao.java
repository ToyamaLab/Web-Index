package dao;

public interface DatabaseDao {
	/**
	 * @param insertData : DBにインサートするオブジェクト
	 */
	public void insert(Object insertData);
	
	public void delete(Object deleteData);
	
	public void update(Object updateData);
	
}