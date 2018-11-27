package core.service;

import java.util.ArrayList;
import java.util.List;

import core.data.Product;
import core.data.ResponseJson;
import core.util.LogUtil;

/**
 * rewrite process
 * @author kosuda
 */
public class Rewriter {
	
	private List<Product> resultOfDecide;
	
	private List<Product> doNotRewriteList;
	
	private String ppBody;
	
	private String bookmarkedWIX;
	
	private String target; // オーサ
	
	private int start;
	
	private int end;
	
	// singleton pattern
	private static LogUtil logger;
	
	public Rewriter(List<Product> resultOfDecide,
			List<Product> doNotRewriteList,
			String ppBody,
			String bookmarkedWIX) {
		this.resultOfDecide = resultOfDecide;
		this.doNotRewriteList = doNotRewriteList;
		this.ppBody = ppBody;
		this.bookmarkedWIX = bookmarkedWIX;
		this.start = 0;
		this.end = 0;
		logger = new LogUtil(Rewriter.class);
	}

	public Rewriter(String bookmarkedWIX) {
		this.bookmarkedWIX = bookmarkedWIX;
		this.start = 0;
		this.end = 0;
		logger = new LogUtil(Rewriter.class);
	}
	
	public String rewrite() {
		StringBuffer newBody = new StringBuffer();
		
		int checkedResultOfDecide = 0, checkedDoNotRewriteList = 0;
		
		while ( checkedResultOfDecide < resultOfDecide.size() 
				&& checkedDoNotRewriteList < doNotRewriteList.size() ) {
			if ( resultOfDecide.get(checkedResultOfDecide).getStart() 
					< doNotRewriteList.get(checkedDoNotRewriteList).getStart() ) {
				if ( resultOfDecide.get(checkedResultOfDecide).getEnd() 
						<= doNotRewriteList.get(checkedDoNotRewriteList).getStart() ) {
					newBody.append(rewriteByResultOfDecide(checkedResultOfDecide));
				}
				
				checkedResultOfDecide++;
			} else {
				newBody.append(rewriteByDoNotRewriteList(checkedDoNotRewriteList));
				checkedDoNotRewriteList++;
			}
		}
		if ( checkedResultOfDecide == resultOfDecide.size() ) {
			for ( int i = checkedDoNotRewriteList; i < doNotRewriteList.size(); i++ ) {
				newBody.append(rewriteByDoNotRewriteList(i));
			}
		} else if ( checkedDoNotRewriteList == doNotRewriteList.size() ) {			
			for ( int i = checkedResultOfDecide; i < resultOfDecide.size(); i++ ) {
				newBody.append(rewriteByResultOfDecide(i));
			}
		}
		// 余ったppbodyをくっつける
		newBody.append(ppBody.substring(start));
		
		logger.debug("Rewrite process :"
			+ " ppbody size = " + ppBody.getBytes().length + " (byte)"
			+ " bookmarkdWIX = " + bookmarkedWIX
			+ " newBody size = " + newBody.toString().getBytes().length + " (byte)"
		);
		
		logger.debug("rewriter.java部のnewbody： " + newBody.toString());
		return newBody.toString();
	}
	
	//Object-Attachのメソッド
	public List<ResponseJson> rewrite(List<List<Product>> resultOfSelect, String[] ppbody) {
		List<ResponseJson> resultOfRewrite = new ArrayList<ResponseJson>();
		
		if ( resultOfSelect.size() != ppbody.length ) {
			logger.warn("The size is diffrence between resultOfSelect and ppbody array.");
			return resultOfRewrite;
		}
		
		for ( int i = 0; i < resultOfSelect.size(); i++ ) {
			this.start = 0;
			this.end = 0;
			
			if ( resultOfSelect.get(i).isEmpty() ) {
				continue;
			}
			
			ResponseJson elem = new ResponseJson();
			elem.setIndex(i);
			StringBuffer newBody = new StringBuffer();
			
			for ( int j = 0; j < resultOfSelect.get(i).size(); j++ ) {
				this.end = resultOfSelect.get(i).get(j).getStart();
				
				
				if ( start < end ) {
					newBody.append(ppbody[i].substring(start, end));
				}
				
				if ( resultOfSelect.get(i).get(j).getNext() == null ) {
					newBody
						.append("<a class='wix-link' href=\"wix:\" wid='")
						.append(resultOfSelect.get(i).get(j).getWid())
						.append("' eid='")
						.append(resultOfSelect.get(i).get(j).getEid())
						.append("' bookmarkedWIX='")
						.append(bookmarkedWIX)
						.append("'>")
						.append(ppbody[i].substring(resultOfSelect.get(i).get(j).getStart(), resultOfSelect.get(i).get(j).getEnd()))
						.append("</a>")
					;
					logger.debug("target(o) : "+ resultOfSelect.get(i).get(j).getTarget());
					this.start = resultOfSelect.get(i).get(j).getEnd();
				} else {
					StringBuffer tmp = new StringBuffer();
					Product next = resultOfSelect.get(i).get(j);
					int endOfAnchorText = next.getEnd();
					
					while ( next != null ) {
						if ( tmp.length() == 0 ) {
							tmp.append("[\"")
							.append(next.getWid())
							.append("-")
							.append(next.getEid())
							.append("-")
							.append(next.getKeyword())
							.append("\"");
						} else {
							tmp.append(",\"")
							.append(next.getWid())
							.append("-")
							.append(next.getEid())
							.append("-")
							.append(next.getKeyword())
							.append("\"");
						}
						
						if ( endOfAnchorText < next.getEnd() ) {
							endOfAnchorText = next.getEnd();
						}
						
						next = next.getNext();
					}
					
					tmp.append("]");
					
					newBody
						.append("<a class=\"wix-decide\" href=\"wix:\" links='")
						.append(tmp)
						.append("' bookmarkedWIX=\"")
						.append(bookmarkedWIX)
						.append("\">")
							.append(ppbody[i].substring(resultOfSelect.get(i).get(j).getStart(), endOfAnchorText))
						.append("</a>")
					;
					
					this.start = endOfAnchorText;
				}
			}
			//余ったppbodyをくっつける
			newBody.append(ppbody[i].substring(start));
			elem.setNewBody(newBody.toString());
			
			resultOfRewrite.add(elem);
		}
		
		logger.debug("Rewrite process :"
			+ " ppbody array length = " + ppbody.length
			+ " bookmarkdWIX = " + bookmarkedWIX
			+ " result of rewrite size = " + resultOfRewrite.size()
		);
		
		return resultOfRewrite;
	}
	
