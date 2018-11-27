package findindex.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.data.Product;
import core.service.ChangeString;
import core.service.Selector;
import core.service.MatchSynonym;
import core.util.LogUtil;
import findindex.controller.impl.FindIndexControllerImpl;
import findindex.service.Finder;

public class FinderImpl implements Finder {

	private static LogUtil logger;
	
	@Override
	public List<Product> answer(
			FindIndexControllerImpl ac, 
			String ppbody, 
			String bookmarkedWIX, 
			int minLength)
			throws FileNotFoundException,UnsupportedEncodingException, IOException {
		Selector selector = new Selector();
		MatchSynonym synonymMatcher = new MatchSynonym();//同義語判定
		ChangeString change = new ChangeString();
		
		logger = new LogUtil(FinderImpl.class);
		
		long findStart = System.currentTimeMillis();
		
		ArrayList<Product>  out = new ArrayList<Product>();
		ArrayList<Product> tmpOut = new ArrayList<Product>();
		
		String[] sa_bookmarkedWIX = bookmarkedWIX.split("_");
		List<Integer> l_bookmarkedWIX = new ArrayList<Integer>();
		for ( int i = 0; i < sa_bookmarkedWIX.length; i++ ) {
			l_bookmarkedWIX.add(Integer.parseInt(sa_bookmarkedWIX[i]));
		}
		
		String TEXT = ppbody;
		TEXT = TEXT.toLowerCase();
		TEXT = change.convert(TEXT);
		
		byte [] byteWord = null; 
		int mojicount = 1;
		int nowNode = 0;
		int nowBlock = 0;
		int numOfAttach = 0;
		
		boolean byteFlag = true;
		int transCount = 0;
		int byteCount = 0;
		boolean symbolFlag = true;
		
		for ( int j = 0; j < TEXT.length(); j++ ) {//１文字単位のループ
    		//読み込んだ1文字をバイトコードに変換
    		try {
				byteWord = TEXT.substring(j, j + 1).getBytes("UTF-8");
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
    			//System.out.print(intWord[k]+";");
    		}
    		char symbol = TEXT.substring(j, j + 1).charAt(0);
    		
    		if ( byteWord.length != 1 ) {
    			byteFlag = false;
    			 byteCount = 0;
    		} else {
    			byteFlag = true;
    			 byteCount++;
    		}
    		//System.out.println(symbol);
    		if ( symbol == ' ' || symbol == '.' || symbol == ',' || symbol == '\'' || symbol == '"' || symbol == '?' || symbol == '!' || symbol == '\n' ) {
    			symbolFlag = true;
    			byteCount = 0;
    			//System.out.println("dfafsad");
    		} else {
    			symbolFlag = false;
    		}
    		
    		if ( symbolFlag == true || byteFlag == false ) {
    			if ( tmpOut.size() != 0 ) {
    				out.addAll(tmpOut);
    				numOfAttach = numOfAttach + tmpOut.size();
    			}
    		}
    		tmpOut.clear();

    		for ( int l = 0; l < byteWord.length; l++ ) {//1byte毎スタート（文字ループ）
    			if ( nowNode != 0 ) {//ルートの時は無限ループになってしまうため回避
    				int tempNode = ac.darts.gotoNext(nowNode, intWord[l]);
    				//System.out.println("nowNode = "+nowNode+" intWord = "+intWord[l]+" next = "+tempNode);
    				if ( tempNode == 0 ) {//遷移失敗時
    					transCount = 0;
    					nowBlock = ac.darts.getDartsToBlock(nowNode);//ブロックの設定
    					tempNode = ac.block.getBlockToNode(ac.block.getFailure(nowBlock));
    					nowNode = tempNode;
    					l = -1;//マルチバイト文字で途中で遷移失敗した場合もう一度やらなくてはならないから。
    				} else {//遷移成功時
    					if ( byteFlag == true ) {
    						transCount++;
    					}
    					nowNode = tempNode;
    				}
    			} else {//ルートの場合
    				for ( int n = 0; n < byteWord.length; n++) {
    					//System.out.println("初期ノードからの遷移");
    					nowNode = ac.darts.gotoNext(nowNode,intWord[n]);
    					if ( nowNode == 0 || byteFlag == false ) {
    						transCount = 0;
    					} else {
    						transCount++;
    					}
    					//System.out.println("root = "+now_node+" int "+int_word[n]);
    				}
    				break;
    			}
    		}//１byte毎終了（文字ループ）
    		
   			nowBlock = ac.darts.getDartsToBlock(nowNode);//ブロックの設定
   			//System.out.println("now_node = "+now_node+"; now_block = "+now_block+"; mojicount = "+mojicount);
    		int nowOutputIndex = ac.block.getBlockToOutput(nowBlock);
			if ( nowOutputIndex != 0 ) {
				//String Entries = output_value[now_output_index];
				Product Entry = ac.entry.getOutputValue(nowOutputIndex);
				int nextOutputIndex;
				do{
					nextOutputIndex = ac.entry.getOutputNext(nowOutputIndex);
					//System.out.println(nextOutputIndex);
					if ( Entry.getKeywordLength() >= minLength ) {
						while ( Entry != null ) {
							Integer start = mojicount - Entry.getKeywordLength();
							Product newobj = new Product(Entry, start, mojicount);
							if ( byteCount < newobj.getKeywordLength() ) {
								if ( selector.isContainsInBookmark(l_bookmarkedWIX, Entry) ) {
									if ( !synonymMatcher.match(out, newobj) ) {
										out.add(newobj);
										//	System.out.println("追加されてみました");
										numOfAttach++;
									}
								}
							} else if ( transCount == newobj.getKeywordLength() && byteCount <= transCount ) {
								if ( selector.isContainsInBookmark(l_bookmarkedWIX, Entry) ) {
									if ( !synonymMatcher.match(tmpOut, newobj) ) {
										//System.out.println("追加されてみました2");
										//System.out.println(byteCount+" : "+transCount);
										tmpOut.add(newobj);
									}
								}
							}
						Entry = Entry.getNext();
						//System.out.println("attached1");
						}//while Entry != null
					}
					Entry = ac.entry.getOutputValue(nextOutputIndex);
					nowOutputIndex = nextOutputIndex;
				} while ( nextOutputIndex != 0 );//while
			}
			//nowOutputIndex = output_next[nowOutputIndex];
			mojicount++;
    		//System.out.println("byteFlag = "+byteFlag+" transFlag = "+transFlag +" symbolFlag = "+symbolFlag);
		}//文章ループ
		
		if ( !tmpOut.isEmpty() ) {
			out.addAll(tmpOut);
		}
		
		long findStop = System.currentTimeMillis();
				
    	Product[] oa = (Product[]) out.toArray(new Product[0]);
    	DataComparatorImpl dataComparator = new DataComparatorImpl();
    	Arrays.sort(oa, dataComparator);
    	
    	out.clear();
    	out.addAll(Arrays.asList(oa));
    	
    	long sortStop = System.currentTimeMillis();
    	
    	List<Product> resultOfSelect = new ArrayList<Product>();
    	resultOfSelect = selector.getLongestMatch(out);
    	
    	long selectStop = System.currentTimeMillis();
    	
    	logger.debug("Find and Select process :"
    		+ " find time = " + ( findStop - findStart ) + " ms"
    		+ " sort time = " + ( sortStop - findStop ) + " ms"
    		+ " select time = " + ( selectStop - sortStop ) + " ms"
    	);
    	 
		return resultOfSelect;
	}
	
