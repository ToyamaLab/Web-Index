package findindex.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;

import core.data.Entry;
import core.data.Product;
import core.service.ChangeString;
import core.util.LogUtil;
import findindex.data.BlockData;
import findindex.data.DebugData;
import findindex.data.EntryData;
import findindex.data.GotoFunctionNode;
import findindex.data.TmpGotoFunctionData;
import findindex.service.CreateTrie;


public class CreateTrieImpl implements CreateTrie{
	
	private TmpGotoFunctionData node;
	private BlockData block;
	private EntryData entry;
	private GotoFunctionNode darts;
	private DebugData debug;
	
	private static LogUtil logger;
	
	public CreateTrieImpl(
			TmpGotoFunctionData node,
			BlockData block, 
			EntryData entry,
			GotoFunctionNode darts,
			DebugData debug) {
		this.node = node;
		this.block = block;
		this.entry = entry;
		this.darts = darts;
		this.debug = debug;
		logger = new LogUtil(CreateTrieImpl.class);
	}
	
	@Override
	public void createTrie(List<Entry> list) throws SQLException, UnsupportedEncodingException { //synonym用 ikuta
	  	int nowLength = 0;
	   	int nowNode = 0;
		//int nowBlock = 0;
		byte[] byteWord = null;
		int whilecount = 0;
		int eraseCount = 0;
		String nowWord = "";
	 	Product nowOutputValue = null;
	 	NumberFormat format = NumberFormat.getInstance();
	 	format.setMaximumFractionDigits(1);
	 	format.setMinimumFractionDigits(1);
	 	ChangeString change = new ChangeString();//半角変更
	 	
	    for ( int i=0; i < list.size(); i++ ) {  //１エントリー毎のループ
            Entry synonym = list.get(i);   
	 	
			boolean isEntryInvalid = false;
			nowNode = 0;
			nowLength = 0;
			nowWord = synonym.getKeyword();
			
			if ( nowWord.equals("") || nowWord.contains("/") ) {
				eraseCount++;
				continue;
			}
			
			nowOutputValue = new Product(synonym.getWid(), 
					synonym.getEid(),
					synonym.getKeyword().length(),
					change.convert(synonym.getKeyword().toLowerCase()),
					change.convert(synonym.getTarget().toLowerCase()));
			
			//構築に時間がかかるためのチェック用
			if ( whilecount % 1000000 == 0 ) {
	    		logger.debug("Now creating trie : " + format.format(100 * ((double) whilecount / debug.getNumOfEntry())) + "%\t" + whilecount + "/" + debug.getNumOfEntry());
	    	}
			
	    	whilecount++;
	    	//System.out.println(nowWord);//現在のワードを示すチェック用
			for ( int j = 0; j < nowWord.length(); j++ ) {//１文字単位のループ
				boolean isTrans = false; //遷移成功か失敗か
	    		//読み込んだ1文字をバイトコードに変換
				byteWord = nowWord.substring(j, j+1).getBytes("UTF-8");
				int[] intWord = new int[byteWord.length];
	    		for ( int k = 0; k < byteWord.length; k++ ) {
	    			if ( byteWord[k] < 0 ) {
		    			intWord[k] = byteWord[k] + 256;
		    		} else {
		    			intWord[k] = byteWord[k];			    		
		    		} 
		    	}
	    		//ここからgotoの生成
		   		for ( int l = 0; l < intWord.length; l++ ) {//１バイトづつノードの作成   
		   			//System.out.println(intWord[l]);
		   			int nextcheck = 0;
		   			try {
						nextcheck = node.gotoNext(nowNode, intWord[l]);
		   			} catch (Exception e) {
		   				logger.warn("nowOutputvalue :"
	   						+ " wid = " + nowOutputValue.getWid()
	   						+ " eid = " + nowOutputValue.getEid()
	   						+ " keyword = " + nowOutputValue.getKeyword()
	   						+ " target = " + nowOutputValue.getTarget()
	   					);
		   				
		   				isEntryInvalid = true;
		   				eraseCount++;
		   				break;
		   			}
		   			
		   			if ( nextcheck == 0 ) {//nextcheck==0ならば次の遷移が無い
	    				isTrans = false;
    					if ( node.getFirstWord(nowNode) != 0 ) {//初めの一回は免除するため。	    						
    						//System.out.println("うそでしょまじで");
	    					moveDarts(node.gotoNext(nowNode, node.getLastWord(nowNode)), nowNode, -1, true);
	    				} else {
	    					if ( nowNode == 0 ) {
	    						node.setFirstWord(nowNode, intWord[l]);
	    					}
	    				}
	    				//System.out.println("de = "+int_word[l]+" df = "+double_first_word[now_node]);
	    				int nextNode = node.setNode(nowNode, intWord[l], node.getBlock(nowNode));
	    				nowNode = nextNode;
		    		} else {//nextcheck
		    			isTrans = true;
		    			nowNode = nextcheck;
		    		}
		    	}//1byteループ
		   		nowLength++;
		   		
		   		if ( isEntryInvalid ) {
		   			break;
		   		}
		   		
		   		//ブロックの処理
			   	if ( !isTrans ) {
			   		if ( node.getFirstWord(nowNode) == 0 ) {
			   			//ブロック作成
			   			int blockPointer = 0;
			   			
			   			blockPointer = block.getBlockPointer();
			   			
			   			//System.out.println("block===="+blockPointer);
			   			node.setNodeToBlock(nowNode,blockPointer);
			   			block.setBlock(nowNode,nowLength);
			   		}
		   		}
			}//１文字単位のループ終了
			
			if ( isEntryInvalid ) {
				// to next entry
				continue;
			}
			
			//outputvalueの格納およびその地点のoutput_index生成
			if ( block.getBlockToOutput(node.getBlock(nowNode)) == 0 ) {
				int entryPointer = entry.getEntryPointer();
				//System.out.println("setOutput "+node.getBlock(nowNode) +" "+entryPointer);
				block.setBlockToOutput(node.getBlock(nowNode), entryPointer);
				entry.setOutput(nowOutputValue);
			} else {
				Product addObj = entry.getOutputValue(block.getBlockToOutput(node.getBlock(nowNode)));
				boolean isNextAdd = false;
				
				while ( addObj != null ) {
					if ( addObj.getTarget().equals(nowOutputValue.getTarget()) && addObj.getKeyword().equals(nowOutputValue.getKeyword()) ) {
						if ( addObj.getWid() != nowOutputValue.getWid() ) {
							isNextAdd = true;
						} else {
							isNextAdd = false;
							break;
						}
					} else {
						isNextAdd = true;
					}
					
					addObj = addObj.getNext();
				}
				
				if ( isNextAdd ) {
					entry.setInnerOutputNext(block.getBlockToOutput(node.getBlock(nowNode)), nowOutputValue);
				}
			}	
		}//１エントリー毎のループ終了	
		//rootの移動
		moveDarts(0, 0, -1, false);
		node = null;
		logger.debug("eraseCount = " + eraseCount);
	}

