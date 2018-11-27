package service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import service.WIXFileParserHandler;
import data.Constant;
import data.Entry;
import data.MetaInfo;
import data.Tree;

@Service
public class WIXFileParserHandlerImpl extends DefaultHandler implements WIXFileParserHandler {
	
	private Logger logger;
	
	private boolean isKeywordTag;
	
	private boolean isTargetTag;
	
	private boolean isCommentTag;
	
	private boolean isDescriptionTag;
	
	private boolean isLanguageTag;
	
	private boolean isChildTag;
	
	
	private Locator locator;
	
	private List<Entry> entryList;
	
	private MetaInfo metaInfo;
	
	private int wid;
	
	private List<Tree> trees;
	
	public WIXFileParserHandlerImpl() {
		this.logger = Logger.getLogger(WIXFileParserHandlerImpl.class);
		this.isKeywordTag = false;
		this.isTargetTag = false;
		this.isCommentTag = false;
		this.isDescriptionTag = false;
		this.isLanguageTag = false;
		this.isChildTag = false;
	}
	
	public void setEntryList(List<Entry> entryList) {
		this.entryList = entryList;
	}
	
	public List<Entry> getEntryList() {
		return entryList;
	}

	public MetaInfo getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(MetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}
	
	public List<Tree> getTrees() {
		return trees;
	}

	public void setTrees(List<Tree> trees) {
		this.trees = trees;
	}
	
	public void setWid(int wid) {
		this.wid = wid;
	}

	@Override
	public void startDocument() throws SAXException {
		logger.debug("Here is start of XML document hoge.");
	}

	@Override
	public void startElement(
			String namespaceURI,
			String localName,
			String qName,
			Attributes attrs)
					throws SAXException {
		logger.debug("startElement : namespaceURI = " + namespaceURI + ", localName = " + localName + 
				", qName = " + qName + ", attrs = " + attrs);
		if ( Constant.BODY_TAG_NAME.getValue().equals(qName.toLowerCase()) ) {
			this.metaInfo.setWid(wid);
		} else if ( Constant.ENTRY_TAG_NAME.getValue().equals(qName.toLowerCase()) ) {
			this.entryList.add(new Entry());
			// entryのwidに現在のmetaInfoのwidをset
			this.entryList.get(this.entryList.size() - 1).setWid(this.metaInfo.getWid());
		} else if ( Constant.KEYWORD_TAG_NAME.getValue().equals(qName.toLowerCase()) ) {
			this.isKeywordTag = true;
		} else if ( Constant.TARGET_TAG_NAME.getValue().equals(qName.toLowerCase()) ) {
			this.isTargetTag = true;
		} else if ( Constant.COMMENT_TAG_NAME.getValue().equals(qName.toLowerCase()) ) {
			this.isCommentTag = true;
		} else if ( Constant.DESCRIPTION_TAG_NAME.getValue().equals(qName.toLowerCase()) ) {
			this.isDescriptionTag = true;
		} else if ( Constant.LANGUAGE_TAG_NAME.getValue().equals(qName.toLowerCase()) ) {
			this.isLanguageTag = true;
		} else if ( Constant.PART_TAG_NAME.getValue().equals(qName.toLowerCase()) ) {
			Tree tree = new Tree();
			
			// 必ず++する前にsetParent
			tree.setParent(this.metaInfo.getWid());
			wid++;
			tree.setChild(wid);
			
			trees.add(tree);
			
			MetaInfo child = new MetaInfo();
			
			for ( int i = 0; i < attrs.getLength(); i++ ) {
				//仮想wixfileのname( i = 0 )
				String attrValue = attrs.getValue(i);
				String attrKey = attrs.getQName(i);
				if ( "name".equals(attrKey) ) {
					child.setName(attrValue);
				}
			}
			
			// childの初期化
			child.setWid(wid);
			child.setAuthor(this.metaInfo.getAuthor());
			child.setComment(this.metaInfo.getComment());
			child.setDescription(this.metaInfo.getDescription());
			child.setLanguage(this.metaInfo.getLanguage());
			// 仮想wix fileとして登録
			child.setType(1);
			
			// childのpointerをsetしてmetainfoの参照をchildに遷移
			this.metaInfo.setChild(child);
			child.setParent(this.metaInfo);
			this.metaInfo = metaInfo.getChild();
		} else if ( Constant.CHILD_TAG_NAME.getValue().equals(qName.toLowerCase()) ) {
			this.isChildTag = true;
			this.metaInfo.setType(1);
			Tree tree = new Tree();
			tree.setParent(this.metaInfo.getWid());
			trees.add(tree);
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if ( isKeywordTag ) {
			logger.error("keyword is null --- linenumber = " + ( this.locator.getLineNumber() ));
			isKeywordTag = false;
		} else if ( isTargetTag ) {
			logger.error("target is null --- linenumber = " + ( this.locator.getLineNumber() ));
			isTargetTag = false;
		}
		
		if ( Constant.PART_TAG_NAME.getValue().equals(qName.toLowerCase()) ) {
			// parentに遷移
			this.metaInfo = metaInfo.getParent();
		}
		
		logger.debug("endElement : namespaceURI = " + namespaceURI + ", localName = " + localName + 
				", qName = " + qName);
	}

	@Override
	public void endDocument() throws SAXException {
		logger.debug("Here is end of XML document.");
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String setData = new String(ch, start, length);
		logger.debug("text = " + setData);
		
		if ( isKeywordTag ) {			
			this.entryList.get(this.entryList.size() - 1).setKeyword(setData);
			isKeywordTag = false;
		} else if ( isTargetTag ) {
			this.entryList.get(this.entryList.size() - 1).setTarget(setData);
			isTargetTag = false;
		} else if ( isCommentTag ) {
			this.metaInfo.setComment(setData);
			this.isCommentTag = false;
		} else if ( isDescriptionTag ) {
			this.metaInfo.setDescription(setData);
			this.isDescriptionTag = false;
		} else if ( isLanguageTag ) {
			this.metaInfo.setLanguage(setData);
			this.isLanguageTag = false;
		} else if ( isChildTag ) {
			this.trees.get(this.trees.size() - 1).setChild(Integer.parseInt(setData));
		}
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		this.locator = locator;
	}
	
}