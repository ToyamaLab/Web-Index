package findindex.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import core.data.Entry;
import core.data.Product;
import findindex.controller.impl.FindIndexControllerImpl;
import findindex.data.BlockData;
import findindex.data.EntryData;
import findindex.data.ExpectTrieData;
import findindex.data.GotoFunctionNode;
import findindex.data.NodeForCreateFailure;
import findindex.service.Update;

public class UpdateImpl implements Update {
	private int rFailCount_1;
	private int rFailCount_2;
	private int rFailCount_3;
	private FindIndexControllerImpl ac;
	private BlockData block;
	private EntryData entry;
	private GotoFunctionNode darts;
	private ExpectTrieData expect;
	@Override
	public int doUpdate(List<Entry> addTarget, List<Entry> eraseTarget, FindIndexControllerImpl ac) {	
		this.ac = ac;
		this.block = ac.block;
		this.entry = ac.entry;
		this.darts = ac.darts;
		this.expect = ac.expect;
    	Date d = new Date();
		long st = d.getTime();
		
		erase(eraseTarget);
		add(addTarget);
		
	    d = new Date();
	    long en = d.getTime();
	    long time = en - st;
	    
	    System.out.println("======update完了=============");
	   	System.out.println("load time is "+time +"ms");
	   	ac.darts.printDartsArray();
    	System.out.println("");
	   	ac.expect.printExpect();
	   	System.out.println("");
    	System.out.println("新規追加ブロック数 = "+rFailCount_1);
    	System.out.println("追加により変更したFailure数 = "+rFailCount_3);
    	System.out.println("OUTPUT_INDEXのみ変更 = "+rFailCount_2);
		return 1;
	}

