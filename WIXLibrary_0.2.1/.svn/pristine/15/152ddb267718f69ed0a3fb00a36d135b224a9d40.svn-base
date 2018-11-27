package controller.impl;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.errors.CannotDeleteCurrentBranchException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NotMergedException;
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

import service.KeywordComparator;
import service.impl.CanonicalizationImpl;
import service.impl.EntryServiceImpl;
import service.impl.VersionManagerImpl;
import service.impl.WIXFileParserErrorHandlerImpl;
import service.impl.WIXFileParserHandlerImpl;
import service.impl.UploadServiceImpl;

import controller.WIXFileUpdateController;
import data.Constant;
import data.Entry;
import data.JsonModel;
import data.MetaInfo;
import data.Version;

@Controller
@RequestMapping(value = "/update")
public class WIXFileUpdateControllerImpl implements WIXFileUpdateController {
	
	@Autowired
	private UploadServiceImpl uploadService;
	
	@Autowired
	private WIXFileParserHandlerImpl wixFileParserHandler;
	
	@Autowired
	private WIXFileParserErrorHandlerImpl wixFileParserErrorHandler;
	
	@Autowired
	private VersionManagerImpl versionManager;
	
	@Autowired
	private KeywordComparator dataComparator;
	
	@Autowired
	private CanonicalizationImpl canonicalization;
	
	@Autowired
	private EntryServiceImpl entryService;
	
	private Logger logger;
	
	public WIXFileUpdateControllerImpl() {
		this.logger = Logger.getLogger(WIXFileUpdateControllerImpl.class);
	}
	
	@Override
	@RequestMapping(method = RequestMethod.POST, headers = { "Accept=application/json" })
	public @ResponseBody JsonModel update(
			@RequestParam("wid") int wid,
			@RequestParam("version") int latestVersion,
			@RequestParam("author") String author,
			@RequestParam("file") MultipartFile file) 
					throws IOException, SAXException, ParserConfigurationException, NoFilepatternException, GitAPIException, RuntimeException, NotBoundException, SQLException, ClassNotFoundException {
		// 拡張子つき
		String originFileName = file.getOriginalFilename();
		long size = file.getSize();
//		String contentType = file.getContentType();
//		byte[] fileContents = file.getBytes();
		// 拡張子なし
		String fileName = originFileName.substring(0, originFileName.lastIndexOf("."));
		
		List<Entry> entryList = new ArrayList<Entry>();
		MetaInfo metaInfo = new MetaInfo();
		
		metaInfo.setWid(wid);
		
		wixFileParserHandler.setMetaInfo(metaInfo);
		wixFileParserHandler.setEntryList(entryList);
		
		//SAX parse
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		XMLReader reader = factory.newSAXParser().getXMLReader();
		reader.setContentHandler(wixFileParserHandler);
		reader.setErrorHandler(wixFileParserErrorHandler);
		reader.parse(new InputSource(file.getInputStream()));
		
		// sort by keyword
		Entry[] entryArray = (Entry[]) entryList.toArray(new Entry[0]);
		Arrays.sort(entryArray, dataComparator);
		entryList.clear();
		entryList.addAll(Arrays.asList(entryArray));
		
		// append eid (TODO : 後でやり方考える)
		int eid = 1;
		for ( Entry entry : entryList ) {
			entry.setEid(eid);
			eid++;
		}
		
		//Git
		Version version = new Version();
		version.setWid(wid);
		version.setVersion(latestVersion + 1);
		versionManager.newGitRepos(author);
		
		//create-branch
		versionManager.createBranch(Constant.UPDATE_BRANCH.getValue());
		
		// new canonicalized form.
		canonicalization.newCanonicalizedForm(author
				, fileName + Constant.CANONICAL_FORM_EXTENSION.getValue()
				, entryList);
		
		// get difference.
		String diffStr = versionManager.getDifference(author, 
				fileName + Constant.CANONICAL_FORM_EXTENSION.getValue());
		if ( "".equals(diffStr) ) {
			throw new RuntimeException(Constant.NO_CHANGE_MASSAGE.getValue());
		}
		// transfer to git repository.
		file.transferTo(new File(Constant.WIX_FILE_MANAGEMENT_DIR.getValue()
				+ "/" + author + "/" + originFileName));
		
		versionManager.add();
		version.setRevisionNum(versionManager.commit("Update to version"
				+ version.getVersion() + ".0 of " 
				+ originFileName + " by " 
				+ author + "."));
		
		//DB
		uploadService.updateWixFile(diffStr, version);
		
		//rebase-branch (ホントはmergeしたい...)
		versionManager.rebase(Constant.UPDATE_BRANCH.getValue());
		
		//create response
		JsonModel response = new JsonModel();
		response.setSuccess(true);
		response.setMessage(Constant.UPDATE_SUCCESS_MESSAGE.getValue());
		response.setFileName(originFileName);
		response.setWid(wid);
		response.setVersion(version.getVersion());
		response.setSize(size);
		
		return response;
	}
	
	@Override
	@ExceptionHandler({ 
		SAXException.class, 
		ParserConfigurationException.class, 
		IOException.class, 
		NoFilepatternException.class, 
		GitAPIException.class,
		RuntimeException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody JsonModel exceptionHandler(Exception e) {
		//ここでtry, catchするのはおかしい. 
		//TODO : 後でやり方考える
		try {
			versionManager.deleteBranch(Constant.UPDATE_SUCCESS_MESSAGE.getValue());
		} catch (NotMergedException err) {
			err.printStackTrace();
		} catch (CannotDeleteCurrentBranchException err) {
			err.printStackTrace();
		} catch (GitAPIException err) {
			err.printStackTrace();
		}
		String message = e.getMessage();
		JsonModel response = new JsonModel();
		response.setSuccess(false);
		response.setMessage(message);
		logger.error(message);
		return response;
	}
	
}
