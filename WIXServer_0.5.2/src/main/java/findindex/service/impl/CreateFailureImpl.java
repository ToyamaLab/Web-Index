package findindex.service.impl;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Queue;

import core.util.LogUtil;
import findindex.data.BlockData;
import findindex.data.EntryData;
import findindex.data.GotoFunctionNode;
import findindex.data.NodeForCreateFailure;
import findindex.service.CreateFailure;

public class CreateFailureImpl implements CreateFailure {
	private BlockData block;
	private GotoFunctionNode darts;
	private EntryData entry;
	
	private static LogUtil logger;
	
	public CreateFailureImpl(
			BlockData block, 
			GotoFunctionNode darts,
			EntryData entry) {	
		this.block = block;
		this.darts = darts;
		this.entry = entry;
		logger = new LogUtil(CreateFailureImpl.class);
	}

	public void createFailure() {
		//ここからFailure、output_indexとoutput_nextの設定
    	//int now_node1;//現在のノード
    	int failCount = 0; //記録用
		Queue<NodeForCreateFailure> queue = new LinkedList<NodeForCreateFailure>();//幅優先探索に必要文字の最後のノード番号が入る。
		NodeForCreateFailure rootNode = new NodeForCreateFailure();
		rootNode.setNode(0);		
		queue.offer(rootNode);
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(1);
		format.setMinimumFractionDigits(1);
		while ( !queue.isEmpty() ) {//キューがなくなるまで実行
			//記録
			if ( failCount % 10000000 == 0 ) {
				logger.debug("Now creating failure : " + format.format(100 * ((double) failCount / block.getNumOfBlock())) + "%\t" + failCount + "/" + block.getNumOfBlock());
			}
			failCount++;
			//記録
			NodeForCreateFailure fnode = queue.poll();
			createfail(fnode);			//現在のノードのfailure生成
			putChildNode(queue, fnode);	//現在のノードの子ノードをキューに追加
			fnode = null;
		}//キュー内の処理
		queue.clear();
	}
	
	private void putChildNode(Queue<NodeForCreateFailure> queue, NodeForCreateFailure fnode) {//キューに現在のノードの子ノードを入れる。
		int nowNode[] = new int[7];
		int pre[] = new int[7];
		
		nowNode[0] = fnode.getNode();
//	  	System.out.println("now_node = "+now_node1);
		for ( int a = 0 ; a < 256; a++ ) {//現在ノードの子集合をキューに追加/１バイト目
			nowNode[1] = darts.gotoNext(nowNode[0], a);	
			if ( nowNode[1] == 0 ) {//遷移失敗
			  continue;
			} else {//ダブルの場合ループする。
				pre[1] = a;
			}
			//System.out.println("pre2 = " +pre2);
			//バイトの終端の場合
			if ( nowNode[1] == block.getBlockToNode(darts.getDartsToBlock(nowNode[1])) ) {
				NodeForCreateFailure newNode = new NodeForCreateFailure(1);
				newNode.set(1,nowNode[0],nowNode[1],pre[1]);
				queue.offer(newNode);
				continue;
			}
		  
			for ( int b = 0 ; b < 256; b++ ) {//２バイト目
				nowNode[2] = darts.gotoNext(nowNode[1], b);	
				if ( nowNode[2] == 0 ) {//遷移失敗
					continue;
				} else {//ダブルの場合ループする。
					pre[2] = b;
				}
				//バイトの終端の場合
				if ( nowNode[2] == block.getBlockToNode(darts.getDartsToBlock(nowNode[2])) ) {
					NodeForCreateFailure newNode = new NodeForCreateFailure(2);
					newNode.set(2, nowNode[0], nowNode[2], pre[1], pre[2]);
					queue.offer(newNode);
					continue;
				}
					
				for ( int c = 0 ; c < 256; c++ ) {//3バイト目
					nowNode[3] = darts.gotoNext(nowNode[2], c);	
					if ( nowNode[3] == 0 ) {//遷移失敗
						continue;
					} else {//ダブルの場合ループする。
						pre[3] = c;
					}
					//バイトの終端の場合
					if ( nowNode[3] == block.getBlockToNode(darts.getDartsToBlock(nowNode[3])) ) {
						NodeForCreateFailure newNode = new NodeForCreateFailure(3);
						newNode.set(3, nowNode[0], nowNode[3], pre[1], pre[2], pre[3]);
						queue.offer(newNode);
						continue;
					 }
					
					for ( int d = 0 ; d < 256; d++ ) {//4バイト目
						nowNode[4] = darts.gotoNext(nowNode[3], d);		
						if ( nowNode[4] == 0 ) {//遷移失敗
							continue;
						} else {//ダブルの場合ループする。
							pre[4] = d;
						}
						//バイトの終端の場合
						if ( nowNode[4] == block.getBlockToNode(darts.getDartsToBlock(nowNode[4])) ) {
							NodeForCreateFailure newNode = new NodeForCreateFailure(4);
							newNode.set(4, nowNode[0], nowNode[4], pre[1], pre[2], pre[3], pre[4]);
							queue.offer(newNode);
							continue;
						}
						  
						for ( int e = 0 ; e < 256; e++ ) {//5バイト目
							nowNode[5] = darts.gotoNext(nowNode[4], e);	
							if ( nowNode[5] == 0 ) {//遷移失敗
								continue;
							} else {//ダブルの場合ループする。
								pre[5] = e;
							}
							//バイトの終端の場合
							if ( nowNode[5] == block.getBlockToNode(darts.getDartsToBlock(nowNode[5])) ) {
								NodeForCreateFailure newNode = new NodeForCreateFailure(5);
								newNode.set(5, nowNode[0], nowNode[5], pre[1], pre[2], pre[3], pre[4], pre[5]);
								queue.offer(newNode);
								continue;
							}
						 
							for ( int f = 0 ; f < 256; f++ ) {//6バイト目
								nowNode[6] = darts.gotoNext(nowNode[5], f);	
								if ( nowNode[6] == 0 ) {//遷移失敗
									continue;
								} else {//ダブルの場合ループする。
									pre[6] = f;
								}
								//バイトの終端の場合
								if ( nowNode[6] == block.getBlockToNode(darts.getDartsToBlock(nowNode[6])) ) {
									NodeForCreateFailure newNode = new NodeForCreateFailure(6);
									newNode.set(6, nowNode[0], nowNode[5], pre[1], pre[2], pre[3], pre[4], pre[5], pre[6]);
									queue.offer(newNode);
									continue;
								}	 
							}//6バイト目
						}//5バイト目
					}//4バイト目
				}//3バイト目
			}//２バイト目
		}//1バイト目
	}
	
