package findindex.data;

import core.data.Product;


public class EntryData {
	private Product[] outputValue;
	private int outputNext[];
	private int entryPointer;
	private int numOfOutput;
	
	public EntryData(int entryNum){
		entryNum = entryNum+50000;
		numOfOutput = 0;
	   	entryPointer = 1;//都合上１からが必要
		outputValue = new Product [entryNum];
		outputNext = new int [entryNum];
	}

	public int getEntryPointer() {
		return this.entryPointer;
	}

	public void setOutput(Product obj) {
		this.outputValue[entryPointer] = obj;
		entryPointer++;	
		numOfOutput++;
	}

	public void setInnerOutputNext(int entry, Product obj) {
		this.outputValue[entry].setNext(obj);
		numOfOutput++;
	}
	public void setOuterOutputNext(int entry, int nextEntry){
		this.outputNext[entry] = nextEntry;
	}

	public Product getOutputValue(int nowEntry) {
		// TODO Auto-generated method stub
		return this.outputValue[nowEntry];
	}
	public int getOutputNext(int nowEntry){
		return this.outputNext[nowEntry];
	}

	public void setOutputValue(int nowEntry, Product obj) {
		this.outputValue[nowEntry] = obj;
		if(obj == null){
			numOfOutput--;
			return;
		}
		numOfOutput++;
	}
	public void printEntry(){
		System.out.println("numOfOutput = "+numOfOutput);
	}
}
