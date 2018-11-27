package core.service;

import java.util.List;

import core.data.Product;
import core.util.LogUtil;

/**
 * PreFind処理の実装
 * 
 * @author kosuda
 */
public class PreFinder {
	
	/** アンカータグもアタッチするかどうか */
	private boolean rewriteAnchorText;
	
	// singleton
	private static LogUtil logger;
	
	/** デフォルトコンストラクタ */
	public PreFinder() {
		logger = new LogUtil(PreFinder.class);
	}
	
	public boolean checkStart(String target) {
		boolean startsWith = target.startsWith("<!--") 
			|| target.startsWith("<option") 
			|| target.startsWith("<OPTION") 
			|| target.startsWith("<script") 
			|| target.startsWith("<SCRIPT")
			|| target.startsWith("<style")
			|| target.startsWith("<STYLE")
		;
		
		if ( !rewriteAnchorText ) {
			startsWith = startsWith 
				|| target.startsWith("<a ") 
				|| target.startsWith("<A ")
			;
		}
		
		return  startsWith;
	}

	public boolean checkStartAndEnd(String target) {
		boolean startsAndEndsWith = (target.startsWith("<!--") && target.endsWith("-->")) 
			|| (target.startsWith("<option") && target.endsWith("</option>")) 	
			|| (target.startsWith("<OPTION") && target.endsWith("</OPTION>")) 
			|| (target.startsWith("<script") && target.endsWith("</script>")) 
			|| (target.startsWith("<SCRIPT") && target.endsWith("</SCRIPT>"))
			|| (target.startsWith("<style") && target.endsWith("</style>"))
			|| (target.startsWith("<STYLE") && target.endsWith("</STYLE>"))
		;
		
		if ( !rewriteAnchorText ) {
			startsAndEndsWith = startsAndEndsWith 
				|| (target.startsWith("<a ") && target.endsWith("</a>")) 
				|| (target.startsWith("<A ") && target.endsWith("</A>"))
			;
		}
		
		return  startsAndEndsWith;
	}

	public String preFind(String body, List<Product> doNotRewriteList, boolean rewriteAnchorText) {
		this.rewriteAnchorText = rewriteAnchorText;
		
		StringBuffer ppBody = new StringBuffer();
		String markup = "";
		
		int start = 0, end, startTag = 0, endTag;

		boolean doNotRewriteTextAlso = false;//テキスト部分もRewriteしてはいけないタグかをチェックするフラグ
		
		boolean isInnerTag = false;
		int innerTagCount = 0;
		
		String text = "", unigram = "";
		
		for ( int i = 0; i < body.length(); i++ ) {
			unigram = body.substring(i, i + 1);
			
			if ( "<".equals(unigram) ) {
				if ( !doNotRewriteTextAlso ) {
					if ( !isInnerTag && innerTagCount == 0 ) {
						end = i;
						text = body.substring(start, end);
						
						if ( checkStart(text) ) {
							doNotRewriteTextAlso = true;
						} else {
							ppBody.append(text);
							start = i;
							startTag = i;
						}
						
						innerTagCount++;
					} else {
						//startを更新しちゃだめ	
						startTag = start;
						innerTagCount++;
					}
				}
				
				isInnerTag = true;
			} else if ( ">".equals(unigram) ) {
				if ( isInnerTag && innerTagCount == 1) {
					endTag = i + 1;
					markup = body.substring(startTag, endTag);
					
					if ( !checkStart(markup) || checkStartAndEnd(markup) ) {
						if ( !rewriteAnchorText || (rewriteAnchorText && !(markup.startsWith("<a ") || "</a>".equals(markup))) ) {
							doNotRewriteList.add(new Product(markup, ppBody.length()));
						}
						
						markup = "";
						start = i + 1;
						doNotRewriteTextAlso = false;
					} else {
						doNotRewriteTextAlso = true;
					}
					
					innerTagCount--;
				} else {
					if ( innerTagCount == 1 ) {
						endTag = i + 1;
						markup = body.substring(startTag, endTag);
						
						if ( !checkStart(markup) || checkStartAndEnd(markup) ) {
							if( !rewriteAnchorText || ( rewriteAnchorText && !(markup.startsWith("<a ") || markup.equals("</a>"))) ) {
								doNotRewriteList.add(new Product(markup, ppBody.length()));
							}
							
							markup = "";
							start = i + 1;
							doNotRewriteTextAlso = false;
						} else {
							doNotRewriteTextAlso = true;
						}
						
						innerTagCount--;
					} else if ( innerTagCount == 0 && doNotRewriteTextAlso ) {
						endTag = i + 1;
						markup = body.substring(startTag, endTag);
						
						if ( !checkStart(markup) || checkStartAndEnd(markup) ) {
							if ( !rewriteAnchorText || ( rewriteAnchorText && !(markup.startsWith("<a ") || markup.equals("</a>"))) ) {
								doNotRewriteList.add(new Product(markup, ppBody.length()));
							}
							
							markup = "";
							start = i + 1;
							doNotRewriteTextAlso = false;
						}
					} else {
						innerTagCount--;
					}
				}
				isInnerTag = false;
			} 
		}
		
		end = body.length();
		text = body.substring(start, end);
		ppBody.append(text);
		
		logger.debug("Prefind process :"
			+ " rewriteAnchorText = " + rewriteAnchorText
			+ " body size = " + body.getBytes().length + " (byte)"
			+ " ppbody size = " + ppBody.toString().getBytes().length + " (byte)"
			+ " doNotRewriteList size = " + doNotRewriteList.size()
		);
		
		return ppBody.toString();
	}

}