    public void erase(List<Entry> eraseTarget) {
    	String keyword;
    	int wid;
    	int eid;
    	
    	System.out.println("erase start ...");
    	
    	for ( int i = 0; i < eraseTarget.size(); i++ ) {//１エントリーループ
    		keyword = eraseTarget.get(i).getKeyword();
    		wid = eraseTarget.get(i).getWid();
    		eid = eraseTarget.get(i).getEid();
    		
        	int nowNode = 0;//初期化
        	boolean isTrans = true;
    		
    		byte[] byteWord = null;
    		for ( int j = 0; j < keyword.length(); j++ ) {//文字ループ
    			try {
    				byteWord = keyword.substring(j, j + 1).getBytes("UTF-8");
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}
    			int[] intWord = new int[byteWord.length];
    			for ( int k = 0; k < byteWord.length; k++ ) {
    				if ( byteWord[k] < 0 ) {
    					intWord[k] = byteWord[k] + 256;
    				} else {
    					intWord[k] = byteWord[k];
    				}
    			}
    		
    			for ( int x = 0; x < intWord.length; x++ ) { //バイトループ   
    				int pre_node = ac.darts.gotoNext(nowNode, intWord[x]);
    				if ( pre_node == 0 ) {//遷移失敗
    					isTrans = false;
    					break;
    				} else {//遷移成功
    					nowNode = pre_node;
    				}
    			}//バイトループ
    			if ( isTrans == false ) {
    				break;
    			}
    		}//文字ループ
    		if ( isTrans == true ) {//遷移成功しているのでアウトプットの削除
    			int nowBlock = darts.getDartsToBlock(nowNode);
    			int nowOutputIndex = block.getBlockToOutput(nowBlock);
    			
    			if ( nowOutputIndex != 0 ) {//アウトプットあれば削除したい
    				int encount = 0;
    				boolean isDeleteTarget = false;
        			Product nowOutput = this.entry.getOutputValue(nowOutputIndex);
        			Product preEntry = nowOutput;
        			//boolean delflag2 = false;
        			if ( nowOutput != null ) {
        				encount++;
        				if ( eid == nowOutput.getEid() && wid == nowOutput.getWid() ) {
        					isDeleteTarget = true;
        					if ( encount > 1 ) {
        						//delflag2 = true;
        						preEntry.setNext(nowOutput.getNext());
        						nowOutput = null;
        						break;
        					}
        				}
        				preEntry = nowOutput;
        				nowOutput = nowOutput.getNext();
        			}
    				//if(encount>1 && isDeleteTarget && delflag2==false){
        			if ( encount > 1 && isDeleteTarget ) {
    					 nowOutput = this.entry.getOutputValue(nowOutputIndex);
    					 this.entry.setOutputValue(nowOutputIndex, nowOutput.getNext());
    					 nowOutput = null;
    				}
    				if ( encount == 1 && isDeleteTarget ) {//エントリー１個しかないのでそこ削除
    					/**
    					 * １件しかないので、自分のvalueの削除、nextの削除、outputindexの貼り直し。
						 * 自分をfailしているアウトプットのインデックスを貼り直し。
						 * しかしそのブロックがnow_output_indexと違う場合はネクストを貼り直し。
    					 */
    					this.entry.setOutputValue(nowOutputIndex, null);    				    					
    					this.entry.setOuterOutputNext(nowOutputIndex, 0);
						int nextOutputIndex = block.getBlockToOutput(block.getFailure(nowBlock));
						//System.out.println("aaaa pre = "+output_block_index[now_block]+";next = "+next_output_index);
						block.setBlockToOutput(nowBlock, nextOutputIndex);
						Queue<Integer> childBlocks = new LinkedList<Integer>();
						//System.out.println("exp_root = "+exp_base[now_block]+";now_block = "+now_block);
						expGetChildren(childBlocks, expect.getBasePointer(nowBlock));///nownodeからのアウトプット全部を得る(逆フェイル)
		    			//child_blocks.offer(now_block);
		    			while ( !childBlocks.isEmpty() ) {//このブロックをnow_blockのfailにする
		    				int pp_block = childBlocks.poll();
		    				if ( pp_block != nowBlock ) {
		    					//System.out.println("bbbb now_block = "+now_block+"; pp_block = "+pp_block);
		    					if ( block.getBlockToOutput(pp_block) == nowOutputIndex ) {
		    						//こいつ自信はアウトプットを持たないため、indexの変更および、そのfailも変更させたい
    		    					//System.out.println("cccc pp_block = "+pp_block+";next = "+next_output_index+";pre = "+output_block_index[pp_block]);
		    						block.setBlockToOutput(pp_block, nextOutputIndex);
		    						//System.out.println("cccc++ pp_block = "+pp_block+";next = "+next_output_index+";pre = "+output_block_index[pp_block]);
		    						if ( expect.getBasePointer(pp_block) != 0 ) {//0だとルートのやつをやってしまう
		    							expGetChildren(childBlocks,expect.getBasePointer(pp_block));
		    						}
		    						
		    					} else {
		    						//つまりこいつ自信もアウトプットのため、ネクストのみを変える    		    					
	    							if ( this.entry.getOutputNext(block.getBlockToOutput(pp_block)) != 0 ) {
	    								//put_child_blocksで自分自身も含まれてしまうので、これが必要。
	    								this.entry.setOuterOutputNext(block.getBlockToOutput(pp_block), nextOutputIndex);
		    						}
		    					}
		    				}//ppblock
		    			}//while
		    			childBlocks.clear();
    				}
    				//ac.entry.numOfEntry--;
				}  	//if(outputなし)
    		}//削除
    	}//１エントリーループ
    }
    public void add(List<Entry> addTarget) {
    	//String entry;
    	String keyword;
    	System.out.println("add start...");
    	int nowNode;
    	int nowBlock;
    	int preNode;
    	int whilecount = 0;
    	for ( int i = 0; i < addTarget.size(); i++ ) {//１エントリーループ
 			if ( whilecount % 10000 == 0 ) {
 				int max = addTarget.size();
 				System.out.print ( whilecount * 100 / max + "% " + whilecount + "/" + max);
 				System.out.print("\r");
 			}
			whilecount++;
			//entry = (String)TEXT.get(i);
    		//entry_sp = entry.split("&;");
    		keyword = addTarget.get(i).getKeyword();
//    		Product nowOutput = new Product(Integer.parseInt(entryArr[0]), Integer.parseInt(entryArr[1]), entryArr[2].length(), entry);
    		Product nowOutput = new Product(addTarget.get(i).getWid(), addTarget.get(i).getEid(), keyword.length(), addTarget.get(i).getKeyword(), addTarget.get(i).getTarget());
    		
    		nowNode = 0;
    		preNode = 0;
    		int length = 0;
    		int[][] intWord = new int[keyword.length()][6];
    		int[] byteLength = new int[keyword.length()];
	    	for ( int j = 0; j < keyword.length(); j++ ) {//１文字ループ	
	        	byte[] byteWord = null;
				try {
					byteWord = keyword.substring(j, j + 1).getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
	    		for ( int k = 0; k < byteWord.length; k++ ) {
	    			if ( byteWord[k] < 0 ) {
	    				intWord[j][k] = byteWord[k] + 256;
	    			} else {
	    				intWord[j][k] = byteWord[k];
	    			}
	    		}
	    		byteLength[j] = byteWord.length;
	    		preNode = nowNode;
	    		boolean create_block = false;//trueなら作らなくてはならない。
	    		//System.out.println(now_node);
	    		for ( int l = 0; l < byteWord.length; l++ ) {//1バイトループ
	    			//System.out.println(int_word[j][l]);
	    			int next_node = darts.gotoNext(nowNode, intWord[j][l]);
	    			if ( next_node == 0 ){
	    				create_block = true;
	    				//System.out.println("作成");
	    				next_node =  ac.createTrie.addNodeDartsTrie(nowNode, intWord[j][l]);
	    				darts.setDartsToBlock(next_node, darts.getDartsToBlock(nowNode));
	    				nowNode = next_node;
	    			} else {
	    				//System.out.println("ノードできてたの巻"+next_node+" b="+darts.getDartsToBlock(next_node));
	    				nowNode = next_node;
	    				create_block = false;
	    			}
	    		}//１バイトループ
	    		length++;
 	    		if ( create_block == true ) {//ブロックの生成および、failure生成など
 		    		//ブロックの処理	
 		    		//ブロック作成
 	    			darts.setDartsToBlock(nowNode, block.getBlockPointer());
 	    			//System.out.println("block作成"+env.get_block2(now_node)+" nn"+now_node);
 	    			block.setBlock(nowNode, length); 					
 		    		
 		    		nowBlock = darts.getDartsToBlock(nowNode);
 		    		//設定　create_failのため
 		    		NodeForCreateFailure node = new NodeForCreateFailure(byteWord.length);
 		    		node.set(byteWord.length, preNode, nowNode, intWord[j]);
 		    		
 		    		if ( j == keyword.length() - 1 ) {//最終文字だったらfailつくる前にアウトプットつくる。
 		    			if ( block.getBlockToOutput(darts.getDartsToBlock(nowNode)) == 0 ) {
 		    				block.setBlockToOutput(darts.getDartsToBlock(nowNode), this.entry.getEntryPointer());
 		    				//output_value[entry_pointer] = entry;
 		    				this.entry.setOutput(nowOutput);	    				
 		    			} else {
 		    				//entry.getOutputValue(block.getBlockToOutput(darts.getBlock(now_node))).setNext(now_output_value_int);
 		    				this.entry.setInnerOutputNext(block.getBlockToOutput(darts.getDartsToBlock(nowNode)), nowOutput);
 		    			}
 		    		}
 		    		ac.createFailure.createfail(node);//現在のブロックのfail作成
 		        	//System.out.println("======"+int_word[j][0]+"============"+now_block);
 		    		updateFail(nowBlock, intWord, byteLength);//現在のブロックにfailさせる。expect treeの更新など
 	    		} else {//ブロック新しくつくったどうかで作らなかった場合
 		    		if ( j == keyword.length() - 1 ) {//アウトプット生成。
 		    			int ima_block = darts.getDartsToBlock(nowNode);
 		    			if ( block.getBlockToOutput(ima_block) == 0 ) {
 		    				block.setBlockToOutput(darts.getDartsToBlock(nowNode), this.entry.getEntryPointer());
 		    				//output_value[entry_pointer] = entry;
 		    				this.entry.setOutput(nowOutput);	 
 		    				//System.out.println("例外のリンク貼り直しa"+ima_block);
 		    				rFailCount_2++;
 			    			updateLink(ima_block);
 			    			if ( block.getBlockToOutput(block.getFailure(ima_block)) != 0 ) {
 			    				this.entry.setOuterOutputNext(block.getBlockToOutput(ima_block),block.getBlockToOutput(block.getFailure(ima_block)));
 			    			}
 		    			} else {
 		    				//env.output_value_int[env.output_block_index[env.get_block2(now_node)]].setnext(now_output_value_int);
 		    				this.entry.setInnerOutputNext(block.getBlockToOutput(darts.getDartsToBlock(nowNode)), nowOutput);
 		    			}			
 		    		}
 	    		}
 	    		preNode = nowNode;
 	    	}//１文字ループ
     	}//１エントリーループ
     }
    
    
    private void updateLink(int now_block) {
    	int failBlock = block.getFailure(now_block);
    	int baseBlock = expect.getBasePointer(now_block);
    	if ( baseBlock == 0 ) {
    		return;
    	} else {
      		//System.out.println("now_block = "+now_block+", fail_block = "+fail_block+", base_block = "+base_block);
    		int fail_output_index = block.getBlockToOutput(failBlock);
    		Queue<Integer> child_blocks = new LinkedList<Integer>();
        	expGetChildren(child_blocks,baseBlock);//nowblockからのアウトプット全部を得
    		while ( !child_blocks.isEmpty() ) {//このブロックをnow_blockのfailにする
    			int pp_block = child_blocks.poll();
    			//System.out.println("pp_block = "+pp_block);
    			if ( pp_block != now_block ) {
    				//output_index&output_next設定
    				//System.out.println(output_block_index[pp_block]+"&&&"+fail_output_index);
    				if ( block.getBlockToOutput(pp_block) == fail_output_index ) {
    					//System.out.println("リンクの貼り直しaaa");
    					changeFailOutput(pp_block, block.getBlockToOutput(now_block));
    					block.setBlockToOutput(pp_block, block.getBlockToOutput(now_block));     					
    				} else {
    					entry.setOuterOutputNext(block.getBlockToOutput(pp_block), block.getBlockToOutput(now_block));
    				}
    			}//if   			
    		}//while
    		child_blocks.clear();
    	}
    }
  
    private void updateFail(int nowBlock, int intWord[][], int byteLength[]) {
    	rFailCount_1++;
    	//int originBlock = nowBlock;
    	int failBlock = block.getFailure(nowBlock);
    	int baseBlock = expect.getBasePointer(failBlock);
		if ( baseBlock == 0 ) { 
			expect.pointerCheck();
			expect.setNewBasePointer(failBlock);
			baseBlock = expect.getBasePointer(failBlock);
		}
    	int baseNode = expect.getBlockToDarts(baseBlock);
    	int nowNode = baseNode;
    	//int last_mae_node = base_node;//最後の遷移前のブロックのノード
    	//System.out.println("fail_block = "+fail_block);
    	//System.out.println("base_block = "+base_block);
    	//System.out.println("b[now_node] = "+exp_get_block(now_node)+"     "+now_node);
    	//int fail_point_node = 0;
    	boolean isTrans = false;
    	int expNowOutputValue = nowBlock;
    	int preBlock = baseBlock;
		//System.out.println("b_length = "+block_length[now_block]+", f_length = "+failure_length[now_block]);
    	for ( int i = block.getBlockLength(nowBlock) - block.getFailureLength(nowBlock) - 1; i >= 0; i-- ) {//文字ループ
    		//System.out.println("i = "+i);
    		if ( i == 0 ) {
    			//自分のアウトプット生成する
    			//System.out.println("expアウトプット作成:"+exp_get_block(now_node));
    			expect.setNewOutput(nowNode, expNowOutputValue);
    		}
    		preBlock = expect.getDartsToBlock(nowNode);
    		//System.out.println("pre_block1 = "+pre_block);
    		for ( int j = 0; j < byteLength[i]; j++ ) {//バイトループ
				int pre_node = expect.gotoNext(nowNode, intWord[i][j]);  		
	    		//System.out.println(now_node+"&&"+pre_node+"&&"+int_word[i][j]);
				if ( pre_node == 0 ) {//遷移失敗。つまりノード生成
    				isTrans = false;
    				pre_node =  ac.createExpect.addNodeDartsTrie(nowNode, intWord[i][j]);
    				expect.setDartsToBlock(pre_node, expect.getDartsToBlock(nowNode));
    				//env.exp_block_darts_index[pre_node] = env.exp_block_darts_index[now_node];
    				expect.numOfDartsNode++;
    				nowNode = pre_node;
				} else {//遷移成功つまりなにもしない？
					nowNode = pre_node;
					nowBlock = expect.getDartsToBlock(nowNode);
					isTrans = true;
					//System.out.println("senni OK"+ exp_get_block(now_node));
				}
    		}//バイトループ
       		//ブロックの処理
    		if ( i != 0 ) {
				if ( expect.getBase(nowNode) == -1 && isTrans == false ) {
					//ブロック作成
					expect.setNewBlock(nowNode, preBlock);
					//System.out.println("expブロック作成:"+exp_block_pointer);    			
 				}		
    		}
    	}//文字ループ終わり
    	if ( isTrans == true ) {//nowノードからのアウトプット集合を求めてそれを追加。
        	Queue<Integer> child_blocks = new LinkedList<Integer>();
        	expGetChildren(child_blocks,expect.getDartsToBlock(nowNode));//nowblockからのアウトプット全部を得
        	//exp_clean_pointer(last_mae_node,int_word[0][byte_length[0]-1]);
        	//System.out.println("erase pointer "+pre_block+" word = "+int_word[0][byte_length[0]-1]);
        	int newBase = 0;
			if ( expect.getBase(nowNode) != -1 ) {
				//env.exp_child[env.exp_get_block2(now_node)]!=0
				//exp_clean_pointer(pre_block,now_block,int_word[0],byte_length[0]);
				newBase = expCleanPointer(nowNode, nowBlock);
				//あたらしいノードがベースになるので、そのノードまでの遷移は消す。
				//さらにそこをベースにする。
				//env.exp_base_pointer[moto_block] = env.exp_get_block2(now_node);
				expect.setBasePointer(nowBlock, newBase);
			}
			//System.out.println("b[-3] = "+exp_get_block(-3));
    		//System.out.println("now_node = "+now_node);
			//System.out.println("exp_base生成2  " +moto_block+"→"+exp_base[moto_block]);
    		//child_blocks.offer(now_block);
    		while ( !child_blocks.isEmpty() ) {//このブロックをnow_blockのfailにする
    			int pp_block = child_blocks.poll();
    			if ( pp_block != nowBlock ) {
    				//System.out.println("Fail作成 "+pp_block+"→"+moto_block);
    				rFailCount_3++;
    				block.setFailure(pp_block, nowBlock);
    				block.setFailureLength(pp_block, block.getBlockLength(nowBlock));
    				//output_index&output_next設定
    				if ( block.getBlockToOutput(pp_block) != block.getBlockToOutput(nowBlock) ) {
    					//同じだった場合なんの問題もない。
    					if ( entry.getOutputNext(block.getBlockToOutput(nowBlock)) == block.getBlockToOutput(pp_block) ) {
    						//ppの方は元々アウトプットない場合はnowのアウトプットをインデックスに持たせる
    						changeFailOutput(pp_block,block.getBlockToOutput(nowBlock));
    						block.setBlockToOutput(pp_block,block.getBlockToOutput(nowBlock));   
    						
    					} else if ( entry.getOutputNext(block.getBlockToOutput(pp_block)) == block.getBlockToOutput(nowBlock) ) {
    						//ppの方はアウトプットもっててnowが持っていない場合なにもしない
    					} else {
    						//ppもnowもアウトプットを元々もっているのでnextを変更
    						entry.setOuterOutputNext(block.getBlockToOutput(pp_block), block.getBlockToOutput(nowBlock));
    					}    					
    				}
    			}//if
    		}//while
    		child_blocks.clear();
    	}
    }
    
    public void changeFailOutput(int nowBlock, int index) {
    	//System.out.println("aaaaaaaaaaaaaaaa"+now_block);
    	if ( expect.getBasePointer(nowBlock) == 0 ) {
    		//System.out.println("削除");
    		return;
    	}
    	Queue<Integer> childBlocks = new LinkedList<Integer>();
    	//System.out.println("きたよ "+exp_base[now_block]);
    	expGetChildren(childBlocks, expect.getBasePointer(nowBlock));
    	//System.out.println(now_block+","+exp_base[now_block]);
    	while ( !childBlocks.isEmpty() ) {//このブロックをnow_blockのfailにする
    		rFailCount_2++;
			int childBlock = childBlocks.poll();
			//System.out.println("OUTPUT_INDEXリンク"+pp_block);
			if ( childBlock != nowBlock ) {
				//output_index&output_next設定
				if ( block.getBlockToOutput(childBlock) == block.getBlockToOutput(nowBlock) ) {
					changeFailOutput(childBlock, index);	
					block.setBlockToOutput(childBlock, index);												
				}
			}//if			
		}//while
    	childBlocks.clear();
    }
    public int expCleanPointer(int nowNode, int nowBlock){
    	//あたらしいノードがベースになるので、そのノードまでのchildかsiblingは消す。
    	//int now_block = exp_get_block(now_node);
    	expect.pointerCheck();
		int newNode = expect.getSingleDartsPointer();
    	int newBlock = expect.getBlockPointer();
    	int child = expect.getChild(nowBlock);
    	expect.blockPointer++;
    	expect.numOfBlock++;
    	expect.singleDartsPointer++;
    	
    	int base = expect.getBase(nowNode);
    	expect.setBase(newNode, base);
    	expect.setCheck(newNode, -2);
    	expect.setBase(nowNode, -1);
    	for ( int i = 0; i < 256; i++ ) {
    		if ( expect.getCheck(base + i) == nowNode ) {
    			expect.setCheck(base + i, newNode);
    		}
    	}
    	expect.setDartsToBlock(newNode, newBlock);
    	expect.setBlockToDarts(newBlock, newNode);
    	expect.setChild(newBlock, child);
    	expect.setChild(nowBlock, 0);	
    	return newBlock;
    }
    
    public void expGetChildren(Queue<Integer> childBlocks, int rootBlock){
    	Queue<Integer> blocks= new LinkedList<Integer>();
    	blocks.offer(rootBlock);
    	//int count = 0;
		while ( !blocks.isEmpty() ) {//キューがなくなるまで実行
			int nowBlock = blocks.poll();
			//count++;
			//if(count%10000==0)
			//System.out.println("now_child = "+now_block);
			expGetChildBlock(blocks, nowBlock);
			int nowOutputIndex = expect.getOutputIndex(nowBlock);
			while ( nowOutputIndex != 0 ) {
				//System.out.println("集合" +now_block+";"+exp_output_value[now_output_index]);
				childBlocks.offer(expect.getOutputValue(nowOutputIndex)); 
				nowOutputIndex = expect.getOutputNext(nowOutputIndex);
			}
		}
    	return;
    }
    
    public void expGetChildBlock(Queue<Integer> blocks, int rootBlock){
    	if ( expect.getChild(rootBlock) == 0 ) {
    		return;
    	}
    	int nowBlock = expect.getChild(rootBlock);
    	//int count = 0;
    	while ( true ) {
    		blocks.offer(nowBlock);
    		//count++;
    		//if(count % 10000 == 0)
    		//	System.out.println(now_block);
    		if ( nowBlock == expect.getSiblingNext(nowBlock) ) {
    			return;
    		}
    		nowBlock = expect.getSiblingNext(nowBlock);
    	}
    }
}