	private void moveDarts(int moveNode, int parentNode, int nowPointer, boolean flag) {
		//System.out.println("dp="+env.darts_pointer+" sdp="+env.single_darts_pointer+" sdnp="+env.single_darts_next_pointer);
		//System.out.println("move_node = "+move_node+" parent_node = "+parent_node+" now_pointer="+now_pointer);
		//System.out.println("double2-230="+env.double_goto[2][230]);

		/*
		 * move_node		//動く対象となるノード？
		 * parent_node	//親ノード？？
		 * now_pointer	//次に作成するdartsの場所 指定がないなら-1
		 * flag			//前のノードがダブルノードか否か。引越しとかするため。
		 */
		//System.out.println("dp="+env.darts_pointer+" sdp="+env.single_darts_pointer+" sdnp="+env.single_darts_next_pointer);
		//System.out.println("move_node = "+move_node+" parent_node = "+parent_node+" pointer = "+now_pointer);
		//boolean flag = false;
		
		if ( moveNode == 0 ) {//root
			nowPointer = 0;
		} else if ( nowPointer < 0 ) {
			while ( darts.getCheck(darts.singleDartsPointer) != -1 ) {//signle pointerの場所が開いている。
				darts.singleDartsPointer++;
			}
			if ( darts.singleDartsPointer >= darts.dartsPointer ) {
				darts.dartsPointer = darts.singleDartsPointer+1;
			}
			nowPointer = darts.singleDartsPointer;
			//System.out.println("henko pointer = "+now_pointer);
		}
	
		//System.out.println("move = "+move_node+" pare = "+parent_node+" nowp = "+now_pointer);
		if ( node.getFirstWord(moveNode) == node.getLastWord(moveNode) ) {//single	
			int tmpTrans = node.getLastWord(moveNode);
			darts.singleDartsNextPointer = nowPointer + 1;
			while ( darts.getCheck(darts.singleDartsNextPointer) != -1 ) {
				darts.singleDartsNextPointer++;
			}
			if ( darts.singleDartsNextPointer == nowPointer ) {
				darts.singleDartsNextPointer++;
			}
		
			int nextMove = node.gotoNext(moveNode, tmpTrans);
			int v_base = 0;
			if ( nextMove != 0 ) {//次の遷移があれば。
				v_base = darts.singleDartsNextPointer - tmpTrans;
			}
			//System.out.println("%%%v_base = "+v_base+" senni ="+tmpTrans+" , "+darts.singleDartsNextPointer);
			darts.setCheck(nowPointer, parentNode);
			darts.setBase(nowPointer, v_base);
			//System.out.println("ポイント地点1は"+now_pointer+" c = "+check[now_pointer]+" b = "+base[now_pointer]);
			//親がダブルの場合の変更
			//if(single_check[-move_node] >= 0 && flag == true){
			if ( flag == true ) {
				//System.out.println(parentNode+" "+node.getLastWord(parentNode)+" "+nowPointer+" "+moveNode);
				node.setDoubleGoto(parentNode, node.getLastWord(parentNode), nowPointer);
				//node.setDoubleGoto(parentNode,tmpTrans,nowPointer);//kosuda
			}
			//消し
			int blockNum = node.getNodeToBlock(moveNode);
			int tmpFirstWord = node.getFirstWord(moveNode);
			node.reset(moveNode, tmpTrans);
			//System.out.println("ぶろっくなんばー"+blockNum);
			darts.setDartsToBlock(nowPointer, blockNum);
			if ( block.getBlockToNode(blockNum) == moveNode ) {
				block.setBlockToNode(blockNum, nowPointer);
				//System.out.println("移動完了！！！！！435et！！！！！");	
			}
			
			//System.out.println("S "+now_pointer);
			if ( tmpFirstWord != 0 ) {
				//env.single_darts_pointer = env.single_darts_next_pointer;
				if(darts.singleDartsNextPointer >= darts.dartsPointer) {
					darts.dartsPointer = darts.singleDartsNextPointer+1;
					//System.out.println("ぬかれたー1");
				}
				//aaa();
				//System.out.println("next_move = "+next_move+" now_pinter = "+now_pointer+" next = "+single_darts_pointer);
				moveDarts(nextMove, nowPointer, darts.singleDartsNextPointer, false);
				return;
			}
			if ( darts.singleDartsPointer >= darts.dartsPointer ) {
				darts.dartsPointer = darts.singleDartsPointer + 1;
				//System.out.println("ぬかれたー2");
			}
			return;
		} else {//double
			int de = node.getLastWord(moveNode); 
			int df = node.getFirstWord(moveNode);	
			node.setLastWord(moveNode, 0);
			node.setFirstWord(moveNode, 0);
			
			int next_move = node.gotoNext(moveNode,de);
			int v_base;
			if ( moveNode == 0 ) {
				v_base = 1;
			} else {
				v_base = darts.dartsPointer -  df;
				darts.dartsPointer = darts.dartsPointer + de - df + 1; //???
			}
			
			
			darts.setCheck(nowPointer, parentNode);
			darts.setBase(nowPointer, v_base);
			//System.out.println("base["+now_pointer+"]="+v_base);
			if ( flag ) {//親がダブルの場合
				//System.out.println("ここだよ");
				node.setDoubleGoto(parentNode,node.getCheckWord(moveNode),nowPointer);
			}
			for ( int i = df; i < de; i++ ) {
				if ( node.gotoNext(moveNode, i) != 0 ) {
					//System.out.println("movenode = "+moveNode);
					int pre = node.gotoNext(moveNode, i);
					int next = v_base + i;
					//System.out.println(i+" pre="+pre+" ch ="+nowPointer+" dfde="+df+","+de+" i="+i);
					//System.out.println("next="+next+" pre="+pre+" mn="+move_node);
					darts.setBase(next, darts.getBase(pre));
					darts.setCheck(next, nowPointer);
					for ( int j = 0; j < 256; j++ ) {
						if ( darts.getBase(next) + j > 0 ) {
							if ( darts.getCheck(darts.getBase(next) + j) == pre ) {
								darts.setCheck(darts.getBase(next) + j, next);
							}
						}
					}
					int block_num = darts.getDartsToBlock(pre);
					//System.out.println("ぶろっくなんばー"+block_num);
					darts.setDartsToBlock(next, block_num);
					if ( block.getBlockToNode(block_num) == pre ) {
						block.setBlockToNode(block_num, next);
					}
					darts.reset(pre);
					node.setDoubleGoto(moveNode, i, 0);
				}
			}
			
			int block_num = node.getNodeToBlock(moveNode);
			//System.out.println("ぶろっくなんばー"+block_num);
			darts.setDartsToBlock(nowPointer, block_num);
			if ( block.getBlockToNode(block_num) == moveNode ) {
				block.setBlockToNode(block_num, nowPointer);
			}
			//消し消し
			node.setDoubleGoto(moveNode, de, 0);
			node.reset(moveNode);
			
			//System.out.println("D "+now_pointer);
			
			moveDarts(next_move, nowPointer, v_base + de, false);
		}
		return;
	}
	
