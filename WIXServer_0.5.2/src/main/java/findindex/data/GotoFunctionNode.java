package findindex.data;
/**
 * TODO 説明
 * 
 * @author ishizaki
 *
 */
public class GotoFunctionNode {
	
	private int[] check;
	private int[] base;
	private int[] dartsToBlock;
	
	public int dartsPointer;
	public int singleDartsPointer;
	public int singleDartsNextPointer;
	
	public GotoFunctionNode(int entryNum){
	 	dartsPointer = 302;
	   	singleDartsPointer = 300;
	   	//updateしない場合　*20
		int DARTS_NODE_NUM = entryNum * 20 + 5000;
		check = new int [DARTS_NODE_NUM];
		base = new int[DARTS_NODE_NUM];
		dartsToBlock = new int[DARTS_NODE_NUM];
		for(int i = 0 ; i < DARTS_NODE_NUM; i++) {
			check[i] = -1;
			base[i] = -1;
			dartsToBlock[i] = -1;
		}
	}
	
	public int gotoNext(int node, int trans) {//gotoがどこに行くか。失敗したら０
		int next = base[node] + trans;
		if(next < 0) {
			return 0;
		}
		if(node != check[next]) {
			next = 0;
		}
		return next;
	}
	
	public void printDartsArray() {
		System.out.println("======darts info=====");
    	System.out.println("single_darts_pointer = " + singleDartsPointer);
    	System.out.println("single_darts_next_pointer = " + singleDartsNextPointer);
    	System.out.println("darts_pointer = " + dartsPointer);
	}
	
	public int getCheck(int nowNode) {
		return check[nowNode];
	}
	
	public void setCheck(int nowNode, int parentNode) {
		this.check[nowNode] = parentNode;
		
	}
	
	public void setBase(int nowNode, int base) {
		this.base[nowNode] = base;
	}
	
	public void setDartsToBlock(int nowNode, int block) {
		this.dartsToBlock[nowNode] = block;
	}
	
	public int getBase(int nowNode) {
		return this.base[nowNode];
	}
	
	public int getDartsToBlock(int nowNode) {
		return this.dartsToBlock[nowNode];
	}
	
	public void reset(int nowNode){
		setDartsToBlock(nowNode,-1);
		setBase(nowNode,-1);
		setCheck(nowNode,-1);
	}

	public int getSingleDartsPointer() {
		return singleDartsPointer;
	}

	public int getDartsPointer() {
		return dartsPointer;
	}

	public void setDartsPointer(int i) {
		dartsPointer = i;
	}

	public void pointerCheck() {
		while(this.check[this.singleDartsPointer]!=-1){
			this.singleDartsPointer++;
		}
		if(this.singleDartsPointer >= this.dartsPointer){
			this.dartsPointer = this.singleDartsPointer+1;
		}
	}
}
