package findindex.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

import core.data.Constant;
import findindex.data.BlockData;
import findindex.data.DebugData;
import findindex.data.ExpectTrieData;
import findindex.data.GotoFunctionNode;
import findindex.service.CreateExpectTrie;

public class CreateExpectTrieImpl implements CreateExpectTrie{

	private BlockData block;
	private GotoFunctionNode darts;
	private ExpectTrieData expect;
	private DebugData debug;
	
	public CreateExpectTrieImpl(
			BlockData block, 
			GotoFunctionNode darts,
			ExpectTrieData expect,
			DebugData debug){
		this.block = block;
		this.darts = darts;
		this.expect = expect;
		this.debug = debug;
	}

	@Override
	public void createExpectTrie(ResultSet rset) throws SQLException, UnsupportedEncodingException {
	   	//各種設定   
	   	int nowNode = 0;
	   	int nowBlock = 0;
	   	byte[] byteWord = null;	   	
	   	String word = "";
	   	
	   	boolean isChecked[] = new boolean [block.getSize()];
	   	rset.first();	
	   	rset.previous();
	   	NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(1);
		format.setMinimumFractionDigits(1);
		
		int whileCount = 0;
		while ( rset.next() ) {//１エントリー毎のループ
			nowNode = 0;
			word = rset.getString(Constant.ARRAY_WID.getInt());
	    	if (whileCount % 100000 == 0) {//構築に時間がかかるためのチェック用
				System.out.print(format.format(100 * ((double) whileCount / debug.getNumOfEntry())) + "%   " + whileCount + "/" + debug.getNumOfEntry());
				System.out.print("\r");
	    	}
	    	
	    	whileCount++;
			//System.out.println(word);
			//System.out.println(word+now_output_value);//現在のワードを示すチェック用
    		int[][] intWord = new int[word.length()][6];
    		int[] byteLength = new int [word.length()];
			for ( int j = 0; j < word.length(); j++ ) {//１文字単位のループ
	    		//読み込んだ1文字をバイトコードに変換
				byteWord = word.substring(j, j+1).getBytes("UTF-8");
	    		for ( int k = 0; k < byteWord.length; k++ ) {
	    			if ( byteWord[k] < 0 ) {
		    			intWord[j][k] = byteWord[k] + 256;
		    		} else {
		    			intWord[j][k] = byteWord[k];
		    		}   
	    		}
	    		byteLength[j] = byteWord.length; 
	    		//ここからgotoの生成
	    		for( int l = 0; l < byteWord.length; l++ ) {//１バイトづつノードの作成   
	    			nowNode = darts.gotoNext(nowNode, intWord[j][l]);
	    		}//バイトずつおわり
	    		nowBlock = darts.getDartsToBlock(nowNode);
	    		if ( isChecked[nowBlock] == false ) {//チェック済みでなければexpect　tree生成
	    			//System.out.println("test");
	    			createExpectTree(nowBlock, intWord, byteLength);
	    			isChecked[nowBlock] = true;
	    		}
			}//文字ずつ終わり
		}//文章ずつおわり
	}
	
	private void createExpectTree(int n, int intWord[][],int byteLength[]) {
		//現在のブロックからexpect-treeを作る
		//int_wordは１次元目が現在のまでの文字列あいうならint_word[0][]があのバイト列といった感じ。
		int nowBlock = n;
		int nowOutputValue = nowBlock;
		int rootBlock;
		int failBlock = block.getFailure(nowBlock);
		//今回のルートの設定（現在のfail先からのやつ）
		if ( expect.getBasePointer(failBlock) == 0 ) { 
			expect.pointerCheck();
			expect.setNewBasePointer(failBlock);
			rootBlock = expect.getBasePointer(failBlock);
			//System.out.println("rootblockは新しくつくった");
		} else {
			rootBlock = expect.getBasePointer(failBlock);
			//System.out.println("rootblockは存在した"+rootBlock);
		}
		int rootNode = expect.getBlockToDarts(rootBlock);
		
		int blockLength = block.getBlockLength(nowBlock);
		int failureLength = block.getFailureLength(nowBlock);
		//System.out.println("root_base = "+exp_base[fail_block]);
		//System.out.println("root_node = "+exp_block_goto_index[exp_base[fail_block]]);
		//System.out.println("block_node = "+exp_block_goto_index[exp_base[fail_block]]);	
		//System.out.println("testtest"+expect.getDartsToBlock(314));
		int nowNode = rootNode;
		//System.out.println("now_node ="+nowNode);
		int preBlock = 0;
		for ( int i = blockLength-failureLength-1; i > 0; i-- ) {//文字ループ
			boolean isTrans = false; //遷移成功か失敗か
			//pre_block = env.exp_get_block2(now_node);
			for ( int j = 0; j < byteLength[i]; j++ ) {//バイトループ
				//System.out.println("now = "+int_word[i][j] +" now_node = "+nowNode+" rootNode = "+rootNode+" block = "+expect.getDartsToBlock(nowNode)+" bb = "+expect.getBase(314)+" dd"+expect.getDartsToBlock(314));
				int preNode = expect.gotoNext(nowNode, intWord[i][j]);
				if ( preNode == 0 ) {//遷移失敗。つまりノード生成
					//System.out.println("out"+int_word[i][j]);
    				isTrans = false;
    				//System.out.println(now_node+"new"+env.exp_get_block2(339));
    				preNode = addNodeDartsTrie(nowNode, intWord[i][j]);
    				//System.out.println("n = "+nowNode+" p = "+preNode+"new"+expect.getBase(314));
    				expect.setDartsToBlock(preNode, expect.getDartsToBlock(nowNode));
    				expect.numOfDartsNode++;
    				nowNode = preNode;				
    				
				} else {//遷移成功つまりなにもしない？
					nowNode = preNode;
					isTrans = true;
					//System.out.println("NG");
					//System.out.println(now_node);
				}
				//System.out.println(now_node+" block="+env.exp_get_block2(now_node)+" 339="+env.exp_get_block2(339));
				//System.out.println("block339="+env.exp_get_block2(339));
			}//バイトループ
    		//ブロックの処理
			//System.out.println(now_node+" block2="+env.exp_get_block2(now_node));
			
			//env.exp_base[now_node]==-1 && d
			if ( expect.getBase(nowNode) == -1 && isTrans == false ) {
				//ブロック作成
				expect.setNewBlock(nowNode,preBlock);   				
		    	//System.out.println("ブロックとして追加="+env.exp_get_block2(339));
			}		
    		//System.out.println("block339="+env.exp_get_block2(339));
		}//文字ループ		
		//アウトプット生成する
		//System.out.println(now_node+" block2="+env.exp_get_block2(now_node));
		expect.setNewOutput(nowNode, nowOutputValue);		
	}
	