	void createfail(NodeForCreateFailure fnode){//現在のノード値のfail,output_index,output_nextを構築
		int failureLength;
		int nowNode = fnode.getNode();
		int preNode = fnode.getPreNode();
		int failNode;//preのfail先のノード
		int nowBlock;
		int preBlock;
		int failBlock = 0;
		boolean isTrans = true;
		int[] text = fnode.getText();
		int byteLength = fnode.getLength();
		//System.out.println("now_node = "+now_node+"; pre_node = "+pre_node+"; text = "+text); 
		if ( nowNode == 0 ) {//ルートノードだけ特別
			block.setFailure(0, 0);
			return;
		}	
		nowBlock = darts.getDartsToBlock(nowNode);//ブロックの設定
		preBlock = darts.getDartsToBlock(preNode);

		if ( preBlock == 0 || nowBlock == 0 ) {//ルート+1ノードは特別
			block.setFailure(nowBlock, 0);
			block.setFailureLength(nowBlock, 0);
			return;
		}
		failBlock = block.getFailure(preBlock);
		failNode = block.getBlockToNode(failBlock);//preのfail
		//System.out.println(text);
		//前の文字列のfail→遷移成功の場合
		//System.out.println(now_block);
		nowNode = failNode;
		for ( int j = 0; j < byteLength; j++ ) {
				nowNode = darts.gotoNext(nowNode, text[j]);//fail[pre]からの遷移
				//System.out.println("nowNode = " + nowNode);
				if ( nowNode == 0 ) {
					isTrans = false;
					if ( failNode == 0 ) {
						break;
					}		
					failBlock = block.getFailure(failBlock);
					failNode = block.getBlockToNode(failBlock);
					nowNode = failNode;
					j = -1;//0 じゃないらしい。
					continue;
				}
				isTrans = true;
		}		
		if ( isTrans ) {//つまりfail[pre]からの遷移が成功している場合
			failBlock = darts.getDartsToBlock(nowNode);//ブロックの設定
			//System.out.println("failBlock = " + failBlock);
			failureLength = block.getBlockLength(failBlock);
		} else {//fail[pre]からの遷移が失敗している場合、ルートからの遷移	
			failBlock = 0;
			failureLength = 0;			
		}//fail[pre]からの遷移失敗
		block.setFailure(nowBlock, failBlock);
		block.setFailureLength(nowBlock, failureLength);
		
		//System.out.println("now_node = "+block_goto_index[now_block]+"; fail_node = "+block_goto_index[fail_block]); 
		//アウトプット設定
		if ( block.getBlockToOutput(nowBlock) != 0 && block.getBlockToOutput(failBlock) != 0 ) {
			//つまり自分自信がアウトプットで次もアウトプットなのでネクスト設定しなきゃね。
			//entry.outputNext[block.getBlockToOutput(nowBlock)] = block.getBlockToOutput(failBlock); 
			entry.setOuterOutputNext(block.getBlockToOutput(nowBlock),block.getBlockToOutput(failBlock));
		} else if ( block.getBlockToOutput(nowBlock) == 0 && block.getBlockToOutput(failBlock) != 0 ) {
			//自分自身はアウトプットなくて、fail先があるので、アウトプットインデックスにアウトプット先を。
			block.setBlockToOutput(nowBlock, block.getBlockToOutput(failBlock));
		}	
	}//create_fail
}
