package controller.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import service.impl.CanonicalizationImpl;
import service.impl.KeywordComparatorImpl;
import service.impl.MetaInfoServiceImpl;
import service.impl.UploadServiceImpl;
import service.impl.VersionManagerImpl;
import service.impl.WIXFileParserErrorHandlerImpl;
import service.impl.WIXFileParserHandlerImpl;
import service.impl.WidComparatorImpl;
import controller.WIXFileUploadController;
import data.Constant;
import data.Entry;
import data.JsonModel;
import data.MetaInfo;
import data.Tree;
import data.Version;

@Controller
@RequestMapping(value = "/upload")
public class WIXFileUploadControllerImpl implements WIXFileUploadController {
	
	@Autowired
	private WIXFileParserHandlerImpl wixFileParserHandler;
	
	@Autowired
	private WIXFileParserErrorHandlerImpl wixFileParserErrorHandler;
	
	@Autowired
	private UploadServiceImpl uploadService;
	
	@Autowired
	private VersionManagerImpl versionManager;
	
	@Autowired
	private CanonicalizationImpl canonicalization;
	
	@Autowired
	private KeywordComparatorImpl keywordComparator;
	
	@Autowired
	private WidComparatorImpl widComparator;
	
	@Autowired
	private MetaInfoServiceImpl metaInfoService;
	
	private Logger logger;
	
	public WIXFileUploadControllerImpl() {
		this.logger = Logger.getLogger(WIXFileUploadControllerImpl.class);
	}
	
