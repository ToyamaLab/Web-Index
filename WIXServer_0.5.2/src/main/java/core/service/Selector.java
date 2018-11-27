package core.service;

import java.util.ArrayList;
import java.util.List;

import core.data.Product;
import core.util.LogUtil;

/**
 * select process
 * @author kosuda
 */
public class Selector {
	
	private static LogUtil logger;
	
	public Selector() {
		logger = new LogUtil(Selector.class);
	}

	public boolean isContainsInBookmark(List<Integer> bookmarkedWIX, Product target) {
		int wid = target.getWid();
		
		if(wid < 0){
			wid = -wid;
		}
		return bookmarkedWIX.contains(wid);
	}
	
	public List<Product> getLongestMatch(List<Product> resultOfFind) {
		List<Product> resultOfSelect = new ArrayList<Product>();
		boolean isResultOfSelectAdd = true;
		boolean isNextAdd = true;
		int resultOfSelectIndex = 0;
		
		for ( int i = 0; i < resultOfFind.size(); i++ ) {
			if ( resultOfSelect.isEmpty() ) {
				resultOfSelect.add(resultOfFind.get(i));
			} else {
				if ( resultOfSelect.get(resultOfSelectIndex).getNext() == null ) {
					if ( resultOfFind.get(i).getStart() >= resultOfSelect.get(resultOfSelectIndex).getEnd() ) {
						//addするタイミングでset null
//						setNull(resultOfSelect, resultOfSelectIndex);
						
						resultOfSelect.add(resultOfFind.get(i));
						resultOfSelectIndex++;
					} else {
						if ( resultOfFind.get(i).getKeywordLength() > resultOfSelect.get(resultOfSelectIndex).getKeywordLength() ) {
							resultOfSelect.set(resultOfSelectIndex, resultOfFind.get(i));
						} else if ( resultOfFind.get(i).getKeywordLength() == resultOfSelect.get(resultOfSelectIndex).getKeywordLength() ) {
							if ( !resultOfFind.get(i).getKeyword().equals(resultOfSelect.get(resultOfSelectIndex).getKeyword()) ) {
								resultOfSelect.get(resultOfSelectIndex).setNext(resultOfFind.get(i));
							} else {
								if ( !resultOfFind.get(i).getTarget().equals(resultOfSelect.get(resultOfSelectIndex).getTarget()) ) {
									resultOfSelect.get(resultOfSelectIndex).setNext(resultOfFind.get(i));
								}
							}
						}
					}
				} else {
					Product next = resultOfSelect.get(resultOfSelectIndex);
					isResultOfSelectAdd = true;
					isNextAdd = true;
					
					while ( next != null ) {
						if ( resultOfFind.get(i).getStart() < next.getEnd() ) {
							isResultOfSelectAdd = false;
							
							if ( resultOfFind.get(i).getKeywordLength() < next.getKeywordLength() ) {
								isNextAdd = false;
							}
						}
						
						next = next.getNext();
					}
					
					if ( isResultOfSelectAdd ) {
						//addするたいみんぐでset null
//						setNull(resultOfSelect, resultOfSelectIndex);
						
						resultOfSelect.add(resultOfFind.get(i));
						resultOfSelectIndex++;
					} else {
						if ( isNextAdd ) {
							if ( !resultOfFind.get(i).getKeyword().equals(resultOfSelect.get(resultOfSelectIndex).getKeyword()) ) {
								resultOfSelect.get(resultOfSelectIndex).setNext(resultOfFind.get(i));
							} else {
								if ( !resultOfFind.get(i).getTarget().equals(resultOfSelect.get(resultOfSelectIndex).getTarget()) ) {
									resultOfSelect.get(resultOfSelectIndex).setNext(resultOfFind.get(i));
								}
							}
						}
					}
				}
			}
		}
		//最後にsetnull
//		setNull(resultOfSelect, resultOfSelectIndex);
		
		logger.debug("Select Process : "
			+ " Result of find size = " + resultOfFind.size()
			+ " Result of select size = " + resultOfSelect.size()
		);
		
		return resultOfSelect;
	}
	
	public List<List<Product>> _getLongestMatch(List<List<Product>> resultOfFind) {
		List<List<Product>> resultOfSelect = new ArrayList<List<Product>>();
		
		for ( int i = 0; i < resultOfFind.size(); i++ ) {
			List<Product> _resultOfFind = resultOfFind.get(i);
			resultOfSelect.add(new ArrayList<Product>());
			
			boolean isResultOfSelectAdd = true;
			boolean isNextAdd = true;
			int resultOfSelectIndex = 0;
			
			for ( int j = 0; j < _resultOfFind.size(); j++ ) {
				if ( resultOfSelect.get(i).isEmpty() ) {
					resultOfSelect.get(i).add(_resultOfFind.get(j));
				} else {
					if ( resultOfSelect.get(i).get(resultOfSelectIndex).getNext() == null ) {
						if ( _resultOfFind.get(j).getStart() >= resultOfSelect.get(i).get(resultOfSelectIndex).getEnd() ) {
							
							resultOfSelect.get(i).add(_resultOfFind.get(j));
							resultOfSelectIndex++;
						} else {
							if ( _resultOfFind.get(j).getKeywordLength() > resultOfSelect.get(i).get(resultOfSelectIndex).getKeywordLength() ) {
								resultOfSelect.get(i).set(resultOfSelectIndex, _resultOfFind.get(j));
							} else if ( _resultOfFind.get(j).getKeywordLength() == resultOfSelect.get(i).get(resultOfSelectIndex).getKeywordLength() 
									&& !(_resultOfFind.get(j).getKeyword().equals(resultOfSelect.get(i).get(resultOfSelectIndex).getKeyword()) 
									&& _resultOfFind.get(j).getTarget().equals(resultOfSelect.get(i).get(resultOfSelectIndex).getTarget())) ) {
								resultOfSelect.get(i).get(resultOfSelectIndex).setNext(_resultOfFind.get(j));
							}
						}
					} else {
						Product next = resultOfSelect.get(i).get(resultOfSelectIndex).getNext();
						isResultOfSelectAdd = true;
						isNextAdd = true;
						
						while ( next != null ) {
							if ( _resultOfFind.get(j).getStart() < next.getEnd() ) {
								isResultOfSelectAdd = false;
								
								if ( _resultOfFind.get(j).getKeywordLength() < next.getKeywordLength() ) {
									isNextAdd = false;
								}
							}
							
							next = next.getNext();
						}
						
						if ( isResultOfSelectAdd ) {
							
							resultOfSelect.get(i).add(_resultOfFind.get(j));
							resultOfSelectIndex++;
						} else {
							if ( isNextAdd
									&& !(_resultOfFind.get(j).getKeyword().equals(resultOfSelect.get(i).get(resultOfSelectIndex).getKeyword()) 
										&& _resultOfFind.get(j).getTarget().equals(resultOfSelect.get(i).get(resultOfSelectIndex).getTarget())) ) {
								resultOfSelect.get(i).get(resultOfSelectIndex).setNext(_resultOfFind.get(j));
							}
						}
					}
				}
			}
			
		}
		
		return resultOfSelect;
	}
	
	private void setNull(List<Product> resultOfSelect, int index) {
		Product next = resultOfSelect.get(index);
		
		while ( next != null ) {
			next.setKeyword(null);
			next.setTarget(null);
			
			next = next.getNext();
		}
	}
	
}
