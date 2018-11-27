package findindex.data;
/**
 * TODO 説明
 * 
 * @author ishizaki
 *
 */
public class NodeForCreateFailure {
	
	private int node;
	private int preNode;
	private int[] text;
	private int length;

	public NodeForCreateFailure() {	
	}
	
	public NodeForCreateFailure(int i){	
		text = new int[i];
	}
	
	public void setNode(int i){
		node = i;
	}
	
	public void setPreNode(int i){
		preNode = i;
	}
	
	public void setText(int i,int j) {
		text[j] = i;	
	}
	
	public void setLength(int i) {
		length = i;	
	}
	
	public int getNode() {
		return node;
	}
	
	public void set(int length, int pre, int node) {
		setLength(length);
		setPreNode(pre);
		setNode(node);
	}
	
	public void set(int length, int pre, int node, int text1) {
		set(length, pre, node);
		setText(text1, 0);
	}
	
	public void set(int length, int pre, int node, int text1, int text2) {
		set(length, pre, node);
		setText(text1, 0);
		setText(text2, 1);
	}
	
	public void set(int length, int pre, int node, int text1, int text2, int text3) {
		set(length, pre, node);
		setText(text1, 0);
		setText(text2, 1);
		setText(text3, 2);
	}
	
	public void set(int length,int pre,int node,int text1,int text2,int text3,int text4){
		set(length, pre, node);
		setText(text1, 0);
		setText(text2, 1);
		setText(text3, 2);
		setText(text4, 3);
	}
	
	public void set(int length, int pre, int node, int text1, int text2, int text3, int text4, int text5) {
		set(length, pre, node);
		setText(text1, 0);
		setText(text2, 1);
		setText(text3, 2);
		setText(text4, 3);
		setText(text5, 4);
	}
	
	public void set(int length, int pre, int node, int text1, int text2, int text3, int text4, int text5, int text6) {
		set(length, pre, node);
		setText(text1, 0);
		setText(text2, 1);
		setText(text3, 2);
		setText(text4, 3);
		setText(text5, 4);
		setText(text6, 5);
	}
	public void set(int length, int pre, int node, int[] text){
		set(length, pre, node);
		for(int i=0;i<length;i++){
			this.text[i] = text[i];
		}
	}

	public int getPreNode() {
		return this.preNode;
	}

	public int[] getText() {
		return text;
	}

	public int getLength() {
		return length;
	}
}
