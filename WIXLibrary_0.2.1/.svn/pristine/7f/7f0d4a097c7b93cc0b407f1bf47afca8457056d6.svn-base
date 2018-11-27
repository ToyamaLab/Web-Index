package service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import service.WIXFileParserErrorHandler;

@Service
public class WIXFileParserErrorHandlerImpl implements WIXFileParserErrorHandler {

	private Logger logger;
	
	public WIXFileParserErrorHandlerImpl() {
		this.logger = Logger.getLogger(WIXFileParserErrorHandlerImpl.class);
	}
	
	@Override
	public void warning(SAXParseException err) throws SAXException {
		String errMessage = "**Parsing Warning**" + " Line: " + err.getLineNumber() + " Message: " + err.getMessage();
		logger.warn(errMessage);
		throw new SAXException(errMessage);
	}

	@Override
	public void error(SAXParseException err) throws SAXException {
		String errMessage = "**Parsing Error**" + " Line: " + err.getLineNumber() + " Message: " + err.getMessage();
		logger.error(errMessage);
		throw new SAXException(errMessage);
	}

	@Override
	public void fatalError(SAXParseException err) throws SAXException {
		String errMessage = "**Parsing Fatal Error**" + " Line: " + err.getLineNumber() + " Message: " + err.getMessage();
		logger.fatal(errMessage);
		throw new SAXException(errMessage);
	}

}
