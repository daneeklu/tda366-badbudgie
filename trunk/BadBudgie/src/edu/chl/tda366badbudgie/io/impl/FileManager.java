package edu.chl.tda366badbudgie.io.impl;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.chl.tda366badbudgie.io.IFileManager;
import edu.chl.tda366badbudgie.io.parsers.util.ParserException;


/** 
 * A file manager, implementing the IFileManager interface.
 * @author d.skalle
 *
 */
public class FileManager implements IFileManager{
	
	@Override
	public void loadData(String configPath) {
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		dbFactory.setIgnoringElementContentWhitespace(true);
		
		try {
			
			db = dbFactory.newDocumentBuilder();
		 	GameDataLoader gameParser = 
		 		new GameDataLoader(db.parse(configPath));
		 	gameParser.loadData();
		 	
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}
}