	public List<List<Product>> answer_2(
			FindIndexControllerImpl ac, 
			String[] ppBody, 
			String bookmarkedWIX, 
			int minLength) throws UnsupportedEncodingException {
		logger = new LogUtil(FinderImpl.class);
		
		long findStart = System.currentTimeMillis();
		
		Selector selector = new Selector();
		ChangeString change = new ChangeString();
		
//		List<Product>  out = new ArrayList<Product>();
//		List<Product> tmpOut = new ArrayList<Product>();
		List<List<Product>> out = new ArrayList<List<Product>>();
		List<List<Product>> tmpOut = new ArrayList<List<Product>>();
		
		String[] saBookmark = bookmarkedWIX.split("_");
		List<Integer> listBookmark = new ArrayList<Integer>();
		MatchSynonym synonymMatcher = new MatchSynonym();

		
		for ( int i = 0; i < saBookmark.length; i++ ) {
			listBookmark.add(Integer.parseInt(saBookmark[i]));
		}
		
		byte [] byteWord = null; 
		int numOfAttach = 0;
		
		for ( int i = 0; i < ppBody.length; i++ ) {
//			String _ppBody = ppBody[i].toLowerCase();
			
			
			String __ppBody = change.convert(ppBody[i]);
			String _ppBody = __ppBody.toLowerCase();
			
			
			out.add(new ArrayList<Product>());
			tmpOut.add(new ArrayList<Product>());
			int count = 1;
			boolean byteFlag = true;
			int transCount = 0;
			int byteCount = 0;
			boolean symbolFlag = true;
			int nowNode = 0;
			int nowBlock = 0;
			
			for ( int j = 0; j < _ppBody.length(); j++ ) {
				String unigram = _ppBody.substring(j, j + 1);
				byteWord = unigram.getBytes("UTF-8");
				
				int[] intWord = new int[byteWord.length];
				
				for ( int k = 0; k < byteWord.length; k++ ) {
					if ( byteWord[k] < 0 ) {
						intWord[k] = byteWord[k] + 256;
					} else {
						intWord[k] = byteWord[k];
					}
				}
				
				char symbol = unigram.charAt(0);
				
				if ( byteWord.length != 1 ) {
					byteFlag = false;
					byteCount = 0;
				} else {
					byteFlag = true;
					byteCount++;
				}
				
				if ( symbol == ' '
	    				|| symbol == '.' 
	    				|| symbol == ',' 
	    				|| symbol == '\'' 
	    				|| symbol == '"'
	    				|| symbol == '?'
	    				|| symbol == '!'
	    				|| symbol == '\n' ) {
	    			symbolFlag = true;
	    			byteCount = 0;
	    		} else {
	    			symbolFlag = false;
	    		}
	    		
	    		if ( symbolFlag || !byteFlag ) {
	    			if ( tmpOut.get(i).size() != 0 ) {
	    				out.get(i).addAll(tmpOut.get(i));
	    				numOfAttach = numOfAttach + tmpOut.get(i).size();
	    			}
	    		}
	    		
	    		tmpOut.get(i).clear();
	    		
	    		for ( int k = 0; k < byteWord.length; k++ ) {
	    			if ( nowNode != 0 ) {
	    				int tempNode = ac.darts.gotoNext(nowNode, intWord[k]);
	    				
	    				if ( tempNode == 0 ) {
	    					transCount = 0;
	    					nowBlock = ac.darts.getDartsToBlock(nowNode);
	    					tempNode = ac.block.getBlockToNode(ac.block.getFailure(nowBlock));
	    					nowNode = tempNode;
	    					k = -1;
	    				} else {
	    					if ( byteFlag ) {
	    						transCount++;
	    					}
	    					nowNode = tempNode;
	    				}
	    			} else {
	    				for ( int l = 0; l < byteWord.length; l++ ) {
	    					nowNode = ac.darts.gotoNext(nowNode, intWord[l]);
	    					
	    					if ( nowNode == 0 || !byteFlag ) {
	    						transCount = 0;
	    					} else {
	    						transCount++;
	    					}
	    				}
	    				break;
	    			}
	    		}
	    		
	    		nowBlock = ac.darts.getDartsToBlock(nowNode);
	    		int nowOutputIndex = ac.block.getBlockToOutput(nowBlock);
	    		
				if ( nowOutputIndex != 0 ) {
					Product Entry = ac.entry.getOutputValue(nowOutputIndex);
					int nextOutputIndex;
					
					do{
						nextOutputIndex = ac.entry.getOutputNext(nowOutputIndex);
						
						if ( Entry.getKeywordLength() >= minLength ) {
							while ( Entry != null ) {
								int start = count - Entry.getKeywordLength();
								Product newobj = new Product(Entry, start, count);
								
								if ( byteCount < newobj.getKeywordLength() ) {
									if ( selector.isContainsInBookmark(listBookmark, Entry) ) {
										if ( !synonymMatcher.match(out.get(i), newobj) ) {
											out.get(i).add(newobj);
											numOfAttach++;
										}
									}
								} else if ( transCount == newobj.getKeywordLength() && byteCount <= transCount ) {
									if ( selector.isContainsInBookmark(listBookmark, Entry) ) {
										if ( !synonymMatcher.match(tmpOut.get(i), newobj) ) {
											tmpOut.get(i).add(newobj);
										}
									}
								}
								Entry = Entry.getNext();
							}
						}
						Entry = ac.entry.getOutputValue(nextOutputIndex);
						nowOutputIndex = nextOutputIndex;
					} while ( nextOutputIndex != 0 );
				}
				
				count++;
			}
			
			if ( !tmpOut.get(i).isEmpty() ) {
				out.get(i).addAll(tmpOut.get(i));
			}
		}
		
		long findStop = System.currentTimeMillis();
		
		for ( int i = 0; i < out.size(); i++ ) {
			Product[] oa = (Product[]) out.get(i).toArray(new Product[0]);
	    	DataComparatorImpl dataComparator = new DataComparatorImpl();
	    	Arrays.sort(oa, dataComparator);
	    	out.get(i).clear();
	    	out.get(i).addAll(Arrays.asList(oa));
		}
		
		long sortStop = System.currentTimeMillis();
    	
    	List<List<Product>> resultOfSelect = new ArrayList<List<Product>>();
    	resultOfSelect = selector._getLongestMatch(out);
    	
    	long selectStop = System.currentTimeMillis();
    	
    	logger.debug("Find and Select process :"
    		+ " find time = " + ( findStop - findStart ) + " ms"
    		+ " sort time = " + ( sortStop - findStop ) + " ms"
    		+ " select time = " + ( selectStop - sortStop ) + " ms"
    	);

		return resultOfSelect;
	}
	
}
