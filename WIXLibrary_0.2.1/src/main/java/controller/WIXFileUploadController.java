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

public interface WIXFileUploadController {
	
	//TODO : authorをsessionから取得
//	JsonModel upload(MultipartFile file, String author, Integer[] cidArray) throws SAXException, ParserConfigurationException, IOException, NoFilepatternException, GitAPIException, RuntimeException, NotBoundException, SQLException, ClassNotFoundException;

	JsonModel upload(MultipartFile file, String author) throws SAXException, ParserConfigurationException, IOException, NoFilepatternException, GitAPIException, RuntimeException, NotBoundException, SQLException, ClassNotFoundException;
	
	JsonModel exceptionHandler(Exception e);
}
