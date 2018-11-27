package service.impl;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import data.Entry;
import data.MetaInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/beans-service.xml", "classpath:/META-INF/spring/beans-dao.xml"})
public class WIXFileParserHandlerImplTest {
	
	@Autowired
	private WIXFileParserHandlerImpl wixFileParserHandler;
	
	@Autowired
	private WIXFileParserErrorHandlerImpl wixFileParserErrorHandler;
	
	@Test
	public void parseTest() 
			throws ParserConfigurationException, SAXException, FileNotFoundException, IOException {
		assertThat(wixFileParserHandler, is(notNullValue()));
		assertThat(wixFileParserErrorHandler, is(notNullValue()));
		
		List<Entry> entryList = new ArrayList<Entry>();
		MetaInfo metaInfo = new MetaInfo();
		
		wixFileParserHandler.setEntryList(entryList);
		wixFileParserHandler.setMetaInfo(metaInfo);
		
		assertThat(wixFileParserHandler.getEntryList(), is(notNullValue()));
		assertThat(wixFileParserHandler.getMetaInfo(), is(notNullValue()));
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		
		XMLReader reader = factory.newSAXParser().getXMLReader();
		reader.setContentHandler(wixFileParserHandler);
		reader.setErrorHandler(wixFileParserErrorHandler);
		
		reader.parse(new InputSource(new FileInputStream("src/main/resources/test.wix")));
		
		System.out.println(entryList.size());
		System.out.println(metaInfo.getLanguage());
	}
}