	//強制アタッチのメソッド
	private StringBuffer rewriteByResultOfDecide(int checkedResultOfDecide) {
		StringBuffer result = new StringBuffer();
		end = resultOfDecide.get(checkedResultOfDecide).getStart();
		
		if ( start < end ) {
			result.append(ppBody.substring(start, end));
		}
		
		if ( resultOfDecide.get(checkedResultOfDecide).getNext() == null ) {
//			result
//				.append("<a class='wix-link' href=\"wix:\" wid='")
//				.append(resultOfDecide.get(checkedResultOfDecide).getWid())
//				.append("' eid='")
//				.append(resultOfDecide.get(checkedResultOfDecide).getEid())
//				.append("' bookmarkedWIX='")
//				.append(bookmarkedWIX)
//				.append("'>")
//				.append(ppBody.substring(resultOfDecide.get(checkedResultOfDecide).getStart(), resultOfDecide.get(checkedResultOfDecide).getEnd()))
//				.append("</a>")
//			;
			//オーサ用
			//logger.debug("target(a) : "+ resultOfDecide.get(checkedResultOfDecide).getTarget());
			this.target = resultOfDecide.get(checkedResultOfDecide).getTarget();
			result
			.append("<a class=\"wix-authorLink\" target=\"_blank\"  href=\"")
			.append(this.target)
			.append("\">")
			.append(ppBody.substring(resultOfDecide.get(checkedResultOfDecide).getStart(), resultOfDecide.get(checkedResultOfDecide).getEnd()))
			.append("</a>")
			;
			//logger.debug("target:"+this.target);
			
			start = resultOfDecide.get(checkedResultOfDecide).getEnd();
		} else {
			StringBuffer tmp = new StringBuffer();
			Product next = resultOfDecide.get(checkedResultOfDecide);
			int endOfAnchorText = next.getEnd();
			
			while ( next != null ) {
				if ( tmp.length() == 0 ) {
					tmp.append("[\"")
					.append(next.getWid())
					.append("-")
					.append(next.getEid())
					.append("-")
					.append(next.getKeyword())
					.append("\"");
				} else {
					tmp.append(",\"")
					.append(next.getWid())
					.append("-")
					.append(next.getEid())
					.append("-")
					.append(next.getKeyword())
					.append("\"");
				}
				
				if ( endOfAnchorText < next.getEnd() ) {
					endOfAnchorText = next.getEnd();
				}
				next = next.getNext();
			}
			
			tmp.append("]");
			
			result
				.append("<a class=\"wix-decide\" href=\"wix:\" links='")
				.append(tmp)
				.append("' bookmarkedWIX=\"")
				.append(bookmarkedWIX)
				.append("\">")
					.append(ppBody.substring(resultOfDecide.get(checkedResultOfDecide).getStart(), endOfAnchorText))
				.append("</a>")
			;
			
			start = endOfAnchorText;
		}

		return result;
	}
	
	private StringBuffer rewriteByDoNotRewriteList(int checkedDoNotRewriteList) {
		StringBuffer result = new StringBuffer();
		end = doNotRewriteList.get(checkedDoNotRewriteList).getStart();
		
		if ( start < end ) {
			result.append(ppBody.substring(start, end));
		}
		
		result.append(doNotRewriteList.get(checkedDoNotRewriteList).getMarkup());
		start = end;
		
		return result;
	}
	
}