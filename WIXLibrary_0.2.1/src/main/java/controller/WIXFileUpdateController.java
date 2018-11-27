package controller;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import data.JsonModel;

public interface WIXFileUpdateController {
	
	//TODO : authorをsessionから取得
	JsonModel update(int wid, int latestVersion, String author, MultipartFile file) throws IOException, SAXException, ParserConfigurationException, NoFilepatternException, GitAPIException, RuntimeException, NotBoundException, SQLException, ClassNotFoundException;

	JsonModel exceptionHandler(Exception e);
	
}
