package findindex.data;
/**
 * TODO　説明
 * 
 * @author ishizaki
 *
 */
public class BlockData {
	
	private int blockToNode[];
	private int failure[];
	private int blockToOutput[];
	
	private int numOfBlock; 
	private int blockPointer;
	
	private int[] blockLength;
	private int[] failureLength;
	
	public BlockData(int entryNum){
	   	numOfBlock = 1;
	   	blockPointer = 1;
		//updateしない場合 *15 あればおｋ
		int BLOCK_NUM = entryNum * 15 + 5000;
		
		blockToNode = new int [BLOCK_NUM];
		failure = new int [BLOCK_NUM];
		blockToOutput = new int [BLOCK_NUM];
		blockLength = new int [BLOCK_NUM];
		failureLength = new int [BLOCK_NUM];
	}
	
	public int getBlockToNode(int nowBlock) {
		return this.blockToNode[nowBlock];
	}
	
	public int getBlockPointer() {
		return this.blockPointer;
	}
	
	public void setBlock(int nowNode, int nowLength) {
		this.blockToNode[blockPointer] = nowNode;	 
		this.blockLength[blockPointer] = nowLength;
		//System.out.println("block = "+blockPointer);
		this.blockPointer++;
		this.numOfBlock++;
	}
	
	public int getBlockToOutput(int block) {
		return this.blockToOutput[block];
	}
	
	public void setBlockToOutput(int nowBlock, int entryPointer) {
		this.blockToOutput[nowBlock] = entryPointer;
	}
	
	public int getNumOfBlock() {
		return this.numOfBlock;
	}
	
	public void setFailure(int nowBlock,int i){
		this.failure[nowBlock] = i;
	}
	
	public void setFailureLength(int nowBlock, int i) {
		this.failureLength[nowBlock] = i;
	}
	
	public int getFailure(int nowBlock){
		return this.failure[nowBlock];
	}
	
	public int getFailureLength(int nowBlock){
		return this.failureLength[nowBlock];
	}
	
	public int getBlockLength(int nowBlock) {
		return this.blockLength[nowBlock];
	}
	
	public void setBlockToNode(int nowBlock, int node) {
		this.blockToNode[nowBlock] = node;
	}
	public int getSize(){
		return this.failure.length;
	}
	public void printBlock(){
		System.out.println("numOfBlock = " + numOfBlock);
	}
}
