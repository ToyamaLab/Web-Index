package service;


import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public interface WIXFileParserHandler {
	
	void startDocument() throws SAXException;
	
	void startElement(String namespaceURI, String localName, String qName, Attributes attrs) throws SAXException;
	
	void endElement(String namespaceURI, String localName, String qName) throws SAXException;
	
	void endDocument() throws SAXException;
	
	void characters(char[] ch, int start, int length) throws SAXException;
	
	void setDocumentLocator(Locator locator);
	
}