	int addNodeDartsTrie(int parentNode, int value) {
    	//System.out.println("parent="+parent_node+" value="+value);
    	//System.out.println("base[parent]="+env.base[parent_node]);
		//System.out.println("c323="+env.check[323]+" b323="+env.base[323]);
    	/*
    	 * 返り値はセットしたノードの番号（つまりnow_nodeになるもの）
    	 * parent_node どのノードから遷移するか
    	 * value　何で遷移するか
    	 */
    	//System.out.println(env.darts_pointer);
    	//baseが無かった場合。つまり遷移先を新しく追加の場合
    	if ( darts.getBase(parentNode) == -1 ) {
    		//System.out.println("はぁ？？？");
    		darts.pointerCheck();
    		int vbase =  darts.getSingleDartsPointer() - value;
    		darts.setBase(parentNode, vbase);
    		darts.setCheck(darts.getSingleDartsPointer(), parentNode);
    		//System.out.println("point ="+env.single_darts_pointer+" aaa");
    		return darts.getSingleDartsPointer();
    	}
    	
    	//追加したらそれでokだった場合
    	int preNext = darts.getBase(parentNode) + value;
    	if ( preNext > 0 ) {
    		if ( darts.getCheck(preNext) == -1 ) {
    			//本当は↑の条件は良くない。まぁたぶん問題ないだろうけど...
    			//System.out.println("はぁ？？？");
    			darts.setCheck(preNext, parentNode);
    			if ( preNext >= darts.getDartsPointer() ) {
    				darts.setDartsPointer(preNext + 1);
    			}
    			//System.out.println("point ="+pre_next+" bbb"+" ch[poi]="+env.check[pre_next]);
    			return preNext;
    		}
    	}
    	
    	
    	//以下は衝突があった場合。退避させなくてはならない...
    	int de = 0;
    	int df = -1;
    	int vbase = 0;
    	//ArrayList<Integer> a = new ArrayList<Integer>();
    	for ( int i = 0; i < 256; i++ ) {
    		int p_next = darts.getBase(parentNode) + i;
    		
    		if ( p_next > 0 ) {
    			if ( darts.getCheck(p_next) == parentNode ) {
    				if ( df != -1 && p_next >= darts.getDartsPointer() ) {
    					break;
    				}
    				//System.out.println("base["+p_next+"]="+env.base[p_next]+"===="+parent_node);
    				//System.out.println("base["+parent_node+"]="+env.base[parent_node]);
    				//System.out.println("退避！");
    				//de...そのノードから遷移するもじの中で値が最も大きい物
    				//df...上に同じで小さいもの
    				if ( df == -1 ) {//dfの設定
    					df = i;
    					if ( value < i ) {
    						df = value;
    					}
    					// ここでベースを決定する
    					vbase = darts.getDartsPointer() - df;
	    				
	    			}    			
    				
    				if ( darts.getBase(p_next) != -1 ) {
    					for ( int j = 0; j < 256; j++ ) {//場所かわるのでそれのチェックの変更
    						int chch = darts.getBase(p_next)+j;
    						if ( darts.getCheck(chch) == p_next ) {
    							darts.setCheck(chch, vbase + i);    							
    						}
    						
    					}
    				}
    				int s_next = vbase+i;
    				darts.setCheck(p_next, -1);
    				darts.setBase(s_next, darts.getBase(p_next));
    				darts.setBase(p_next, -1);
    				darts.setCheck(s_next, parentNode);
    				int tmp_block = darts.getDartsToBlock(p_next);
    				if ( block.getBlockToNode(tmp_block) == p_next ) {
    					block.setBlockToNode(tmp_block, s_next);
    					//System.out.println("===="+p_next+"→"+s_next+" tmpb="+tmp_block);
    				}
    				darts.setDartsToBlock(s_next, tmp_block);
    				darts.setDartsToBlock(p_next, -1);
    				//block.setBlockToNode(s_next,tmp_block);
    				//block.setBlockToNode(p_next,-1);
    				
    				////////////////////////////////
    				//System.out.println("変更値 ="+p_next+"→"+(v_base + i)+" ch="+env.check[v_base + i]+" ba="+env.base[(v_base + i)]);
    				//System.out.println("base[p]="+env.base[parent_node]+" i="+i);
    				de = i;//deの設定
    			}//check    			
    		}//p_next>0   		
    	}//for

		
    	darts.setBase(parentNode, vbase);
    	if ( value > de ) {	
    		de = value;
    	}
    	darts.setDartsPointer(darts.getDartsPointer() + de - df + 1);
    	//System.out.println("de="+de+" df="+df);
    	//現在のノードへの設定
    	darts.setCheck((vbase + value),parentNode);
    	//System.out.println("point ="+(v_base + value)+" ccc");
    	return (vbase + value);
    	
    }
}