	public int addNodeDartsTrie(int parentNode, int value) {
		//System.out.println("parent="+parentNode+" value="+value);
    	//System.out.println("base[parent]="+env.base[parent_node]);
		//System.out.println("c323="+env.check[323]+" b323="+env.base[323]);
    	/*
    	 * 返り値はセットしたノードの番号（つまりnow_nodeになるもの）
    	 * parent_node どのノードから遷移するか
    	 * value　何で遷移するか
    	 */
    	//System.out.println(env.darts_pointer);
    	//baseが無かった場合。つまり遷移先を新しく追加の場合
    	if ( expect.getBase(parentNode) == -1 ) {
    		//System.out.println("はぁ？？？");
    		expect.pointerCheck();
    		int vbase =  expect.getSingleDartsPointer() - value;
    		expect.setBase(parentNode, vbase);
    		expect.setCheck(expect.getSingleDartsPointer(), parentNode);
    		//System.out.println("point ="+env.single_darts_pointer+" aaa");
    		return expect.getSingleDartsPointer();
    	}
    	
    	//追加したらそれでokだった場合
    	int preNext = expect.getBase(parentNode) + value;
    	if ( preNext > 0 ) {
    		if ( expect.getCheck(preNext) == -1 ) {
    			//本当は↑の条件は良くない。まぁたぶん問題ないだろうけど...
    			//System.out.println("はぁ？？？");
    			expect.setCheck(preNext, parentNode);
    			if ( preNext >= expect.getDartsPointer() ) {
    				expect.setDartsPointer(preNext + 1);
    			}
    			//System.out.println("point ="+pre_next+" bbb"+" ch[poi]="+env.check[pre_next]);
    			return preNext;
    		}
    	}
    	
    	
    	//以下は衝突があった場合。退避させなくてはならない...
    	int de = 0;
    	int df = -1;
    	int v_base = 0;
    	//ArrayList<Integer> a = new ArrayList<Integer>();
    	for ( int i = 0; i < 256; i++ ) {
    		int p_next = expect.getBase(parentNode)+i;
    		
    		if ( p_next > 0 ) {
    			if ( expect.getCheck(p_next) == parentNode ) {
    				if ( df != -1 && p_next >= expect.getDartsPointer() ) {
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
    					v_base = expect.getDartsPointer() - df;
	    				
	    			}    			
    				
    				if ( expect.getBase(p_next) != -1 ) {
    					for ( int j = 0; j < 256; j++ ) {//場所かわるのでそれのチェックの変更
    						int chch = expect.getBase(p_next) + j;
    						
    						if ( expect.getCheck(chch) == p_next ) {
    							expect.setCheck(chch, v_base + i);    							
    						}
    						
    					}
    				}
    				int s_next = v_base+i;
    				expect.setCheck(p_next,-1);
    				expect.setBase(s_next,expect.getBase(p_next));
    				expect.setBase(p_next,-1);
    				expect.setCheck(s_next, parentNode);
    				int tmp_block = expect.getDartsToBlock(p_next);
    				//System.out.println("p_next = "+p_next+" parentNode = "+parentNode+"  fdd"+expect.getDartsToBlock(314));
    				if ( expect.getBlockToDarts(tmp_block) == p_next ) {
    					expect.setBlockToDarts(tmp_block, s_next);
    					//System.out.println("===="+p_next+"→"+s_next+" tmpb="+tmp_block);
    				}
    				expect.setDartsToBlock(s_next, tmp_block);
    				expect.setDartsToBlock(p_next, -1);
    				
    				////////////////////////////////
    				//System.out.println("変更値 ="+p_next+"→"+(v_base + i)+" ch="+expect.getCheck(v_base + i)+" ba="+expect.getBase(v_base + i));
    				//System.out.println("base[p]="+env.base[parent_node]+" i="+i);
    				de = i;//deの設定
    			}//check    			
    		}//p_next>0   		
    	}//for

		
    	expect.setBase(parentNode, v_base);
    	if ( value > de ) {	
    		de = value;
    	}
    	expect.setDartsPointer(expect.getDartsPointer() + de - df + 1);
    	//System.out.println("de="+de+" df="+df);
    	//現在のノードへの設定
    	expect.setCheck((v_base + value),parentNode);
    	//System.out.println("point ="+(v_base + value)+" ccc");
    	return (v_base + value);
	}
}
