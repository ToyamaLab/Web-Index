package service;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public interface WIXFileParserErrorHandler extends ErrorHandler{
	
	public void warning(SAXParseException err) throws SAXException;
	
	public void error(SAXParseException err) throws SAXException;
	
	public void fatalError(SAXParseException err) throws SAXException;

}