	@Override
	@ExceptionHandler({ 
		SAXException.class, 
		ParserConfigurationException.class, 
		IOException.class, 
		NoFilepatternException.class, 
		GitAPIException.class,
		RuntimeException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody JsonModel exceptionHandler(Exception e) {
		String message = e.getMessage();
		JsonModel response = new JsonModel();
		response.setSuccess(false);
		response.setMessage(message);
		logger.error(message);
		return response;
	}

//	@Override
//	@RequestMapping(method = RequestMethod.POST, headers = { "Accept=application/json"})
//	public @ResponseBody JsonModel upload(
//			@RequestParam("file") MultipartFile file,
//			@RequestParam("author") String author,
//			@RequestParam("cid") Integer[] cidArray) 
//					throws SAXException, ParserConfigurationException, IOException, NoFilepatternException, GitAPIException, RuntimeException, NotBoundException, SQLException, ClassNotFoundException {
////		// 拡張子つき
//		String originFileName = new String(file.getOriginalFilename().getBytes("iso-8859-1"),"utf-8");
//		// 拡張子なし
//		String fileName = originFileName.substring(0, originFileName.lastIndexOf("."));
//		long size = file.getSize();
//		
//		// wikipediaは別処理
//		File wikiFile = null;
//		// TODO switch文に変更
//		if ( "wikipedia_ja".equals(fileName.toLowerCase()) ) {
//			logger.debug("wikipedia_ja");
//			wikiFile = new File("/var/lib/wix/admin/Wikipedia_ja.wix");
//		}
//		if ( "wikipedia_en".equals(fileName.toLowerCase()) ) {
//			logger.debug("wikipedia_en");
//			wikiFile = new File("/var/lib/wix/admin/Wikipedia_en.wix");
//		}
//		
//		List<Entry> entryList = new ArrayList<Entry>();
//		MetaInfo metaInfo = new MetaInfo();
//		Version version = new Version();
//		List<Tree> trees = new ArrayList<Tree>();
//		
//		metaInfo.setName(fileName);
//		metaInfo.setAuthor(author);
//		// 実体wix file typeとして登録
//		metaInfo.setType(0);
//		version.setVersion(1);
//		// get latest wid
//		Integer latestWid = metaInfoService.loadLatestWid();
//		
//		if ( latestWid == null ) latestWid = 0;
//		
//		wixFileParserHandler.setEntryList(entryList);
//		wixFileParserHandler.setMetaInfo(metaInfo);
//		wixFileParserHandler.setTrees(trees);
//		// latest_wid + 1をset
//		int wid = latestWid + 1;
//		wixFileParserHandler.setWid(wid);
//		
//		// SAX parse
//		SAXParserFactory factory = SAXParserFactory.newInstance();
//		factory.setValidating(true);
//		XMLReader reader = factory.newSAXParser().getXMLReader();
//		reader.setContentHandler(wixFileParserHandler);
//		reader.setErrorHandler(wixFileParserErrorHandler);
//		if ( wikiFile != null ) {
//			logger.info("sax parse wikipedia_en.wix has started.");
//			
//			reader.parse(new InputSource(new FileInputStream(wikiFile)));
//			
//			logger.info("sax parse wikipedia_en.wix has completed successfully.");
//		} else {
//			reader.parse(new InputSource(file.getInputStream()));
//		}
//		
//		// sort by wid ( for insert db and cononicalize)
//		// widが一致してる場合はkeywordでsort
//		logger.info("sort by wid has started.");
//		
//		Entry[] entryArray = (Entry[]) entryList.toArray(new Entry[0]);
//		Arrays.sort(entryArray, widComparator);
//		entryList.clear();
//		entryList.addAll(Arrays.asList(entryArray));
//		
//		logger.info("sort by wid has completed successfully.");
//		
//		
//		// DB
//		logger.info("db insert has started. :"
//			+ " num of entry = " + entryList.size()
//		);
//		
//		uploadService.setEntryList(entryList);
//		uploadService.setTrees(trees);
//		
//		uploadService.newWixFile(metaInfo);
//		
//		uploadService.insertCategoryWix(Arrays.asList(cidArray), wid);
//		
//		logger.info("db insert has completed successfully.");
//		
//		// Git
//		version.setWid(wid);
//		versionManager.newGitRepos(author);
//		
//		// transfer file to git repository.
//		if ( wikiFile == null ) {
//			file.transferTo(new File(Constant.WIX_FILE_MANAGEMENT_DIR.getValue() 
//					+ "/" + author + "/" + originFileName));
//		}
//		
//		// new canonicalized form.
//		logger.info("canonicalization has started.");
//		canonicalization.newCanonicalizedForm(author
//				, fileName + Constant.CANONICAL_FORM_EXTENSION.getValue()
//				, entryList);
//		logger.info("canonicalization has completed successfully.");
//		
//		//　version management
//		versionManager.add();
//		version.setRevisionNum(versionManager.commit("Fist commit of " + originFileName));
//		
//		uploadService.insertVersion(version);//ここ気に入らん
//		
//		//　create response
//		JsonModel response = new JsonModel();
//		response.setSuccess(true);
//		response.setMessage(Constant.UPLOAD_SUCCESS_MESSAGE.getValue());
//		response.setFileName(originFileName);
//		response.setVersion(1);
//		response.setSize(size);
//		response.setWid(wid);
//		
//		return response;
//	}
	
	@Override
	@RequestMapping(method = RequestMethod.POST, headers = { "Accept=application/json"})
	public @ResponseBody JsonModel upload(
			@RequestParam("file") MultipartFile file,
			@RequestParam("author") String author) 
					throws SAXException, ParserConfigurationException, IOException, NoFilepatternException, GitAPIException, RuntimeException, NotBoundException, SQLException, ClassNotFoundException {
//		// 拡張子つき
		String originFileName = new String(file.getOriginalFilename().getBytes("iso-8859-1"),"utf-8");
		// 拡張子なし
		String fileName = originFileName.substring(0, originFileName.lastIndexOf("."));
		long size = file.getSize();
		
		// wikipediaは別処理
		File wikiFile = null;
		// TODO switch文に変更
		if ( "wikipedia_ja".equals(fileName.toLowerCase()) ) {
			logger.debug("wikipedia_ja");
			wikiFile = new File("/var/lib/wix/admin/Wikipedia_ja.wix");
		}
		if ( "wikipedia_en".equals(fileName.toLowerCase()) ) {
			logger.debug("wikipedia_en");
			wikiFile = new File("/var/lib/wix/admin/Wikipedia_en.wix");
		}
		
		List<Entry> entryList = new ArrayList<Entry>();
		MetaInfo metaInfo = new MetaInfo();
		Version version = new Version();
		List<Tree> trees = new ArrayList<Tree>();
		
		metaInfo.setName(fileName);
		metaInfo.setAuthor(author);
		// 実体wix file typeとして登録
		metaInfo.setType(0);
		version.setVersion(1);
		// get latest wid
		Integer latestWid = metaInfoService.loadLatestWid();
		
		if ( latestWid == null ) latestWid = 0;
		
		wixFileParserHandler.setEntryList(entryList);
		wixFileParserHandler.setMetaInfo(metaInfo);
		wixFileParserHandler.setTrees(trees);
		// latest_wid + 1をset
		int wid = latestWid + 1;
		wixFileParserHandler.setWid(wid);
		
		// SAX parse
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		XMLReader reader = factory.newSAXParser().getXMLReader();
		reader.setContentHandler(wixFileParserHandler);
		reader.setErrorHandler(wixFileParserErrorHandler);
		if ( wikiFile != null ) {
			logger.info("sax parse wikipedia_en.wix has started.");
			
			reader.parse(new InputSource(new FileInputStream(wikiFile)));
			
			logger.info("sax parse wikipedia_en.wix has completed successfully.");
		} else {
			reader.parse(new InputSource(file.getInputStream()));
		}
		
		// sort by wid ( for insert db and cononicalize)
		// widが一致してる場合はkeywordでsort
		logger.info("sort by wid has started.");
		
		Entry[] entryArray = (Entry[]) entryList.toArray(new Entry[0]);
		Arrays.sort(entryArray, widComparator);
		entryList.clear();
		entryList.addAll(Arrays.asList(entryArray));
		
		logger.info("sort by wid has completed successfully.");
		
		
		// DB
		logger.info("db insert has started. :"
			+ " num of entry = " + entryList.size()
		);
		
		uploadService.setEntryList(entryList);
		uploadService.setTrees(trees);
		
		uploadService.newWixFile(metaInfo);
		
		logger.info("db insert has completed successfully.");
		
		// Git
		version.setWid(wid);
		versionManager.newGitRepos(author);
		
		// transfer file to git repository.
		if ( wikiFile == null ) {
			file.transferTo(new File(Constant.WIX_FILE_MANAGEMENT_DIR.getValue() 
					+ "/" + author + "/" + originFileName));
		}
		
		// new canonicalized form.
		logger.info("canonicalization has started.");
		canonicalization.newCanonicalizedForm(author
				, fileName + Constant.CANONICAL_FORM_EXTENSION.getValue()
				, entryList);
		logger.info("canonicalization has completed successfully.");
		
		//　version management
		versionManager.add();
		version.setRevisionNum(versionManager.commit("Fist commit of " + originFileName));
		
		uploadService.insertVersion(version);//ここ気に入らん
		
		//　create response
		JsonModel response = new JsonModel();
		response.setSuccess(true);
		response.setMessage(Constant.UPLOAD_SUCCESS_MESSAGE.getValue());
		response.setFileName(originFileName);
		response.setVersion(1);
		response.setSize(size);
		response.setWid(wid);
		
		return response;
	}
}
