package findindex.data;
/**
 * デバッグ用のデータ保持オブジェクト
 * 
 * @author ishizaki
 */
public class DebugData {
	//TODO 説明
	private static int numOfDoubleNode = 1;
	private static int numOfSingleNode = 0;
	private static int numOfBlock = 1;
	private static int numOfEntry = 0;
	
	public int getNumOfDoubleNode() {
		return numOfDoubleNode;
	}
	public void setNumOfDoubleNode(int numOfDoubleNode) {
		DebugData.numOfDoubleNode = numOfDoubleNode;
	}
	
	public int getNumOfSingleNode() {
		return numOfSingleNode;
	}
	public void setNumOfSingleNode(int numOfSingleNode) {
		DebugData.numOfSingleNode = numOfSingleNode;
	}
	
	public int getNumOfBlock() {
		return numOfBlock;
	}
	public void setNumOfBlock(int numOfBlock) {
		DebugData.numOfBlock = numOfBlock;
	}
	
	public int getNumOfEntry() {
		return numOfEntry;
	}
	public void setNumOfEntry(int numOfEntry) {
		DebugData.numOfEntry = numOfEntry;
	}
}
