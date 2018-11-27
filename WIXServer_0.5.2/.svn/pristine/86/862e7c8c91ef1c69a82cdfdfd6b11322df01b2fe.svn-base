package findindex.data;

public class ExpectTrieData {
	public int numOfBlock; 
	public int blockPointer;
	private int numOfOutput;
	private int outputPointer;
	
	private int dartsPointer;
	public int singleDartsPointer;
	public int numOfDartsNode;
	
	private int basePointer[];//ブロックからexpブロック（自分がルートとなる木）のポインタ

	//exp_darts
	private int[] check;
	private int[] base;
	private int[] dartsToBlock;
	private int[] blockToDarts;
	
	private int[] siblingNext;
	private int[] siblingPre;
	private int[] child;

	private int[] outputValue;//ブロック番号をもつ
	private int[] outputIndex;//インデックス
	private int[] outputNext;//
	
	public ExpectTrieData(int entryNum){
		numOfBlock = 0; 
		blockPointer = 1;
		numOfOutput = 0;
		outputPointer = 1;	
		dartsPointer = 302;
		singleDartsPointer = 300;
		numOfDartsNode = 0;
		
		//updateしない場合 *20
		int EXP_DARTS_NODE_NUM = (entryNum * 80 + 5000) * 4;
		//updateしない場合 *20
		int EXP_BLOCK_NUM = (entryNum * 80 + 5000) * 4;
		//updateしない場合 * 15
		int BLOCK_NUM = entryNum * 15 + 5000;
		
		basePointer = new int [BLOCK_NUM];//ブロックからexpブロック（自分がルートとなる木）のポインタ
		base = new int[EXP_DARTS_NODE_NUM];//exp２次元配列のgoto
		check = new int[EXP_DARTS_NODE_NUM];
	
		siblingNext = new int [EXP_BLOCK_NUM];
		siblingPre = new int [EXP_BLOCK_NUM];
		child = new int [EXP_BLOCK_NUM];
	
		dartsToBlock = new int [EXP_DARTS_NODE_NUM];//２次元配列のノードがどのブロックか				
		blockToDarts = new int [EXP_BLOCK_NUM];//ブロックが複数バイト文字の場合末端のノードを指す

		outputValue = new int [BLOCK_NUM];//ブロック番号をもつ
		outputIndex = new int [EXP_BLOCK_NUM];//インデックス
		outputNext = new int [BLOCK_NUM];//
		
		for(int i=0;i<EXP_DARTS_NODE_NUM;i++){
			check[i] = -1;
			base[i] = -1;
			dartsToBlock[i] = -1;
		}
	}
	public int getBasePointer(int block){
		return this.basePointer[block];
	}
	public void setBasePointer(int block, int i){
		this.basePointer[block] = i;
	}
	public int getCheck(int node){
		return this.check[node];
	}
	public int getBase(int node){
		return this.base[node];
	}
	
	public void pointerCheck(){//空いているポインタ探し
		while(this.check[this.singleDartsPointer]!=-1){
			this.singleDartsPointer++;
		}
		if(this.singleDartsPointer >= this.dartsPointer){
			this.dartsPointer = this.singleDartsPointer+1;
		}
	}
	public void setNewBasePointer(int block) {
		this.basePointer[block] = this.blockPointer;//ベース値の設定（ブロックの値を持つ）
		this.blockToDarts[this.blockPointer] = this.singleDartsPointer;//ブロックからノードへ
		this.dartsToBlock[this.singleDartsPointer] = this.blockPointer;
		this.check[this.singleDartsPointer] = -2;
		
		this.singleDartsPointer++;
		this.numOfDartsNode++; 
		this.numOfBlock ++;
		this.blockPointer ++;
		
	}
	public int getBlockToDarts(int block) {
		return this.blockToDarts[block];
	}
	public void setBlockToDarts(int block, int i){
		this.blockToDarts[block] = i;
	}
	public int gotoNext(int node,int trans){//gotoがどこに行くか。失敗したら０
		int next = base[node]+trans;
		if(next < 0){
			return 0;
		}
		if(node != check[next]){
			next = 0;
		}
		return next;		
	}
	public int getDartsToBlock(int node) {
		return this.dartsToBlock[node];
	}
	public void setDartsToBlock(int node, int block) {
		this.dartsToBlock[node] = block;
	}
	public void setNewBlock(int node, int preBlock) {
		this.dartsToBlock[node] = this.blockPointer;
		this.blockToDarts[this.blockPointer] = node;	 
    	//sibling とか生成。
    	if(this.child[preBlock]==0){
    		this.child[preBlock] = this.blockPointer;
    		this.siblingNext[this.blockPointer] = this.blockPointer;
    		this.siblingPre[this.blockPointer] = this.blockPointer;
    	}else{
    		int child = this.child[preBlock];
    		this.child[preBlock] = this.blockPointer;
    		this.siblingNext[this.blockPointer] = child;
    		this.siblingPre[this.blockPointer] = this.blockPointer;
    		this.siblingPre[child] = this.blockPointer;
    	}
    	this.blockPointer++;
    	this.numOfBlock++;
	}
	public void setNewOutput(int node, int output) {
		int nowOutputIndex = this.outputIndex[this.getDartsToBlock(node)];
		if(this.outputIndex[this.getDartsToBlock(node)]==0){
			this.outputIndex[this.getDartsToBlock(node)] = this.outputPointer;
			this.outputValue[this.outputPointer] = output;
		}else{
			while(this.outputNext[nowOutputIndex] != 0){//つまり次もネクストある
				nowOutputIndex = this.outputNext[nowOutputIndex];
			}
			this.outputValue[this.outputPointer] = output;
			this.outputNext[nowOutputIndex] = this.outputPointer;
		}
		this.outputPointer++;
		this.numOfOutput++;
		
	}
	public int getSingleDartsPointer() {
		return this.singleDartsPointer;
	}
	public void setBase(int node, int i) {
		this.base[node] = i;
	}
	public void setCheck(int node, int i){
		this.check[node] = i;
	}
	public int getDartsPointer() {
		return this.dartsPointer;
	}
	public void setDartsPointer(int i){
		this.dartsPointer = i;
	}
	
	public void printExpect(){
		System.out.println("======expect trie info=====");
		System.out.println("single_darts_pointer = " + singleDartsPointer);
    	System.out.println("darts_pointer = " + dartsPointer);
    	System.out.println("numOfDartsNode = " + numOfDartsNode);
    	System.out.println("numOfBlock = " + numOfBlock);
    	System.out.println("numOfOutput = " + numOfOutput);
	}
	public int getOutputIndex(int block) {
		return this.outputIndex[block];
	}
	
	public int getBlockPointer() {
		return blockPointer;
	}
	public int getChild(int block) {
		return this.child[block];
	}
	public void setChild(int block, int i) {
		this.child[block] = i;
	}
	public int getOutputValue(int entry) {
		return this.outputValue[entry];
	}
	public int getOutputNext(int entry) {
		return this.outputNext[entry];
	}
	public int getSiblingNext(int block) {
		return this.siblingNext[block];
	}

}
