package findindex.data;
/**
 * TODO 説明
 * 
 * @author ishizaki
 *
 */
public class TmpGotoFunctionData {
	
	final int BYTE = 256;
	private int doubleGoto[][];
	private int lastWord[];
	private int firstWord[];
	private int check[];
	private int checkWord[];
	private int nodeToBlock[];
	
	//private int numOfNode; 
	private int nodePointer;
	
	public TmpGotoFunctionData(){
		//numOfNode = 1;
		nodePointer = 1;
		int nodeNum = 5000;
		doubleGoto = new int[nodeNum][BYTE];//２次元配列のgoto
		nodeToBlock = new int [nodeNum];//２次元配列のノードがどのブロックか
		lastWord = new int[nodeNum];
		firstWord = new int[nodeNum];
		check = new int[nodeNum];
		checkWord = new int[nodeNum];
	}
	
	public int[][] test() {
		return this.doubleGoto;
	}
	
	public int gotoNext(int node, int trans){//gotoがどこに行くか。失敗したら０
		return this.doubleGoto[node][trans];	
	}
	
	public int getBlock(int nowNode){//現在のノードからブロックの値を返す
		return this.nodeToBlock[nowNode];
	}
	
	public int getFirstWord(int nowNode){
		return this.firstWord[nowNode];
	}	
	
	public int getLastWord(int nowNode){
		return this.lastWord[nowNode];
	}	
	
	public int getCheckWord(int nowNode){
		return this.checkWord[nowNode];
	}
	
	public int getCheck(int nowNode){
		return this.check[nowNode];
	}
	
	public int setNode(int nowNode, int nowWord, int nowBlock){
		if(getFirstWord(nowNode)==0) {
			this.firstWord[nowNode] = nowWord;
		}
		this.lastWord[nowNode] = nowWord;
		this.doubleGoto[nowNode][nowWord] = nodePointer;
		this.check[nodePointer] = nowNode;
		this.checkWord[nodePointer] = nowWord;
		//System.out.println("@@@@@@@="+nowBlock);
		setNodeToBlock(nodePointer,nowBlock);
		nodePointer++;
		//numOfNode++;
		return nodePointer - 1;
	}
	
	public void setNodeToBlock(int nowNode,int nowBlock){
		this.nodeToBlock[nowNode] = nowBlock;
	}

	public void setDoubleGoto(int node,int trans, int next) {
		this.doubleGoto[node][trans] = next;
	}

	public void reset(int node,int trans) {
		this.doubleGoto[node][trans] = 0;
		this.lastWord[node] = 0;
		this.firstWord[node] = 0;
		this.check[node] = 0;
		this.checkWord[node] = 0;
		this.nodeToBlock[node] = 0;
		this.nodePointer--;
	}
	
	public void reset(int node){
		this.lastWord[node] = 0;
		this.firstWord[node] = 0;
		this.check[node] = 0;
		this.checkWord[node] = 0;
		this.nodeToBlock[node] = 0;
		this.nodePointer--;
	}

	public int getNodeToBlock(int nowNode) {
		return this.nodeToBlock[nowNode];
	}

	public void setLastWord(int nowNode, int i) {
		this.lastWord[nowNode] = i;
	}
	
	public void setFirstWord(int nowNode, int i) {
		this.firstWord[nowNode] = i;
	}
}
